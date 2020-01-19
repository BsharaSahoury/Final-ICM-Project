package messages;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Boundary.LoginController;
import Boundary.NotificationsController;
import Client.ClientConsole;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class RejectMessageInitiatorController implements Initializable {
	@FXML
	Label DecisionLable;
	public static RejectMessageInitiatorController ctrl;
	public static Stage primaryStage;
	private AnchorPane lowerAnchorPane;
	public static SplitPane splitpane;
	private int notificationID;
	private int requestID;
	private static String CommitteeDecision;
	public static int flag = -1;
	private static String notdetails;

	public void start(SplitPane splitpane, String path) {
		primaryStage = LoginController.primaryStage;
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
			lowerAnchorPane = loader.load();
			ctrl = loader.getController();
			if (!CommitteeDecision.equals("passed")) {
				Object[] message = { "get explain notification", ctrl.notificationID,
						"Initiator to approve the reject message" };
				try {
					LoginController.cc.getClient().sendToServer(message);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			splitpane.getItems().set(1, lowerAnchorPane);
			this.splitpane = splitpane;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void setdetails(String details) {
		ctrl.notdetails = details;
		ctrl.DecisionLable.setText(ctrl.notdetails);
	}

	public void approveAction(ActionEvent e) {
		if (ClientConsole.map.get(requestID).equals("frozen")) {
			ClientConsole.displayFreezeError();
			return;
		}

		Object[] message = { "initiator approved the decision of the request", requestID, CommitteeDecision };
		try {
			LoginController.cc.getClient().sendToServer(message);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		CommitteeDecision = NotificationsController.getDecisionofcommitteemember();
		if (!CommitteeDecision.equals("passed"))
			notificationID = NotificationsController.getidnotification();
		requestID = NotificationsController.getidofrequestforDecision();
	}
}
