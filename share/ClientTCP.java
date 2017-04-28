//
// IUT de Nice / Departement informatique / Module M412
// Annee 2014_2015 - Communications reseaux sous TCP/IP
//
// Classe ClientTCP - Echanges parametrables de messages avec un serveur 
//                    TCP/IP au moyen d'une socket unique dediee
//
// Edition A       : echanges bidirectionnels de messages en mode "bytes" 
//
//    + Version 1.0.0   : serialisation d'objets dans une socket sous jacente
//             
// Auteur : A. Thuaire
//

import java.util.*;
import java.io.*;
import java.net.*;
      
public class ClientTCP extends Thread {
private String             nomServeur;
private int                portServeur;
private Socket             socketSupport;	
private ObjectInputStream  bIn;            // Buffer entree en mode "bytes"
private ObjectOutputStream bOut;           // Buffer sortie en mode "bytes"
private LinkedList         listeMessages;  // Liste des messages a envoyer
private boolean            statusEmission; // Status autorisationemettre
  
// ---                                         Premier constructeur normal  
   
   public ClientTCP (String nomThread) {
      
      super(nomThread);
      nomServeur    = "localHost"; 
      portServeur   = 8080;
      listeMessages = new LinkedList();
      statusEmission= false;
      
      start();
   }
   
// ---                                          Second constructeur normal  
   
   public ClientTCP (String nomThread, String host, int port) {
   	
   	 super(nomThread);
   	 nomServeur    = host; 
   	 portServeur   = port;
   	 listeMessages = new LinkedList();
   	 statusEmission= false;
   	 
   	 start();
   }

// ---                                                          Accesseurs  
   
   public Socket     obtenirSocket()             {return socketSupport;}
   public void       ajouterMessage(HashMap msg) {listeMessages.add(msg);}
   public LinkedList obtenirMessages()           {return listeMessages;}
   public String     obtenirMessage() {
   	
      if (listeMessages.size() == 0) return null;
   	  return (String)listeMessages.getFirst();
   }
   public void debuterEmission() {statusEmission= true; }
   public void stopperEmission() {statusEmission= false;} 
   
// ---                                                        Methode main  

   public static void main(String[] args) {
   	
      // Creer et demarrer un client TCP
      //
      ClientTCP clientTCP= new ClientTCP("Client_1");
      System.out.print("* Creation et demarrage du client TCP/IP ");
      System.out.println("V 1.0.0\n");

      // Attendre la mise en service du serveur
      //
      while (clientTCP.obtenirSocket() == null) Chrono.attendre(200);
      System.out.println("* Client connecte");
      System.out.println();
   	  
      // Obtenir la socket support
      //
      Socket socketSupport= clientTCP.obtenirSocket();
       	
      // Construire un premier message a envoyer au serveur
      //
      HashMap msg_1= new HashMap();
      
      msg_1.put("commande",  "DECLARER");
      msg_1.put("adresseIP", socketSupport.getLocalAddress().getHostAddress());
      
      // Ajouter le message courant a la liste des messages a envoyer
      //
      clientTCP.ajouterMessage(msg_1);
      
      // Calculer la taille courante de la liste
      //
      int tailleCourante= clientTCP.obtenirMessages().size();
   
      // Visualiser le message transmis
      //
      System.out.println("--> Commande envoyee  : " + "DECLARER");
      System.out.println("    Rang du message   : " + tailleCourante);
      System.out.println();
      
      // Debuter l'emission des messages
      //
      clientTCP.debuterEmission();
      Chrono.attendre(1000);
      
      // Construire un second message a envoyer au serveur
      //
      HashMap msg_2= new HashMap();
      
      msg_2.put("commande",  "CONTINUER");
      
      // Ajouter le message courant a la liste des messages a envoyer
      //
      clientTCP.ajouterMessage(msg_2);
      
      // Attendre envoi effectif du message msg_2 par le second thread
      //
      Chrono.attendre(100);
   
      // Visualiser le message transmis
      //
      System.out.println("--> Commande envoyee  : " + "CONTINUER");
      System.out.println("    Rang du message   : 2");
      System.out.println();
      
      // Stopper l'emission des messages
      //
      clientTCP.stopperEmission();
      Chrono.attendre(1000);
      
      // Fermer les flux d'echange avec le serveur
      //
      clientTCP.fermer();
      
      System.out.println("* Client deconnecte");
      System.out.println();	
   }  
   
// ---                                                         Methode run  

   public void run() {
   
      // Etablir la connexion avec le serveur cible
      //
      connecter(); 
      
      // Attendre l'autorisation d'emettre
      //
      while (!statusEmission) Chrono.attendre(200);
       	
      // Parcourir la liste courante des messages
      //
      HashMap msg= null;
      
      while (statusEmission) {
      	
      	 if (listeMessages.size() != 0) {
      	
      	    // Executer une operation atomique d'envoi d'un message 
      	    //
      	    synchronized (listeMessages) {
      	
      	       // Extraire le message suivant
      	       //
      	       msg= (HashMap)listeMessages.getFirst();
      
               // Envoyer le message courant
               //
               statusEmission= envoyerMessage(msg);
            
               // Retirer ce message de la liste
      	       //
      	       listeMessages.removeFirst();
      	    }
      	 }
      	    
         else {
      	  	
      	  	// Temporiser pour attendre le message suivant
      	    //
      	  	Chrono.attendre(100);
      	 }
      }	
   }
   
// ---                                                  Methode connecter  

   private boolean connecter() {
   	
      // Creer une connexion avec le serveur cible
      //
      while (true) {
   	  	
   	 // Creer la socket support
   	 //
   	 try{socketSupport= new Socket(nomServeur, portServeur);}
   	 catch (Exception e){}
   	     
   	 // Controler la validite de cette socket
   	 //
   	 if (socketSupport != null) break;
      }
 
      // Initialiser les flux entrant et sortant de la connexion
      //
      return initFlux(socketSupport);
   }
   
// ---                                                  Methode initFlux  

   private boolean initFlux(Socket s) {
   
      // Controler l'existence de la socket support
      //
      if (s==null) return false;
   
      // Creer le flux de sortie
      //
      OutputStream streamOut= null;
      try{streamOut= s.getOutputStream();}
      catch(Exception e){return false;}
      if (streamOut == null) return false;
      
      // Creer le buffer de sortie
      //
      try{bOut= new ObjectOutputStream(streamOut);}
      catch (Exception e) {return false;}
      if (bOut == null) return false;
      
      // Creer le flux d'entree
      //
      InputStream streamIn= null;
      try{streamIn= s.getInputStream();}
      catch(Exception e){return false;}
      if (streamIn == null) return false;

      // Creer le buffer d'entree
      //
      // ATTENTION : le constructeur est bloquant jusqu'a la reception du
      //             premier objet (message)
      //
      try{bIn= new ObjectInputStream(streamIn);}
      catch (Exception e) {return false;}
      if (bIn == null) return false;
      
      return true;
   }
   
// ---                                              Methode envoyerMessage  

   public boolean envoyerMessage (Object msg) {
   	
      // Controler la validite du flux de sortie
      //
      if (bOut == null) return false;
      
      // Transferer le message dans le flux de sortie
      //
      try {bOut.writeObject(msg);}
   	  catch(Exception e){return false;}
   	  
   	  return true;
   }
   
// ---                                                      Methode fermer  

   public void fermer () {
   	
   	  try { 
   	     bIn.close();
   	     bOut.close();
   	     socketSupport.close();
   	  }
   	  catch(Exception e){}
   }
   
// --------------------------------           Classe interne privee Chrono
   
   private static class Chrono {

      private static void attendre (int tms) {
         	
         // Attendre tms millisecondes, en bloquant le thread courant 
         //
         try {Thread.currentThread().sleep(tms);} 
         catch(InterruptedException e){ }
      }
   }
}
