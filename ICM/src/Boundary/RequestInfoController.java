package Boundary;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Client.ClientConsole;
import Client.Func;
import Entity.Request;
import Entity.User;
import javafx.fxml.*;

public class RequestInfoController implements Initializable {
	public static Stage primaryStage;
	private AnchorPane lowerAnchorPane;
	@FXML
	private Label LfileName;
	@FXML
	private Button downloadFile;
	@FXML
	private TextField lbId;
	@FXML
	private TextField lbSystem;
	@FXML
	private TextArea lbSituation;
	@FXML
	private TextArea lbChange;
	@FXML
	private TextArea lbComment;
	@FXML
	private TextField InitiatorName;
	@FXML
	private TextField InitiatorRole;
	@FXML
	private TextField Date;
	@FXML
	private TextField Email;
	@FXML
	private static SplitPane splitpane;
	@FXML
	Button BackBtn;

	private static ClientConsole cc;
	private static String job;
	public static RequestInfoController Requestinfo;

	public static Request r2;

	public void start(SplitPane splitpane, Request s, String job) {
		this.job = job;
		primaryStage = LoginController.primaryStage;
		this.cc = LoginController.cc;
		this.splitpane = splitpane;
		String keymessage;
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/Boundary/RequestInfo.fxml"));
			lowerAnchorPane = loader.load();
			Requestinfo = loader.getController();
			splitpane.getItems().set(1, lowerAnchorPane);
			keymessage = "Request Info";
			int id = s.getId();
			Object[] message = { keymessage, id };
			LoginController.cc.getClient().sendToServer(message);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void SetInfo(Request r) {
		r2 = r;
		InitiatorName.setText(r.getInitiatorName());
		InitiatorRole.setText(r.getInitiatorRole());
		Date.setText(r.getDate().toString());
		Email.setText(r.getInitiatorEmail());
		lbSystem.setText(r.getPrivilegedInfoSys());
		lbId.setText(Integer.toString(r.getId()));
		lbSituation.setText(r.getExistingSituation());
		lbChange.setText(r.getExplainRequest());
		lbComment.setText(r.getComment());
		if (r.getMyFile().getMybyterray() != null) {
			Platform.runLater(new Runnable() {

				@Override
				public void run() {
					LfileName.setText(r.getFilename());
				}

			});
		}

	}

	@FXML
	public void downloadFile(ActionEvent e) {
		if(!Requestinfo.LfileName.getText().equals("None.")){
		try {            
			FileOutputStream fos = new FileOutputStream("C:\\Users\\Administrator\\AppData\\Local\\" + r2.getFilename());
			BufferedOutputStream bos = new BufferedOutputStream(fos);
			int len = r2.getMyFile().getMybyterray().length;
			bos.write(r2.getMyFile().getMybyterray(), 0, len);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("TEST");
		alert.setHeaderText("Success");
		alert.setContentText("the file is downloaded, you can find it at your C:\\Users\\Administrator\\AppData\\Local");
		alert.showAndWait();
		}
		else {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("TEST");
			alert.setHeaderText("failed");
			alert.setContentText("there is no file to download");
			alert.showAndWait();
		}
	}

	public void BackBtnAction(ActionEvent e) {

		if (job.equals("Inspector")) {
				InspectorHomeController.AllRequests.start(splitpane, "/Boundary/allRequests.fxml", "Inspector");
		} else if (job.equals("Chairman")) {
				ChairmanHomeController.RequestWorkON.start(splitpane, "/Boundary/RequestWorkOnChairman.fxml",
						ChairmanHomeController.getchairman(), "Chairman", "decision");
		}
		/*
		 * else if(BackTo.equals("Back To RequestsWorkOnChairman")) {
		 * ChairmanHomeController.RequestWorkON.start(splitpane,
		 * "/Boundary/RequestWorkOnChairman.fxml",
		 * comitteeMemberHomeController.getchairman(), "Chairman","decision"); }
		 */
		else if (job.equals("Comittee Member")) {
				ComitteeMemberHomeController.RequestWorkON.start(splitpane,
						"/Boundary/RequestWorkOnCommittemember.fxml", ComitteeMemberHomeController.getcomitteeMember(),
						"Comittee Member", "decision");
		}

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}
}
