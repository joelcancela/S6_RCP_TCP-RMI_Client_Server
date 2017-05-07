package bounouascancela.server;

import sharedobjects.Idea;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Abstract class InnovServer
 *
 * @author Joël CANCELA VAZ
 */
public abstract class InnovServer {

    private List<Idea> ideas = new ArrayList<>();

    public abstract void start();
    public abstract int addClient(Object stream);
    public abstract void delClient(int idClient);

    public void addIdea(Idea idea) {
        this.ideas.add(idea);
    }

    public Idea getIdea(int i) {
        return this.ideas.get(i);
    }

    public String listIdeas() {
        StringBuilder stringBuilder = new StringBuilder();
        if (this.ideas.size() > 0) {
            for (int i = 0; i < this.ideas.size(); i++) {
                Idea idea = this.ideas.get(i);
                stringBuilder.append(i + " : " + idea.getName() + " by " + idea.getCreator().getName() + '\n');
            }
        } else {
            stringBuilder.append("Aucun projet disponible sur le serveur");
        }
        return stringBuilder.toString();
    }

}
