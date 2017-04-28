package bounouascancela.sharedobjects;

/**
 * Created by Nassim B on 28/04/17.
 */
public class CommandAdd {

    private Idea idea;

    public CommandAdd(Idea idea) {
        this.idea = idea;
    }

    public Idea getIdea() {
        return this.idea;
    }
}
