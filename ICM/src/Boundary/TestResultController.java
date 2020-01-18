package Boundary;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import Client.ClientConsole;
import Entity.MyFile;
import Entity.Request;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class TestResultController implements Initializable {
	@FXML
	private RadioButton TestFaildRadioButton;
	@FXML
	private RadioButton TestSuccessRadioButton;
	@FXML
	private TextArea FailureDetails;
	@FXML
	Button SendBtn;
	@FXML
	private Button SuccessSendBtn;
	@FXML
	private Button FaildSendBtn;
	@FXML
	private TextArea FaildDetails;
	public static TestResultController TestResult;
	public static RequestsWorkedOnController RequestWorkON;
	public static Stage primaryStage;
	private AnchorPane lowerAnchorPane;
	private static SplitPane splitpane;
	private static Request r;
	private static ClientConsole cc;

	public void start(SplitPane splitpane, Request r) {
		this.splitpane = splitpane;
		primaryStage = LoginController.primaryStage;
		this.cc = LoginController.cc;
		this.r = r;

		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/Boundary/TestResults.fxml"));
			lowerAnchorPane = loader.load();
			TestResult = loader.getController();
			splitpane.getItems().set(1, lowerAnchorPane);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void SuccessSendBtn() {
		try {
			String keymessage = "send Passed test result";
			Object[] message = { keymessage, r.getId(), null };
			LoginController.cc.getClient().sendToServer(message);
			Alert alertWarning = new Alert(AlertType.CONFIRMATION);
			alertWarning.setHeaderText("SUCCESS!");
			alertWarning.setContentText("Passed test result has been sent to Inspector");
			alertWarning.showAndWait();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

	public void FaildSendBtn() {
		String FaildDetails = FailureDetails.getText().trim();
		if (!FaildDetails.equals("")) {
			try {
				String keymessage = "send Failure test result";
				String d = FailureDetails.getText().toString();
				Object[] message = { keymessage, r.getId(), d };
				LoginController.cc.getClient().sendToServer(message);
				Alert alertWarning = new Alert(AlertType.CONFIRMATION);
				alertWarning.setHeaderText("SUCCESS!");
				alertWarning.setContentText("Failure test result has been sent to Inspector");
				alertWarning.showAndWait();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} else {
			Alert alertWarning = new Alert(AlertType.WARNING);
			alertWarning.setHeaderText("Warning!");
			alertWarning.setContentText("Please check the dates correctly");
			alertWarning.showAndWait();
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

	}

	public void radioselectFaild(ActionEvent event) {

		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/Boundary/TestResultFaild.fxml"));
			lowerAnchorPane = loader.load();

			splitpane.getItems().set(1, lowerAnchorPane);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void radioselectSuccess(ActionEvent event) {

		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/Boundary/TestResultPass.fxml"));
			lowerAnchorPane = loader.load();
			splitpane.getItems().set(1, lowerAnchorPane);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
