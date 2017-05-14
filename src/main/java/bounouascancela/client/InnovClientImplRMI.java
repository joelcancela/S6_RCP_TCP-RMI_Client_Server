package bounouascancela.client;

import bounouascancela.server.InnovServerRMI;
import sharedobjects.*;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Scanner;

/**
 * {@code InnovClientImplRMI} is [desc]
 * <p>
 * [descSuite]
 *
 * @author Joël CANCELA VAZ
 */
public class InnovClientImplRMI extends InnovClient {

	private InnovServerRMI associatedServer;
	private Scanner scanner;
	private String input;
	private List<Idea> ideas;

	public InnovClientImplRMI(String ip) {
		super(ip, 0);
		scanner = new Scanner(System.in);
	}

	@Override
	protected void connect() {
		String url = "rmi://"+ip+"/PNSInnov";


		Remote remote = null;
		try {
			remote = Naming.lookup(url);
		} catch (NotBoundException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		associatedServer = (InnovServerRMI) remote;
		System.out.println("Connected");
		System.out.printf("###>");
	}

	@Override
	public void start() {
		System.out.println("Client RMI Lancé");
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
				toSend = this.parseInput();
				try {
					this.processResponse(associatedServer.receiveAndProcess(toSend));
				} catch (RemoteException e) {
					e.printStackTrace();
				}
				if (toSend instanceof CommandQuit) break;
				System.out.printf("###>");
			} catch (UnrecognizedCommandException e) {
				System.out.println("Sorry, this command is undefined");
				System.out.printf("###>");
			}
		}
	}

	protected void processResponse(ServerResponse response) {
			if (toSend instanceof CommandAdd | toSend instanceof CommandHelp | toSend instanceof CommandQuit) {
				System.out.println(response.readResponse());
			} else if (toSend instanceof CommandList) {
				this.ideas = response.getIdeas();
				StringBuilder stringBuilder = new StringBuilder();
				if (this.ideas.size() > 0) {
					for (int i = 0; i < this.ideas.size(); i++) {
						Idea idea = this.ideas.get(i);
						stringBuilder.append(i + " : " + idea.getName() + " by " + idea.getCreator().getName() + '\n');
					}
				} else {
					stringBuilder.append("No project available on the server.");
				}
				System.out.println(stringBuilder.toString());
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
