package Boundary;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Client.ClientConsole;
import Entity.Employee;
import Entity.EvaluationReport;
import Entity.RequestPhase;
import Entity.User;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class CommitteeEvaluationController {
	@FXML
	private Label requestID;
	@FXML
	private Label LocOfChange;
	@FXML
	private Label DesOfChange;
	@FXML
	private Label ExcpResult;
	@FXML
	private Label Constraint;
	@FXML
	private Label Risk;
	@FXML
	private Label Duration;
	public static Stage primaryStage;
	private AnchorPane lowerAnchorPane;
	private static ClientConsole cc;
	private FXMLLoader loader;;
	@FXML
	private static SplitPane splitpane;
	private RequestPhase selected;
	public static CommitteeEvaluationController ctrl1;
	public static int flag = -1;
	private static String job;

	public void start(SplitPane splitpane, RequestPhase selected, User user, String job) {
     this.job=job;
		try {
			loader = new FXMLLoader(getClass().getResource("/Boundary/CommitteeMember-EvaluationReport.fxml"));
			lowerAnchorPane = loader.load();
			ctrl1 = loader.getController();
			splitpane.getItems().set(1, lowerAnchorPane);
			ctrl1.primaryStage = LoginController.primaryStage;
			ctrl1.selected = selected;
			ctrl1.cc = LoginController.cc;
			ctrl1.splitpane = splitpane;
			Object[] Message = new Object[2];
			Message[0] = "get evaluation report";
			Message[1] = ctrl1.selected.getR().getId();
			try {
				cc.getClient().sendToServer(Message);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setlabels(EvaluationReport report) {
		ctrl1.requestID.setText(Integer.toString(report.getRequestID()));
		ctrl1.requestID.setTextFill(Color.web("#9d1b34"));
		ctrl1.Risk.setText(report.getRisks());
		ctrl1.Risk.setTextFill(Color.web("#9d1b34"));
		ctrl1.Constraint.setText(report.getConstraints());
		ctrl1.Constraint.setTextFill(Color.web("#9d1b34"));
		ctrl1.DesOfChange.setText(report.getDescription());
		ctrl1.DesOfChange.setTextFill(Color.web("#9d1b34"));
		ctrl1.Duration.setText(Integer.toString(report.getEstimatedPerfomanceDuration()));
		ctrl1.Duration.setTextFill(Color.web("#9d1b34"));
		ctrl1.LocOfChange.setText(report.getLocation());
		ctrl1.LocOfChange.setTextFill(Color.web("#9d1b34"));
		ctrl1.ExcpResult.setText(report.getExpectedResult());
		ctrl1.ExcpResult.setTextFill(Color.web("#9d1b34"));
	}

	public void BackBtnAction(ActionEvent e) {

	
			ComitteeMemberHomeController.RequestWorkON.decision.start(splitpane, selected, ComitteeMemberHomeController.getcomitteeMember(),ctrl1.job);
			
	}

}
