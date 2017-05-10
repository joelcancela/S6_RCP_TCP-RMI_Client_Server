package sharedobjects;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Object used to communicate from the server to the client
 * @author Bounouas Nassim
 * @author Joël CANCELA VAZ
 */
public class ServerResponse implements Serializable {

    List<Idea> ideas;
    String toDisplay;

    /**
     * Create a new empty response
     */
    public ServerResponse() {
        this.ideas = new ArrayList();
        this.toDisplay = new String();
    }

    /**
     * Add an idea to the response
     * @param idea The idea to add
     * @see Idea
     */
    public void addIdea(Idea idea) {
        this.ideas.add(idea);
    }

    /**
     * Add a collection of ideas to the response
     * @param ideas The collection to add
     * @see Idea
     * @see Collection
     */
    public void addIdeas(Collection<Idea> ideas) {
        this.ideas.addAll(ideas);
    }

    /**
     * Add a new textual line to the response
     * @param response The text to send
     */
    public void writeResponse(String response) {
        this.toDisplay += response;
    }

    /**
     * Read a textual response
     * @return The response
     * @see String
     * @see CommandHelp
     * @see CommandAdd
     * @see CommandQuit
     */
    public String readResponse() {
        return this.toDisplay;
    }

    /**
     * Get the ideas in the response
     * @return The list of ideas
     * @see Idea
     * @see CommandList
     */
    public List<Idea> getIdeas() {
        return this.ideas;
    }
}
