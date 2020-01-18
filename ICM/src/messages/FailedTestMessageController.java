package messages;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Boundary.LoginController;
import Boundary.NotificationsController;
import Boundary.RequestsWorkedOnController;
import Client.ClientConsole;
import Entity.User;
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
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class FailedTestMessageController implements Initializable {
	@FXML
	Label requestLabel;
	@FXML
	Label evaluatorLabel;
	@FXML
	Button recruit;
	@FXML
	ComboBox<String> combo;
	@FXML
	TextArea FailureDetails;

	public static FailedTestMessageController ctrl;
	public static Stage primaryStage;
	private AnchorPane lowerAnchorPane;
	public static SplitPane splitpane;
	private int requestID;
	private ObservableList<String> list;
	private int notificationID;
	private static String notdetails;

	public void start(SplitPane splitpane, int id) {
		primaryStage = LoginController.primaryStage;
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/messages/FaildTest-message.fxml"));
			lowerAnchorPane = loader.load();
			ctrl = loader.getController();
			ctrl.requestID = id;
			splitpane.getItems().set(1, lowerAnchorPane);
			this.splitpane = splitpane;
			ctrl.requestLabel.setVisible(false);
			ctrl.requestLabel.setText("Request with id #" + id + ", Test Failed");
			ctrl.requestLabel.setVisible(true);

			Object[] message = { "get explain notification", ctrl.notificationID, "FailedTestDetails" };
			try {
				LoginController.cc.getClient().sendToServer(message);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void RecruitAction(ActionEvent e) {
		if(ClientConsole.map.get(requestID).equals("frozen")) {
			ClientConsole.displayFreezeError();
			return;
		}

		String fullname = combo.getSelectionModel().getSelectedItem();

		if (fullname == null) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Select Performance Leader");
			alert.setHeaderText("ERROR");
			alert.setContentText("please choose an Performer!");
			alert.showAndWait();
			Alert alertWarning = new Alert(AlertType.CONFIRMATION);
			alertWarning.setHeaderText("SUCCESS!");
			alertWarning.setContentText("Perform recruit has been sent to Selected Performer!");
			alertWarning.showAndWait();
			return;
		}

		Object[] msg = { "Performer confirmation for step", fullname, ctrl.requestID };
		try {
			System.out.println("try-logged");
			LoginController.cc.getClient().sendToServer(msg);
			System.out.println("after-send-to-server");
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	public static void setdetails(String details) {

		ctrl.notdetails = details;
		ctrl.FailureDetails.setText(ctrl.notdetails);

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		notificationID = NotificationsController.getidnotification();
		requestID = NotificationsController.getidofrequestforDecision();
		Object[] msg = { "Performance leaders", getClass().getName() };
		try {
			LoginController.cc.getClient().sendToServer(msg);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void fillCombo(ArrayList<String> names) {
		list = FXCollections.observableArrayList(names);
		combo.setItems(list);

	}
}