package bounouascancela;

import bounouascancela.protocol.PNSClient;
import bounouascancela.protocol.PNSClientImplTCP;
import bounouascancela.protocol.PNSServer;
import bounouascancela.protocol.PNSServerImplTCP;

import java.util.Scanner;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {

        if(args.length<2)
            return;
        String ip = args[0];
        String port = args[1];
        System.out.println("Arguments: "+ip+" "+port);

        System.out.println("Lancer client (C), serveur (S), les deux (B) :");
        Scanner scanner = new Scanner(System.in);
        PNSClient pnsClient;
        PNSServer pnsServer;
        boolean goodchoice = false;
        while(!goodchoice){
            String choice = scanner.nextLine();
            switch (choice) {
                case "C":
                    pnsClient = new PNSClientImplTCP(ip, port);
                    new Thread(pnsClient).start();
                    goodchoice=true;
                    break;
                case "S":
                    pnsServer = new PNSServerImplTCP();
                    new Thread(pnsServer).start();
                    goodchoice=true;
                    break;
                case "B":
                    pnsClient = new PNSClientImplTCP(ip, port);
                    new Thread(pnsClient).start();
                    pnsServer = new PNSServerImplTCP();
                    new Thread(pnsServer).start();
                    goodchoice=true;
                    break;
                default:
                    break;
            }
        }


    }


    // RMI LATER
//    String url = "rmi://localhost/PNSServer";
//                try {
//        PNSServer ps = (PNSServer) Naming.lookup("PNSServer");
//    } catch (NotBoundException e) {
//        e.printStackTrace();
//    } catch (MalformedURLException e) {
//        e.printStackTrace();
//    } catch (RemoteException e) {
//        e.printStackTrace();
//    }
//                new Thread(new PNSServerImplTCP(ps)).start();
}
