package messages;

import Boundary.LoginController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
/**
 * Notification for recruit performance 
 * 
 *
 */
public class RecruitPerformanceMessageController {
	@FXML
	Label label1;
	@FXML
	public static RecruitPerformanceMessageController ctrl;
	public static Stage primaryStage;
	private AnchorPane lowerAnchorPane;
	public static SplitPane splitpane;
/**
 * for start Notification GUI 
 * @param splitpane GUI
 * @param id Request Id
 * @param path GUI path
 * @param content Notification Content
 */
	public void start(SplitPane splitpane, int id, String path, String content) {
		primaryStage = LoginController.primaryStage;
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
			lowerAnchorPane = loader.load();
			ctrl = loader.<RecruitPerformanceMessageController>getController();
			splitpane.getItems().set(1, lowerAnchorPane);
			this.splitpane = splitpane;
			ctrl.label1.setVisible(false);
			if (path.equals("/messages/RecruitEngineerPerform.fxml"))
				ctrl.label1.setText(content);
			else if (path.equals("/messages/ReplacePerformanceLeader-Message.fxml")) {
				ctrl.label1.setText(content);
			}

			else
				ctrl.label1.setText(content);

			ctrl.label1.setVisible(true);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
