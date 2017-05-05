package bounouascancela.server;

/**
 * Abstract class InnovServer
 *
 * @author Joël CANCELA VAZ
 */
public abstract class InnovServer {

    public abstract void start();
    public abstract int addClient(Object stream);
    public abstract void delClient(int idClient);

}
