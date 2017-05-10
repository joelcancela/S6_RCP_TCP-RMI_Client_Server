package sharedobjects;

import java.io.Serializable;

/**
 * Represent a Student
 * @author Bounouas Nassim
 * @author Joël CANCELA VAZ
 */
public class Student implements Serializable{
    private String name;
    private String mail;

    /**
     * Create a new Student
     * @param name Student's name
     * @param mail Student's mail adress
     */
    public Student(String name, String mail) {
        this.name = name;
        this.mail = mail;
    }

    /**
     * Get Student's name
     * @return The student's name
     */
    public String getName() {
        return name;
    }

    /**
     * Get Student's mail
     * @return The student's mail
     */
    public String getMail() {
        return mail;
    }
}
