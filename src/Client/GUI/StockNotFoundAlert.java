package Client.GUI;

import java.util.logging.Level;

import CentralLogger.SendLogThread;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class StockNotFoundAlert {
	
	public static void display(String label) {
		Stage stage = new Stage();
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setTitle("Stock was not found!");
		stage.setMinWidth(200);
		stage.setResizable(false);
		Label message = new Label();
		message.setText("The stock: "  + label + " was not found.");
		new SendLogThread(Level.WARNING,new Exception("Stock: " + label + " was not found.")).start();
		Button close = new Button("Close");
		close.setOnAction(e -> stage.close());
		VBox layout = new VBox(10);
		layout.getChildren().addAll(message,close);
		layout.setAlignment(Pos.CENTER);
		Scene scene = new Scene(layout,200,100);
		stage.setScene(scene);
		stage.setAlwaysOnTop(true);
		stage.showAndWait();
	}

}
