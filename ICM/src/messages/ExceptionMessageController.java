
package messages;

import Boundary.LoginController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
/**
 * Exception Notification 
 * 
 *
 */
public class ExceptionMessageController {
	@FXML
	Label label1;
	@FXML
	public static ExceptionMessageController ctrl;
	public static Stage primaryStage;
	private AnchorPane lowerAnchorPane;
	public static SplitPane splitpane;
/**
 * Exception Notification GUI
 * @param splitpane GUI for FXMLOADER
 * @param id Request-Id
 * @param phase Request-Phase
 */
	public void start(SplitPane splitpane, int id, String phase) {
		primaryStage = LoginController.primaryStage;
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/messages/Exception Message.fxml"));
			lowerAnchorPane = loader.load();
			ctrl = loader.getController();
			splitpane.getItems().set(1, lowerAnchorPane);
			this.splitpane = splitpane;
			ctrl.label1.setVisible(false);
			ctrl.label1.setText("There is an exception occured due to not finishing request #" + id + " " + phase
					+ " by the given time!");
			ctrl.label1.setVisible(true);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
