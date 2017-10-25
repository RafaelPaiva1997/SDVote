import ElectionObjects.Election;
import People.Person;
import UniversityObjects.Faculty;

import java.util.ArrayList;

/**
 * Created by Yevgen on 24/10/2017.
 */
public class Database {
    ArrayList<Election> elections;
    ArrayList<Person> people;
    ArrayList<Faculty> faculties;

    Database(){
        elections = new ArrayList<Election>();
        people = new ArrayList<Person>();
        faculties = new ArrayList<Faculty>();
    }

    public Person findPersonByName(String name){
        System.out.println(name);
        for(int i = 0; i<people.size(); i++){
            if(people.get(i).getName().equals(name)){
                return people.get(i);
            }
        }
        return null;
    }

    /**
     * this method searches the elections list and compares it to the arg.
     * @param description a unique description of elections
     * @return
     */
    public Election findElectionByDescription(String description){
        for(int i = 0; i<elections.size(); i++){
            if(elections.get(i).getDescription().equals(description)){
                return elections.get(i);
            }
        }
        return null;
    }

    /**
     * Checks to see if the name/passwords match
     * @param name
     * @param password
     * @return
     */
    public boolean authenticate(String name, String password){
        for(int i = 0; i<people.size(); i++){
            if(people.get(i).getName().equals(name)) {
                if (people.get(i).getPassword().equals(password))
                    return true;
                else
                    return false;
            }
        }
        return false;
    }

    public ArrayList<String> getAllElectionsForUser(String name){
        ArrayList<String> myList = new ArrayList<String>();
        Person voter = findPersonByName(name);
        System.out.println(voter.getName());
        for(int i = 0; i<elections.size(); i++){
            if(elections.get(i).allowedToVote(voter)){
                myList.add(elections.get(i).getDescription());
            }
        }
        return myList;
    }
}
