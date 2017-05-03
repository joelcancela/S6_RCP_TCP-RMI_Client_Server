package bounouascancela.protocol;

import bounouascancela.sharedobjects.CommandQuit;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Class PNSServerImplTCP
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
//        while (!endOfCommunication) {
//            System.out.println("SERVER");
//            while (!acceptConnection()) {
//                parseMessages();
//                return;
//            }
//        }
        /*try {
            serverSocket = new ServerSocket(port);
            System.out.println("Lancement du serveur");

            while(true){
                socket = serverSocket.accept();
                if(socket!=null){
                    System.out.println("Connexion avec : "+socket.getInetAddress());
                    break;
                }
            }*/

        acceptConnection();

            while (true) {

                String message = "";

                // InputStream in = socketClient.getInputStream();
                // OutputStream out = socketClient.getOutputStream();

                //BufferedReader in = new BufferedReader(
                //        new InputStreamReader(socket.getInputStream()));
//                PrintStream out = new PrintStream(socketClient.getOutputStream());
                //message = in.readLine();
                //System.out.println(message);
                System.out.println("BEFORE parseMessages");
                parseMessages();
                System.out.println("AFTER parseMessages");
                break;
                /*if(message.equals("quit")){
                    closeConnection();
                }*/
            }
        /*} catch (Exception e) {
            e.printStackTrace();
        }*/
    }

    private void parseMessages() {
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

        //DataInputStream inFromClient = null;
        Object message;
        try {
            //inFromClient = new DataInputStream(socket.getInputStream());
            while((message = bIn.readObject())!= null){
                System.out.println("Received : " + message.getClass().toString());
                if (message instanceof CommandQuit) {
                    System.out.println("SOCKET TO CLOSE");
                    bIn.close();
                    break;
                }
            }
//            while(!(message = ).equals("quit")){
//                System.out.println(message);
//            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
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
            socket.setTcpNoDelay(true);
            System.out.println("Connection accepted");
        } catch (Exception e) {
            return false;
        }
        return initFlux(socket);
    }

    private boolean initFlux(Socket s) {

        System.out.println("in initFlux");
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
            System.out.println("bIn created");
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
