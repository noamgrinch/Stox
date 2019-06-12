package org.openjfx.hellofx;

import java.io.File;
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
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class MainFrame extends Application{

	@Override
	public void start(Stage stage) throws Exception {
		
		stage.setTitle("Stox");	
		stage.setResizable(false);
		Stock[] ss = new Stock[10];

		try {
				//LoginFrame.display(); // dispay later on
			

				
				ss[0] = Stock.findStockName("BABA");
				ss[1] = Stock.findStockName("AWK");
				ss[2] = Stock.findStockName("AAPL");
				ss[3] = Stock.findStockName("NVDA");
				ss[4] = Stock.findStockName("INTL");
				ss[5] = Stock.findStockName("AMZN");
				ss[6] = Stock.findStockName("DLR");
				ss[7] = Stock.findStockName("TSLA");
				ss[8] = Stock.findStockName("NOC");
				ss[9] = Stock.findStockName("TWTR");
				
				VBox main = new VBox();
				main.setPadding(new Insets(3,3,3,3));
			
				//Edit button
				BorderPane br = new BorderPane();
				Button edit = new Button("Edit");
				HBox bottom = new HBox(10);
				bottom.setPadding(new Insets(5,5,5,5));
				bottom.getChildren().add(edit);
				bottom.setAlignment(Pos.CENTER);
				
				ScrollPane scroll = new ScrollPane();

				GridPane content = new GridPane();
				//lay1.setVgap(2);
				content.setAlignment(Pos.TOP_CENTER);
				ColumnConstraints column1 = new ColumnConstraints();
				column1.setPercentWidth(100);
				content.getColumnConstraints().add(column1);
				
				StockBox sb;
				for(int i=0;i<ss.length;i++) {
					sb = new StockBox(ss[i]);
					content.getChildren().add(sb);
					sb.setId("StockBox-mainback-cust");
					GridPane.setFillWidth(sb, true);
					GridPane.setConstraints(sb, 0, i);	
				}
				
				//stocks
				scroll.setContent(content);
				
				
				//general configuration
				main.getChildren().add(scroll);
				main.setFillWidth(true);
				
				br.setCenter(main);
				br.setBottom(bottom);
				BorderPane.setMargin(bottom, new Insets(5,5,5,5));
		        Scene scene = new Scene (br, 297, 500);
		        scene.getStylesheets().clear();
		        scene.getStylesheets().add(MainFrame.class.getResource("MainFrameStyle.css").toExternalForm());
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
