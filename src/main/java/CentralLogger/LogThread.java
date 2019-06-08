package CentralLogger;
/**
 * @author Noam Greenshtain
 * Writes a log to the file.
 */
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LogThread extends Thread{
	
	private Socket s;
	private Logger LOGGER;
	private Exception e;
	private ObjectInputStream in;
	
	public LogThread(Socket s){
		this.s=s;
		LOGGER = Logger.getLogger(CentralLogger.class.getName());

	}
	
	public void run(){
		String msg = null;
		Level level=null;
		try{
			in = new ObjectInputStream(s.getInputStream());
			level = (Level)in.readObject();
			e = (Exception)in.readObject();
			msg=CentralLogger.getExString(e);
			in.close();
		}
		catch(IOException | ClassNotFoundException e){
			e.printStackTrace();
		}
	    LOGGER.log(level,msg);
	}

}