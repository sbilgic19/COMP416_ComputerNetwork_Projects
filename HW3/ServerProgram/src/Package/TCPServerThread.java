package Package;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

class TCPServerThread extends Thread {
	
    protected BufferedReader is;
    protected PrintWriter os;
    protected Socket tcpSocket;
    private String line = new String();
    private final String SERVER_ACK_MESSAGE = "server_ack";

    /**
     * Creates a server thread on the input socket
     *
     * @param s input socket to create a thread on
     */
    public TCPServerThread(Socket tcpSocket)
    {
        this.tcpSocket = tcpSocket;
    }

    /**
     * The server thread, echos the client until it receives the QUIT string from the client
     */
    public void run() {
        try {
            is = new BufferedReader(new InputStreamReader(tcpSocket.getInputStream()));
            os = new PrintWriter(tcpSocket.getOutputStream());

            //line = is.readLine();
            String line;
            while((line = is.readLine()) != null) {
            	//System.out.println("line: "+line);
            	System.out.println("Client " + tcpSocket.getRemoteSocketAddress() + " sent : " + line);
            	os.println(SERVER_ACK_MESSAGE);
                os.flush();
                //line = is.readLine();
            }
          }
            
        catch (IOException e) {
            line = this.getName(); //reused String line for getting thread name
            System.err.println("TCPServer Thread. Run. IO Error/ Client " + line + " terminated abruptly");
        }
        catch (NullPointerException e) {
            line = this.getName(); //reused String line for getting thread name
            System.err.println("TCPServer Thread. Run.Client " + line + " Closed");
        } 
        finally {
            try {
                System.out.println("Closing the connection");
                if (is != null) {
                    is.close();
                }
                if (os != null) {
                    os.close();
                }
                if (tcpSocket != null){
                    tcpSocket.close();
                }
            }
            catch (IOException ie) {
                System.err.println("Socket Close Error");
            }
        }
    }
}