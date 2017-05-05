package sharedobjects;

import java.io.Serializable;

/**
 * Class Idea
 *
 * @author Joël CANCELA VAZ
 */
public class Idea implements Serializable {
    private String name;
    private Student creator;
    private String desc;
    private String[] techs;

    public Idea(String name, Student creator, String desc, String[] techs) {
        this.name = name;
        this.creator = creator;
        this.desc = desc;
        this.techs = techs;
    }

    public String getName() {
        return name;
    }

    public Student getCreator() {
        return creator;
    }

    public String getDesc() {
        return desc;
    }

    public String[] getTechs() {
        return techs;
    }
}
