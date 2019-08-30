package Client.GUI;


import StockReader.Stock;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;


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
        this.getStylesheets().add(EditStockBox.class.getResource("StockBoxStyle.css").toExternalForm());
        this.setId("EditStockBox-cust");
		slabel = new Label(stock.getLabel());
		slabel.setId("StockBox-label");
		sname = new Label(stock.getName());
		sname.setId("StockBox-name");
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

		GridPane.setConstraints(slabel,1,0);
		GridPane.setConstraints(sname,1,1);
		GridPane.setHalignment(sname, HPos.CENTER);
		GridPane.setHalignment(slabel, HPos.CENTER);
		ColumnConstraints column1 = new ColumnConstraints();
		column1.setHgrow(Priority.ALWAYS );
		this.getColumnConstraints().addAll( new ColumnConstraints( 25 ), column1);
		if(addordelete==1) {
			this.getChildren().addAll(delete,slabel,sname);
		}
		else {
			this.getChildren().addAll(add,slabel,sname);
		}

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
