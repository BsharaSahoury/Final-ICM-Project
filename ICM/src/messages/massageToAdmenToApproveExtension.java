package messages;

import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import Boundary.LoginController;
import Boundary.NotificationsController;
import Boundary.RequestsWorkedOnController;
import Entity.Phase;
import Entity.Request;
import Entity.RequestPhase;
import Entity.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class massageToAdmenToApproveExtension implements Initializable {
	/**
	 * Notification to Administrator for approving the extension of the request 
	 */
	private static final long serialVersionUID = 1L;
	@FXML
	Label RequestIdLabel;
	@FXML
	Label requestLabel;
	@FXML
	TextArea ExtensionReasonLabel;
	@FXML
	Label RequestPhaseLabel;
	@FXML
	Label phaseAdministratorLabel;
	@FXML
	Label OldDueDateLabel;
	@FXML
	Label NewDueDateLabel;
	private static Request r;
	public static massageToAdmenToApproveExtension ctrl;

	public static Stage primaryStage;
	private AnchorPane lowerAnchorPane;
	public static SplitPane splitpane;
	private static int requestID;
	private String fullname;
	private ObservableList<String> list;
	private static String phase;
	private int notificationID;
	private static String notdetails;
	private String ExtensionRequest;
/**
 * Open notification GUI
 * @param splitpane GUI
 * @param id Request-Id
 * @param content Notification-Content
 * @param phase1 Request-Phase
 */
	public void start(SplitPane splitpane, int id, String content, String phase1) {
		primaryStage = LoginController.primaryStage;
		try {
			FXMLLoader loader = new FXMLLoader(
					getClass().getResource("/messages/RequestExtendedSuccessfullyToAdmin.fxml"));
			lowerAnchorPane = loader.load();
			ctrl = loader.getController();
			splitpane.getItems().set(1, lowerAnchorPane);
			this.splitpane = splitpane;
			ctrl.RequestPhaseLabel.setText(phase1);
			ctrl.RequestIdLabel.setText(Integer.toString(id));
			Object[] message = { "get extension data", id, phase1, "admen" };
			try {
				LoginController.cc.getClient().sendToServer(message);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		this.requestID = id;
		this.phase = phase1;
	}
	 /**
	  * set Notification content
	  * @param data
	  */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		notificationID = NotificationsController.getidnotification();
		requestID = NotificationsController.getidofrequestforDecision();
		Object[] message = { "get explain notification", notificationID, "admin message" };
		try {
			LoginController.cc.getClient().sendToServer(message);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	/**
	 * set Notification content 
	 * @param details
	 * 
	 **/
	public static void setdetails(String details) {
		massageToAdmenToApproveExtension.notdetails = details;
		String[] b = new String[2];
		b = massageToAdmenToApproveExtension.notdetails.split("#");
		ctrl.ExtensionReasonLabel.setText(b[0]);
	}
	/**
	 * set Notification content 
	 * @param details
	 * 
	 **/
	public void setData(String[] data) {
		ctrl.OldDueDateLabel.setText(data[0]);
		ctrl.NewDueDateLabel.setText(data[1]);
		ctrl.phaseAdministratorLabel.setText(data[2]);
	}

}
