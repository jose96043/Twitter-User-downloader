import java.io.*;

public class Command {
	public String execCommand(String command){
		Process pCommand = null;
		String output = null;
		
		Runtime rt = Runtime.getRuntime();
		
		try {
			pCommand = rt.exec( command );
			BufferedReader stdInput = new BufferedReader(new InputStreamReader(pCommand.getInputStream()));
			output = stdInput.readLine();
			
			return output;
		} catch (IOException ioe) {
			return "Error executing date command";
		}
	}
}
