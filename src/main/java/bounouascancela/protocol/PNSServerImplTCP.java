package bounouascancela.protocol;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
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
    private ObjectInputStream bIn;             // Buffer entree en mode "bytes"
    private ObjectOutputStream bOut;            // Buffer sortie en mode "bytes"

    public PNSServerImplTCP(int port) {
        this.port = port;
        start();
    }

    public PNSServerImplTCP() {
        this.port = 8080;
        start();
    }

    public void run() {

    }

    public boolean acceptConnection() {
        try {
            serverSocket = new ServerSocket(port);
        } catch (Exception e) {
            return false;
        }

        try {
            socket = serverSocket.accept();
        } catch (Exception e) {
            return false;
        }
        return initFlux(socket);
    }

    private boolean initFlux(Socket s) {

        // Controler l'existence de la socket support
        //
        if (s==null) return false;

        // Creer le flux de sortie
        //
        OutputStream streamOut= null;
        try{streamOut= s.getOutputStream();}
        catch(Exception e){return false;}
        if (streamOut == null) return false;

        // Creer le buffer de sortie
        //
        try{bOut= new ObjectOutputStream(streamOut);}
        catch (Exception e) {return false;}
        if (bOut == null) return false;

        // Creer le flux d'entree
        //
        InputStream streamIn= null;
        try{streamIn= s.getInputStream();}
        catch(Exception e){return false;}
        if (streamIn == null) return false;

        // Creer le buffer d'entree
        //
        // ATTENTION : le constructeur est bloquant jusqu'a reception
        //             du premier objet (message)
        //
        try{bIn= new ObjectInputStream(streamIn);}
        catch (Exception e) {return false;}
        if (bIn == null) return false;

        return true;
    }

    public void closeConnection() {

        try {
            bIn.close();
            bOut.close();
            socket.close();
            serverSocket.close();
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
