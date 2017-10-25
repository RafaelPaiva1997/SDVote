package ElectionObjects;

import People.Person;

/**
 * Created by Yevgen on 20/10/2017.
 */
public class Vote {
    public Person voter;
    public VotingOption candidate;

    public Vote(Person voter, VotingOption candidate){
        this.voter = voter;
        this.candidate = candidate;
    }
}
