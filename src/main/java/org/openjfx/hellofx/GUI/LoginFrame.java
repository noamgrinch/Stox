package org.openjfx.hellofx.GUI;

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

public class LoginFrame { //TODO add functions to buttons.
	
	public static void display() throws Exception {
		Stage frame = new Stage();
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
		
		TextField userinput = new TextField();
		userinput.setPromptText("Username");
		GridPane.setConstraints(userinput, 1, 0);
		
		Label password = new Label("Password: ");
		GridPane.setConstraints(password, 0, 1);
		
		TextField passinput = new TextField();
		passinput.setPromptText("Password");
		GridPane.setConstraints(passinput, 1, 1);
		
		//buttons
		Button login = new Button("Login");
		Button cancel = new Button("Cancel");
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

}
