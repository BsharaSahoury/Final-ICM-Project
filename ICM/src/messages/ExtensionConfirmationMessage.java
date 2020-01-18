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

public class ExtensionConfirmationMessage implements Initializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@FXML
	Label RequestIdLabel;
	@FXML
	Label requestLabel;
	@FXML
	Label evaluatorLabel;
	@FXML
	Button ApproveBtn;
	@FXML
	Button RejectBtn;
	@FXML
	Button other;

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
	public static ExtensionConfirmationMessage ctrl;

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

	public void start(SplitPane splitpane, int id, String content, String phase1) {
		primaryStage = LoginController.primaryStage;
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/messages/ExtensionConfirmationMessage.fxml"));
			lowerAnchorPane = loader.load();
			ctrl = loader.getController();
			splitpane.getItems().set(1, lowerAnchorPane);
			this.splitpane = splitpane;
			ctrl.RequestPhaseLabel.setText(phase1);
			ctrl.RequestIdLabel.setText(Integer.toString(id));
			Object[] message = { "get extension data", id, phase1, "inspector" };
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

	public void approveAction(ActionEvent e) {

		Alert alertWarning = new Alert(AlertType.INFORMATION);
		alertWarning.setTitle("Approve Extension Request Time Warning");
		alertWarning.setHeaderText("Are you sure about your Approve!");
		alertWarning.setContentText("Extension request will Approved!");
		Optional<ButtonType> result = alertWarning.showAndWait();
		ButtonType button = result.orElse(ButtonType.CANCEL);
		if (button == ButtonType.OK) {
			try {
				String date = NewDueDateLabel.getText().toString();
				date = date.replaceAll("(\\r|\\n)", "");
				LocalDate localDate = null;
				DateTimeFormatter formatter = null;
				formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
				localDate = LocalDate.parse(date, formatter);
				String keymessage = "send Request extension approve to Admin";
				Object[] message = { keymessage, requestID, phase, localDate,
						ExtensionReasonLabel.getText().toString(),phaseAdministratorLabel.getText().toString()};
				LoginController.cc.getClient().sendToServer(message);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			Alert alertWarning1 = new Alert(AlertType.CONFIRMATION);
			alertWarning1.setHeaderText("Success!");
			alertWarning1.setContentText("Request Extension has been approved successfully!");
			alertWarning1.showAndWait();
			ApproveBtn.setDisable(true);
			RejectBtn.setDisable(true);
		}

	}

	public void RejectBtn(ActionEvent e) {

		Alert alertWarning = new Alert(AlertType.INFORMATION);
		alertWarning.setTitle("Reject Extension Request Time Warning");
		alertWarning.setHeaderText("Are you sure about your reject!");
		alertWarning.setContentText("Extension request will reject!");
		Optional<ButtonType> result = alertWarning.showAndWait();
		ButtonType button = result.orElse(ButtonType.CANCEL);
		if (button == ButtonType.OK) {
			Alert alertWarning1 = new Alert(AlertType.CONFIRMATION);
			alertWarning1.setHeaderText("Rejected Successfully!");
			alertWarning1.setContentText("Request Extension has been Rejected successfully!");
			alertWarning1.showAndWait();
			ApproveBtn.setDisable(true);
			RejectBtn.setDisable(true);
			try {
			String keymessage = "send Request extension reject to Admin";
			Object[] message = { keymessage, requestID, phaseAdministratorLabel.getText().toString()};
			LoginController.cc.getClient().sendToServer(message);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		}

	}

	public static void setdetails(String details) {
		ctrl.notdetails = details;
		String[] b = new String[2];
		b = ctrl.notdetails.split("#");
		ctrl.ExtensionReasonLabel.setText(b[0]);
	}

	public static String getRequestPhase() {
		return phase;
	}

	public static int getRequestId() {
		return requestID;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		notificationID = NotificationsController.getidnotification();
		requestID = NotificationsController.getidofrequestforDecision();
		Object[] message = { "get explain notification", notificationID, "Inspector to approve the Extension" };
		try {
			LoginController.cc.getClient().sendToServer(message);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

	public void setData(String[] data) {
		ctrl.OldDueDateLabel.setText(data[0]);
		ctrl.NewDueDateLabel.setText(data[1]);
		ctrl.phaseAdministratorLabel.setText(data[2]);
	}

}
