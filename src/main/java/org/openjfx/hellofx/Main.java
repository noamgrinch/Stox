package org.openjfx.hellofx;

import CentralLogger.CentralLogger;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application{

	@SuppressWarnings("exports")
	@Override
	public void start(Stage primaryStage) throws Exception {
		CentralLogger cl = new CentralLogger();
		Thread centrallogger = new Thread(cl);
		centrallogger.start();
		new MainFrame(centrallogger).start(primaryStage);;
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
