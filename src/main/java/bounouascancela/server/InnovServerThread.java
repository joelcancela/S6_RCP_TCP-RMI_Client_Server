package bounouascancela.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Created by Nassim B on 04/05/17.
 */
public class InnovServerThread implements Runnable {
    private Thread thread;                         // Thread du client
    private Socket socket;                         // Socket de connexion au client
    private ObjectOutputStream objectOutputStream; // Flux de sortie vers le client
    private ObjectInputStream objectInputStream;   // Flux d'entrée depuis le client
    private InnovServer innovServer;               // Accès aux méthodes du serveur
    private int idClient;                          // Identifiant du client

    InnovServerThread(Socket socket, InnovServer innovServer) {
        this.socket = socket;
        this.innovServer = innovServer;

        try {
            //Emission
            this.objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            //Réception
            this.objectInputStream  = new ObjectInputStream(socket.getInputStream());
            //Attribution d'un ID
            this.idClient = innovServer.addClient(this.objectOutputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.thread = new Thread(this);
        this.thread.start();
    }

    public void run() {
        Object received;
        System.out.println("Connexion du client n° : " + this.idClient);

        try {
            while ((received = objectInputStream.readObject()) instanceof  String && !((String)received).equals("quit")) {
                System.out.println("Received : " + received);
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        finally {
            try {
                System.out.println("Client n° " + this.idClient + " déconnecté");
                this.innovServer.delClient(this.idClient);
                this.socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
