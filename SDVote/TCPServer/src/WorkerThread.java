import java.io.*;
import java.lang.reflect.Array;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * Accepts client (Voting Terminal) and communicates with it.
 */
public class WorkerThread implements Runnable {
    VotingTerminal thisTerminal;
    votingInterface myServer;


    WorkerThread(VotingTerminal thisTerminal, votingInterface myServer) {
        this.myServer = myServer;
        this.thisTerminal = thisTerminal;
    }

    @Override
    public void run() {
        String clientSentence;
        String messageToSend;
        ArrayList<Message> currentMessage;
        while (true) {
            try {

                clientSentence = thisTerminal.inFromClient.readLine();
                System.out.println("Received: " + clientSentence);
                currentMessage = decodeClientMessage(clientSentence);
                System.out.println(currentMessage);
                messageToSend = analyseMessage(currentMessage);
                if (messageToSend != null)
                    thisTerminal.outToClient.writeBytes(messageToSend + "\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Transforms the recieved String into an arrayList of Messages
     */
    public ArrayList<Message> decodeClientMessage(String message) {
        ArrayList<Message> myMessage = new ArrayList<Message>();
        StringTokenizer strTok = new StringTokenizer(message, "|;");

        while (strTok.hasMoreTokens()) {
            String key;
            String value;
            key = strTok.nextToken();
            value = strTok.nextToken();
            myMessage.add(new Message(new String(key), new String(value)));
        }
        return myMessage;
    }

    /**
     * Looks at the message and chooses a course of action to take
     *
     * @param currentMessage
     * @return
     */
    public String analyseMessage(ArrayList<Message> currentMessage) {
        System.out.println(currentMessage.get(0).key);
        if (currentMessage.get(0).key.equals("type")) {
            switch (currentMessage.get(0).value) {
                case "turnedOn":
                    System.out.println("New voting table turned on");
                    terminalLoggedOn();
                    return "type|Welcome;ID|" + thisTerminal.terminalID;
                case "authentication":
                    return authentication(currentMessage);
                case "voteCast":
                    return voteCast(currentMessage);
                case "ack":
                    System.out.println("Acknowladged");
                    return null;
                case "elections":
                    return getListsFromServer(currentMessage);
            }

        } else {
            System.out.println("INCORRECT MESSAGE FORMAT!");
            return null;
        }
        return null;
    }

    /**
     * Executes when the terminal turns on.
     * Changes status to ready.
     */
    public void terminalLoggedOn() {
        thisTerminal.status = "ready";
    }

    /**
     * Casts the vote on the RMI server
     * @param myMessage
     * @return
     */
    public String voteCast(ArrayList<Message> myMessage){
        String username = myMessage.get(1).value;
        String elections = myMessage.get(2).value;
        String voteOption = myMessage.get(3).value;

        boolean success = false;
        try {
            success = myServer.voteCast(username, elections, voteOption);
            System.out.println(success);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        if(success){
            return "type|voteSuccessful;";
        }
        else
            return  "type|voteFailed;";

    }

    public String authentication(ArrayList<Message> myMessage) {
        String name;
        String password;

        ArrayList<String> myElections = null;

        name = myMessage.get(1).value;
        password = myMessage.get(2).value;

        try {
            myElections = myServer.authenticate(name, password);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        String clientMessage= "type|elections;";

        for(int i= 0; i<myElections.size(); i++){
            clientMessage += "elections|"+myElections.get(i)+";";
        }
        System.out.println(clientMessage);

        return clientMessage;
    }

    /**
     * Searches the RMI server for lists a user can vote for in a certain election
     * @param currentMessage
     * @return
     */
    public String getListsFromServer(ArrayList<Message> currentMessage){
        ArrayList<String> myLists = null;
        try {
            myLists = myServer.getListsForElection(currentMessage.get(1).value, currentMessage.get(2).value);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        String messageToUser = "type|lists;";

        for(int i = 0; i<myLists.size(); i++){
            messageToUser += "list|"+ myLists.get(i)+";";
        }

        return messageToUser;
    }
}

