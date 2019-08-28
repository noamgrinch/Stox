package DB;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.logging.Level;

import CentralLogger.SendLogThread;
import javafx.scene.control.Dialog;

public class UserThread extends Thread{
	
	private Socket s;
	private ObjectInputStream in;
	private Connection con;
	
	public UserThread(Socket s,Connection con) {
		this.s=s;
		this.con=con;
		try {
			in = new ObjectInputStream(s.getInputStream());
		} catch (IOException e) {
			new SendLogThread(Level.SEVERE,e).start();
		}
	}

	
	public void run() {
		try {
			int flag = (int)in.readObject();
			if(flag==0) {
				loginFlow();			
			}
			else {
				registerFlow();
			}
		}
		catch (Exception e) {
			new SendLogThread(Level.SEVERE,e).start();
		}
	}
		
	public void loginFlow() {
		try {
			String username = (String)in.readObject();
			String password = (String)in.readObject();
			
			PreparedStatement proc = con.prepareStatement("SELECT * FROM users where UserName = ? and Password = ?"); // Search procedure.
			proc.setString(1, username);
			proc.setString(2, password);
			
			ResultSet rs = proc.executeQuery();
			
		    if(rs.next()) {  //success
		    	proc = con.prepareStatement("UPDATE users SET LastLoginDate = ? WHERE username=? and password=?"); // Search procedure.
		    	proc.setString(1, new java.sql.Timestamp(new java.util.Date().getTime()).toString());
				proc.setString(2, username);
				proc.setString(3, password);
		    	int result = proc.executeUpdate();
		    	if(result!=-1) {
		    		new SendLogThread(Level.INFO,new Exception("User " + username + "has logged in successfuly")).start();
		    	}
		    	else {
		    		new SendLogThread(Level.SEVERE,new Exception("There was a problem logging " + username + " into the system.")).start();
		    	}
		    	
		    } else { //failure
		    	new SendLogThread(Level.WARNING,new Exception("User " + username + "hasn't logged in successfuly")).start();
		    }
		} 
		catch (Exception e) {
			new SendLogThread(Level.SEVERE,e).start();
		}
		finally {
			try {
				in.close();
			} catch (IOException e) {
				new SendLogThread(Level.SEVERE,e).start();
			}
		}
	}
	
	public void registerFlow() {
		
	}
}
