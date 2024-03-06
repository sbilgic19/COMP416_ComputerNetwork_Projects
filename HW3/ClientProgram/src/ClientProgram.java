import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ClientProgram {

	public static void main(String[] args) throws IOException {
		System.out.print("Enter your preferred connection SSL/TCP: "); 
		BufferedReader std_in = new BufferedReader(new InputStreamReader(System.in));
		String choice = std_in.readLine();
		choice = choice.toLowerCase();
		if (choice.equals("ssl")) {
			new SSLClient();
		}
		else if (choice.equals("tcp")) {
			new TCPClient();
		}
		else {
			System.out.print("There are only two available type SSL and TCP, restart the program!");
		}
	}

}
