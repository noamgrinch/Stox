package DB;

import java.net.ServerSocket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import CentralLogger.LogThread;
import CentralLogger.SendLogThread;

public class DBHandler implements Runnable{
	
	private String url = "jdbc:mysql://localhost:3306/users?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC"; //DB is on the localhost.
	private int PORT;
	private Connection _con;
	private boolean cont = true;
	private ServerSocket ss;
	public static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);


	
	public DBHandler() {
		PORT=8080;
	}
	
	public DBHandler(int port) {
		PORT=port;
	}
	
	public void setConnectionStringDB(String string) {
		url = string;
	}

	@Override
	public void run() {


			try {
				_con = getConnection(url);
				ss = new ServerSocket(PORT);
				System.out.println("DBHandler is running on port " + PORT + "...");
				try {
					while(cont){
						new UserThread(ss.accept(),_con).start();
					}
				}
				catch(Exception e) {
					new SendLogThread(Level.SEVERE,e).start();
				}
				
			}
			catch(Exception e) {
				new SendLogThread(Level.SEVERE,e).start();
			}
			
			
	}
	
	public Connection getConnection(String conString) throws Exception{
		Connection conn;
		
		try {
			String username = "root";
			String password ="pipoca12";	
			conn = DriverManager.getConnection(conString,username,password);
			new SendLogThread(Level.INFO,new Exception("DBHandler has connected to the database.")).start();
			return conn;
		}
		catch(Exception e) {
			new SendLogThread(Level.SEVERE,e).start();
		}
		
		return null;
		
	}
	
	public void setEnable(boolean bool) {
		cont=bool;
	}
	
	public static boolean validate(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(emailStr);
        return matcher.find();
}
	
	

}
