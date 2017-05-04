package bounouascancela.protocol.server;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nassim B on 04/05/17.
 */
public class InnovServer {

    private String ip;
    private int port;

    private List<ObjectOutputStream> clients = new ArrayList();

    public InnovServer(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    public static void main(String[] args) {
        InnovServer innovServer =  new InnovServer("localhost", 8080);

        try {
            //new Commandes(innovServer);
            ServerSocket serverSocket = new ServerSocket(innovServer.port);
            System.out.println("Serveur créé");
            while (true) {
                new InnovServerThread(serverSocket.accept(), innovServer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    synchronized public int addClient(ObjectOutputStream objectOutputStream) {
        this.clients.add(objectOutputStream);
        return this.clients.size() - 1;
    }

    synchronized public void delClient(int idClient) {
        this.clients.remove(idClient);
    }
}
