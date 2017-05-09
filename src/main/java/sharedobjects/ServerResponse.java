package sharedobjects;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Nassim B on 10/05/17.
 */
public class ServerResponse implements Serializable {

    List<Idea> ideas;
    String toDisplay;

    public ServerResponse() {
        this.ideas = new ArrayList();
        this.toDisplay = new String();
    }

    public void addIdea(Idea idea) {
        this.ideas.add(idea);
    }

    public void addIdeas(Collection<Idea> ideas) {
        this.ideas.addAll(ideas);
    }

    public void writeResponse(String response) {
        this.toDisplay = response;
    }

    public String readResponse() {
        return this.toDisplay;
    }

    public List<Idea> getIdeas() {
        return this.ideas;
    }
}
