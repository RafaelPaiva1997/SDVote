import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Yev on 21-10-2017.
 */
public class TCPServer implements Runnable {
    protected int serverPort;
    protected ServerSocket serverSocket = null;
    protected Thread curentThread = null;
    protected ExecutorService threadPool;
    private boolean shutdown = false;
    private ArrayList<VotingTerminal> terminals;
    private votingInterface myServer;

    public TCPServer(int serverPort,  ArrayList<VotingTerminal> terminals, votingInterface myServer) throws IOException {
        this.serverPort = serverPort;
        this.serverSocket = new ServerSocket(serverPort);
        this.threadPool = Executors.newFixedThreadPool(8);
        this.terminals = terminals;
        this.myServer = myServer;
    }

    /**
     * Accepts Connections and sends said connections to the worker pool
     */
    @Override
    public void run() {
        this.curentThread = Thread.currentThread();

        int terminalID = 0;
        while(!shutdown){
            Socket clientSocket = null;
            VotingTerminal myTerminal = null;
            try {
                System.out.println("Accepting Connections");
                clientSocket = serverSocket.accept();
                myTerminal = new VotingTerminal(clientSocket, terminalID);
                terminals.add(myTerminal);
                terminalID++;
            } catch (IOException e) {
                e.printStackTrace();
            }
            threadPool.execute(new WorkerThread(myTerminal, myServer));
        }
    }


}
