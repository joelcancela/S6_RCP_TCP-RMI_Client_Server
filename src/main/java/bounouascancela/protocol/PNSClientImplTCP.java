package bounouascancela.protocol;

import bounouascancela.sharedobjects.*;
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

    public void parseMessages(String message) throws IOException {
        String[] tokens = message.split(" ");
        Command commandToSend;
        switch (tokens[0]) {
            case "add" :
                String[] techs = {"C", "JAVA"};
                commandToSend = new CommandAdd(new Idea("Doe", new Student("Creator", "Creator@mail.com"), "Desc", techs));
                System.out.println("CREATE ADD COMMAND");
                break ;
            case "help" :
                commandToSend = new CommandHelp();
                System.out.println("CREATE HELP COMMAND");
                break ;
            case "list" :
                commandToSend = new CommandList();
                System.out.println("CREATE LIST COMMAND");
                break ;
            case "quit" :
                commandToSend = new CommandQuit();
                System.out.println("CREATE QUIT COMMAND");
                break ;
            default :
                commandToSend = new CommandAbort();
                System.out.println("COMMAND NOT RECOGNIZED");
                break;
        }
        //SEND THE COMMAND
        bOut.writeObject(commandToSend);
    }

    private void writeMessages() {
        String message;
        DataOutputStream outToServer = null;
        try {
            //outToServer = new DataOutputStream(socket.getOutputStream());
            bOut = new ObjectOutputStream(socket.getOutputStream());
            //outToServer.flush();
            bOut.flush();
            System.out.printf("###>");
        } catch (IOException e) {
            e.printStackTrace();
        }

        while((message = scanner.nextLine()) != null){
            try {
                parseMessages(message);
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
