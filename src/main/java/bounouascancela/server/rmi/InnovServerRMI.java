package bounouascancela.server.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by Nassim Bounouas on 19/05/2017.
 */
public interface InnovServerRMI extends Remote {

    String help() throws RemoteException;
    String addIdea(String nameCreator, String mailCreator, String ideaName, String ideaDesc, String[] techs) throws RemoteException;
    String list() throws RemoteException;
    String getIdea(int index) throws RemoteException;
    void setIdeaName(int index, String name) throws RemoteException;
}
