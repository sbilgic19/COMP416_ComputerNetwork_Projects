import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.json.JSONArray;
import org.json.JSONObject;

public class MultithreadedClient {

	public static void main(String[] args) {
        ConnectionToServer connectionToServer = new ConnectionToServer(ConnectionToServer.DEFAULT_SERVER_ADDRESS, ConnectionToServer.DEFAULT_SERVER_PORT);
        connectionToServer.Connect();
//        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
//        Runnable task = () -> {
//            if(connectionToServer.checkConnection() == false) {
//            	 System.out.println("Closing the connection");
//            	 connectionToServer.Disconnect();
//            }
//        };

        // Schedule the task to run every 1 second
        //scheduler.scheduleAtFixedRate(task, 0, 1, TimeUnit.SECONDS);
        System.out.println("Welcome to NFTNet Server");
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your query: ");
        String message = scanner.nextLine();
        while (!message.equals("QUIT"))
        {

            String serverMsg = connectionToServer.SendForAnswer(message);
            if(serverMsg != null && serverMsg.contains("[{\"id\"")) { // all of the NFTs are called

            	String resultPart = serverMsg.substring(serverMsg.indexOf(" at  : ") + 9);
                String initialHandShaking = serverMsg.substring(0, serverMsg.indexOf(resultPart));
                int subStringIndex = resultPart.indexOf("[");
                
                System.out.println("Response from server: " + initialHandShaking);
                
                //System.out.println("=> Response: ");
                String resultJson = resultPart.substring(subStringIndex);
                JSONArray jsonObject = new JSONArray(resultJson);
                for (int i = 0; i < jsonObject.length(); i++)
                {
                    String id = jsonObject.getJSONObject(i).getString("id");
                    String name = jsonObject.getJSONObject(i).getString("name");
                    String symbol = jsonObject.getJSONObject(i).getString("symbol");
                    String contractAdress = jsonObject.getJSONObject(i).isNull("contract_address") ? null : jsonObject.getJSONObject(i).getString("contract_address");
                    String asset_plat_id = jsonObject.getJSONObject(i).getString("asset_platform_id");
                    System.out.println("id: "+id + "\tname: " + name + "\tsymbol: "+ symbol + "\tasset_platform_id: "+ asset_plat_id + "\tcontract_adress: "+ contractAdress);
                }
            }else if(serverMsg != null && serverMsg.contains("{\"id\"")) { // NFT by id called
            	
            
            	String resultPart = serverMsg.substring(serverMsg.indexOf(" at  : ") + 9);
            	String initialHandShaking = serverMsg.substring(0, serverMsg.indexOf(resultPart));
            	System.out.println("Response from server: " + initialHandShaking);
            	
            	String[] strArr = resultPart.split("--");
            	for(int k=0; k<strArr.length; k++) {
            		JSONObject jsonObject = new JSONObject(strArr[k]);
            		
            		String id = jsonObject.getString("id");
            		String asset_platform_id = jsonObject.getString("asset_platform_id");
            		String name = jsonObject.getString("name");
            		
            		JSONObject floor_price =  (JSONObject) jsonObject.get("floor_price");
            		Double floor_price_usd = floor_price.getDouble("usd");
            		Double floor_price_native_currency = floor_price.getDouble("native_currency");
            		
            		JSONObject market_cap =  (JSONObject) jsonObject.get("market_cap");
            		Double market_cap_usd = market_cap.getDouble("usd");
            		Double market_cap_native_currency = market_cap.getDouble("native_currency");
            		//System.out.println(floor_price.getInt("usd"));
            		System.out.println("id: "+id + "\tasset_platform_id:" + asset_platform_id + "\tname: " + name+ "\tfloor_price_usd: " 
            							+ floor_price_usd + "\tfloor_price_native_currency: " + floor_price_native_currency
            							+ "\tmarket_cap_usd: " + market_cap_usd + "\tmarket_cap_native_currency: " + market_cap_native_currency);
            		//System.out.println("id: "+id + "\tname: " + name);
            	}
            	
            
            }else if(serverMsg != null && serverMsg.contains("60")) { // Error code returned from NFTNetServer
              	 String resultPart = serverMsg.substring(serverMsg.indexOf(" at  : ") + 9);
                 String initialHandShaking = serverMsg.substring(0, serverMsg.indexOf(resultPart));
                 System.out.println("Response from server: " + initialHandShaking + "\n" + resultPart);    
            }else{
            	System.err.println("Something unexpected happened. Server might closed Socket because of timeout. Closing the Connection...");
            	//connectionToServer.Disconnect();
            	scanner.close();
            	break;
            }
           
            System.out.println("Enter your query: ");
            message = scanner.nextLine();
        }
        connectionToServer.Disconnect();
    }

}
