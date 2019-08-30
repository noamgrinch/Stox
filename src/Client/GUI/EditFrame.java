package Client.GUI;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class EditFrame {
	
	
	public static void display() {
		Stage frame = new Stage();
		frame.setTitle("Edit");
		frame.initModality(Modality.APPLICATION_MODAL);//User must finish dealing with this frame before other stuff.
		VBox v = new VBox(20);
		Button b = new Button("what");
		v.getChildren().add(b);
		Scene scene = new Scene(v,300,700);
		frame.setScene(scene);
		frame.show();
	}

}
