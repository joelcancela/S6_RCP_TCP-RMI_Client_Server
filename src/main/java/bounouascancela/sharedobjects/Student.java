package bounouascancela.sharedobjects;

import java.io.Serializable;

/**
 * Class Student
 *
 * @author Joël CANCELA VAZ
 */
public class Student implements Serializable{
    private String name;
    private String email;

    public Student(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}
