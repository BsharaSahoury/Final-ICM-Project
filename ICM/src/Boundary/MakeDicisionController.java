package Boundary;

import java.io.IOException;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import Client.ClientConsole;
import Client.Func;
import Entity.RequestPhase;
import Entity.User;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class MakeDicisionController implements Initializable {
	public static Stage primaryStage;
	private AnchorPane lowerAnchorPane;
	private static ClientConsole cc;
	private FXMLLoader loader;;
	@FXML
	private static SplitPane splitpane;
	@FXML
	private RadioButton Approve;
	@FXML
	private RadioButton Reject;
	@FXML
	private RadioButton AdditionalInfo;
	@FXML
	private TextArea ExplainDectxt;
	@FXML
	private Button Sendbtn;
	@FXML
	private Label requestid;
	public static MakeDicisionController ctrl;
	private RequestPhase selected;
	public static int flag = -1;
	private User user;

	public void start(SplitPane splitpane, RequestPhase selected, User user) {
		try {
			loader = new FXMLLoader(getClass().getResource("/Boundary/DecisionCommitteMember.fxml"));
			lowerAnchorPane = loader.load();
			ctrl = loader.getController();
			splitpane.getItems().set(1, lowerAnchorPane);
			loader.<MakeDicisionController>getController().requestid.setText(Integer.toString(selected.getR().getId()));
			ctrl.user = user;
			primaryStage = LoginController.primaryStage;
			ctrl.selected = selected;
			this.cc = LoginController.cc;
			this.splitpane = splitpane;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void VisibleExplain(ActionEvent e) {
		ExplainDectxt.setDisable(false);
	}

	public void SendToChairMan(ActionEvent e) {

		if (!Approve.isSelected() && !Reject.isSelected() && !AdditionalInfo.isSelected()) {
			Alert alertWarning = new Alert(AlertType.WARNING);
			alertWarning.setTitle("Warning Alert Title");
			alertWarning.setHeaderText("Warning!");
			alertWarning.setContentText("Please Select your decision");
			alertWarning.showAndWait();
		} else if (ExplainDectxt.getText().equals("")) {
			Alert alertWarning = new Alert(AlertType.WARNING);
			alertWarning.setTitle("Warning Alert Title");
			alertWarning.setHeaderText("Warning!");
			alertWarning.setContentText("Please fill Explain about the decision");
			alertWarning.showAndWait();
		} else {
			String[] Message = new String[5];
			Message[0] = "Committee Member Decision";
			if (Approve.isSelected())
				Message[1] = "approve";
			else if (Reject.isSelected())
				Message[1] = "reject";
			else
				Message[1] = "ask for additional Information";
			Message[2] = ExplainDectxt.getText();
			Message[3] = Integer.toString(ctrl.selected.getR().getId());
			Message[4]=Integer.toString(ctrl.selected.getRepetion());
			try {
				cc.getClient().sendToServer(Message);
				Sendbtn.setDisable(true);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

	public void showEvaluationReport() {
		CommitteeEvaluationController evaluation = new CommitteeEvaluationController();
		runLater(() -> {
			evaluation.start(splitpane, ctrl.selected, ctrl.user);
		});
	}

	public void BackBtnAction(ActionEvent e) {
		ChairmanHomeController.RequestWorkON.start(splitpane, "/Boundary/RequestWorkOnChairman.fxml",
				ChairmanHomeController.getchairman(), "Chairman", "decision");

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		ExplainDectxt.setDisable(true);
	}

	private void runLater(Func f) {
		f.call();
		Platform.runLater(() -> {
			try {
				Thread.sleep(1);
				f.call();

			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
	}
}
