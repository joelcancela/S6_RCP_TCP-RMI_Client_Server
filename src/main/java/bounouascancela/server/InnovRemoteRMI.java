package bounouascancela.server;


import bounouascancela.rmiobjects.Idea;
import bounouascancela.rmiobjects.IdeaImpl;
import bounouascancela.rmiobjects.StudentImpl;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.UUID;

/**
 * Created by Nassim Bounouas on 19/05/2017.
 */
public class InnovRemoteRMI extends UnicastRemoteObject implements InnovServerRMINassim {

    private Registry registry;
    private Database database;

    protected InnovRemoteRMI(Registry registry) throws RemoteException {
        this.registry = registry;
        this.database = Database.getDatabase();
    }

    @Override
    public String help() {
        return "Command help";
    }

    @Override
    public String addIdea(String nameCreator, String mailCreator, String ideaName, String ideaDesc, String[] techs) {
        try {
            StudentImpl student = new StudentImpl(nameCreator, mailCreator);
            IdeaImpl idea = new IdeaImpl(ideaName, student, ideaDesc, techs);
            database.addIdea(idea);
            System.out.println("Added a project");
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return "Project added";
    }

    @Override
    public String list() {
        return database.list();
    }

    @Override
    public String getIdea(int index) throws RemoteException {
        String uuid = UUID.randomUUID().toString();
        try {
            this.registry.bind(uuid, Database.getDatabase().getItem(index));
        } catch (AlreadyBoundException e) {
            e.printStackTrace();
        }
        return uuid;
    }

    @Override
    public void setIdeaName(int index, String name) throws RemoteException {
        this.database.getItem(index).setName(name);
    }

    @Override
    public String quit() {
        return "Command quit";
    }
}
