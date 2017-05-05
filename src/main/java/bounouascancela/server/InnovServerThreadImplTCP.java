package bounouascancela.server;

import sharedobjects.Command;
import sharedobjects.CommandQuit;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Created by Nassim B on 04/05/17.
 */
public class InnovServerThreadImplTCP implements Runnable {
    private Thread thread;                         // Thread du client
    private Socket socket;                         // Socket de connexion au client
    private ObjectOutputStream objectOutputStream; // Flux de sortie vers le client
    private ObjectInputStream objectInputStream;   // Flux d'entrée depuis le client
    private InnovServerImplTCP innovServerImplTCP;               // Accès aux méthodes du serveur
    private int idClient;                          // Identifiant du client

    InnovServerThreadImplTCP(Socket socket, InnovServerImplTCP innovServerImplTCP) {
        this.socket = socket;
        this.innovServerImplTCP = innovServerImplTCP;

        try {
            //Emission
            this.objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            //Réception
            this.objectInputStream  = new ObjectInputStream(socket.getInputStream());
            //Attribution d'un ID
            this.idClient = innovServerImplTCP.addClient(this.objectOutputStream);
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
            while ((received = objectInputStream.readObject()) instanceof Command) {
                System.out.println("Received : " + received);
                if (received instanceof CommandQuit) {
                    break;
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        finally {
            try {
                System.out.println("Client n° " + this.idClient + " déconnecté");
                this.innovServerImplTCP.delClient(this.idClient);
                this.socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
