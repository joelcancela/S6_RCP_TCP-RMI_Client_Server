package bounouascancela;

import bounouascancela.client.InnovClient;
import bounouascancela.client.InnovClientImplRMI;
import bounouascancela.client.InnovClientImplTCP;
import bounouascancela.server.InnovServer;
import bounouascancela.server.InnovServerImplRMI;
import bounouascancela.server.InnovServerImplTCP;
import bounouascancela.server.InnovServerRMI;

import java.rmi.RemoteException;
import java.util.Scanner;

/**
 * Main App
 */
public class App {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String protocol = entrerProtocole(scanner);
        entrerChoix(scanner,protocol);
    }

    private static void entrerChoix(Scanner scanner, String protocol) {
        if(protocol.equals("T")){
            entrerChoixTCP(scanner);
        }
        else if(protocol.equals("R")){
            entrerChoixRMI(scanner);
        }
        else{
            return;
        }
    }

    private static void entrerChoixRMI(Scanner scanner) {
        System.out.println("Lancer client (C), serveur (S)");
        boolean goodchoice = false;
        while (!goodchoice) {
            String choice = scanner.nextLine();
            String ip;
            switch (choice) {
                case "C":
                    ip = entrerIp(scanner);
                    InnovClient innovClient = new InnovClientImplRMI(ip);
                    innovClient.start();
                    goodchoice = true;
                    break;
                case "S":
	                InnovServerRMI innovServer = null;
	                try {
		                innovServer = new InnovServerImplRMI();
		                innovServer.start();
	                } catch (RemoteException e) {
		                e.printStackTrace();
	                }
                    goodchoice = true;
                    break;
                default:
                    break;
            }
        }
    }

    private static void entrerChoixTCP(Scanner scanner) {
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
                    InnovClient innovClient = new InnovClientImplTCP(ip, port);
                    innovClient.start();
                    goodchoice = true;
                    break;
                case "S":
                    port = entrerPort(scanner);
                    InnovServer innovServer = new InnovServerImplTCP(port);
                    innovServer.start();
                    goodchoice = true;
                    break;
                default:
                    break;
            }
        }

    }

    private static String entrerProtocole(Scanner scanner) {
        System.out.println("Entrer protocole: TCP(T) - RMI(R)");
        String choice = scanner.next("R|T");
        return choice;
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

}
