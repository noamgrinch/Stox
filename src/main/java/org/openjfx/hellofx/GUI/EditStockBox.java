package org.openjfx.hellofx.GUI;

import org.openjfx.hellofx.MainFrame;

import StockReader.Stock;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class EditStockBox extends GridPane implements EventHandler<ActionEvent>{
	
	private Stock stock;
	private Label slabel,sname;
	private Button delete,add;
	private int index;
	private StockEditFrame parent;
	
	
	public EditStockBox(Stock stock,int index,StockEditFrame parent,int addordelete) {
		super();
		this.stock=stock;
		this.setIndex(index);
		this.parent=parent;
        this.getStylesheets().clear();
        this.getStylesheets().add(MainFrame.class.getResource("StockBoxStyle.css").toExternalForm());
        this.setId("EditStockBox-cust");
		//sep.setBackground(new Background(new BackgroundFill(Color.BLACK, corn, Insets.EMPTY)));
		slabel = new Label(stock.getLabel());
		slabel.setId("StockBox-label");
		//slabel.setBorder(new Border(new BorderStroke(Color.BLUE, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
		sname = new Label(stock.getName());
		sname.setId("StockBox-name");
		//sname.setBorder(new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
		if(addordelete==1) {
			delete = new Button("X");
			delete.setId("Delete-button");
			delete.setOnAction(this);
			GridPane.setConstraints(delete,0,0);
			GridPane.setRowSpan(delete, 2);
			GridPane.setHalignment(delete, HPos.CENTER);
		}
		else {
			add = new Button("+");
			add.setId("Add-button");
			add.setOnAction(this);
			GridPane.setConstraints(add,0,0);
			GridPane.setRowSpan(add, 2);
			GridPane.setHalignment(add, HPos.CENTER);
		}
		//delete.setBorder(new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
		//delete.setPadding(new Insets(0,0,0,10));
		GridPane.setConstraints(slabel,1,0);
		GridPane.setConstraints(sname,1,1);
		GridPane.setHalignment(sname, HPos.CENTER);
		GridPane.setHalignment(slabel, HPos.CENTER);
		ColumnConstraints column1 = new ColumnConstraints();
		column1.setHgrow(Priority.ALWAYS );
		//column1.setPercentWidth(70-slabel.getText().length()*3);
		//this.getColumnConstraints().add(column1);
		this.getColumnConstraints().addAll( new ColumnConstraints( 25 ), column1);
		if(addordelete==1) {
			this.getChildren().addAll(delete,slabel,sname);
		}
		else {
			this.getChildren().addAll(add,slabel,sname);
		}
		//this.setBorder(new Border(new BorderStroke(Color.YELLOW, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
		//this.setGridLinesVisible(true);

		
	}

	public Stock getStock() {
		return stock;
	}

	public void setStock(Stock stock) {
		this.stock = stock;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	@Override
	public void handle(ActionEvent event) {
		if(event.getSource()==delete) {
			parent.deleteStock(index);
		}
		else {
			parent.addStock(this.stock);
			parent.refresh();
		}
	}
	
}
