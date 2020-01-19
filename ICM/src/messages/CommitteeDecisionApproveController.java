package messages;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Boundary.LoginController;
import Boundary.NotificationsController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
/**
 * the approve decision of  the committee member Notification
 * 
 *
 */
public class CommitteeDecisionApproveController implements Initializable {
	@FXML
	Label DecisionLable;
	private ObservableList<String> list;
	public static CommitteeDecisionApproveController ctrl;
	public static Stage primaryStage;
	private AnchorPane lowerAnchorPane;
	public static SplitPane splitpane;
	private int requestID;
	private String CommitteeDecision;
	public static int flag = -1;
	private static String notdetails;
	private static int notificationID;
/**
 * open CommitteeDecisionApprove Notification 
 * @param splitpane Notification GUI		
 * @param path For open fxml ( used in fxml loader ) for open correct notification.
 */
	public void start(SplitPane splitpane, String path) {
		primaryStage = LoginController.primaryStage;
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
			lowerAnchorPane = loader.load();
			ctrl = loader.getController();
			Object[] message = { "get explain notification", ctrl.notificationID, "inspector to recruit performance" };
			try {
				LoginController.cc.getClient().sendToServer(message);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			splitpane.getItems().set(1, lowerAnchorPane);
			this.splitpane = splitpane;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
/**
 * Notification contents
 * @param details contents
 */
	public static void setdetails(String details) {
		ctrl.notdetails = details;
		ctrl.DecisionLable.setText(ctrl.notdetails);
	}
 /**
  * notification parameters will initialize
  */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		requestID = NotificationsController.getidofrequestforDecision();
		CommitteeDecision = NotificationsController.getDecisionofcommitteemember();
		requestID = NotificationsController.getidofrequestforDecision();
		notificationID = NotificationsController.getidnotification();
	}
}
