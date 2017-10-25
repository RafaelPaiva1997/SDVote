import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Yev on 21-10-2017.
 */
public class VotingTable {
    static ArrayList<VotingTerminal> terminals;
    static votingInterface myServer;

    public static void main(String[] args) throws InterruptedException {
        TCPServer myTCPServer= null;
        try {
            terminals = new ArrayList<VotingTerminal>();
            connectRMI();
            myTCPServer = new TCPServer(8009, terminals, myServer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        new Thread(myTCPServer).start();
        interactWithUser();
    }

    /**
     * Connects to the RMI Server.
     */
    public static void connectRMI(){
        try {
            myServer = (votingInterface) Naming.lookup("rmi://localhost/elections");
            System.out.println("Server says: " + myServer.sayHello());
        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public static void interactWithUser(){
        System.out.println("Bem vindo a mesa de voto! Caso deseja votar, identifique-se a partir do seu nome, numero, telemovel, ou email");
        while(true){
            Scanner myScanner = new Scanner(System.in);
            String outToServer = myScanner.nextLine();
            try {
                System.out.println(myServer.checkForPerson(outToServer));
                if(myServer.checkForPerson(outToServer)){
                    VotingTerminal myTerminal = chooseTerminal();

                    if(myTerminal!= null){
                        System.out.println("Dirige-se ao terminal " + myTerminal.terminalID);
                        myTerminal.outToClient.writeBytes("type|activate;\n");
                        myTerminal.status= "active";
                    }
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * Escolhe o primeiro terminal livre
     * @return
     */
    public static VotingTerminal chooseTerminal(){
        System.out.println(terminals.size());
        for(int i = 0; i<terminals.size(); i++){
            System.out.println(terminals.get(i).status);
            if(terminals.get(i).status.equals("ready")){
                return terminals.get(i);
            }
        }
        return null;

    }

}
