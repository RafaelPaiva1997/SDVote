import ElectionObjects.Election;
import ElectionObjects.Vote;
import ElectionObjects.VotingOption;
import People.Person;
import People.Student;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 * Created by Yev on 22-10-2017.
 */
public class votingImpl
        extends java.rmi.server.UnicastRemoteObject
        implements votingInterface{
    Database database;


    @Override
    public String sayHello() throws RemoteException {
        return "Hello";
    }
    public votingImpl(Database database) throws java.rmi.RemoteException{
        this.database = database;
    }
    public boolean checkForPerson(String searchField){
        if(database.findPersonByName(searchField) != null)
            return true;
        else return false;
    }

    /**
     *
     * @param name
     * @param password
     * @return a list of all elections that the user can participate in
     */
    public ArrayList<String> authenticate(String name, String password){
        ArrayList<String> myElections = new ArrayList<String>();
        if(database.authenticate(name, password)){
            return database.getAllElectionsForUser(name);
        }

        return null;
    }

    /**
     *
     */
    public ArrayList<String> getListsForElection(String description, String name) throws RemoteException{
        System.out.println("|||||||||||||");
        Person voter;
        ArrayList<VotingOption> myOptions;
        ArrayList<String> listNames = new ArrayList<>();
        voter = (Student)database.findPersonByName(name);
        System.out.println(voter);
        Election myElection;
        myElection = database.findElectionByDescription(description);
        myOptions = myElection.getVotingListForPerson(voter);

        for(int i = 0; i<myOptions.size(); i++){
            listNames.add(myOptions.get(i).getName());
        }
        return  listNames;

    }


    /**
     * Casts a vote in the specified list by a specified user, in a specified election
     * @param name
     * @param electionDescription
     * @param voteOption
     * @return
     * @throws RemoteException
     */
    public boolean voteCast(String name, String electionDescription, String voteOption) throws  RemoteException{
        Person voter = database.findPersonByName(name);
        Election election = database.findElectionByDescription(electionDescription);

        if(voter instanceof Student){
            VotingOption myOption = election.findVotingOption(election.getStudentLists(),voteOption);
            System.out.println("Voting option: "+voteOption);
            return election.voteCast(election.getStudentLists(), new Vote(voter, myOption));
        }

        System.out.println(election.getListOfVoters().get(0).getName());
        System.out.println(election.getStudentListsVotes().get(0));
        return false;
    }
}
