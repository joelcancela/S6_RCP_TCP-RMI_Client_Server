package sharedobjects;

/**
 * Command to send to server to add a new Idea
 * @author Bounouas Nassim
 * @author Joël CANCELA VAZ
 * @see sharedobjects.Idea
 */
public class CommandAdd extends Command{

    private Idea idea;

    /**
     * Create a new Add Command with embedding an Idea
     * @param idea The idea to add
     * @see Idea
     */
    public CommandAdd(Idea idea) {
        this.idea = idea;
    }

    /**
     * Get the Idea send using the command
     * @return The idea to add to server
     */
    public Idea getIdea() {
        return this.idea;
    }
}
