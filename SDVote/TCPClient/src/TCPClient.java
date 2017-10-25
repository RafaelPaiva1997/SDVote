import java.io.*;
import java.net.*;
import java.util.*;

/**
 * This class establishes a TCP connection to a specified server, and loops
 * sending/receiving strings to/from the server.
 * <p>
 * The main() method receives two arguments specifying the server address and
 * the listening port.
 * <p>
 * The usage is similar to the 'telnet <address> <port>' command found in most
 * operating systems, to the 'netcat <host> <port>' command found in Linux,
 * and to the 'nc <hostname> <port>' found in macOS.
 *
 * @author Raul Barbosa
 * @author Alcides Fonseca
 * @version 1.1
 */
class TCPClient {
  public static int id;
  public static String username;
  public static String elections;
  public static String passw;

  public static void main(String[] args) {
    Socket socket;
    PrintWriter outToServer;
    BufferedReader inFromServer = null;
    try {
      // connect to the specified address:port (default is localhost:12345)
      if (args.length == 2)
        socket = new Socket(args[0], Integer.parseInt(args[1]));
      else
        socket = new Socket("localhost", 8009);

      // create streams for writing to and reading from the socket
      inFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      outToServer = new PrintWriter(socket.getOutputStream(), true);

      communicateWithServer(inFromServer, outToServer, socket);

    } catch (IOException e) {
      if (inFromServer == null)
        System.out.println("\nUsage: java TCPClient <host> <port>\n");
      System.out.println(e.getMessage());
    } finally {
      try {
        inFromServer.close();
      } catch (Exception e) {
      }
    }
  }

  public static void communicateWithServer(BufferedReader inFromServer, PrintWriter outToServer, Socket socket) throws IOException {
    String messageFromServer = null;
    Scanner keyboardScanner = new Scanner(System.in);
    ArrayList<Message> myMessage;
    String toServer =null;

    //Tell the server that this terminal just turned on
    outToServer.println("type|turnedOn");


    while (!socket.isClosed()) {

      while ((messageFromServer = inFromServer.readLine()) != null) {
        System.out.println(messageFromServer);
        myMessage = decodeServerMessage(messageFromServer);
        toServer = analyseServerMessage(myMessage);
        System.out.println(toServer);
        outToServer.println(toServer);
      }



    }
  }

  public static ArrayList<Message> decodeServerMessage(String message){
    ArrayList<Message> myMessage = new ArrayList<Message>();
    StringTokenizer strTok = new StringTokenizer(message, "|;");

    while(strTok.hasMoreTokens()) {
      String key;
      String value;
      key = strTok.nextToken();
      value = strTok.nextToken();
      myMessage.add(new Message(new String(key), new String(value)));
    }
    return myMessage;
  }

  public static String analyseServerMessage(ArrayList<Message> myMessage){
    if(myMessage.get(0).key.equals("type")){
      switch(myMessage.get(0).value){
          case "Welcome":
            id = new Integer(myMessage.get(1).value);
            System.out.println("Welcome to the voting table " + id);
            System.out.println("Dirige-se para a mesa de voto para iniciar a votação");
            return "type|ack";
          case "activate":
            return authentication();
          case "elections":
            return election(myMessage);
          case "lists":
            return castVote(myMessage);
          case "voteSuccessful":
              return voteSuccessful();
      }
    }
    return null;
  }

  /**
   *
   * @param myMessage
   * @return
   */
  public static String election(ArrayList<Message> myMessage){
    System.out.println("Escolha a eleição em que pretende votar");
    for(int i = 1; i<myMessage.size(); i++){
      if(myMessage.get(i).key.equals("elections")){
        System.out.println(i+ ". " + myMessage.get(i).value);
      }

    }
    Scanner myScanner = new Scanner(System.in);
    int choice;
    System.out.println("Escolha a opção em que pretende votar(o número)");
    choice = myScanner.nextInt();
    elections =myMessage.get(choice).value;

    return "type|elections;elections|"+myMessage.get(choice).value+";name|"+username;
  }

  /**
   * Asks for username and password
   * @return
   */
  public static String authentication(){
    Scanner myScanner = new Scanner(System.in);
    System.out.println("Insert your name:");
    String name = myScanner.nextLine();
    username = name;
    System.out.println("Insert you password:");
    String password = myScanner.nextLine();
    passw = password;
    return "type|authentication;name|" + name + ";password|" + password;
  }

    /**
     * Casts a vote, and sends the username, the elections, and the choice.
     * @param myMessage
     * @return
     */
  public static String castVote(ArrayList<Message> myMessage){
      System.out.println("Escolhe a lista em que quer votar:");
      for(int i = 1; i<myMessage.size(); i++){
          System.out.println(i+ ". "+myMessage.get(i).value);
      }
      Scanner myScanner = new Scanner(System.in);

      return "type|voteCast;name|"+username+";elections|"+ elections +";list|"+myMessage.get(myScanner.nextInt()).value+";";
  }

  public static String voteSuccessful(){
      System.out.println("Voto registado com successo. Quere votar nas outras eleições?");
      System.out.println("1. Sim");
      System.out.println("2. Não");

      Scanner myScanner = new Scanner(System.in);
      int choice = myScanner.nextInt();

      if(choice == 1)
        return "type|authentication;name|"+username+";password|"+passw+";";
      else
          return "type|ack";
  }

}