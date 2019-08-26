package org.openjfx.hellofx;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import org.openjfx.hellofx.GUI.MoveableScene;
import org.openjfx.hellofx.GUI.StockBox;
import org.openjfx.hellofx.GUI.StockEditFrame;
import CentralLogger.CentralLogger;
import CentralLogger.SendLogThread;
import StockReader.Stock;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;


public class MainFrame extends Application implements EventHandler<ActionEvent>{ 
	
	
	private ArrayList<Stock> stocks;
	private ArrayList<StockBox> stockboxes;
	private Stage stage;
	private CentralLogger centralogger;
	private BorderPane br;
	private Button edit;
	private HBox bottom;
	private ScrollPane scroll;
	private GridPane content;
	private ColumnConstraints column1;
	private Image image3;
	private MoveableScene mainscene;
	private Button exit,minimize;
	private HBox top;
	private Thread thread;
	private Date current;

	
	@SuppressWarnings("exports")
	public MainFrame(CentralLogger centralogger) {
		super();
		this.centralogger=centralogger;
	}
	
	@SuppressWarnings({ "exports" })
	@Override
	public void start(Stage stage) throws Exception {
		
		this.stage=stage;
		this.stage.setTitle("Stox");	
		this.stage.setResizable(false);
		stocks = new ArrayList<Stock>();
		stockboxes = new ArrayList<StockBox>();

		try {
				//LoginFrame.display(); // display later on
			
				//PrintWriter writer = new PrintWriter("myStocks.txt", "UTF-8");
				File file = new File("myStocks.txt");
				if(file.canRead()) {
					BufferedReader br = new BufferedReader(new FileReader(file)); 
					String sto;
					while((sto=br.readLine())!=null) {
						stocks.add(Stock.findStockName(sto));
					}
					br.close();
				}

				//for debugging

			
				//Edit button
				br = new BorderPane();
				br.setId("BorderPane-main");
				edit = new Button("Edit");
				edit.setPrefSize(45, 30);
				edit.setId("Button");
				edit.setOnAction(this);
				bottom = new HBox(10);
				bottom.getChildren().add(edit);
				bottom.setId("HBox-bottom");
				bottom.setAlignment(Pos.CENTER);
				
				
				top = new HBox(4);
				top.setAlignment(Pos.CENTER_RIGHT);
				top.setId("HBox-top");
				exit = new Button("X");
				exit.setId("Exit-button");
				exit.setOnAction(this);
				minimize = new Button("_");
				minimize.setId("Minimize-button");
				minimize.setOnAction(e -> ((Stage)((Button)e.getSource()).getScene().getWindow()).setIconified(true));
				top.getChildren().addAll(minimize,exit);
				
				
				// content panel
				scroll = new ScrollPane();
				scroll.setId("Scroll-main");
				scroll.getStyleClass().add ("edge-to-edge");
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
				br.setTop(top);
				mainscene = new MoveableScene (this.stage,br, 295, 500);
				mainscene.getStylesheets().clear();
				mainscene.getStylesheets().add(MainFrame.class.getResource("MainFrameStyle.css").toExternalForm());
		        image3 = new Image(MainFrame.class.getResource("thumbnail.jpg").toExternalForm(), 100, 0, false, false);
		        stage.getIcons().add(image3);
		        this.stage.setScene(mainscene);
		        this.stage.initStyle(StageStyle.TRANSPARENT);
		        this.stage.show();
		        
		        
		        thread = new Thread(new Runnable() { //Updating the stocks every 8 seconds.
		        	
		        	boolean active = true;
		            @Override
		            public void run() {
		                Runnable updater = new Runnable() {
		                	
		                	

		                   // @SuppressWarnings("deprecation")
							@Override
		                    public void run() {
		                       try {
		               
		                 		updateStockBoxes();
		                    	
							} catch (IOException e) {
								new SendLogThread(Level.SEVERE,e).start();
							}
		                    }
		                };
		                
		                current = new Date();

		                while (active) {
		                    try {
		                        Thread.sleep(8000);
		                    } catch (InterruptedException ex) {
		                    	new SendLogThread(Level.SEVERE,ex).start();
		                    }
		                    //if(rushHour(current)) {  
		                    	Platform.runLater(updater);
		                    //}
		                   // else {
		                    //	active=false;
		                   // }
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

	
	
	

	@SuppressWarnings("deprecation")
	private boolean rushHour(Date date) {
		int day = date.getDay();
		int hour = date.getHours();
		int minutes = date.getMinutes();
		if(day==0 || day==7)
		if((hour==16&&minutes>=47) || ((hour>16)&&(hour<22)) || (hour==22&&minutes<=47)) {
			return true;
		}

		return false;
	}

	@SuppressWarnings("exports")
	@Override
	public void handle(ActionEvent event) {
		if(event.getSource()==exit) {
			try {
				PrintWriter writer = new PrintWriter("myStocks.txt", "UTF-8");
				for(int i=0;i<stocks.size();i++) {
					writer.write(stocks.get(i).getLabel() + "\n");
				}
				writer.close();
				Exception e = new Exception("Application closed successfuly.");
				centralogger.setEnable(false);
				new SendLogThread(Level.INFO,e).start();
			} catch (Exception e) {
				new SendLogThread(Level.SEVERE,e).start();
			} 
			//centralogger.terminate();
	    	stage.close();
	    	Platform.exit();
	    
		}
		else {
			new StockEditFrame(stocks,this,stage);
		}
	}


	@SuppressWarnings("exports")
	public void updateList(ArrayList<Stock> stocks2) {
		
		stocks=stocks2;
		stockboxes.clear();
		br = new BorderPane();
		br.setId("BorderPane-main");
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
		br.setTop(top);
		mainscene = new MoveableScene (this.stage,br, 295, 500);
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
