package bounouascancela.protocol;

/**
 * Class x
 *
 * @author Joël CANCELA VAZ
 */
public interface PNSServer {

    //Serveur
    boolean acceptConnection();
    void closeConnection();


    //Fonctionnalités
    void addIdea();
    void listIdeas();
    void help();
}
