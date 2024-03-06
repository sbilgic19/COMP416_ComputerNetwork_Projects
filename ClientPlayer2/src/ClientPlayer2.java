
import java.io.*;
import java.net.*;

public class ClientPlayer2 {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		String host;
	    int port;
	    host = "localhost";
	    
	        try {
	        	
	        	// Initiating I/O streams
	        	BufferedReader stdIn =
                        new BufferedReader (
                                new InputStreamReader ( System.in ) );
	        	// Port is asked at execution
	        	System.out.println("Enter server socket's port");
	        	String strPort = stdIn.readLine();
	        	port = Integer.parseInt(strPort);
	        	
	        	// clientSocket is initialized
	            Socket clientSocket = new Socket ( host, port );
	            System.out.println("Binding to port localhost:"+port);
	            
	            // Initiating I/O streams
	            PrintWriter out =
                        new PrintWriter ( clientSocket.getOutputStream (), true );
                BufferedReader in =
                        new BufferedReader (
                                new InputStreamReader ( clientSocket.getInputStream () ) );
                
	            // Showing the socket address of the Server
	            System.out.println("Server socket: "+clientSocket.getRemoteSocketAddress());
	         
	            // Getting Player 1's name from Server and asking for Player 2's name
	        	String playerName1 = in.readLine();
	        	System.out.print("Player 2, you will be playing with "+playerName1+ ", please enter your name: ");
	        	String playerName2 = stdIn.readLine();
	        	out.println(playerName2);
	        	
	        	
	        	for(int i=0; i<3; i++) {
					
	        		in.readLine(); // To keep waiting client until Player 1 inputs the x and y values
	        		System.out.println(playerName2+", please enter your x and y guesses, comma seperated.");
					String nums = stdIn.readLine();
					out.println(nums);
					
					/*
					String[] arr = nums.split(",");
					int x_guess_p2 = Integer.parseInt(arr[0]);
					int y_guess_p2 = Integer.parseInt(arr[1]);
					*/
					
					// Printing the result of the round and getting it from Server
					System.out.println(in.readLine());
				}
	        	
	        	System.out.println(in.readLine());
	            out.close();
	            in.close();
	            clientSocket.close();
	                
	        }catch(Exception e) {
	        	System.out.println("Bir hata oluştu");
	        	e.printStackTrace();
	        }
	}

}
