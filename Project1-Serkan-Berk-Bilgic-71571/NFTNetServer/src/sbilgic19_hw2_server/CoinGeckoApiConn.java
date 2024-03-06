package sbilgic19_hw2_server;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class CoinGeckoApiConn {

	
	
	public CoinGeckoApiConn() {
		
	}
	
	public String getAllNFTs() throws MalformedURLException, IOException{
		URL url = new URL("https://api.coingecko.com/api/v3/nfts/list");
		
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.connect();
        String inline = "";
        Scanner scanner = new Scanner(url.openStream());
        while (scanner.hasNext()) {
            inline += scanner.nextLine();
        }
        //Close the scanner
        scanner.close();
       
        return inline;
	}
	
	public String getNFTsById(String ids) throws IOException {
		
		ids = ids.substring(1, ids.length()-1); // getting rid of {} paranthesis
		String[] idArr = ids.split(",");
		
		String inline = "";
		try {
			
		
			for(int i=0; i<idArr.length; i++) {
				URL url = new URL("https://api.coingecko.com/api/v3/nfts/"+idArr[i].trim());
				
		        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		        conn.setRequestMethod("GET");
		        conn.connect();
		        Scanner scanner = new Scanner(url.openStream());
		        while (scanner.hasNext()) {
		            inline += scanner.nextLine();
		        }
		        //Close the scanner
		        inline += "--";
		        scanner.close();
			}
			return inline;
		}catch(Exception ex) {
			return "Something unexpected happened. Please check your NFT ids";
		}
		
		
	}
	
   
}

