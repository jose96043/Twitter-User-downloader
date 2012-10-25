import java.io.*;

public class ReaderBuffer {
	final PrintStream sdout = System.out;
	
	public String Reader(String directory) throws Exception{
		String output;
		FileInputStream stream = new FileInputStream(directory);
		DataInputStream data = new DataInputStream(stream);
		BufferedReader buffer = new BufferedReader(new InputStreamReader(data));
		output = buffer.readLine();
		buffer.close();
		return output;
	}
	
	public int str2Int(String str) {
		int result = Integer.parseInt(str);
		return result;
	}
	
	public void openStream (String dir) throws Exception{
		File file = new File(dir);
		file.createNewFile();
		FileOutputStream fis = new FileOutputStream(file);
		PrintStream out = new PrintStream(fis);
		System.setOut(out);
	}
	
	public void closeStream(){
		System.out.close();
		System.setOut(sdout);
	}
}
	

