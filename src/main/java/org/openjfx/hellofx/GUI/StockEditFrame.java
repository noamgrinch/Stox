package org.openjfx.hellofx.GUI;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;

import org.openjfx.hellofx.MainFrame;

import CentralLogger.SendLogThread;
import StockReader.Stock;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;

public class StockEditFrame implements EventHandler<ActionEvent> {
	
	private ArrayList<Stock> stocks;
	private Button delete,done,search;
	private Stage stage;
	private MainFrame parent;
	private ToolBar toolbar;
	private TextField searchlabel;
	
	public StockEditFrame(ArrayList<Stock> stocks,MainFrame parent) {
		
		this.setStocks(stocks);
		this.setParent(parent);
		
		BorderPane br = new BorderPane();
		
		//delete.setBorder(new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
		
		ScrollPane scroll = new ScrollPane();
		scroll.getStylesheets().clear();
		scroll.getStylesheets().add(MainFrame.class.getResource("MainFrameStyle.css").toExternalForm());
		scroll.getStyleClass ().add ("edge-to-edge");
		scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
		scroll.setFitToHeight(true);
		scroll.setFitToWidth(true);
		
		toolbar = new ToolBar();
		searchlabel = new TextField("");
		searchlabel.setPromptText("Search by label");
		toolbar.getItems().add(searchlabel);
		search = new Button("Search");
		search.setOnAction(this);
		toolbar.getItems().add(search);
		toolbar.setId("toolbar");
		
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
			sep = new Separator();
			sep.setId("StockBox-seprator");
			sb.add(sep, 0, 4, 2, 1);
			content.getChildren().addAll(/*delete,*/sb);
			GridPane.setFillWidth(sb, true);
			//GridPane.setFillWidth(delete, true);
			//GridPane.setHalignment(delete, HPos.CENTER);
			//GridPane.setConstraints(delete, 0,i);
			GridPane.setConstraints(sb, /*1*/0, i);
		}
		
		HBox bottom = new HBox();
		bottom.setId("HBox-bottom");
		done = new Button("Done");
		done.setOnAction(this);
		bottom.getChildren().add(done);
		bottom.setAlignment(Pos.CENTER);
		br.setBottom(bottom);
		scroll.setContent(content);
		content.setId("GridPane-content");
		br.setTop(toolbar);
		br.setCenter(scroll);
		stage = new Stage();
		Scene scene = new Scene(br,250,450);
		stage.setScene(scene);
		stage.setAlwaysOnTop(true);
		stage.show();
		
	}
	
	public void refresh() {
		BorderPane br = new BorderPane();
		
		//delete.setBorder(new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

		
		ScrollPane scroll = new ScrollPane();
		scroll.getStylesheets().clear();
		scroll.getStylesheets().add(MainFrame.class.getResource("MainFrameStyle.css").toExternalForm());
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
		
		toolbar = new ToolBar();
		searchlabel = new TextField("");
		toolbar.getItems().add(searchlabel);
		search = new Button("Search");
		search.setOnAction(this);
		toolbar.getItems().add(search);
		toolbar.setId("toolbar");
		
		EditStockBox sb;
		Separator sep;
		for(int i=0;i<stocks.size();i++) {
			//delete = new Button("X");
			//delete.setId("Delete-button");
			sb = stocks.get(i).toEdit(i,this);
			sep = new Separator();
			sep.setId("StockBox-seprator");
			sb.add(sep, 0, 4, 2, 1);
			content.getChildren().addAll(/*delete,*/sb);
			GridPane.setFillWidth(sb, true);
			//GridPane.setFillWidth(delete, true);
			//GridPane.setHalignment(delete, HPos.CENTER);
			//GridPane.setConstraints(delete, 0,i);
			GridPane.setConstraints(sb, /*1*/0, i);
		}
		HBox bottom = new HBox();
		bottom.setId("HBox-bottom");
		done = new Button("Done");
		done.setOnAction(this);
		content.setId("GridPane-content");
		bottom.getChildren().add(done);
		bottom.setAlignment(Pos.CENTER);
		br.setBottom(bottom);
		scroll.setContent(content);
		br.setTop(toolbar);
		br.setCenter(scroll);
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
	
	public void presentsearched(Stock stock,String label) {
		
   if(stock!=null) {
		BorderPane br = new BorderPane();
		//delete.setBorder(new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
		ScrollPane scroll = new ScrollPane();
		scroll.getStylesheets().clear();
		scroll.getStylesheets().add(MainFrame.class.getResource("MainFrameStyle.css").toExternalForm());
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
		
		toolbar = new ToolBar();
		searchlabel = new TextField("");
		searchlabel.setPromptText("Search by label");
		toolbar.getItems().add(searchlabel);
		search = new Button("Search");
		search.setOnAction(this);
		toolbar.getItems().add(search);
		toolbar.setId("toolbar");
		
		EditStockBox sb;
		Separator sep;
		for(int i=0;i<1;i++) {
			sb = new EditStockBox(stock,0,this,2);
			sep = new Separator();
			sep.setId("StockBox-seprator");
			sb.add(sep, 0, 4, 2, 1);
			content.getChildren().addAll(/*delete,*/sb);
			GridPane.setFillWidth(sb, true);
			GridPane.setConstraints(sb, /*1*/0, i);
		}
		HBox bottom = new HBox();
		bottom.setId("HBox-bottom");
		content.setId("GridPane-content");
		done = new Button("Done");
		done.setOnAction(this);
		bottom.getChildren().add(done);
		bottom.setAlignment(Pos.CENTER);
		br.setBottom(bottom);
		scroll.setContent(content);
		br.setTop(toolbar);
		br.setCenter(scroll);
		Scene scene = new Scene(br,250,450);
		stage.setScene(scene);
		stage.setAlwaysOnTop(true);
		stage.show();
   }
   else {
	   StockNotFoundAlert.display(label);
   }
	}
	
	public void deleteStock(int index) {
		stocks.remove(index);
		
		BorderPane br = new BorderPane();
		
		//delete.setBorder(new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

		
		ScrollPane scroll = new ScrollPane();
		scroll.getStylesheets().clear();
		scroll.getStylesheets().add(MainFrame.class.getResource("MainFrameStyle.css").toExternalForm());
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
		
		toolbar = new ToolBar();
		searchlabel = new TextField("");
		toolbar.getItems().add(searchlabel);
		search = new Button("Search");
		search.setOnAction(this);
		toolbar.getItems().add(search);
		toolbar.setId("toolbar");
		
		EditStockBox sb;
		Separator sep;
		for(int i=0;i<stocks.size();i++) {
			//delete = new Button("X");
			//delete.setId("Delete-button");
			sb = stocks.get(i).toEdit(i,this);
			sep = new Separator();
			sep.setId("StockBox-seprator");
			sb.add(sep, 0, 4, 2, 1);
			content.getChildren().addAll(/*delete,*/sb);
			GridPane.setFillWidth(sb, true);
			//GridPane.setFillWidth(delete, true);
			//GridPane.setHalignment(delete, HPos.CENTER);
			//GridPane.setConstraints(delete, 0,i);
			GridPane.setConstraints(sb, /*1*/0, i);
		}
		
		HBox bottom = new HBox();
		bottom.setId("HBox-bottom");
		done = new Button("Done");
		done.setOnAction(this);
		bottom.getChildren().add(done);
		bottom.setAlignment(Pos.CENTER);
		br.setBottom(bottom);
		content.setId("GridPane-content");
		scroll.setContent(content);
		br.setTop(toolbar);
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
		if(search == event.getSource()) {
			try {
				Stock searched = Stock.findStockName(searchlabel.getText().toUpperCase());
				presentsearched(searched,searchlabel.getText().toUpperCase());
				searchlabel.setText("");
			} catch (NumberFormatException | IOException e) {
				new SendLogThread(Level.SEVERE,e).start();
			}
		}
		else {
			parent.updateList(stocks);
			stage.close();
		}
	}
	
	public void addStock(Stock stock) {
		if(!this.stocks.contains(stock)) {
			this.stocks.add(stock);
		}
	}

}
