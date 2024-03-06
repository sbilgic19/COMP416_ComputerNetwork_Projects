

import java.io.*;
import java.util.Random;
import java.net.*;
public class ServerPlayer1 {

	public static void main(String[] args) throws IOException{
		// TODO Auto-generated method stub
		

		int port;
		
		// Generating Random x and y values
		Random random = new Random();
		int x = random.nextInt(256);
		int y = random.nextInt(256);
			
		
		try {
			// Initiating I/O streams
			BufferedReader stdIn =
                    new BufferedReader (
                            new InputStreamReader ( System.in ) );
			
			// Port is asked at execution
			System.out.println("Enter welcoming socket's port");
			String strPort = stdIn.readLine();
			port = Integer.parseInt(strPort);
			System.out.println("Waiting for client to connect...");
			ServerSocket serverSocket = new ServerSocket(port);
			Socket clientSocket = serverSocket.accept();
			
			
			// Initiating I/O streams	
			PrintWriter out = 
					new PrintWriter(clientSocket.getOutputStream(), true);
			BufferedReader in = 
					new BufferedReader(
							new InputStreamReader(clientSocket.getInputStream()));
			
			
			// Showing the socket address of the Server
			System.out.println("Client socket: "+clientSocket.getRemoteSocketAddress());
			
			// Setting the game, asking for Player Names
			System.out.print("Player 1, enter your name: ");
			String playerName1 = stdIn.readLine();
			// Sending Player 1's names to the client
			out.println(playerName1);
			System.out.println("Waiting for Player 2 name...");
			// Getting Player 2's name from client
			String playerName2 = in.readLine();
			System.out.println("You are playing with " + playerName2);
			
			
			// Initiating scores for each player
			int player1Score = 0;
			int player2Score = 0;
			
			// Game Logic
			for(int i=1; i<=3; i++) {
				
				
				System.out.println(playerName1 + ", please enter your x and y guesses, comma separated.");
				
				String nums = stdIn.readLine(); // asking for x, y values to Player 1
				nums = nums.replace(" ",""); // Getting rid of blanks between values
				int[] arr = convertPointInputs(nums); // Calling the string/int converter method 
				int x_guess_p1 = arr[0]; // first value is x
				int y_guess_p1 = arr[1]; // second value is y
				
				System.out.println("Waiting for player 2 guess...");
				out.println(); // After inputting the x, y values Client were waiting sending signal
								// so that Client asks for input from Player 2
				
				String nums2 = in.readLine();
				nums2 = nums2.replace(" ", "");
				int[] arr2 = convertPointInputs(nums2);
				int x_guess_p2 = arr2[0];
				int y_guess_p2 = arr2[1];
				
				double dist_pl1 = Math.sqrt( Math.pow((x_guess_p1-x),2) + Math.pow((y_guess_p1-y),2));
				double dist_pl2 = Math.sqrt( Math.pow((x_guess_p2-x),2) + Math.pow((y_guess_p2-y),2));
				
				//System.out.println("x: "+x+" y: "+y+" :: x1: "+ x_guess_p1+" y: "+y_guess_p1+" :: x2: "+ x_guess_p2+" y2: "+y_guess_p2);
				if (dist_pl1 < dist_pl2) {
					player1Score++;
					System.out.println("Winner for round "+i+" is "+playerName1);
					out.println("Winner for round "+i+" is "+playerName1);
				}else if(dist_pl1 > dist_pl2) {
					player2Score++;
					System.out.println("Winner for round "+i+" is "+playerName2);
					out.println("Winner for round "+i+" is "+playerName2);
				}else {
					System.out.println("Winner for round "+i+" is Both players");
					out.println("Winner for round "+i+" is Both players");
				}
			}
			
			
			// Checking the Winner of the Game at the end
			if(player1Score == player2Score) {
				System.out.println("Game Winner is Both players");
				out.println("Game Winner is Both players");
			}else if(player1Score > player2Score ) {
				System.out.println("Game Winner is "+playerName1);
				out.println("Game Winner is "+ playerName1);
			}else {
				System.out.println("Game Winner is "+playerName2);
				out.println("Game Winner is "+playerName2);
			}
			
			// Closing sockets and I/O streams
			out.close();
			in.close();
			serverSocket.close();
			
		}catch(Exception e) { // catching error and printing if any
			System.out.println("Bir hata oluştu");
			e.printStackTrace();
		}
		
		
	}
	
	// Converter method takes String as an argument when Player inputs the x and y values
	// creates an int[] array which contains x in its 0th index and y in 1st index. Then returns
	private static int[] convertPointInputs(String str) {
		str = str.replace(" ", "");
		String[] arr = str.split(",");
		int x_guess = Integer.parseInt(arr[0]);
		int y_guess = Integer.parseInt(arr[1]);
		
		int[] points = new int[2];
		points[0] = x_guess;
		points[1] = y_guess;
		
		return points;
	}

}
