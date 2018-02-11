package bounouascancela.server.rmi;


import bounouascancela.server.rmi.model.Database;
import bounouascancela.server.rmi.rmiobjects.IdeaImpl;
import bounouascancela.server.rmi.rmiobjects.StudentImpl;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.UUID;

/**
 * Created by Nassim Bounouas on 19/05/2017.
 */
public class InnovServerImplRMI extends UnicastRemoteObject implements InnovServerRMI {

    private Registry registry;
    private Database database;

    protected InnovServerImplRMI(Registry registry) throws RemoteException {
        this.registry = registry;
        this.database = Database.getDatabase();
    }

    @Override
    public String help() {
        /*
        Method[] methods = InnovServerRMI.class.getDeclaredMethods();
        for (int i = 0; i < methods.length; i++) {
                System.out.println(methods[i].getName() + " " );
                Parameter[] parameters = methods[i].getParameters();
                for (int j = 0; j < parameters.length; j++) {
                    System.out.print(parameters[j].getType());
                }
                System.out.println();
        }*/
        return "This server is accepting those commands: add, list, help, getName [n], setName [n][name], quit";
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
        System.out.println("Changed project name index : " + index);
    }
}
