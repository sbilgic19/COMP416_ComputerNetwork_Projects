import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by Yahya Hassanzadeh on 20/09/2017.
 */

public class TCPConnectionToServer {

    private Socket tcpSocket;
    protected BufferedReader is;
    protected PrintWriter os;

    protected String serverAddress;
    protected int serverPort;

    /**
     *
     * @param address IP address of the server, if you are running the server on the same computer as client, put the address as "localhost"
     * @param port port number of the server
     */
    public TCPConnectionToServer(String address, int port) {
        serverAddress = address;
        serverPort    = port;
    }

    /**
     * Establishes a socket connection to the server that is identified by the serverAddress and the serverPort
     */
    public void Connect() {
        try {
            tcpSocket = new Socket(serverAddress, serverPort);
 
            is = new BufferedReader(new InputStreamReader(tcpSocket.getInputStream()));
            os = new PrintWriter(tcpSocket.getOutputStream());

            System.out.println("Successfully connected to server at " + tcpSocket.getRemoteSocketAddress());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * sends the message String to the server and retrives the answer
     * @param message input message string to the server
     * @return the received server answer
     */
    public String SendForAnswer(char c) {
        String response = new String();
        try {
        	
        	//System.out.println(c);
            /*
            Sends the message to the server via PrintWriter
             */
        	//System.out.println("TCP Client SendForAnswer");
        	//char[] charArr = message.toCharArray();
        	//for (char c: charArr) {
        	os.println(c);
        	os.flush();
        	//System.out.println("TCP Client SendForAnswer inside for");
        	//}
            //os.println();
            response = is.readLine();
            System.out.println("Response: "+response);
        	//os.println(message);
        	//os.flush();
            //os.flush();
            /*
            Reads a line from the server via Buffer Reader
            
            os.println(message);
            os.flush();
            response = is.readLine();
             */
            
        }
        catch(IOException e) {
            e.printStackTrace();
            System.out.println("ConnectionToServer. SendForAnswer. Socket read Error");
        }
        return response;
    }

    /**
     * Disconnects the socket and closes the buffers
     */
    public void Disconnect() {
        try {
            is.close();
            os.close();
            tcpSocket.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}