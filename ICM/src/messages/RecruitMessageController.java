package messages;

import Boundary.LoginController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class RecruitMessageController {
	@FXML
	Label label1;
	@FXML
	public static RecruitMessageController ctrl;
	public static Stage primaryStage;
	private AnchorPane lowerAnchorPane;
	public static SplitPane splitpane;

	public void start(SplitPane splitpane, int id) {
		primaryStage = LoginController.primaryStage;
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/messages/recruit-message.fxml"));
			lowerAnchorPane = loader.load();
			ctrl = loader.getController();
			splitpane.getItems().set(1, lowerAnchorPane);
			this.splitpane = splitpane;
			ctrl.label1.setVisible(false);
			ctrl.label1.setText("you've been recruited to work on request#" + id);
			ctrl.label1.setVisible(true);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
