package Client;

import java.util.logging.Level;


import CentralLogger.SendLogThread;
import Client.GUI.MainFrame;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application{

	@Override
	public void start(Stage primaryStage) {
		try {
			new MainFrame().start(primaryStage);
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
