import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 * Created by Yev on 22-10-2017.
 */
public interface votingInterface extends Remote {

    String sayHello() throws RemoteException;
    boolean checkForPerson(String searchField) throws RemoteException;
    ArrayList<String> authenticate(String name, String password) throws RemoteException;
    ArrayList<String> getListsForElection(String name, String electionDescription) throws RemoteException;
    boolean voteCast(String name, String electionDescription, String voteOption) throws RemoteException;

}
