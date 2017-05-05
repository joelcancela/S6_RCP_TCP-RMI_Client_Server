package bounouascancela.client;

/**
 * Created by Nassim B on 05/05/17.
 */
public class UnrecognizedCommandException extends RuntimeException {
    public UnrecognizedCommandException(){
        super("Unrecognized command Exception");
    }
}
