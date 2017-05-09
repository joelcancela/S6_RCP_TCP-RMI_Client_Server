package bounouascancela.client;

import sharedobjects.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
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
    private List<Idea> ideas;

    public void start() {

        System.out.println("Client lancé");
        connect();
        send();

    }

    public InnovClientImplTCP(String ip, int port) {
        super(ip, port);
    }

    protected void connect() {
        try {
            this.socket = new Socket(this.ip, this.port);
            System.out.println("Connecté à "+ip+":"+port);
            this.objectOutputStream = new ObjectOutputStream(this.socket.getOutputStream());
            this.objectInputStream = new ObjectInputStream(this.socket.getInputStream());
            try {//TODO Remove before transfer #MessageDaccueil
                System.out.println(objectInputStream.readObject());
            } catch (ClassNotFoundException e) {
            }
            System.out.printf("###> ");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected String getInput() {
        this.input = this.scanner.nextLine();
        return this.input;
    }

    protected void send() {
        while (this.getInput() != null) {

            try {
                toSend = this.parseInput();
                this.objectOutputStream.writeObject(toSend);
                this.processResponse();
                System.out.printf("###>");
            } catch (UnrecognizedCommandException e) {
                System.out.println("Sorry, this command is undefined");
                System.out.printf("###>");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    protected void processResponse() {
        try {
            ServerResponse receivedObject = (ServerResponse) this.objectInputStream.readObject();
            if (toSend instanceof CommandAdd | toSend instanceof CommandHelp | toSend instanceof CommandQuit) {
                System.out.println(receivedObject.readResponse());
            } else if (toSend instanceof CommandList) {
                this.ideas = receivedObject.getIdeas();
                StringBuilder stringBuilder = new StringBuilder();
                if (this.ideas.size() > 0) {
                    for (int i = 0; i < this.ideas.size(); i++) {
                        Idea idea = this.ideas.get(i);
                        stringBuilder.append(i + " : " + idea.getName() + " by " + idea.getCreator().getName() + '\n');
                    }
                } else {
                    stringBuilder.append("Aucun projet disponible sur le serveur");
                }
                System.out.println(stringBuilder.toString());
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    protected Command parseInput() throws UnrecognizedCommandException {
        String[] tokens = this.input.trim().split(" ");
        Command toSend;
        switch (tokens[0]) {

            case "add" :
                toSend = new CommandAdd(this.inputProject());
                break;

            case "help":
                toSend = new CommandHelp();
                break;

            case "list":
                toSend = new CommandList();
                break;

            case "quit":
                toSend = new CommandQuit();
                break;

            default:
                throw new UnrecognizedCommandException();
        }

        return toSend;
    }

    protected Idea inputProject() {
        System.out.printf("\tProject Name : ");
        String name = scanner.nextLine();
        System.out.printf("\tCreator Name : ");
        String creator = scanner.nextLine();
        System.out.printf("\tCreator Mail : ");
        String mail = scanner.nextLine();
        System.out.printf("\tProject Description : ");
        String description = scanner.nextLine();
        System.out.printf("\tTech (coma separated) : ");
        String[] techs = scanner.nextLine().split(",");

        return new Idea(name,new Student(creator, mail), description, techs);
    }
}
