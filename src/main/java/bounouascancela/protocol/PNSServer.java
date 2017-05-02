package bounouascancela.protocol;

/**
 * Class x
 *
 * @author Joël CANCELA VAZ
 */
public interface PNSServer extends Runnable {

    //Serveur
    boolean acceptConnection();
    void closeConnection();


    //Fonctionnalités
    void addIdea();
    void listIdeas();
    void help();
}
