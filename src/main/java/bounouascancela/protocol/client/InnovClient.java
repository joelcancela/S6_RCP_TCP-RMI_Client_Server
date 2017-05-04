package bounouascancela.protocol.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by Nassim B on 04/05/17.
 */
public class InnovClient {
    private String ip = "localhost";
    private int port = 8080;
    private Socket socket;                         // Socket de connexion au client
    private ObjectOutputStream objectOutputStream; // Flux de sortie vers le client
    private ObjectInputStream objectInputStream;   // Flux d'entrée depuis le client
    private Object toSend;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Client lancé");
        InnovClient innovClient = new InnovClient();

        try {
            innovClient.socket = new Socket(innovClient.ip, innovClient.port);
            innovClient.objectOutputStream = new ObjectOutputStream(innovClient.socket.getOutputStream());
            innovClient.objectInputStream  = new ObjectInputStream(innovClient.socket.getInputStream());
            System.out.printf("###>");
        } catch (IOException e) {
            e.printStackTrace();
        }

        while((innovClient.toSend = scanner.nextLine()) != null){
            try {
                innovClient.objectOutputStream.writeObject(innovClient.toSend);
                System.out.printf("###>");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
