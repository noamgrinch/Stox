package Login;


import java.awt.Frame;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;

import CentralLogger.SendLogThread;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class LoginFrame implements EventHandler<ActionEvent>{ //TODO add functions to buttons.
	
	private Button login,cancel;
	private Socket soc;
	private TextField userinput,passinput;
	private ObjectOutputStream out;
	private final int LOGINFLOW = 0;
	private Stage frame;
	
	public void display() throws Exception {
		frame = new Stage();
		frame.initModality(Modality.APPLICATION_MODAL);
		frame.setTitle("Login");
		frame.setResizable(false);
		GridPane gr = new GridPane();
		gr.setPadding(new Insets(5,5,5,5));
		gr.setVgap(8);
		gr.setHgap(10);
		
		BorderPane br = new BorderPane();
		
		Label username = new Label("Username: ");
		GridPane.setConstraints(username, 0, 0);
		
		userinput = new TextField();
		userinput.setPromptText("Username");
		GridPane.setConstraints(userinput, 1, 0);
		
		Label password = new Label("Password: ");
		GridPane.setConstraints(password, 0, 1);
		
		passinput = new TextField();
		passinput.setPromptText("Password");
		GridPane.setConstraints(passinput, 1, 1);
		
		//buttons
		login = new Button("Login");
		login.setOnAction(this);
		cancel = new Button("Cancel");
		cancel.setOnAction(this);
		HBox hb = new HBox(20);
		hb.setPadding(new Insets(5,5,5,5));
		hb.getChildren().addAll(login,cancel);
		hb.setAlignment(Pos.CENTER);
		
		gr.getChildren().addAll(username,userinput,password,passinput);
		br.setCenter(gr);
		br.setBottom(hb);
		Scene scene = new Scene(br,250,110);
		
		frame.setScene(scene);
		frame.show();
		
	}


	@Override
	public void handle(ActionEvent event) {
		if(event.getSource()==login) {
			try {
				soc = new Socket("Localhost",8080);
				out = new ObjectOutputStream(soc.getOutputStream());
				out.writeObject(LOGINFLOW);
				out.writeObject(userinput.getText());
				out.writeObject(passinput.getText());
			} 
			catch (Exception e) {
				new SendLogThread(Level.SEVERE,e).start();
			}
			finally {
				try {
					soc.close();
					out.close();
				} catch (IOException e) {
					new SendLogThread(Level.SEVERE,e).start();
				}
				
			}
			
		}
		else {
			if(event.getSource() == cancel) {
				frame.close();
			}
		}
		
	}

}
