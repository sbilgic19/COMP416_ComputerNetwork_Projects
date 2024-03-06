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
public class SSLClient {
    public final static String SSL_SERVER_ADDRESS = "localhost";
    public final static String MESSAGE_TO_SSL_SERVER = "71571COMP416";
    public final static int SSL_SERVER_PORT = 9999;

    public SSLClient() {
        /*
        Creates an SSLConnectToServer object on the specified server address and port
         */
        SSLConnectionToServer sslConnectToServer = new SSLConnectionToServer(SSL_SERVER_ADDRESS, SSL_SERVER_PORT);
        /*
        Connects to the server
         */
        sslConnectToServer.Connect();

        /*
        Sends a message over SSL socket to the server and prints out the received message from the server
         */
        
        long startTime = System.currentTimeMillis(); 
        for (char c: MESSAGE_TO_SSL_SERVER.toCharArray()) {
        	sslConnectToServer.SendForAnswer(c);
        }
        long endTime = System.currentTimeMillis();
        long total_time = endTime - startTime;
        System.out.println("Total time passed: " + total_time + "ms");

        /*
        Disconnects from the SSL server
         */
        sslConnectToServer.Disconnect();
    }
}
