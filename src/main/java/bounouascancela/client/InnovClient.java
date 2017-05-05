package bounouascancela.client;

import sharedobjects.Command;

/**
 * Created by Nassim B on 05/05/17.
 */
public abstract class InnovClient {

    protected String ip;
    protected int port;

    protected InnovClient(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    protected abstract void connect();
    protected abstract String getInput();
    protected abstract void send();
    public abstract void start();
    protected abstract Command parseInput();
}
