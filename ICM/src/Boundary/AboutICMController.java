package Boundary;

import javafx.event.ActionEvent; 
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class AboutICMController {
	public static Stage primaryStage;
	private AnchorPane lowerAnchorPane;


	public void start(SplitPane splitpane) {
		primaryStage=LoginController.primaryStage;
		try{		
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/Boundary/AboutICM.fxml"));
			lowerAnchorPane = loader.load();
			splitpane.getItems().set(1, lowerAnchorPane);
		} catch(Exception e) {
			e.printStackTrace();
		}	
	}
}
