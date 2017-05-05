package bounouascancela.client;

import sharedobjects.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by Nassim B on 04/05/17.
 */
public class InnovClientImplTCP extends InnovClient {
    private Socket socket; // Socket de connexion au client
    private ObjectOutputStream objectOutputStream; // Flux de sortie vers le client
    private ObjectInputStream objectInputStream;   // Flux d'entrée depuis le client
    private Scanner scanner = new Scanner(System.in);
    private String input;

    public static void main(String[] args) {

        System.out.println("Client lancé");

        InnovClientImplTCP innovClient = new InnovClientImplTCP();
        innovClient.connect();
        innovClient.send();

    }

    public InnovClientImplTCP(){
        this("localhost", 8080);
    }

    public InnovClientImplTCP(String ip, int port){
        super(ip, port);
    }

    protected void connect() {
        try {
            this.socket = new Socket(this.ip, this.port);
            this.objectOutputStream = new ObjectOutputStream(this.socket.getOutputStream());
            this.objectInputStream  = new ObjectInputStream(this.socket.getInputStream());
            System.out.printf("###>");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected String getInput() {
        this.input = this.scanner.nextLine();
        return this.input;
    }

    protected void send() {
        while(this.getInput() != null){

            try {
                Object toSend = this.parseInput();
                this.objectOutputStream.writeObject(toSend);
                System.out.printf("###>");
            } catch (UnrecognizedCommandException e){
                System.out.println("Sorry, this command is undefined");
                System.out.printf("###>");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    protected Command parseInput() throws UnrecognizedCommandException {
        String[] tokens = this.input.trim().split(" ");
        Command toSend;
        switch (tokens[0]) {
            case "add" :
                toSend = new CommandAdd(null);
                break;

            case "help" :
                toSend = new CommandHelp();
                break;

            case "list" :
                toSend = new CommandList();
                break;

            case "quit" :
                toSend = new CommandQuit();
                break;

            default :
                throw new UnrecognizedCommandException();
        }

        return toSend;
    }
}
