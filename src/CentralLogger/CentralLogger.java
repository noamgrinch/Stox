package CentralLogger;
/**@author Noam Greenshtain
 * A server which is responsible for logging log to the file.
 * Logging format has been changed by me.(class MyFormatter).
 */

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.ServerSocket;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;



public class CentralLogger implements Runnable{
	
	
	private Connection _con;
	private int index = 0;
	private Logger LOGGER = null;
	private int PORT;
	private String url = "jdbc:mysql://localhost:3306/logs?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC"; //DB is on the localhost.
	private boolean cont = true;
	
	public CentralLogger() {
		PORT=7777;
	}
	
	public CentralLogger(int port) {
		PORT=port;
	}
	
	public void setConnectionStringDB(String string) {
		url = string;
	}
	
	public void run(){
		
		FileHandler handler=null;

		LOGGER = Logger.getLogger(CentralLogger.class.getName());
		try {
			handler = new FileHandler("LogEntries.xml", true);
			handler.setFormatter(new MyFormatter());
		    String str = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\r\n" + "<logfile>\n";
		    BufferedWriter writer = new BufferedWriter(new FileWriter("LogEntries.xml"));
		    writer.write(str);
		     
		    writer.close();
		} catch (SecurityException | IOException e) {
			e.printStackTrace();
		}
		if(handler!=null){
			LOGGER.addHandler(handler);
		}
		
		ServerSocket ss;
		

		try {
			_con = getConnection(url);
			ss = new ServerSocket(PORT);
			System.out.println("Central logger is running on port " + PORT + "...");
			try {
				while(cont){
					new LogThread(ss.accept(),this).start();
				}
			}
			catch(Exception e) {
				System.out.println("Central Logger has encountred a problem:\n" + this.getExString(e));
				this.LogProcedure(new Exception("Central Logger has encountred a problem ---> " + this.getExString(e)),Level.SEVERE);
			}
		}
		
		catch(Exception e){
			try {
				this.LogProcedure(new Exception("Unknown Host Exception ---> " + this.getExString(e)),Level.SEVERE);
			} catch (ParserConfigurationException | SAXException | IOException | SQLException e1) {
				//Failure. handle.
			}
		}
	
	}//main
	
	public String getExString(Exception e){
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
	

	
	public Connection getConnection(String conString) throws Exception{
		Connection conn;
		
		try {
			String username = "root";
			String password ="pipoca12";	
			conn = DriverManager.getConnection(conString,username,password);
			System.out.println("Connection to the DB established");
			return conn;
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		return null;
		
	}
	
	
	@SuppressWarnings("deprecation")
	public synchronized void  LogProcedure(Exception e,Level l) throws ParserConfigurationException, SAXException, IOException, SQLException { 
		
		try {
			Exception _e = e; 
			Level _l = l;
		
			LOGGER.logrb(_l,_e.getStackTrace()[0].getClassName(),_e.getStackTrace()[0].getMethodName(),_e.getStackTrace()[0].getClassName(),this.getExString(_e));
			
			/*
			 * Parsing the XML file attributes
			 */
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			
			FileInputStream input = new FileInputStream("LogEntries.xml");
			Document doc = builder.parse(input);
			NodeList children = doc.getElementsByTagName("Level");
			Element record = (Element) children.item(index);
			String level = record.getTextContent();
			children = doc.getElementsByTagName("ClassName");
			record = (Element) children.item(index);
			String Class = record.getTextContent();
			children = doc.getElementsByTagName("MethodName");
			record = (Element) children.item(index);
			String Method = record.getTextContent();
			children = doc.getElementsByTagName("Application");
			record = (Element) children.item(index);
			String Application = record.getTextContent();
			String message = this.getExString(_e);
			children = doc.getElementsByTagName("Date");
			record = (Element) children.item(index);
			String CreateDate = record.getTextContent();
			Path path = Paths.get("LogEntries.xml");
			Charset charset = StandardCharsets.UTF_8;
			String content = new String(Files.readAllBytes(path), charset);
			content = content.replaceAll("</logfile>\n", "");
			Files.write(path, content.getBytes(charset));
			index++;	
			/*
			 * StoredProcedure
			 */
			PreparedStatement proc = _con.prepareStatement("insert into LogEntries (LogCreateDate,LogEntryDate,Level,ClassName,MethodName,Message,Application) values (?,?,?,?,?,?,?)",Statement.RETURN_GENERATED_KEYS); // Insert procedure.
			String EntryDate = new java.sql.Timestamp(new java.util.Date().getTime()).toString(); //Gets time-stamp if CURRENT DATE(LogENTRYDATE) in SQL format.		
			proc.setString(1, CreateDate);
			proc.setString(2, EntryDate);
			proc.setString(3, level);
			proc.setString(4, Class);
			proc.setString(5, Method);
			proc.setString(6, message);
			proc.setString(7, Application);
			
			ResultSet oldTableKeys = proc.getGeneratedKeys();
			int oldautoGeneratedID = 0;
			if(oldTableKeys.next()) { //Checks if there is more to insert.
				oldautoGeneratedID = oldTableKeys.getInt(1);
			}
			proc.executeUpdate();
			/*
			 * Checks for validation of insertion
			 */
			ResultSet tableKeys = proc.getGeneratedKeys();
			tableKeys.next();
			int autoGeneratedID = tableKeys.getInt(1);
			if(autoGeneratedID>oldautoGeneratedID) { //Checks if there was an error.
				//success
			}
			else {
				System.out.println("failure to insert to DB.");
			}	
			
			input.close();
			
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}


	}
	
	public void setEnable(boolean bool) {
		cont=bool;
	}

}
