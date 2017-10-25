import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Created by Yev on 21-10-2017.
 */
public class VotingTerminal {
    Socket clientSocket;
    int terminalID;
    String status;
    BufferedReader inFromClient;
    DataOutputStream outToClient;
    VotingTerminal(Socket clientSocket, int terminalID){
        this.clientSocket = clientSocket;
        this.terminalID = terminalID;
        status = new String("unknown");
        try {
            inFromClient = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));
            outToClient = new DataOutputStream(clientSocket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
