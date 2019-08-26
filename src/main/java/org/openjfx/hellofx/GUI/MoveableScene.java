package org.openjfx.hellofx.GUI;

import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class MoveableScene extends Scene{
	
	private double xOffset = 0; 
	private double yOffset = 0;
	private Stage _stage;

	public MoveableScene(Stage stage,Parent root, double width, double height) {
		super(root, width, height);
		this._stage=stage;
		this.setFill(Color.TRANSPARENT);
        this.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });
        this.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
            	_stage.setX(event.getScreenX() - xOffset);
            	_stage.setY(event.getScreenY() - yOffset);
            }
        });
	}


	
	

}
