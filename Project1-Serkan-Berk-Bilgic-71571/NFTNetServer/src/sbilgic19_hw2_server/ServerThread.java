package sbilgic19_hw2_server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ServerThread extends Thread{

	
	protected BufferedReader is;
	protected PrintWriter os;
	protected Socket s;
	private String line = new String();
	private String lines = new String();
	
	
	public ServerThread(Socket s)
    {
        this.s = s;
    }

    /**
     * The server thread, echos the client until it receives the QUIT string from the client
     */
    public void run()
    {
        try
        {
        	
            is = new BufferedReader(new InputStreamReader(s.getInputStream()));
            os = new PrintWriter(s.getOutputStream());

            s.setSoTimeout(50000);
            line = is.readLine();
            s.setSoTimeout(50000);
            while (line.compareTo("QUIT") != 0)
            {
            	lines = "Client messaged : " + line + " at  : " + Thread.currentThread().getId();

                //os.println(lines);
                //os.flush();
                System.out.println("Client " + s.getRemoteSocketAddress() + " sent :  " + lines);
                //lines += "\n";
                
                CoinGeckoApiConn apiConn = new CoinGeckoApiConn();
                
                String lowercase_line = line.toLowerCase();
                String[] arr = lowercase_line.split("-");
                String idOfNfts = "";
            	boolean[] violationFlags = {true, true, true, true, true, false}; // {protocol_name, method_type, resource, version, id, general}
            	boolean idFlag = false;
            	if(arr[0].trim().equals("sbbp")) {
            		violationFlags[0] = false;
            	}
            	
            	/*
            	for(int i=1; i<arr.length; i++) {
            		if(arr[i].substring(2).trim().equals("nftserver")) {
            			violationFlags[2] = false;
            		}else if(arr[i].substring(2).trim().equals("get")) {
            			violationFlags[1] = false;
            		}else if(arr[i].substring(2).trim().equals("1.0")) {
            			violationFlags[3] = false;
            		}else {
            			//System.out.println(arr[i].substring(2).trim().equals("get"));
            			violationFlags[5] = true;
            			//System.out.println("Patladı");
            		}
            	}
            	*/
            	
            	for(int i=1; i<arr.length; i++) {
            		if(arr[i].contains("m/")) {
            			if(arr[i].substring(2).trim().equals("get")) {
            				violationFlags[1] = false;
            			}else {
            				violationFlags[1] = true;
            			}
            			
            		}else if(arr[i].contains("r/")) {
            			if(arr[i].substring(2).trim().equals("nftserver")) {
            				violationFlags[2] = false;
            			}else {
            				violationFlags[2] = true;
            			}
            			
            		}else if(arr[i].contains("v/")) {
            			if(arr[i].substring(2).trim().equals("1.0")) {
            				violationFlags[3] = false;
            			}else {
            				violationFlags[3] = true;
            			}
            		}else if(arr[i].contains("i/")) {
            			idFlag = true; //id is given to reach specific nft(s)
            			idOfNfts = arr[i].substring(2).trim();
            			//System.out.println(idOfNfts);
            			String regex = "\\{[^}]*\\}";
            		    Pattern pattern = Pattern.compile(regex);
            		    Matcher matcher = pattern.matcher(idOfNfts);
            		    if (matcher.matches()) {
            		    	violationFlags[4] = false;
            		    }else {
            		    	violationFlags[4] = true;
            		    }
            		    
            		}else{
            			violationFlags[5] = true;
            			
            		}
            	}
            	
            	if(violationFlags[5] == true) {
        			lines += "=> Unexpected Bad Request 605!"; // General problem with the request please review the API documentation!
        		}else if(violationFlags[0] == true) {
        			lines += "=> Bad Request 600! Problem with protocol naming"; // Problem with protocol naming. Either protocol name is not provided or protocol name is wrong!
        		}else if(violationFlags[1] == true) {
        			lines += "=> Bad Request 601! Problem with method type";	//  Problem with method type
        		}else if(violationFlags[2] == true) {
        			lines += "=> Bad Request 602! Problem with resource"; //  Problem with resource. Either resource is not provided or requested resource is not available
        		}else if(violationFlags[3] == true) {
        			lines += "=> Bad Request 603! Problem with the version input"; //  Problem with version. Either version is not provided or requested version is not available!
        		}else if(idFlag==false) {
                		String result = apiConn.getAllNFTs();
                		lines += result;
            	}else if(idFlag==true) {
            		if(violationFlags[4] == true) {
            			lines += "=> Bad Request 604!";
            		}else {
            			String result = apiConn.getNFTsById(idOfNfts);
                		lines += result;
            		}
            	}else {
            		lines += "=> For unknown reason server is crashed: ";
            	}
            		
            	
            	os.println(lines);
            	os.flush();
                line = is.readLine();
                s.setSoTimeout(50000);
            }
        }
        catch(SocketTimeoutException e) {
        	line = this.getName(); //reused String line for getting thread name
            System.err.println("Server Thread. Run. Client timeout exception! " + line + " terminated due to timeout");
        	os.println("Timeout exceeded connection is closed!");
            System.out.println(e.getMessage());
        }
        catch (IOException e)
        {
            line = this.getName(); //reused String line for getting thread name
            System.err.println("Server Thread. Run. IO Error/ Client " + line + " terminated abruptly");
        }
        catch (NullPointerException e)
        {
            line = this.getName(); //reused String line for getting thread name
            System.err.println("Server Thread. Run.Client " + line + " Closed");
        } finally
        {
            try
            {
                System.out.println("Closing the connection");
                if (is != null)
                {
                    is.close();
                    System.err.println(" Socket Input Stream Closed");
                }

                if (os != null)
                {
                    os.close();
                    System.err.println("Socket Out Closed");
                }
                if (s != null)
                {
                    s.close();
                    System.err.println("Socket Closed");
                }

            }
            catch (IOException ie)
            {
                System.err.println("Socket Close Error");
            }
        }//end finally
    }
	
}
