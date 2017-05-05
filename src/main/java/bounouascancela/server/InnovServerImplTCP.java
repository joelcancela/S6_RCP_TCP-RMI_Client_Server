package bounouascancela.server;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nassim B on 04/05/17.
 */
public class InnovServerImplTCP extends InnovServer {

    private int port;

    private List<ObjectOutputStream> clients = new ArrayList();//TODO Index Shift problem lambda

    public InnovServerImplTCP(int port) {
        this.port = port;
    }

    public void start() {

        try {
            //new Commandes(innovServerImplTCP);
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Serveur créé");
            while (true) {
                new InnovServerThreadImplTCP(serverSocket.accept(), this);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized int addClient(Object stream) {
        ObjectOutputStream objectOutputStream = (ObjectOutputStream) stream;
        this.clients.add(objectOutputStream);
        return this.clients.size() - 1;
    }

    public synchronized void delClient(int idClient) {
        this.clients.remove(idClient);
    }
}
