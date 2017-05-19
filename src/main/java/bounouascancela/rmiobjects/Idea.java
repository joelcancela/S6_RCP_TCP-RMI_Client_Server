package bounouascancela.rmiobjects;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by Nassim Bounouas on 19/05/2017.
 */
public interface Idea extends Remote {
    /**
     * Get the idea/project Name
     * @return The project's name
     */
    public String getName() throws RemoteException;

    /**
     * Set the idea/project Name
     * @return The project's name
     */
    public void setName(String name) throws RemoteException;

    /**
     * Get the student leading the project
     * @return The student
     */
    public StudentImpl getCreator() throws RemoteException;

    /**
     * Get the project's description
     * @return The description
     */
    public String getDesc() throws RemoteException;

    /**
     * Get the technologies involved in the project
     * @return The technologies
     */
    public String[] getTechs() throws RemoteException;
}
