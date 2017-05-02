package bounouascancela.protocol;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Class x
 *
 * @author Joël CANCELA VAZ
 */
public class PNSServerImplTCP extends Thread implements PNSServer {
    private int port;
    private ServerSocket serverSocket;
    private Socket socket;
    private ObjectInputStream bIn;
    private ObjectOutputStream bOut;
    private boolean endOfCommunication = false;

    public PNSServerImplTCP() {
        this.port = 8080;
    }

    public void run() {
        while (!endOfCommunication) {
            System.out.println("SERVER");
            while (!acceptConnection()) {
                parseMessages();
                return;
            }
        }
    }

    private void parseMessages() {
        boolean stopParsing = false;
//        Object msg = null;
//        while (!stopParsing) {
//            try {
//                msg = bIn.readObject();
//                if (msg != null) break;
//            } catch (IOException e) {
//                e.printStackTrace();
//            } catch (ClassNotFoundException e) {
//                e.printStackTrace();
//            }
//            stopParsing=true;
//        }

        BufferedReader inFromClient = null;
        try {
            inFromClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (!stopParsing) {
            try {
                String message = inFromClient.readLine();
                if (message.equals("quit")) {
                    stopParsing = true;
                }
                System.out.println(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public boolean acceptConnection() {

        if (serverSocket == null) {
            try {
                serverSocket = new ServerSocket(port);
            } catch (Exception e) {
                return false;
            }
        }

        try {
            socket = serverSocket.accept();
            System.out.println("Connection accepted");
            //TODO send command list
        } catch (Exception e) {
            return false;
        }
        return initFlux(socket);
    }

    private boolean initFlux(Socket s) {


        if (s == null) return false;

        OutputStream streamOut = null;
        try {
            streamOut = s.getOutputStream();
        } catch (Exception e) {
            return false;
        }
        if (streamOut == null) return false;


        try {
            bOut = new ObjectOutputStream(streamOut);
        } catch (Exception e) {
            return false;
        }
        if (bOut == null) return false;


        InputStream streamIn = null;
        try {
            streamIn = s.getInputStream();
        } catch (Exception e) {
            return false;
        }
        if (streamIn == null) return false;

        try {
            bIn = new ObjectInputStream(streamIn);
        } catch (Exception e) {
            return false;
        }
        if (bIn == null) return false;

        return true;
    }

    public void closeConnection() {

        try {
            bIn.close();
            bOut.close();
            socket.close();
            serverSocket.close();
            endOfCommunication = true;
        } catch (Exception e) {
        }
    }


    public void addIdea() {

    }

    public void listIdeas() {

    }

    public void help() {

    }
}
