//
// IUT de Nice / Departement informatique / Module M412
// Annee 2014_2015 - Communications reseau sous TCP/IP
//
// Classe ServeurTCP - Echanges parametrables de messages avec des 
//                     clients IP au moyen d'une socket unique dediee
//
// Edition A       : echanges bidirectionnels de messages en mode "bytes"  
//
//    + Version 1.0.0   : echanges par serialisation d'objets dans une
//                        socket sous jacente
//
// Auteur : A. Thuaire
//

import java.util.*;
import java.io.*;
import java.net.*;
      
public class ServeurTCP extends Thread {
private int                portReception;
private Socket             socketSupport;	
private ObjectInputStream  bIn;             // Buffer entree en mode "bytes"
private ObjectOutputStream bOut;            // Buffer sortie en mode "bytes"
private LinkedList         listeMessages;   // Liste courante messages recus
private boolean            statusReception; // Statut autorisation recevoir
  
// ---                                         Premier constructeur normal  
   
   public ServeurTCP (String nomThread) {
      
      super(nomThread);
      
      portReception  = 8080;
      listeMessages  = new LinkedList();
      statusReception= false;
      
      start();
   }
   
// ---                                         Second constructeur normal  
   
   public ServeurTCP (String nomThread, int port) {
   	
   	 super(nomThread);
   	 
   	 portReception  = port;
   	 listeMessages  = new LinkedList();
   	 statusReception= false;
   	 
   	 start();
   }

// ---                                                         Accesseurs  
   
   public Socket     obtenirSocket  ()         {return socketSupport;}
   public LinkedList obtenirMessages()         {return listeMessages;}
   public boolean    obtenirStatusReception () {return statusReception;}
    
   public HashMap retirerMessage ()  {
   HashMap msg= null;
   	
      if (listeMessages.size() == 0) return null;
   	  
      // Executer une operation atomique pour obtenir le premier
      // message courant recu et le retirer de la liste
      //
      synchronized (listeMessages) {
   	  	
   	 msg= (HashMap)listeMessages.getFirst();
   	  
         listeMessages.removeFirst();
      }
      
      // Restituer le resultat
      //
      return msg;
   }
   
   public void debuterReception() {statusReception= true; }
   public void stopperReception() {statusReception= false;} 
   
// ---                                                        Methode main  

   public static void main(String[] args) {
   	
      // Creer et demarrer un serveur IP
      //
      ServeurTCP serveurTCP= new ServeurTCP("Serveur_1");
      System.out.print("* Creation et demarrage du serveur TCP/IP ");
      System.out.println("(V 1.0.0)\n");
   	  
      // Debuter la reception des messages
      //
      serveurTCP.debuterReception();
      
      // Attendre les messages en provenance du client unique
      //
      HashMap msg= null;
      
      while (serveurTCP.obtenirStatusReception()) {
      	
      	 // Attendre la reception d'un nouveau message
      	 //
      	 while (serveurTCP.obtenirMessages().size() == 0) 
      	    Chrono.attendre(100);
      	 
      	 // Retirer le message courant de la liste de reception
      	 //
      	 msg= serveurTCP.retirerMessage();
      	 
      	 // Visualiser la commande recue
         // 
         System.out.println("<-- Commande recue : " + 
                                 (String)msg.get("commande"));
      }
      
      // Stopper la reception des messages
      //
      serveurTCP.stopperReception();
      
      // Fermer les flux d'echange avec le client unique
      //
      serveurTCP.fermer();		
   }  
   
// ---                                                         Methode run  

   public void run() {
   
      // Etablir la connexion avec le serveur cible
      //
      accepter();
      
      // Attendre succession messages du protocole de niveau superieur
      //
      while (statusReception) {
      	
         // Attendre le prochain message (appel bloquant)
      	 //
      	 attendreMessage();
      }	
   }
   
// ---                                                    Methode accepter  

   private boolean accepter() {
   
      // Creer la socket serveur
      //
      ServerSocket serveur;
      try {serveur= new ServerSocket(portReception);}
      catch (Exception e){return false;}
      
      // Attendre la connexion du client
      //
      try{socketSupport= serveur.accept();}
      catch (Exception e){return false;}
      
      return initFlux(socketSupport);
   }
   
// ---                                                   Methode initFlux  

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
      // ATTENTION : le constructeur est bloquant jusqu'a reception
      //             du premier objet (message)
      //
      try{bIn= new ObjectInputStream(streamIn);}
      catch (Exception e) {return false;}
      if (bIn == null) return false;
      
      return true;
   }
   
// ---                                            Methode attendreMessage  

   public void attendreMessage () {
   Object msg=null;

      // ATTENTION : la methode readObject leve plusieurs types (classes)
      //             d'exceptions suivant la nature du probleme rencontre
      //
   
      while (true) {
         try {
            msg= bIn.readObject();
      	    if (msg != null) break;
      	 }
      	 
      	 // Traiter le cas ou l'autre extremite de la socket disparait
      	 // sans coordination prealable au niveau applicatif (OSI - 7).
      	 //
      	 // Ce cas se produit quand l'objet "socket" distant est detruit
      	 // (mort du thread distant par exemple)
      	 //                     
      	 catch (SocketException e){}
      	 
      	 // Traiter le cas ou l'autre extremite ferme la socket sans 
      	 // coordination prealable au niveau applicatif (OSI - 7)
      	 //
      	 catch (EOFException e){}
      	 
      	 // Traiter le cas d'autres exceptions relatives aux IO
      	 //
      	 catch (IOException e){}
      	 
      	 // Traiter les autres cas d'exceptions
      	 //
      	 catch (Exception e){}
      	 
      	 // Temporiser pour attendre le message suivant
      	 
      	 Chrono.attendre(100);
      }
      
      // Enregistrer le message courant dans la liste des messages
      //
      listeMessages.add(msg);
   }
   
// ------                                                 Methode fermer  

   public void fermer () {
   	
   	  try { 
   	     bIn.close();
   	     bOut.close();
   	     socketSupport.close();
   	  }
   	  catch(Exception e){}
   }
   
// -----------------------------------      Classe interne privee Chrono
   
   private static class Chrono {

      private static void attendre (int tms) {
         	
         // Attendre tms millisecondes, en bloquant le thread courant 
         //
         try {Thread.currentThread().sleep(tms);} 
         catch(InterruptedException e){ }
      }
   }
}
