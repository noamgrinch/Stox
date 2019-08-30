package CentralLogger;
/**
 * @author Noam Greenshtain
 * Writes a log to the file.
 */

import java.io.ObjectInputStream;
import java.net.Socket;

import java.util.logging.Level;



public class LogThread extends Thread{
	
	private Socket s;
	private Exception e;
	private ObjectInputStream in;

	private CentralLogger _cl;
	
	public LogThread(Socket s,CentralLogger cl){
		this.s=s;
		_cl=cl;

	}
	
	public void run(){
		Level level=null;
		try{
			in = new ObjectInputStream(s.getInputStream());
			level = (Level)in.readObject();
			e = (Exception)in.readObject();
			_cl.LogProcedure(e,level);
		}
		catch(Exception e) {
			// Find a solution
		}

	}
}