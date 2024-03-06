package Package;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.ServerSocket;
import java.net.Socket;


public class TCPServer
{
    private ServerSocket tcpServerSocket;
    /**
     * Initiates a server socket on the input port, listens to the line, on receiving an incoming
     * connection creates and starts a TCPServerThread on the client
     * @param port
     */
    public TCPServer(int port){
        try {
            tcpServerSocket = new ServerSocket(port);
            System.out.println("TCP server is up and running on port " + port);
        }
        catch (IOException e) {
            e.printStackTrace();
            System.err.println("TCPServer class.Constructor exception on oppening a server socket");
        }
        while (true) {
            ListenAndAccept();
        }
    }

    /**
     * Listens to the line and starts a connection on receiving a request from the client
     * The connection is started and initiated as a TCPServerThread object
     */
    private void ListenAndAccept() {
        Socket tcpSocket;
        try {
            tcpSocket = tcpServerSocket.accept();
            System.out.println("A TCP connection was established with a client on the address of " + tcpSocket.getRemoteSocketAddress());
            TCPServerThread tcpServerThread = new TCPServerThread(tcpSocket);
            tcpServerThread.start();
        }
        catch (Exception e) {
            e.printStackTrace();
            System.err.println("TCPServer Class. Connection establishment error inside listen and accept function");
        }
    }
}