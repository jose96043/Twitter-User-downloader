import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class twitter {

	public static void main(String[] args){
		
		String counterFile = "counter.txt", rnumberFile = "rnumber.txt" ;
		String counter, rnumber;
		String name;
		String url;
		String date;
		String ipAddress ="";
		String portNumber = "";
		String limitString = "";
		int limit = 150;
		String directory = System.getProperty("user.dir");

		Command d = new Command();
		String os = System.getProperty("os.name").toLowerCase();
		boolean osWindows = os.indexOf("win") >= 0;
		
		try{
			for (int i = 0; i < args.length; i+=2){
	            if(args[i].equals("--ip")){
	            	ipAddress = args[i+1];
	            }
	            else if (args[i].equals("--port")){
	            	portNumber = args[i+1];
	            }
	            else if (args[i].equals("--directory") || args[i].equals("-d")){
	            	directory = args[i+1];
	            }else if (args[i].equals("--limit") || args[i].equals("-l")){
	            	limitString = args[i+1];
	            	limit = Integer.parseInt(limitString);
	            }
			}
			//it determines the Operating system to apply the correct slash.
			if(osWindows == true){
				directory = directory+"\\";
			}else{
				directory = directory+"/";
			}
			
			if (ipAddress == ""){
	            Downloader ip = new Downloader();
	            ipAddress = ip.getIpAddress();

			}
			if (portNumber == "")
				portNumber = "8080";
			
			File fCounter = new File(directory+counterFile);
			File fRnumber = new File(directory+rnumberFile);
			if(!fCounter.exists()){
				try {
					FileWriter fw = new FileWriter(directory+counterFile);
					BufferedWriter out = new BufferedWriter(fw);
					out.write("1\n");
					out.close();
					System.out.println("countert.txt file does not exist\nCreating file.");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(!fRnumber.exists()){
				try {
					FileWriter fw = new FileWriter(directory+rnumberFile);
					BufferedWriter out = new BufferedWriter(fw);
					out.write("1\n");
					out.close();
					System.out.println("rnumber.txt file does not exist\nCreating file.");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}


			System.out.println("IP: "+ipAddress);
			System.out.println("Port: "+portNumber);
			System.out.println("Limit: " + limit);
			System.out.println("Directory: "+ directory);
			
			
			int x=1;

			while(x<limit){
				++x;
				date=d.execCommand("date");
				
				ReaderBuffer bufferCounter = new ReaderBuffer();
				ReaderBuffer bufferRnumber = new ReaderBuffer();
				ReaderBuffer buffWr = new ReaderBuffer ();
				ReaderBuffer intBuff = new ReaderBuffer ();
				
			
				counter = bufferCounter.Reader(directory.concat(counterFile)); //gets the counter number from the counter.txt file
				rnumber = bufferRnumber.Reader(directory.concat(rnumberFile)); //gets the real number or real amount of actual files downloaded

		
				Downloader dLoader = new Downloader();    //sets the dLoader object in order to use for downloading xml file.
				dLoader.setCounter(rnumber);			  //this sets the rnumber in order to be used to determine what number was the prvious download
		
				
				url = "http://api.twitter.com/1/users/lookup.xml?user_id="+counter;
				
				name=rnumber.concat(".xml");
				
				buffWr.openStream(directory+name);
				System.out.println("<body>\n\t<api>tweeter</api>\n\t<requestnumber>"+rnumber+"</requestnumber>\n\t<detail></detail>\n\t<date>"+date+"</date>");	
				dLoader.Get(url, directory.concat(name), ipAddress, portNumber);
				System.out.println("</body>");
				buffWr.closeStream();
				
				buffWr.openStream(directory+counterFile);
				int intRnumber = intBuff.str2Int(counter) + 1;
				System.out.println(intRnumber);
				buffWr.closeStream();

				buffWr.openStream(directory+rnumberFile);
				System.out.println(dLoader.getCounter());
				buffWr.closeStream();
				
			}
		}catch (Exception e){
			System.err.println("Error: " + e.getMessage());
		}
	}
}
