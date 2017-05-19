package bounouascancela.server.rmi.rmiobjects;

import bounouascancela.client.rmi.rmiobjects.Student;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Represent a StudentImpl
 * @author Bounouas Nassim
 * @author Joël CANCELA VAZ
 */
public class StudentImpl extends UnicastRemoteObject implements Student {
    private String name;
    private String mail;

    /**
     * Create a new StudentImpl
     * @param name StudentImpl's name
     * @param mail StudentImpl's mail adress
     */
    public StudentImpl(String name, String mail) throws RemoteException{
        this.name = name;
        this.mail = mail;
    }

    /**
     * Get StudentImpl's name
     * @return The student's name
     */
    @Override
    public String getName() throws RemoteException {
        return name;
    }

    /**
     * Get StudentImpl's mail
     * @return The student's mail
     */
    @Override
    public String getMail() throws RemoteException {
        return mail;
    }
}
