import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Abdulrezzak Zekiye
 */	
public class RESTfulAPI {

    public static void main(String[] args) throws MalformedURLException, IOException {
        // Uncomment the following if you get an exception related to SSL certificate
		/*
		TrustManager[] trustAllCerts = new TrustManager[]{
        new X509TrustManager() {
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return null;
            }
            public void checkClientTrusted(
                java.security.cert.X509Certificate[] certs, String authType) {
            }
            public void checkServerTrusted(
                java.security.cert.X509Certificate[] certs, String authType) {
            }
        }};
        try {
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        } catch (Exception e) {
            
        }
		*/
    	
    	String ids = "{squiggly, autoglyph}";
    	ids = ids.substring(1, ids.length()-1);
    	String[] idArr = ids.split(",");
    	for (int k=0; k<idArr.length; k++) {
    		
    		try {
    			
    	
            URL url = new URL("https://api.coingecko.com/api/v3/nfts/"+idArr[k].trim());

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
            System.out.println(inline);
    	}catch(Exception ex) {
    		System.out.println("Something unexpected happened");
    	}
    	}
    	
    	
    	

        
        /*
        JSONArray jsonObject = new JSONArray(inline);
        for (int i = 0; i < jsonObject.length(); i++)
        {
            String id = jsonObject.getJSONObject(i).getString("id");
            String name = jsonObject.getJSONObject(i).getString("name");
            System.out.println(id + "\t" + name);
        }
        */
    }
    
}
