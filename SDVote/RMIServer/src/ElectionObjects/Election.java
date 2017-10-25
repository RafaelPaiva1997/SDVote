package ElectionObjects;
import People.Person;
import People.Student;

import java.util.*;

/**
 * Created by Yevgen on 20/10/2017.
 * This class describes an abstract election
 */
public abstract class Election {
    private Date start;
    private Date end;

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<VotingOption> getStudentLists() {
        return studentLists;
    }

    public void setStudentLists(ArrayList<VotingOption> studentLists) {
        this.studentLists = studentLists;
    }

    public ArrayList<Integer> getStudentListsVotes() {
        return studentListsVotes;
    }

    public void setStudentListsVotes(ArrayList<Integer> studentListsVotes) {
        this.studentListsVotes = studentListsVotes;
    }

    public ArrayList<Person> getListOfVoters() {
        return listOfVoters;
    }

    public void setListOfVoters(ArrayList<Person> listOfVoters) {
        this.listOfVoters = listOfVoters;
    }

    private String description;

    private ArrayList<VotingOption> studentLists;

    private ArrayList<Integer> studentListsVotes;
    private ArrayList<Person> listOfVoters;
    /**
    *Creates a new Elections, with a set starting and ending date;
    *
     */
    Election(Date start, Date end){
        this.listOfVoters = new ArrayList<>();
        this.start = start;
        this.end = end;
        this.studentLists = new ArrayList<VotingOption>();
        this.studentListsVotes = new ArrayList<Integer>();
        this.studentLists.add(new VotingOption("Em branco"));
        this.studentListsVotes.add(new Integer(0));

    }
    /**
     *Describes the process of person voting
     */
    public boolean voteCast(ArrayList<VotingOption> lists, Vote myVote){
        int i;
        if(checkVote(myVote.voter)) {

            for(i = 0; i<lists.size(); i++){
                if(lists.get(i).getName().equals(myVote.candidate.getName())){
                    /* adds a new vote to the specified list*/
                    this.studentListsVotes.set(i, this.studentListsVotes.get(i) + 1);
                    /*adds a new voter to the list of voters*/
                    this.listOfVoters.add(myVote.voter);
                    return true;
                }
            }
        }
        else {
            System.out.println("Vote failed");
        }
        return false;
    }

    private boolean checkVote(Person voter){
        /*checks to see if voter already voted
         */
        for(int i = 0; i<listOfVoters.size(); i++){
            if(voter.equals(listOfVoters.get(i))){
                return false;
            }
        }
        return true;
    }

    /**
     * checks to see if voter can vote in this election
     * @param voter
     * @return
     */
    public boolean allowedToVote(Person voter){
        return checkVote(voter);
    }

    /**
     * This method adds a new student list to the list of student options, and adds a new int to the list of votes, initialized at 0
     * @param studentList
     */
    public void newStudentList(VotingOption studentList){
        this.studentLists.add(studentList);
        this.studentListsVotes.add(0);
    }

    public ArrayList<VotingOption> getVotingListForPerson(Person voter){
        if(voter instanceof Student){
            return studentLists;
        }
        else
            return null;
    }

    public VotingOption findVotingOption(ArrayList<VotingOption> myList, String myOption){
        System.out.println("My Option: " + myOption);
        for(int i = 0; i<myList.size(); i++){
            System.out.println(myList.get(i).getName());
            if(myList.get(i).getName().equals(myOption))
                return  myList.get(i);
        }
        System.out.println("RETURNING NULL CARALHO!");
        return null;
    }

}
