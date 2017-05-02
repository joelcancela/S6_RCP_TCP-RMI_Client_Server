package bounouascancela.protocol;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 * Class x
 *
 * @author Joël CANCELA VAZ
 * @author Nassim B.
 */
public class PNSClientImplTCP extends Thread implements PNSClient {

    Scanner ScanIn =  new Scanner(System.in);
    private int port;
    private ServerSocket serverSocket;
    private Socket socket;
    private ObjectInputStream bIn;             // Buffer entree en mode "bytes"
    private ObjectOutputStream bOut;            // Buffer sortie en mode "bytes"

    public static void main(String[] args) {
        System.out.println("IN CLIENT");
        PNSClientImplTCP main = new PNSClientImplTCP();
        while (true){
            System.out.printf("### >");
            String inputted = main.ScanIn.nextLine();
            System.out.println("You entered : " + inputted);
        }
    }
}
