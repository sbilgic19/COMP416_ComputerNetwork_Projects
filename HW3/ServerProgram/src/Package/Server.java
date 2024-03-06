package Package;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
	public static void main(String[] args) {
		
		SSLServerThreadMain sslServerMain = new SSLServerThreadMain();
		TCPServerThreadMain tcpServerMain = new TCPServerThreadMain();
		sslServerMain.start();
		tcpServerMain.start();

	}
	
	
}
class SSLServerThreadMain extends Thread {
	public void run() {
		SSLServer sslServer = new SSLServer(9999);
	}
}

class TCPServerThreadMain extends Thread {
	public void run() {
		TCPServer tcpServer = new TCPServer(9998);
	}
}