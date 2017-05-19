package bounouascancela.client.rmi;

import bounouascancela.client.InnovClient;
import bounouascancela.client.UnrecognizedCommandException;
import bounouascancela.client.rmi.rmiobjects.Idea;
import bounouascancela.server.rmi.InnovServerRMI;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * {@code InnovClientImplRMI} is [desc]
 * <p>
 * [descSuite]
 *
 * @author Nassim Bounouas
 */
public class InnovClientImplRMI extends InnovClient {

	private static InnovServerRMI stub = null;
	private Scanner scanner;
	private String input;
	private List<Idea> ideas;
	private Registry registry;

	public InnovClientImplRMI() {
		super("localhost", 1099);
	}

	public InnovClientImplRMI(String ip) {
		super(ip, 1099);
	}

	protected void connect() {
		String url = "PNSInnov";

		try {
			// Fetch remote service
			this.registry = LocateRegistry.getRegistry(ip,port);
			stub = (InnovServerRMI) registry.lookup(url);
			System.out.println("[INFO] Connected @ " + url);
			System.out.printf("###>");
		} catch (Exception e) {
			System.out.println("[ERR] Remote connection failed... " + e.getMessage());
			System.exit(1);
		}
	}

	public void start() {
		System.out.println("Client RMI Lancé");
		this.scanner = new Scanner(System.in);
		this.ideas = new ArrayList<>();
		connect();
		send();
	}

	protected String getInput() {
		this.input = this.scanner.nextLine();
		return this.input;
	}

	protected void send() {
		while (this.getInput() != null) {
			try {
				this.parseInput();
				System.out.printf("###>");
			} catch (UnrecognizedCommandException e) {
				System.out.println("Sorry, this command is undefined");
				System.out.printf("###>");
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	}

		protected void parseInput() throws RemoteException {
		String[] tokens = this.input.trim().split(" ");
		switch (tokens[0]) {

			case "add" :
				String[] techs = {"C", "C++", "SQL"};
				System.out.println(InnovClientImplRMI.stub.addIdea("Joe", "joe@dalton.com", "Project Name", "Project Description", techs ));
				break;

			case "help":
				System.out.println(InnovClientImplRMI.stub.help());
				break;

			case "list":
				System.out.println(InnovClientImplRMI.stub.list());
				break;

			case "quit":
				System.out.println(InnovClientImplRMI.stub.quit());
				break;

			case "get":
				String ref = InnovClientImplRMI.stub.getIdea(Integer.parseInt(tokens[1]));
				try {
					Idea idea = (Idea) this.registry.lookup(ref);
					System.out.println(idea.getName());
				} catch (NotBoundException e) {
					e.printStackTrace();
				}
				break;

			case "set":
				InnovClientImplRMI.stub.setIdeaName(Integer.parseInt(tokens[1]), "DALTON PROJECT");
				break;

			default:
				throw new UnrecognizedCommandException();
		}
	}

	public static void main(String[] args) {
		InnovClientImplRMI innovClientImplRMI = new InnovClientImplRMI();
		innovClientImplRMI.start();
	}
	/*
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

		return new Idea(name,new StudentImpl(creator, mail), description, techs);
	}*/
}
