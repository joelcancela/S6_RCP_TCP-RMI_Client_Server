package bounouascancela;

import bounouascancela.client.PNSClient;
import bounouascancela.client.PNSClientImplTCP;
import bounouascancela.protocol.PNSServer;
import bounouascancela.protocol.PNSServerImplTCP;

import java.util.Scanner;

/**
 * Main App
 */
public class App {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
//        String protocol = entrerProtocole(scanner);
//        entrerChoix(scanner,protocol);
        entrerChoix(scanner);
    }

    private static void entrerChoix(Scanner scanner) {
        System.out.println("Lancer client (C), serveur (S)");
        boolean goodchoice = false;
        while (!goodchoice) {
            String choice = scanner.nextLine();
            String ip;
            int port;
            switch (choice) {
                case "C":
                    ip = entrerIp(scanner);
                    port = entrerPort(scanner);
                    PNSClient pnsClient = new PNSClientImplTCP(ip, port);
                    pnsClient.start();
                    goodchoice = true;
                    break;
                case "S":
                    port = entrerPort(scanner);
                    PNSServer pnsServer = new PNSServerImplTCP(port);
                    pnsServer.start();
                    goodchoice = true;
                    break;
                default:
                    break;
            }
        }

    }

    private static String entrerProtocole(Scanner scanner) {
        return null;
    }

    private static int entrerPort(Scanner scanner) {
        System.out.println("Enter port: ");
        int choice = scanner.nextInt();
        return choice;
    }

    private static String entrerIp(Scanner scanner) {
        System.out.println("Enter ip: ");
        String IPADDRESS_PATTERN =
                "(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)";
        String choice = null;
        while (true) {
            choice = scanner.nextLine();
            if (choice.matches(IPADDRESS_PATTERN) || choice.equals("localhost")) {
                break;
            }
        }
        return choice;
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
