package bounouascancela.server.rmi;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * Created by Nassim Bounouas on 18/05/2017.
 */
public class InnovServerRMILauncher {

    public static void main(String[] args) {

            try {
                Registry r = LocateRegistry.createRegistry(1099);
                r.rebind("PNSInnov", new InnovServerImplRMI(r));
                System.out.println("[INFO] Service is up on port 1099 at 'rmi://localhost/PNSInnov'");
            } catch (Exception e) {
                e.printStackTrace();
                System.err.println("Problem...");
            }
    }
}
