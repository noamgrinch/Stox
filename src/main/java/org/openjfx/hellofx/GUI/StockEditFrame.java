package org.openjfx.hellofx.GUI;

import java.util.ArrayList;

import org.openjfx.hellofx.MainFrame;

import StockReader.Stock;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;

public class StockEditFrame implements EventHandler<ActionEvent> {
	
	private ArrayList<Stock> stocks;
	private Button delete,done;
	private Stage stage;
	private MainFrame parent;
	
	public  StockEditFrame(ArrayList<Stock> stocks,MainFrame parent) {
		
		this.setStocks(stocks);
		this.setParent(parent);
		
		BorderPane br = new BorderPane();
		
		//delete.setBorder(new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

		
		ScrollPane scroll = new ScrollPane();
		scroll.getStylesheets().clear();
		scroll.getStylesheets().add(MainFrame.class.getResource("StockBoxStyle.css").toExternalForm());
		scroll.getStyleClass ().add ("edge-to-edge");
		scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
		scroll.setFitToHeight(true);
		scroll.setFitToWidth(true);
		
		GridPane content = new GridPane();
		content.setGridLinesVisible(true);
		content.setAlignment(Pos.TOP_CENTER);
		ColumnConstraints column1 = new ColumnConstraints();
		column1.setHgrow(Priority.ALWAYS );
		content.getColumnConstraints().addAll(/*new ColumnConstraints(25),*/column1);
		
		EditStockBox sb;
		Separator sep;
		for(int i=0;i<stocks.size();i++) {
			//delete = new Button("X");
			//delete.setId("Delete-button");
			sb = stocks.get(i).toEdit(i,this);
			if(i<stocks.size()-1) {
				sep = new Separator();
				sep.setId("StockBox-seprator");
				sb.add(sep, 0, 4, 2, 1);
			}
			content.getChildren().addAll(/*delete,*/sb);
			GridPane.setFillWidth(sb, true);
			//GridPane.setFillWidth(delete, true);
			//GridPane.setHalignment(delete, HPos.CENTER);
			//GridPane.setConstraints(delete, 0,i);
			GridPane.setConstraints(sb, /*1*/0, i);
		}
		
		HBox bottom = new HBox();
		done = new Button("Done");
		done.setOnAction(this);
		bottom.getChildren().add(done);
		bottom.setAlignment(Pos.CENTER);
		br.setBottom(bottom);
		scroll.setContent(content);
		br.setCenter(scroll);
		stage = new Stage();
		Scene scene = new Scene(br,250,450);
		stage.setScene(scene);
		stage.setAlwaysOnTop(true);
		stage.show();
		
	}

	public ArrayList<Stock> getStocks() {
		return stocks;
	}

	public void setStocks(ArrayList<Stock> stocks) {
		this.stocks = stocks;
	}
	
	public void deleteStock(int index) {
		stocks.remove(index);
		
		BorderPane br = new BorderPane();
		
		//delete.setBorder(new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

		
		ScrollPane scroll = new ScrollPane();
		scroll.getStylesheets().clear();
		scroll.getStylesheets().add(MainFrame.class.getResource("StockBoxStyle.css").toExternalForm());
		scroll.getStyleClass ().add ("edge-to-edge");
		scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
		scroll.setFitToHeight(true);
		scroll.setFitToWidth(true);
		
		GridPane content = new GridPane();
		content.setGridLinesVisible(true);
		content.setAlignment(Pos.TOP_CENTER);
		ColumnConstraints column1 = new ColumnConstraints();
		column1.setHgrow(Priority.ALWAYS );
		content.getColumnConstraints().addAll(/*new ColumnConstraints(25),*/column1);
		
		EditStockBox sb;
		Separator sep;
		for(int i=0;i<stocks.size();i++) {
			//delete = new Button("X");
			//delete.setId("Delete-button");
			sb = stocks.get(i).toEdit(i,this);
			if(i<stocks.size()-1) {
				sep = new Separator();
				sep.setId("StockBox-seprator");
				sb.add(sep, 0, 4, 2, 1);
			}
			content.getChildren().addAll(/*delete,*/sb);
			GridPane.setFillWidth(sb, true);
			//GridPane.setFillWidth(delete, true);
			//GridPane.setHalignment(delete, HPos.CENTER);
			//GridPane.setConstraints(delete, 0,i);
			GridPane.setConstraints(sb, /*1*/0, i);
		}
		
		HBox bottom = new HBox();
		done = new Button("Done");
		done.setOnAction(this);
		bottom.getChildren().add(done);
		bottom.setAlignment(Pos.CENTER);
		br.setBottom(bottom);
		scroll.setContent(content);
		br.setCenter(scroll);
		Scene scene = new Scene(br,250,450);
		stage.setScene(scene);
		stage.setAlwaysOnTop(true);
		stage.show();
		
	}

	public MainFrame getParent() {
		return parent;
	}

	public void setParent(MainFrame parent) {
		this.parent = parent;
	}

	public Button getDelete() {
		return delete;
	}

	public void setDelete(Button delete) {
		this.delete = delete;
	}

	@Override
	public void handle(ActionEvent event) {
		parent.updateList(stocks);
		
	}

}
