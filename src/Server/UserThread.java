package Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import CentralLogger.SendLogThread;



public class UserThread extends Thread{
	
	private Socket s;
	private ObjectInputStream in;
	private Connection con;
	private ObjectOutputStream out;
	private PreparedStatement proc;
	
	
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
			switch(flag) {
				case Flows.LOGINFLOW:
					loginFlow();
					break;
				
				case Flows.REGFLOW:
					registerFlow();
					break;
				
				case Flows.UPDATESTOCKSFLOW:
					updateStocksFlow();
					break;
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
			
			proc = con.prepareStatement("SELECT * FROM users where UserName = ? and Password = ?"); // Search procedure.
			proc.setString(1, username);
			proc.setString(2, password);
			ResultSet rs = proc.executeQuery();
			out = new ObjectOutputStream(s.getOutputStream());
			if(rs.next()) {
				String email = rs.getString("Email");
				String stocks = rs.getString("Stocks");
		    	proc = con.prepareStatement("UPDATE users SET LastLoginDate = ? WHERE username=? and password=?"); // Search procedure.
		    	proc.setString(1, new java.sql.Timestamp(new java.util.Date().getTime()).toString());
				proc.setString(2, username);
				proc.setString(3, password);
		    	int result = proc.executeUpdate();
		    	if(result!=-1) {
		    		new SendLogThread(Level.INFO,new Exception("User " + username + " has logged in successfuly")).start();
		    		out.writeObject(username);
		    		out.writeObject(password);
		    		out.writeObject(email);
		    		out.writeObject(stocks);
		    	}
		    	else {
		    		new SendLogThread(Level.SEVERE,new Exception("There was a problem logging " + username + " into the system.")).start();
		    		out.writeObject(null);
		    	}
				
			}
			
			else { //failure
		    	new SendLogThread(Level.WARNING,new Exception("User " + username + " does not exsits.")).start();
		    	out.writeObject(null);
		    }
		} 
		catch (Exception e) {
			new SendLogThread(Level.SEVERE,e).start();
		}
		finally {
			try {
				in.close();
				out.close();
			} catch (IOException e) {
				new SendLogThread(Level.SEVERE,e).start();
			}
		}
	}
	
	public void registerFlow() {
		try {
			String username = (String)in.readObject();
			String password = (String)in.readObject();
			String email = (String)in.readObject();
			out = new ObjectOutputStream(s.getOutputStream());
			if((username.equals(""))||(password.equals(""))||(password.equals(""))) { //validation of input
				new SendLogThread(Level.WARNING,new Exception("Missing input.")).start();
				out.writeObject(false);
			}
			else{
				if(userNameExsits(username)) {
			
					if(!DBHandler.validate(email)) { //validation of email
						new SendLogThread(Level.WARNING,new Exception("Email " + email + " is not valid")).start();
						out.writeObject(false);
					}
					else {
						proc = con.prepareStatement("INSERT into users (UserName,Password,Email,RegistrationDate) values (?,?,?,?)"); // Search procedure.
			
						proc.setString(1, username);
						proc.setString(2, password);
						proc.setString(3, email);
						proc.setString(4, new java.sql.Timestamp(new java.util.Date().getTime()).toString());
			
						int rs = proc.executeUpdate();
						if(rs>0) {
							new SendLogThread(Level.INFO,new Exception("User " + username + " registered successfuly")).start();
							out.writeObject(true);
						}
					}
		    	
					} 
				else { //failure
					new SendLogThread(Level.WARNING,new Exception("User " + username + " is already exsits")).start();
					out.writeObject(false);
				}
			}
		}
		catch(Exception e) {
			new SendLogThread(Level.SEVERE,e).start();
		}
	}
	
	private void updateStocksFlow() {
		try {
			String username = (String)in.readObject();
			String stocks = (String)in.readObject();
			if(stocks==null) {
				stocks = "";
			}
			proc = con.prepareStatement("UPDATE users SET Stocks = ? WHERE username=?");
			proc.setString(1, stocks);
			proc.setString(2, username);
			int rs = proc.executeUpdate();
			if(rs>0) {
				new SendLogThread(Level.INFO,new Exception("User " + username + " updated stocks in DB successfuly")).start();
			}
			else {
				new SendLogThread(Level.SEVERE,new Exception("There was a problem updating Stocks in DB.")).start();
			}
				
		}
		catch(Exception e) {
			new SendLogThread(Level.SEVERE,e).start();
		}
		
	}
	
	
	private boolean userNameExsits(String userName) throws SQLException {
		proc = con.prepareStatement("SELECT * FROM users where UserName = ?"); // Search procedure.
		proc.setString(1, userName);
		ResultSet rs = proc.executeQuery();
		if(rs.next()) {
			return false;
		}
		return true;
	}
	
}
