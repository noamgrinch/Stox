package Client.GUI;

import java.io.IOException;
import java.util.logging.Level;
import CentralLogger.SendLogThread;
import StockReader.Stock;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;


public class StockBox extends GridPane{
	
	private Stock stock;
	private Label slabel,sname,sprice,sper;

	
	
	public StockBox(Stock stock) {
		super();
		try {
			if(stock==null) {
				throw new Exception("Failed to initilize StockBox because of null value");
			}
			this.stock=stock;
			this.getStylesheets().clear();
			this.getStylesheets().add(StockBox.class.getResource("StockBoxStyle.css").toExternalForm());
			slabel = new Label(stock.getLabel());
			slabel.setId("StockBox-label");
			sname = new Label(stock.getName());
			sname.setId("StockBox-name");
			sprice = new Label(Double.toString(stock.getPrice()) + "$");
			sprice.setFont(new Font("Arial",15));
			sprice.setId("StockBox-price");
			sper = new Label();
			sper.setPrefSize(55, 20);
			if(stock.getChangepercent()>0) {
				sper.setId("StockBox-percentgreen-cust"); //green
				sper.setText(("+" + Double.toString(stock.getChangepercent()) + "%"));
			} 
			else {
				sper.setId("StockBox-percentred-cust"); //red
				sper.setText((Double.toString(stock.getChangepercent()) + "%"));
			}
			GridPane.setConstraints(slabel,0,0);
			GridPane.setConstraints(sname,0,1);
			GridPane.setConstraints(sprice,1,0);
			GridPane.setConstraints(sper,1,1);
			GridPane.setHalignment(sper, HPos.RIGHT);
			GridPane.setHalignment(sprice, HPos.RIGHT);
			GridPane.setHalignment(sname, HPos.LEFT);
			GridPane.setHalignment(slabel, HPos.LEFT);
			ColumnConstraints column1 = new ColumnConstraints();
			column1.setPercentWidth(95-sprice.getText().length()*3);
			this.getColumnConstraints().add(column1);
			this.getChildren().addAll(slabel,sname,sprice,sper);
			}
		catch(Exception e) {
				new SendLogThread(Level.SEVERE,e).start();
		}

	}
	


	public Stock getStock() {
		return stock;
	}

	public void setStock(Stock stock) {
		this.stock = stock;
	}



	
	public void updateStockBox() throws IOException {
		try {
			this.stock.updateStats();
			String newprice = Double.toString(stock.getPrice());
			sprice.setText(newprice + "$");
			if(stock.getChangepercent()>0) {
				sper.setId("StockBox-percentgreen-cust"); //green
				sper.setText("+" + Double.toString(stock.getChangepercent())+ "%");
			} 
			else {
				sper.setId("StockBox-percentred-cust"); //red
				sper.setText(Double.toString(stock.getChangepercent())+ "%");
			}
		}
		catch(NumberFormatException e) {
			//parsing error. Happens sometimes due to timing with HTML updating.
		}
		this.getColumnConstraints().get(0).setPercentWidth(95-sprice.getText().length()*3);
		sper.setAlignment(Pos.CENTER);
		
	}
	

}
