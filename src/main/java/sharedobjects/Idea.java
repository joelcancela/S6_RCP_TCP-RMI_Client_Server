package sharedobjects;

import java.io.Serializable;

/**
 * Represent a project idea
 *
 * @author Bounouas Nassim
 * @author Joël CANCELA VAZ
 */
public class Idea implements Serializable {
    private String name;
    private Student creator;
    private String desc;
    private String[] techs;

    /**
     * Create a new Idea
     * @param name The idea/project name
     * @param creator The Student leading the project
     * @param desc Project's description
     * @param techs Technologies used
     */
    public Idea(String name, Student creator, String desc, String[] techs) {
        this.name = name;
        this.creator = creator;
        this.desc = desc;
        this.techs = techs;
    }

    /**
     * Get the idea/project Name
     * @return The project's name
     */
    public String getName() {
        return name;
    }

    /**
     * Get the student leading the project
     * @return The student
     */
    public Student getCreator() {
        return creator;
    }

    /**
     * Get the project's description
     * @return The description
     */
    public String getDesc() {
        return desc;
    }

    /**
     * Get the technologies involved in the project
     * @return The technologies
     */
    public String[] getTechs() {
        return techs;
    }
}
