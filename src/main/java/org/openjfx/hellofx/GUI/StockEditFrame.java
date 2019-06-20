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
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class StockEditFrame implements EventHandler<ActionEvent> {
	
	private ArrayList<Stock> stocks;
	private Button delete,done,search;
	private Stage stage;
	private MainFrame parent;
	private HBox toolbar;
	private TextField searchlabel;
	private ScrollPane scroll;
	private GridPane content;
	private HBox bottom;
	private BorderPane br;
	private Scene scene;
	private Image image3;
	
	public StockEditFrame(ArrayList<Stock> stocks,MainFrame parent,Stage stage) {
		
		this.setStocks(stocks);
		this.setParent(parent);
		this.stage=stage;
		br = new BorderPane();
		
		//delete.setBorder(new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
		
		scroll = new ScrollPane();
		scroll.getStyleClass ().add ("edge-to-edge");
		scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
		scroll.setFitToHeight(true);
		scroll.setFitToWidth(true);
		
		toolbar = new HBox(10);
		toolbar.setAlignment(Pos.CENTER);
		searchlabel = new TextField("");
		searchlabel.setPromptText("Search by label");
		searchlabel.setId("Search-label");
		toolbar.getChildren().add(searchlabel);
		search = new Button("Search");
		search.setId("Button");;
		search.setOnAction(this);
		toolbar.getChildren().add(search);
		toolbar.setId("Toolbar");
		
		content = new GridPane();
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
		
		bottom = new HBox();
		bottom.setId("HBox-bottom");
		done = new Button("Done");
		done.setPrefSize(45, 30);
		done.setId("Button");
		done.setOnAction(this);
		bottom.getChildren().add(done);
		bottom.setAlignment(Pos.CENTER);
		br.setBottom(bottom);
		scroll.setContent(content);
		content.setId("GridPane-content");
		br.setTop(toolbar);
		br.setCenter(scroll);
		//stage = new Stage();
		stage.setTitle("Edit");
		scene = new Scene(br,295,500);
		scene.getStylesheets().clear();
		scene.getStylesheets().add(MainFrame.class.getResource("MainFrameStyle.css").toExternalForm());
        image3 = new Image(MainFrame.class.getResource("thumbnail.jpg").toExternalForm(), 100, 0, false, false);
        stage.getIcons().add(image3);
		stage.setScene(scene);
		stage.setAlwaysOnTop(true);
		stage.show();
		
	}
	
	public void refresh() {
		
		
		br = new BorderPane();
		
		//delete.setBorder(new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

		content = new GridPane();
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

		br.setBottom(bottom);
		content.setId("GridPane-content");
		scroll.setContent(content);
		br.setTop(toolbar);
		br.setCenter(scroll);
		scene.setRoot(br);
		scene.getStylesheets().clear();
		scene.getStylesheets().add(MainFrame.class.getResource("MainFrameStyle.css").toExternalForm());
		stage.setScene(scene);

	}

	public ArrayList<Stock> getStocks() {
		return stocks;
	}

	public void setStocks(ArrayList<Stock> stocks) {
		this.stocks = stocks;
	}
	
	public void presentsearched(Stock stock,String label) {
		
		if(stock!=null) {
	   
			br = new BorderPane();
			//delete.setBorder(new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

		
			content = new GridPane();
			content.setGridLinesVisible(true);
			content.setAlignment(Pos.TOP_CENTER);
			ColumnConstraints column1 = new ColumnConstraints();
			column1.setHgrow(Priority.ALWAYS );
			content.getColumnConstraints().addAll(/*new ColumnConstraints(25),*/column1);
		
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

			br.setBottom(bottom);
			content.setId("GridPane-content");
			scroll.setContent(content);
			br.setTop(toolbar);
			br.setCenter(scroll);
			scene.setRoot(br);
			scene.getStylesheets().clear();
			scene.getStylesheets().add(MainFrame.class.getResource("MainFrameStyle.css").toExternalForm());
			stage.setScene(scene);

		}
		else {
			StockNotFoundAlert.display(label);
		}
	}
	
	public void deleteStock(int index) {
		stocks.remove(index);
		

		br = new BorderPane();
		
		//delete.setBorder(new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

		
		content = new GridPane();
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
		

		br.setBottom(bottom);
		content.setId("GridPane-content");
		scroll.setContent(content);
		br.setTop(toolbar);
		br.setCenter(scroll);
		scene.setRoot(br);
		scene.getStylesheets().clear();
		scene.getStylesheets().add(MainFrame.class.getResource("MainFrameStyle.css").toExternalForm());
		stage.setScene(scene);

		
		
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
		}
	}
	
	public void addStock(Stock stock) {
		if(!this.stocks.contains(stock)) {
			this.stocks.add(stock);
		}
	}

}
