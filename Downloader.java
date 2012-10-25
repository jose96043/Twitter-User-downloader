
import java.net.*;
import java.io.*;
/**
 * The Downloader class is in charge of setting up the connection by
 * using the proxy information provided. This is the heart of the information
 * retrieval.
 * @author jose96043
 *
 */
public class Downloader{
	private int intRnumber = 0;
	private int i = 0;

	/**
	 * It determines when the connection expires (current twitter limit is 150 requests per hour)
	 * and makes the application sleep for one hour (3600000) before re-initializing. 
	 * Also it's in charge to write the downloaded information. 
	 * @param url
	 * @param directory
	 * @param proxyHost
	 * @param proxyPort
	 */
	
	public void Get(String url, String directory, String proxyHost, String proxyPort){
		try{
		
			System.setProperty("http.proxyHost", proxyHost);
			System.setProperty("http.proxyPort", proxyPort);
			
			URL source = new URL (url);
			URLConnection con = source.openConnection(); 
			if (con instanceof HttpURLConnection){
				HttpURLConnection httpConn = (HttpURLConnection) con;
				int statusCode = httpConn.getResponseCode();
				if (statusCode == 400){		
					try {
					    Thread.sleep(3600000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				else{
					BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
					String inputLine;
					intRnumber = intRnumber + 1; 				//gets the next id number
					while ((inputLine = in.readLine()) != null){
						/* This if statement is implemented in order to get rid of 
						<?xml version="1.0" encoding="UTF-8"?>tag since its
						added manually on the main class */ 
						if(i == 0){
							System.out.print("");
							++i;
						}
						else{
							System.out.println(inputLine);
						}
					}
					in.close();
				}
			}
		}catch(IOException a){
			/*If a file is created but there is no information
			 * downloaded the file is empty therefore it gets deleted. 
			 */
			File file = new File (directory);
			file.delete();
		}
	}
	
	/**
	 * Sets the new counter number to be used.
	 * @param counter
	 */
	public void setCounter(String counter){
		ReaderBuffer intBuff = new ReaderBuffer ();	
		intRnumber = intBuff.str2Int(counter);
	}
	/**
	 * Get the value of the counter.
	 * @return intRnumber;
	 */
	public int getCounter(){
		return intRnumber;
	}
	/**
	 * Gets the external IP address of the host computer
	 * @return null
	 */
    public String getIpAddress() {
    	try{
        URL myIP = new URL("http://api.externalip.net/ip/");
        BufferedReader in = new BufferedReader(
                             new InputStreamReader(myIP.openStream())
                            );
        return in.readLine();
    	}catch(MalformedURLException m ){
    		m.printStackTrace();
    	}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
    	
      }
	
}
