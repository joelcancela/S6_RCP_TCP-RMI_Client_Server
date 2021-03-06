package sharedobjects;

import java.io.Serializable;

/**
 * Represent a StudentImpl
 * @author Bounouas Nassim
 * @author Joël CANCELA VAZ
 */
public class Student implements Serializable{
    private String name;
    private String mail;

    /**
     * Create a new StudentImpl
     * @param name StudentImpl's name
     * @param mail StudentImpl's mail adress
     */
    public Student(String name, String mail) {
        this.name = name;
        this.mail = mail;
    }

    /**
     * Get StudentImpl's name
     * @return The student's name
     */
    public String getName() {
        return name;
    }

    /**
     * Get StudentImpl's mail
     * @return The student's mail
     */
    public String getMail() {
        return mail;
    }
}
