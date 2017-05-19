package bounouascancela.server.rmi.rmiobjects;

import bounouascancela.client.rmi.rmiobjects.Idea;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Represent a project idea
 *
 * @author Bounouas Nassim
 * @author Joël CANCELA VAZ
 */
public class IdeaImpl extends UnicastRemoteObject implements Idea {
    private String name;
    private StudentImpl creator;
    private String desc;
    private String[] techs;

    /**
     * Create a new Idea
     * @param name The idea/project name
     * @param creator The StudentImpl leading the project
     * @param desc Project's description
     * @param techs Technologies used
     */
    public IdeaImpl(String name, StudentImpl creator, String desc, String[] techs) throws RemoteException {
        super();
        this.name = name;
        this.creator = creator;
        this.desc = desc;
        this.techs = techs;
    }

    /**
     * Get the idea/project Name
     * @return The project's name
     */
    @Override
    public String getName() throws RemoteException {
        return name;
    }

    @Override
    public void setName(String name) throws RemoteException {
        this.name = name;
    }

    /**
     * Get the student leading the project
     * @return The student
     */
    @Override
    public StudentImpl getCreator() throws RemoteException {
        return creator;
    }

    /**
     * Get the project's description
     * @return The description
     */
    @Override
    public String getDesc() throws RemoteException {
        return desc;
    }

    /**
     * Get the technologies involved in the project
     * @return The technologies
     */
    @Override
    public String[] getTechs() throws RemoteException {
        return techs;
    }
}
