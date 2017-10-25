package People;

import java.util.Date;

/**
 * Created by Yevgen on 24/10/2017.
 */
public class Student extends Person {
    public Student(String faculty, String department, String name, int phoneNumber, int identityNumber, Date validUntill, String password){
        super(faculty, department, name, phoneNumber, identityNumber, validUntill, password);

    }
}
