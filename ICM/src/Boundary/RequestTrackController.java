
package Boundary;

import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

import Client.ClientConsole;
import Client.Func;
import Entity.Request;
import Entity.RequestPhase;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
/**
 * 
 * Request Tracking , show information about the request work 
 *
 */
public class RequestTrackController implements Initializable {
	public static Stage primaryStage;
	private static ClientConsole cc;
	private AnchorPane lowerAnchorPane;
	@FXML
	private static SplitPane splitpane;
	@FXML
	private TextField requeststatus;
	@FXML
	private TextField requestPhase;
	@FXML
	private TextField subDate;
	@FXML
	private TextField reamining;
	@FXML
	private TextField dueDate;
	@FXML

	private TextField currentDate;
	private static String job;

	public static RequestTrackController RequestTrack;
/**
 * start Request Track GUI
 * @param splitpane GUI
 * @param r Request
 * @param job Role of Initiator
 */
	public void start(SplitPane splitpane, Request r, String job) {
		this.job = job;
		this.splitpane = splitpane;
		primaryStage = LoginController.primaryStage;
		this.cc = LoginController.cc;

		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/Boundary/UserStatus.fxml"));
			lowerAnchorPane = loader.load();
			RequestTrack = loader.getController();
			splitpane.getItems().set(1, lowerAnchorPane);
			String keymessage = "Track request";
			int id = r.getId();
			Object[] message = { keymessage, id };
			LoginController.cc.getClient().sendToServer(message);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

	}
/**
 * Back to last stage
 * @param e
 */
	public void BackBtnAction(ActionEvent e) {

		if (job.equals("Inspector")) {
				InspectorHomeController.MyRequests.start(splitpane, InspectorHomeController.getinspector(),
						"Inspector");
		} else if (job.equals("Chairman")) {
				ChairmanHomeController.RequestWorkON.start(splitpane, "/Boundary/RequestWorkOnChairman.fxml",
						ChairmanHomeController.getchairman(), "Chairman", "decision");
		} else if (job.equals("Comittee Member")) {
				ComitteeMemberHomeController.MyRequests.start(splitpane,
						ComitteeMemberHomeController.getcomitteeMember(), "Comittee Member");
		}

	}
/**
 * set Track Data on GUI
 * @param rp Request Phase
 */
	public void SetTrack(RequestPhase rp) {
		requeststatus.setText(rp.getR().getStatus());
		requestPhase.setText(rp.getCurrentPhase().toString());
		subDate.setText(rp.getDate().toString());
		long millis = System.currentTimeMillis();
		Date date = new java.sql.Date(millis);
		currentDate.setText(date.toString());
		if (rp.getDueDate() != null) {
			dueDate.setText(rp.getDueDate().toString());
			long diff = rp.getDueDate().getTime() - date.getTime();
			long diffdays = diff / (24 * 60 * 60 * 1000);
			long diffHours = diff / (60 * 60 * 1000) - (diffdays * 24);
			reamining.setText(String.valueOf(diffdays) + " Days and " + String.valueOf(diffHours) + " Hours");
		} else {
			dueDate.setText("not defined");
			reamining.setText("not defined");
		}

	}
}