package bounouascancela.client.rmi.rmiobjects;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by Nassim Bounouas on 19/05/2017.
 */
public interface Student extends Remote {
    /**
     * Get StudentImpl's name
     * @return The student's name
     */
    public String getName() throws RemoteException;

    /**
     * Get StudentImpl's mail
     * @return The student's mail
     */
    public String getMail() throws RemoteException;
}
