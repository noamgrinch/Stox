package org.openjfx.hellofx;

import java.util.ArrayList;
import java.util.logging.Level;
import org.openjfx.hellofx.GUI.StockBox;
import org.openjfx.hellofx.GUI.StockEditFrame;

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
import javafx.event.ActionEvent;
import javafx.event.EventHandler;


public class MainFrame extends Application implements EventHandler<ActionEvent>{ 
	
	
	private ArrayList<Stock> stocks;
	private Stage stage;
	@SuppressWarnings("exports")
	@Override
	

	public void start(Stage stage) throws Exception {
		this.stage=stage;
		this.stage.setTitle("Stox");	
		this.stage.setResizable(false);
		stocks = new ArrayList<Stock>();

		try {
				//LoginFrame.display(); // dispay later on

				//for debugging
				stocks.add(Stock.findStockName("BABA"));
				stocks.add(Stock.findStockName("AWK"));
				stocks.add(Stock.findStockName("AAPL"));
				stocks.add(Stock.findStockName("NVDA"));
				stocks.add(Stock.findStockName("INTL"));
				stocks.add(Stock.findStockName("AMZN"));
				//stocks.add(Stock.findStockName("DLR"));
				//stocks.add(Stock.findStockName("TSLA"));
				//stocks.add(Stock.findStockName("NOC"));
				//stocks.add(Stock.findStockName("TWTR"));
			
			
				//Edit button
				BorderPane br = new BorderPane();
				Button edit = new Button("Edit");
				edit.setOnAction(this);
				HBox bottom = new HBox(10);
				bottom.getChildren().add(edit);
				bottom.setId("HBox-bottom");
				bottom.setAlignment(Pos.CENTER);
				
				
				// content panel
				ScrollPane scroll = new ScrollPane();
				scroll.getStyleClass ().add ("edge-to-edge");
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
				for(int i=0;i<stocks.size();i++) {
					sb = new StockBox(stocks.get(i));
					if(i<stocks.size()-1) {
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
				BorderPane.setMargin(content, new Insets(-3,-3,-3,-3));
				br.setBottom(bottom);
				BorderPane.setMargin(bottom, new Insets(-3,-3,-3,-3));
				//BorderPane.setMargin(bottom, new Insets(5,5,5,5));
		        Scene scene = new Scene (br, 295, 500);
		        scene.getStylesheets().clear();
		        scene.getStylesheets().add(MainFrame.class.getResource("MainFrameStyle.css").toExternalForm());
		        this.stage.setScene(scene);
		        this.stage.show();
		} 
		catch (Exception e) {
			e.printStackTrace();
			new SendLogThread(Level.SEVERE,e).start();
		}
		
        

		
	}


	@Override
	public void handle(ActionEvent event) {
		new StockEditFrame(stocks,this);
	}


	public void updateList(ArrayList<Stock> stocks2) {
		stocks=stocks2;
		BorderPane br = new BorderPane();
		Button edit = new Button("Edit");
		edit.setOnAction(this);
		HBox bottom = new HBox(10);
		bottom.getChildren().add(edit);
		bottom.setId("HBox-bottom");
		bottom.setAlignment(Pos.CENTER);
		
		
		// content panel
		ScrollPane scroll = new ScrollPane();
		scroll.getStyleClass ().add ("edge-to-edge");
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
		for(int i=0;i<stocks.size();i++) {
			sb = new StockBox(stocks.get(i));
			if(i<stocks.size()-1) {
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
		BorderPane.setMargin(content, new Insets(-3,-3,-3,-3));
		br.setBottom(bottom);
		BorderPane.setMargin(bottom, new Insets(-3,-3,-3,-3));
		//BorderPane.setMargin(bottom, new Insets(5,5,5,5));
        Scene scene = new Scene (br, 295, 500);
        scene.getStylesheets().clear();
        scene.getStylesheets().add(MainFrame.class.getResource("MainFrameStyle.css").toExternalForm());
        this.stage.setScene(scene);
        this.stage.show();
	}
	


}
