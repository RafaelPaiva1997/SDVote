package ElectionObjects;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Yevgen on 20/10/2017.
 */
public class GeneralElection extends Election {
    ArrayList<VotingOption> workerLists;
    ArrayList<VotingOption> teacherLists;
    ArrayList<Integer> workerListsVotes;
    ArrayList<Integer> teacherListsVotes;

    /**
     * Creates a new Elections, with a set starting and ending date;
     *
     * @param start
     * @param end
     */
    public GeneralElection(Date start, Date end) {
        super(start, end);
        this.workerLists = new ArrayList<VotingOption>();
        this.teacherLists = new ArrayList<VotingOption>();
        this.teacherListsVotes = new ArrayList<Integer>();
        this.workerListsVotes = new ArrayList<Integer>();
    }
}
