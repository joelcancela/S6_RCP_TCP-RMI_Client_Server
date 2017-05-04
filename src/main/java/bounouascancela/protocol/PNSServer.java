package bounouascancela.protocol;

/**
 * Class x
 *
 * @author Joël CANCELA VAZ
 */
public interface PNSServer {

    public void start();

    //Serveur
    boolean acceptConnection();
    void closeConnection();


    //Fonctionnalités
    void addIdea();
    void listIdeas();
    void help();
}
