package org.openjfx.hellofx;

import java.util.logging.Level;

import CentralLogger.CentralLogger;
import CentralLogger.SendLogThread;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application{

	@SuppressWarnings("exports")
	@Override
	public void start(Stage primaryStage) {
		try {
			CentralLogger cl = new CentralLogger();
			Thread centrallogger = new Thread(cl);
			centrallogger.start();
			new SendLogThread(Level.INFO,new Exception("Central logger has started running")).start();
			new MainFrame(cl).start(primaryStage);
			new SendLogThread(Level.INFO,new Exception("Stox successfuly booted up")).start();
		}
		catch(Exception e) {
			new SendLogThread(Level.SEVERE,e).start();
		}
		
	}
	
	public static void main(String[] args) {
		launch(args);

	}

}
