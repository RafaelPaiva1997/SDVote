package ElectionObjects;

/**
 * Created by Yevgen on 20/10/2017.
 * This class describes a voting option for the election
 */
public class VotingOption {


    private String name;

    public VotingOption(String name){
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
