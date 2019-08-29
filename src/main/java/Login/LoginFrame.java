package Login;


import java.awt.Frame;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
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
	
	private Button login,cancel,register,submit;
	private Socket soc;
	private TextField userinput,passinput,emailinput;
	private ObjectOutputStream out;
	private final int LOGINFLOW = 0;
	private Stage frame;
	private ObjectInputStream in;
	private Label username,password,email;
	private BorderPane LoginBr,regBr;
	private Scene LoginScene,RegisScene;
	private GridPane loginGr,regGr;
	private HBox hb;
	
	public void display() throws Exception {
		frame = new Stage();
		frame.initModality(Modality.APPLICATION_MODAL);
		frame.setTitle("Login");
		frame.setResizable(false);
		initLogin();
		frame.setScene(LoginScene);
		frame.show();
		
	}


	@Override
	public void handle(ActionEvent event) {
	  try {	
		if(event.getSource()==login) {
			try {
				soc = new Socket("Localhost",8080);
				out = new ObjectOutputStream(soc.getOutputStream());
				out.writeObject(LOGINFLOW);
				out.writeObject(userinput.getText());
				out.writeObject(passinput.getText());
				userinput.setText("");
				passinput.setText("");
				in = new ObjectInputStream(soc.getInputStream());
				boolean result = (boolean)in.readObject();
				if(result) { //success
					frame.close();
				}
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
			else if(event.getSource() == register){
				frame.setTitle("Register");
				initRegister();
				frame.setScene(RegisScene);
			}
			else {
				frame.setTitle("Login");
				initLogin();
				frame.setScene(LoginScene);
			}
		}
	  }
	  catch(Exception e) {
		  new SendLogThread(Level.SEVERE,e).start();
	  }
		
	}
	
	protected void initLogin() {
		loginGr = new GridPane();
		loginGr.setPadding(new Insets(5,5,5,5));
		loginGr.setVgap(8);
		loginGr.setHgap(10);
		
		LoginBr = new BorderPane();
		
		username = new Label("Username: ");
		GridPane.setConstraints(username, 0, 0);
		
		userinput = new TextField();
		userinput.setPromptText("Username");
		GridPane.setConstraints(userinput, 1, 0);
		
		password = new Label("Password: ");
		GridPane.setConstraints(password, 0, 1);
		
		passinput = new TextField();
		passinput.setPromptText("Password");
		GridPane.setConstraints(passinput, 1, 1);
		
		//buttons
		login = new Button("Login");
		login.setOnAction(this);
		cancel = new Button("Cancel");
		cancel.setOnAction(this);
		register = new Button("Register");
		register.setOnAction(this);
		hb = new HBox(20);
		hb.setPadding(new Insets(5,5,5,5));
		hb.getChildren().addAll(login,register,cancel);
		hb.setAlignment(Pos.CENTER);
		
		loginGr.getChildren().addAll(username,userinput,password,passinput);
		LoginBr.setCenter(loginGr);
		LoginBr.setBottom(hb);
		LoginScene = new Scene(LoginBr,250,110);
	}
	
	protected void initRegister() {
		regGr = new GridPane();
		regGr.setPadding(new Insets(5,5,5,5));
		regGr.setVgap(8);
		regGr.setHgap(10);
		
		regBr = new BorderPane();
		
		email = new Label("Email");
		emailinput = new TextField();
		emailinput.setPromptText("Email...");

		GridPane.setConstraints(username, 0, 0);
		

		GridPane.setConstraints(userinput, 1, 0);
		

		GridPane.setConstraints(password, 0, 1);
		

		GridPane.setConstraints(passinput, 1, 1);
		
		GridPane.setConstraints(email, 0, 2);
		
		GridPane.setConstraints(emailinput, 1, 2);
		
		//buttons
		submit = new Button("Submit");
		submit.setOnAction(this);
		hb = new HBox(20);
		hb.setPadding(new Insets(5,5,5,5));
		hb.getChildren().addAll(submit,cancel);
		hb.setAlignment(Pos.CENTER);
		
		regGr.getChildren().addAll(username,userinput,password,passinput,email,emailinput);
		regBr.setCenter(regGr);
		regBr.setBottom(hb);
		RegisScene = new Scene(regBr,250,145);
	}
	

}
