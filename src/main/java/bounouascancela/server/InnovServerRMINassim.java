package bounouascancela.server;

import bounouascancela.rmiobjects.Idea;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * Created by Nassim Bounouas on 19/05/2017.
 */
public interface InnovServerRMINassim extends Remote {

    String help() throws RemoteException;
    String addIdea(String nameCreator, String mailCreator, String ideaName, String ideaDesc, String[] techs) throws RemoteException;
    String list() throws RemoteException;
    String getIdea(int index) throws RemoteException;
    void setIdeaName(int index, String name) throws RemoteException;
    String quit() throws RemoteException;
}
