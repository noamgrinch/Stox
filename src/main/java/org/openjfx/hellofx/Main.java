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
			new MainFrame(centrallogger).start(primaryStage);
		}
		catch(Exception e) {
			new SendLogThread(Level.SEVERE,e).start();
		}
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
