import java.util.Scanner;


public class TCPClient {

	public final static String TCP_SERVER_ADDRESS = "localhost";
    public final static String MESSAGE_TO_TCP_SERVER = "71571COMP416";
    public final static int TCP_SERVER_PORT = 9998;
    /**
     * @param args the command line arguments
     */
    public TCPClient() {
        TCPConnectionToServer connectionToServer = new TCPConnectionToServer(TCP_SERVER_ADDRESS, TCP_SERVER_PORT);
        connectionToServer.Connect();
        
        long startTime = System.currentTimeMillis(); 
        for (char c: MESSAGE_TO_TCP_SERVER.toCharArray()) {
        	 connectionToServer.SendForAnswer(c);
        }
        long endTime = System.currentTimeMillis();
        long total_time = endTime - startTime;
        System.out.println("Total time passed: " + total_time + "ms");
       
        connectionToServer.Disconnect();
    } 
}