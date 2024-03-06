package Package;
import javax.net.ssl.SSLSocket;
import java.io.*;
import java.net.SocketTimeoutException;

/**
 * Copyright [Yahya Hassanzadeh-Nazarabadi]

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
 */


public class SSLServerThread extends Thread {
    private final String SERVER_ACK_MESSAGE = "server_ack";
    private SSLSocket sslSocket;
    private String line = new String();
    private BufferedReader is;
    private PrintWriter os;
    
    public SSLServerThread(SSLSocket sslSocket) {
        this.sslSocket = sslSocket;
    }

    public void run() {
        try {
            is = new BufferedReader(new InputStreamReader(sslSocket.getInputStream()));
            os = new PrintWriter(sslSocket.getOutputStream());
            
            String line;
            
            while((line = is.readLine()) != null) {
            	//System.out.println("line: "+line);
            	System.out.println("Client " + sslSocket.getRemoteSocketAddress() + " sent : " + line);
            	os.println(SERVER_ACK_MESSAGE);
                os.flush();
                //line = is.readLine();
            }
        }
        catch (IOException e) {
            line = this.getClass().toString(); //reused String line for getting thread name
            System.out.println("SSLServer Thread. Run. IO Error/ Client " + line + " terminated abruptly");
        }
        catch (NullPointerException e) {
            line = this.getClass().toString(); //reused String line for getting thread name
            System.out.println("SSLServer Thread. Run.Client " + line + " Closed");
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
                if (sslSocket != null) {
                    sslSocket.close();
                }
            }
            catch (IOException ie) {
                System.out.println("Socket Close Error");
            }
        }
    }
}