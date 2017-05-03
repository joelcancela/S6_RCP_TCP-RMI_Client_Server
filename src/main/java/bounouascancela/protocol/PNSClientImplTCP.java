package bounouascancela.protocol;

import com.sun.corba.se.impl.orbutil.ObjectUtility;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

/**
 * Class x
 *
 * @author Joël CANCELA VAZ
 * @author Nassim B.
 */
public class PNSClientImplTCP extends Thread implements PNSClient {
    Scanner scanner = new Scanner(System.in);
    private String ip;
    private int port;
    private Socket socket;
    private ObjectInputStream bIn;             // Buffer entree en mode "bytes"
    private ObjectOutputStream bOut;            // Buffer sortie en mode "bytes"
    private boolean endOfCommunication = false;

    public PNSClientImplTCP(String ip, String port) {
        this.ip = ip;
        this.port = Integer.parseInt(port);
    }

    public void run() {
        System.out.println("CLIENT");
        while (!endOfCommunication) {
            while (askConnection()) {
                writeMessages();
                return;
            }
        }
    }

    private void writeMessages() {
        String message;
        DataOutputStream outToServer = null;
        try {
            outToServer = new DataOutputStream(socket.getOutputStream());
            outToServer.flush();
            System.out.printf("###>");
        } catch (IOException e) {
            e.printStackTrace();
        }

        while(!(message = scanner.nextLine()).equals("quit")){
            try {
                System.out.println("WRITTEN "+message);
                outToServer.writeBytes(message+ "\r\n");
                System.out.printf("###>");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        endOfCommunication=true;
    }

    private boolean askConnection() {
        try {
            socket = new Socket(ip, this.port);
        } catch (IOException e) {
            return false;
        }
        return true;
    }
}
