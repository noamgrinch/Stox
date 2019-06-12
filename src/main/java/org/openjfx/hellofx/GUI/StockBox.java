package org.openjfx.hellofx.GUI;

import org.openjfx.hellofx.MainFrame;

import StockReader.Stock;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

public class StockBox extends GridPane{
	
	private Stock stock;
	private Label slabel,sname,sprice,sper;

	
	
	public StockBox(Stock stock) {
		super();
		this.stock=stock;
        this.getStylesheets().clear();
        this.getStylesheets().add(MainFrame.class.getResource("StockBoxStyle.css").toExternalForm());
		Separator sep = new Separator();
		//sep.setBackground(new Background(new BackgroundFill(Color.BLACK, corn, Insets.EMPTY)));
		sep.setPadding(new Insets(2,2,0,2));
		slabel = new Label(stock.getLabel());
		slabel.setPadding(new Insets(2,0,2,1));
		slabel.setTextAlignment(TextAlignment.LEFT);
		slabel.setId("StockBox-label");
		//slabel.setBorder(new Border(new BorderStroke(Color.BLUE, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
		sname = new Label(stock.getName());
		sname.setPadding(new Insets(2,0,2,1));
		sname.setTextAlignment(TextAlignment.LEFT);
		sname.setId("StockBox-name");
		//sname.setBorder(new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
		sprice = new Label(Double.toString(stock.getPrice()) + "$");
		sprice.setPadding(new Insets(2,1,2,15));
		sprice.setFont(new Font("Arial",15));
		sprice.setTextAlignment(TextAlignment.RIGHT);
		sprice.setId("StockBox-price");
		//sprice.setBorder(new Border(new BorderStroke(Color.YELLOW, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
		sper = new Label(Double.toString(stock.getChangepercent()) + "%");
		if(stock.getChangepercent()>0) {
			sper.setId("StockBox-percentgreen-cust"); //green
		} 
		else {
			sper.setId("StockBox-percentred-cust"); //red
		}
		sper.setPadding(new Insets(2,5,2,7));
		//sper.setBorder(new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
		GridPane.setConstraints(slabel,0,0);
		GridPane.setConstraints(sname,0,1);
		GridPane.setConstraints(sprice,1,0);
		GridPane.setConstraints(sper,1,1);
		this.add(sep, 0, 4, 2, 1);
		GridPane.setHalignment(sper, HPos.RIGHT);
		GridPane.setHalignment(sprice, HPos.RIGHT);
		GridPane.setHalignment(sname, HPos.LEFT);
		GridPane.setHalignment(slabel, HPos.LEFT);
		ColumnConstraints column1 = new ColumnConstraints();
		column1.setPercentWidth(95-sprice.getText().length()*3);
		this.getColumnConstraints().add(column1);
		this.getChildren().addAll(slabel,sname,sprice,sper);
		//this.setBorder(new Border(new BorderStroke(Color.YELLOW, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

		
	}

	public Stock getStock() {
		return stock;
	}

	public void setStock(Stock stock) {
		this.stock = stock;
	}

}
