package org.openjfx.hellofx;

import java.util.logging.Level;

import org.openjfx.hellofx.GUI.EditFrame;
import org.openjfx.hellofx.GUI.LoginFrame;
import org.openjfx.hellofx.GUI.StockBox;

import CentralLogger.SendLogThread;
import StockReader.Stock;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class MainFrame extends Application{

	@Override
	public void start(Stage stage) throws Exception {
		
		stage.setTitle("Stox");

		try {
				//LoginFrame.display(); // dispay later on
				
				Stock st = Stock.findStockName("BABA");
				
				VBox main = new VBox();
			
				//Edit button
				BorderPane br = new BorderPane();
				Button edit = new Button("Edit");
				HBox bottom = new HBox(10);
				bottom.setPadding(new Insets(5,5,5,5));
				bottom.getChildren().add(edit);
				bottom.setAlignment(Pos.CENTER);
				
				//stocks
				GridPane lay1 = new GridPane(); 
				lay1.setAlignment(Pos.TOP_CENTER);
				StockBox sb = new StockBox(st);
				lay1.getChildren().addAll(sb);
				GridPane.setFillWidth(sb, true);
				GridPane.setConstraints(sb, 0, 0);
				
				//general configuration
				main.getChildren().add(lay1);
				main.setFillWidth(true);
				
				br.setCenter(main);
				br.setBottom(bottom);
				BorderPane.setMargin(bottom, new Insets(5,5,5,5));
		        Scene scene = new Scene (br, 250, 500);
		        stage.setScene(scene);
		        stage.show();
		} 
		catch (Exception e) {
			e.printStackTrace();
			new SendLogThread(Level.SEVERE,e).start();
		}
		
        

		
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
