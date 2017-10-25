import ElectionObjects.GeneralElection;
import ElectionObjects.VotingOption;
import People.Person;
import People.Student;

import java.rmi.Naming;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.util.Date;

/**
 * Created by Yev on 22-10-2017.
 */
public class ElectionsServer {
    public static Database database;

    public static void main(String[] args) {
        database = new Database();

        //Testar
        database.people.add(new Student("test", "tst", new String("tst"), 111, 111, new Date(), new String("tst")));

        database.elections.add(new GeneralElection(new Date(), new Date()));
        database.elections.get(0).setDescription("Eleicoes Gerais");

        database.elections.get(0).getStudentLists().add((new VotingOption("ListA")));
        database.elections.get(0).getStudentLists().add((new VotingOption("ListB")));

        System.setProperty("java.security.policy","file:./security.policy");
        System.setSecurityManager(new RMISecurityManager());
        try {
            votingImpl myVoting = new votingImpl(database);
            Naming.rebind("elections", myVoting);
            System.out.println("Test!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
