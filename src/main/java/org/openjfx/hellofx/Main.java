package org.openjfx.hellofx;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application{

	@SuppressWarnings("exports")
	@Override
	public void start(Stage primaryStage) throws Exception {
		new MainFrame().start(primaryStage);;
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
