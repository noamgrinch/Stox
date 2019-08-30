package Main;

import javax.swing.JOptionPane;

import CentralLogger.CentralLogger;
import Client.GUI.Alert;
import Server.DBHandler;

public class RunServers {
	
	public static void main(String[] args) {
			CentralLogger cl = new CentralLogger();
			Thread centrallogger = new Thread(cl);
			centrallogger.start();
			DBHandler db= new DBHandler();
			Thread dbt = new Thread(db);
			dbt.start();
			new Alert().display("Central Logger and main Server are running!\nPress on \"Got it\" to continure to the client program.");
		}
	

}
