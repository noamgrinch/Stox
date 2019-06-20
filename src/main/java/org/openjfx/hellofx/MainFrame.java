package org.openjfx.hellofx;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import org.openjfx.hellofx.GUI.StockBox;
import org.openjfx.hellofx.GUI.StockEditFrame;
import CentralLogger.SendLogThread;
import StockReader.Stock;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;


public class MainFrame extends Application implements EventHandler<ActionEvent>{ 
	
	
	private ArrayList<Stock> stocks;
	private ArrayList<StockBox> stockboxes;
	private Stage stage;
	private Thread centralogger;
	private BorderPane br;
	private Button edit;
	private HBox bottom;
	private ScrollPane scroll;
	private GridPane content;
	private ColumnConstraints column1;
	private Image image3;
	private Scene mainscene;
	
	public MainFrame(Thread centralogger) {
		super();
		this.centralogger=centralogger;
	}
	
	@SuppressWarnings("exports")
	@Override
	public void start(Stage stage) throws Exception {
		this.stage=stage;
		this.stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override public void handle(WindowEvent t) {
		    	centralogger.interrupt();
		    	stage.close();
		    	Platform.exit();
		    }
		});
		this.stage.setTitle("Stox");	
		this.stage.setResizable(false);
		stocks = new ArrayList<Stock>();
		stockboxes = new ArrayList<StockBox>();

		try {
				//LoginFrame.display(); // display later on

				//for debugging
				stocks.add(Stock.findStockName("BABA"));
				stocks.add(Stock.findStockName("AWK"));
				stocks.add(Stock.findStockName("AAPL"));
				stocks.add(Stock.findStockName("BYND"));
				stocks.add(Stock.findStockName("INTL"));
			
				//Edit button
				br = new BorderPane();
				edit = new Button("Edit");
				edit.setPrefSize(45, 30);
				edit.setId("Button");
				edit.setOnAction(this);
				bottom = new HBox(10);
				bottom.getChildren().add(edit);
				bottom.setId("HBox-bottom");
				bottom.setAlignment(Pos.CENTER);
				
				
				// content panel
				scroll = new ScrollPane();
				scroll.getStyleClass ().add ("edge-to-edge");
				scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
				scroll.setFitToHeight(true);
				scroll.setFitToWidth(true);
				content = new GridPane();
				content.setId("GridPane-content");
				content.setAlignment(Pos.TOP_CENTER);
				column1 = new ColumnConstraints();
				column1.setPercentWidth(100);
				content.getColumnConstraints().add(column1);
				StockBox sb;
				Separator sep;
				for(int i=0;i<stocks.size();i++) {
					sb = new StockBox(stocks.get(i));
					stockboxes.add(sb);
					sep = new Separator();
					sep.setId("StockBox-seprator");
					sb.add(sep, 0, 4, 2, 1);
					content.getChildren().add(sb);
					sb.setId("StockBox-mainback-cust");
					GridPane.setFillWidth(sb, true);
					GridPane.setConstraints(sb, 0, i);	
				}
				

				//stocks
				scroll.setContent(content);
				//general configuration
				//content.setBorder(new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT))); //debugging
				br.setCenter(scroll);
				BorderPane.setMargin(content, new Insets(-3,-3,-3,-3));
				br.setBottom(bottom);
				mainscene = new Scene (br, 295, 500);
				mainscene.getStylesheets().clear();
				mainscene.getStylesheets().add(MainFrame.class.getResource("MainFrameStyle.css").toExternalForm());
		        image3 = new Image(MainFrame.class.getResource("thumbnail.jpg").toExternalForm(), 100, 0, false, false);
		        stage.getIcons().add(image3);
		        this.stage.setScene(mainscene);
		        this.stage.show();
		        
		        
		        Thread thread = new Thread(new Runnable() {

		            @Override
		            public void run() {
		                Runnable updater = new Runnable() {

		                    @Override
		                    public void run() {
		                       try {
								updateStockBoxes();
							} catch (IOException e) {
								new SendLogThread(Level.SEVERE,e).start();
							}
		                    }
		                };

		                while (true) {
		                    try {
		                        Thread.sleep(5000);
		                    } catch (InterruptedException ex) {
		                    	new SendLogThread(Level.SEVERE,ex).start();
		                    }
		                    Platform.runLater(updater);
		                }
		            }

		        });
		        thread.setDaemon(true);
		        thread.start();      
		} 
		catch (Exception e) {
			e.printStackTrace();
			new SendLogThread(Level.SEVERE,e).start();
		}
		
        
		
	}


	@SuppressWarnings("exports")
	@Override
	public void handle(ActionEvent event) {
		new StockEditFrame(stocks,this,stage);
	}


	@SuppressWarnings("exports")
	public void updateList(ArrayList<Stock> stocks2) {
		
		stocks=stocks2;
		stockboxes.clear();
		br = new BorderPane();

		content = new GridPane();
		content.setId("GridPane-content");
		content.setAlignment(Pos.TOP_CENTER);
		column1 = new ColumnConstraints();
		column1.setPercentWidth(100);
		content.getColumnConstraints().add(column1);
		StockBox sb;
		Separator sep;
		for(int i=0;i<stocks.size();i++) {
			sb = new StockBox(stocks.get(i));
			stockboxes.add(sb);
			sep = new Separator();
			sep.setId("StockBox-seprator");
			sb.add(sep, 0, 4, 2, 1);
			content.getChildren().add(sb);
			sb.setId("StockBox-mainback-cust");
			GridPane.setFillWidth(sb, true);
			GridPane.setConstraints(sb, 0, i);	
		}
		
		//stocks
		scroll.setContent(content);
		//general configuration
		
		//content.setBorder(new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT))); //debugging
		br.setCenter(scroll);
		BorderPane.setMargin(content, new Insets(-3,-3,-3,-3));
		br.setBottom(bottom);
		mainscene = new Scene (br, 295, 500);
		mainscene.getStylesheets().clear();
		mainscene.getStylesheets().add(MainFrame.class.getResource("MainFrameStyle.css").toExternalForm());
        this.stage.setScene(mainscene);
        this.stage.show();
	}
	
	public void updateStockBoxes() throws IOException {
		for(int i=0;i<stockboxes.size();i++) {
			stockboxes.get(i).updateStockBox();
		}
	}
	

}
