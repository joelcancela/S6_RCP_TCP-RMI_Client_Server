package bounouascancela.server.rmi.model;

import bounouascancela.server.rmi.rmiobjects.IdeaImpl;

import java.rmi.RemoteException;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Nassim Bounouas on 19/05/2017.
 */
public class Database {

    private  List<IdeaImpl> ideas;
    private static Database DATABASE = null;

    private Database() {
        this.ideas = new LinkedList();
    }

    public static  Database getDatabase() {

        if (Database.DATABASE == null) {
            Database.DATABASE = new Database();
        }

        return Database.DATABASE;
    }

    public void addIdea(IdeaImpl idea) {
        this.ideas.add(idea);
    }

    public IdeaImpl getItem(int i) {
        return ideas.get(i);
    }

    public String list() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < ideas.size(); i++) {
            try {
                stringBuilder.append(i + " : " + ideas.get(i).getName());
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return stringBuilder.toString();
    }
}
