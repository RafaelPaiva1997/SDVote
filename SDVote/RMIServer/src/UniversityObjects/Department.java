package UniversityObjects;

import People.Person;

import java.util.*;

/**
 * Created by Yevgen on 20/10/2017.
 */
public class Department {
    public String name;
    public ArrayList<Person> people;

    Department(String name){
        this.name = name;
        this.people = new ArrayList<Person>();
    }

}
