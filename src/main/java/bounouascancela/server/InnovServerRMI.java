package bounouascancela.server;

import sharedobjects.Command;
import sharedobjects.ServerResponse;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * {@code InnovServerRMI} is [desc]
 * <p>
 * [descSuite]
 *
 * @author Joël CANCELA VAZ
 */
public interface InnovServerRMI extends Remote {

	void start() throws RemoteException;

	ServerResponse receiveAndProcess(Command toSend) throws RemoteException;
}
