package org.openjfx.hellofx;

import java.util.logging.Level;
import org.openjfx.hellofx.GUI.StockBox;
import CentralLogger.SendLogThread;
import StockReader.Stock;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.beans.binding.Bindings;


public class MainFrame extends Application{
	
	
	private Stock[] stocks;

	@SuppressWarnings("exports")
	@Override
	
	
	public void start(Stage stage) throws Exception {
		
		stage.setTitle("Stox");	
		stage.setResizable(false);
		stocks = new Stock[6];

		try {
				//LoginFrame.display(); // dispay later on

				//for debugging
				stocks[0] = Stock.findStockName("BABA");
				stocks[1] = Stock.findStockName("AWK");
				stocks[2] = Stock.findStockName("AAPL");
				stocks[3] = Stock.findStockName("NVDA");
				stocks[4] = Stock.findStockName("INTL");
				stocks[5] = Stock.findStockName("AMZN");
				//stocks[6] = Stock.findStockName("DLR");
				//stocks[7] = Stock.findStockName("TSLA");
				//stocks[8] = Stock.findStockName("NOC");
				//stocks[9] = Stock.findStockName("TWTR");
			
			
				//Edit button
				BorderPane br = new BorderPane();
				Button edit = new Button("Edit");
				HBox bottom = new HBox(10);
				//bottom.setPadding(new Insets(5,5,5,5));
				bottom.getChildren().add(edit);
				bottom.setAlignment(Pos.CENTER);
				
				
				// content panel
				ScrollPane scroll = new ScrollPane();
				scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
				scroll.setFitToHeight(true);
				scroll.setFitToWidth(true);
				GridPane content = new GridPane();
				content.setId("GridPane-content");
				content.setAlignment(Pos.TOP_CENTER);
				ColumnConstraints column1 = new ColumnConstraints();
				column1.setPercentWidth(100);
				content.getColumnConstraints().add(column1);
				StockBox sb;
				Separator sep;
				for(int i=0;i<stocks.length;i++) {
					sb = new StockBox(stocks[i]);
					if(i<stocks.length-1) {
						sep = new Separator();
						sep.setId("StockBox-seprator");
						sb.add(sep, 0, 4, 2, 1);
					}
					content.getChildren().add(sb);
					sb.setId("StockBox-mainback-cust");
					GridPane.setFillWidth(sb, true);
					GridPane.setConstraints(sb, 0, i);	
				}
				
				//stocks
				scroll.setContent(content);
				//general configuration
				//main.getChildren().add(scroll);
				//main.setFillWidth(true);
				//content.setBorder(new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT))); //debugging
				br.setCenter(scroll);
				br.setBottom(bottom);
				//BorderPane.setMargin(bottom, new Insets(5,5,5,5));
		        Scene scene = new Scene (br, 295, 500);
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
	


}
