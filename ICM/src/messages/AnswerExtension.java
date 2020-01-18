package messages;

import Boundary.LoginController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
public class AnswerExtension {

	@FXML
	Label label1;
	@FXML
	public static AnswerExtension ctrl;
	public static Stage primaryStage;
	private AnchorPane lowerAnchorPane;
	public  static SplitPane splitpane;
	public void start(SplitPane splitpane,String content) {
		primaryStage=LoginController.primaryStage;
		try{	
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/messages/answerExtension.fxml"));
			lowerAnchorPane = loader.load();
			ctrl=loader.getController();
			splitpane.getItems().set(1, lowerAnchorPane);
			this.splitpane=splitpane;
			ctrl.label1.setVisible(false);
			ctrl.label1.setText(content);
			ctrl.label1.setVisible(true);
			
		} catch(Exception e) {
			e.printStackTrace();
		}			
	}

}
