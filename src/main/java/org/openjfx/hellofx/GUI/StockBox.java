package org.openjfx.hellofx.GUI;

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
	private String labeltextcolor = "-fx-text-fill: white";
	
	
	public StockBox(Stock stock) {
		super();
		this.stock=stock;
		CornerRadii corn = new CornerRadii(5);
		Separator sep = new Separator();
		//sep.setBackground(new Background(new BackgroundFill(Color.BLACK, corn, Insets.EMPTY)));
		sep.setPadding(new Insets(2,2,0,2));
		slabel = new Label(stock.getLabel());
		slabel.setPadding(new Insets(2,15,2,1));
		slabel.setFont(new Font("Arial",15));
		slabel.setTextAlignment(TextAlignment.LEFT);
		slabel.setStyle(labeltextcolor);
		//slabel.setBorder(new Border(new BorderStroke(Color.BLUE, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
		sname = new Label(stock.getName());
		sname.setPadding(new Insets(2,15,2,1));
		sname.setTextAlignment(TextAlignment.LEFT);
		sname.setStyle(labeltextcolor);
		//sname.setBorder(new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
		sprice = new Label(Double.toString(stock.getPrice()) + "$");
		sprice.setPadding(new Insets(2,1,2,15));
		sprice.setFont(new Font("Arial",15));
		sprice.setTextAlignment(TextAlignment.RIGHT);
		sprice.setStyle(labeltextcolor);
		//sprice.setBorder(new Border(new BorderStroke(Color.YELLOW, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
		sper = new Label(Double.toString(stock.getChangepercent()) + "%");
		sper.setFont(new Font("Arial",15));
		//sper.setStyle(labeltextcolor);
		Color col;
		if(stock.getChangepercent()>0) {
			//sper.setStyle("-fx-background-color:linear-gradient(to right top, #48e511,#68e909,#81ee03,#97f201,#acf605);-fx-border-syle: round;-fx-text-alignment: center");
			col = Color.web("#7FFF00"); //green
		} 
		else {
			//sper.setStyle("-fx-background-color:linear-gradient(to right top, #ee0a0a, #f22213, #f7301b, #fb3c23, #ff462a); -fx-font-weight: bold");
			col = Color.web("#FF0000"); //red
		}
		Background background = new Background(new BackgroundFill(col, corn, Insets.EMPTY));
		sper.setBackground(background);
		sper.setPadding(new Insets(2,1,2,15));
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
		/*if(sprice.getText().length()==6) {
			column1.setPercentWidth(75);
		}
		else {
			column1.setPercentWidth(72);
		}*/
		column1.setPercentWidth(95-sprice.getText().length()*3);
		this.getColumnConstraints().add(column1);
		this.getChildren().addAll(slabel,sname,sprice,sper);
		background = new Background(new BackgroundFill(Color.BLACK, corn, Insets.EMPTY));
		this.setBackground(background);
		//this.setGridLinesVisible(true);
		//this.setBorder(new Border(new BorderStroke(Color.YELLOW, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

		
	}

	public Stock getStock() {
		return stock;
	}

	public void setStock(Stock stock) {
		this.stock = stock;
	}

}
