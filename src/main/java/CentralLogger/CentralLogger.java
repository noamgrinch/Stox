package CentralLogger;
/**@author Noam Greenshtain
 * A server which is responsible for logging log to the file.
 * Logging format has been changed by me.(class MyFormatter).
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.ServerSocket;
import java.util.logging.FileHandler;
import java.util.logging.Logger;



public class CentralLogger {
	

	
	public static void main(String[] args){
	
		final Logger LOGGER;
		FileHandler handler=null;

		LOGGER = Logger.getLogger(CentralLogger.class.getName());
		try {
			handler = new FileHandler("LogEntries.log", true);
			handler.setFormatter(new MyFormatter());
		} catch (SecurityException | IOException e) {
			e.printStackTrace();
		}
		if(handler!=null){
			LOGGER.addHandler(handler);
		}
		
		ServerSocket ss;
		
		boolean cont = true;
		try {
			ss = new ServerSocket(7777);
			while(cont){
				new LogThread(ss.accept()).run();
			}
		}
		
		catch(Exception e){
			LOGGER.severe(getExString(e));
			
		}
	
	}//main
	
	public static String getExString(Exception e){
		String sStackTrace=null;
		try{
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			sStackTrace = sw.toString(); 
			sw.close();
			pw.close();

		}
		catch(IOException ex){
			e.printStackTrace();
		}
		
		return sStackTrace;
	}//getExString
	
	/*public void setDirectory(String dir) {
		try {
			FileHandler newhand = new FileHandler(dir + ".log");
			newhand.setFormatter(new MyFormatter());

		} catch (SecurityException | IOException e) {
			e.printStackTrace();
		}
	}*/

}
