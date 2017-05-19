package bounouascancela.server;

import sharedobjects.*;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

/**
 * {@code InnovServerImplRMI} is [desc]
 * <p>
 * [descSuite]
 *
 * @author Joël CANCELA VAZ
 */
public class InnovServerImplRMI extends UnicastRemoteObject implements InnovServerRMI {

	private List<Idea> ideas = new ArrayList<>();

	public InnovServerImplRMI() throws RemoteException {
		super();
	}

	public void start() {

		try {
			Registry r = LocateRegistry.createRegistry(1099);
			r.rebind("PNSInnov", new InnovServerImplRMI());
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Problem...");
		}
		System.out.println("RMI Registry OK");
	}


	public ServerResponse receiveAndProcess(Command toSend) {
		ServerResponse serverResponse = new ServerResponse();
		if (toSend instanceof CommandAdd) {
			addIdea(((CommandAdd) toSend).getIdea());
			serverResponse.writeResponse("Project added");
		} else if (toSend instanceof CommandList) {
			serverResponse.addIdeas(getIdeas());
		} else if (toSend instanceof CommandHelp) {
			serverResponse.writeResponse("This server is accepting those commands objects : CommandAdd, CommandList, CommandHelp, CommandQuit");
		} else if (toSend instanceof CommandQuit) {
			serverResponse.writeResponse("See you later on our server ;)");
		}
		return serverResponse;
	}

	public void addIdea(Idea idea) {
		this.ideas.add(idea);
	}

	public Idea getIdea(int i) {
		return this.ideas.get(i);
	}

	public List<Idea> getIdeas() {
		return ideas;
	}
}
