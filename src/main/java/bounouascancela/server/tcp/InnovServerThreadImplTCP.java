package bounouascancela.server.tcp;

import bounouascancela.server.tcp.InnovServerImplTCP;
import sharedobjects.*;

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
            ServerResponse serverResponse = new ServerResponse();
            serverResponse.writeResponse("Welcome on PNS Innov Server!");
            objectOutputStream.writeObject(serverResponse);
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.thread = new Thread(this);
        this.thread.start();
    }

    public void run() {
        Object received;
        System.out.println("Client n° : " + this.idClient + " connected");

        try {
            while ((received = objectInputStream.readObject()) instanceof Command) {
                ServerResponse serverResponse = new ServerResponse();
                //System.out.println("Received : " + received.getClass().getName() + " from client : " + this.idClient);
                if (received instanceof CommandAdd) {
                    innovServerImplTCP.addIdea(((CommandAdd) received).getIdea());
                    serverResponse.writeResponse("Project added");
                }else if (received instanceof CommandList) {
                    serverResponse.addIdeas(this.innovServerImplTCP.getIdeas());
                }else if (received instanceof CommandHelp) {
                    serverResponse.writeResponse("This server is accepting those commands objects : (add) CommandAdd, (list) CommandList, (help) CommandHelp, (quit)CommandQuit");
                }else if (received instanceof CommandQuit) {
                    serverResponse.writeResponse("See you later on our server ;)");
                    objectOutputStream.writeObject(serverResponse);
                    break;
                }
                objectOutputStream.writeObject(serverResponse);
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Connection ended");
        }

        finally {
            try {
                System.out.println("Client n° " + this.idClient + " disconnected");
                this.innovServerImplTCP.delClient(this.idClient);
                this.socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
