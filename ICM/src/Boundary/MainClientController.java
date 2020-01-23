package Boundary;

import java.awt.Button;

import java.awt.event.ActionEvent;
import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

import Client.ClientAllRequestsObserver;
import Client.ClientApproveDuratinObserver;
import Client.ClientApproveEvaluatorObserver;
import Client.ClientApprovePerformanceObserver;

import Client.ClientChairmanApproveObserver;
import Client.ClientChangePermissionsObserver;
import Client.ClientCheckApproveDuratinObserver;
import Client.ClientConsole;
import Client.ClientDecisionCommitteMemberObserver;
import Client.ClientDelaysReportObserver;
import Client.ClientDocumentExceptionObserver;
import Client.ClientEvaluationReportObserver;
import Client.ClientExtendRequestTimeObserver;
import Client.ClientFileTobigObserver;
import Client.ClientGetChairmanObserver;
import Client.ClientGetDurationObserver;
import Client.ClientGetEvaluationReportObserver;
import Client.ClientGetEvaluatorsObserver;
import Client.ClientGetExtensionDataObsaerver;
import Client.ClientInitPermissionsPageObserver;

import Client.ClientGetFullNameEmployeeObserver;

import Client.ClientInitiatorapprovedrequestdecisionObserver;
import Client.ClientLogOutObserver;
import Client.ClientLoginByAnotherClientObserver;
import Client.ClientInspectorfreazerequestObserver;
import Client.ClientLoginObserver;
import Client.ClientMapObserver;
import Client.ClientMessageSentToInitiatorObserver;
import Client.ClientMyRequestsObserver;
import Client.ClientNotificationObserver;
import Client.ClientNotificationdetailsObserver;
import Client.ClientObserver;

import Client.ClientPerformanceReportObserver;
import Client.ClientPeriodricReportObserver;

import Client.ClientProfileSettingObserver;
import Client.ClientRecruitEvaluatorObserver;
import Client.ClientRejectRequestMessageSendToInitiatorObserver;
import Client.ClientRequestInfoObserver;
import Client.ClientRequestTrack;
import Client.ClientRequestsWorkedOnObserver;
import Client.ClientServerDisconnectedObserver;
import Client.ClientSetDuratinObserver;
import Client.ClientSubmissionObserver;
import Client.MainForClient;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import ocsf.client.ObservableClient;

public class MainClientController {
	public static LoginController login;
	@FXML
	TextField ip;
	@FXML
	Label wrong;

	public void connect() {
		String host = ip.getText();
		System.out.println(Thread.currentThread().toString());
		try {
			if (host.equals(""))
				throw new Exception();

			ClientConsole cc = new ClientConsole(host);
			// ClientObserver CO = new ClientObserver(cc.getClient());
			ClientLoginObserver clo = new ClientLoginObserver(cc.getClient());
			ClientAllRequestsObserver allReqObserver = new ClientAllRequestsObserver(cc.getClient());
			ClientSubmissionObserver cso = new ClientSubmissionObserver(cc.getClient());
			ClientNotificationObserver cno = new ClientNotificationObserver(cc.getClient());
			ClientMyRequestsObserver ceo = new ClientMyRequestsObserver(cc.getClient());
			ClientGetChairmanObserver chairman = new ClientGetChairmanObserver(cc.getClient());
			ClientRecruitEvaluatorObserver creo = new ClientRecruitEvaluatorObserver(cc.getClient());
			ClientGetEvaluatorsObserver cgeo = new ClientGetEvaluatorsObserver(cc.getClient());
			ClientRequestInfoObserver cri = new ClientRequestInfoObserver(cc.getClient());
			ClientRequestsWorkedOnObserver ss = new ClientRequestsWorkedOnObserver(cc.getClient());
			ClientRequestTrack rt = new ClientRequestTrack(cc.getClient());
			ClientDecisionCommitteMemberObserver qq = new ClientDecisionCommitteMemberObserver(cc.getClient());
			ClientDecisionCommitteMemberObserver xx = new ClientDecisionCommitteMemberObserver(cc.getClient());
			ClientChairmanApproveObserver m = new ClientChairmanApproveObserver(cc.getClient());
			ClientNotificationdetailsObserver n = new ClientNotificationdetailsObserver(cc.getClient());
			ClientSetDuratinObserver cd = new ClientSetDuratinObserver(cc.getClient());
			ClientApprovePerformanceObserver capo = new ClientApprovePerformanceObserver(cc.getClient());
			ClientEvaluationReportObserver sendreport = new ClientEvaluationReportObserver(cc.getClient());
			ClientGetDurationObserver getDuration = new ClientGetDurationObserver(cc.getClient());
			ClientMessageSentToInitiatorObserver ee = new ClientMessageSentToInitiatorObserver(cc.getClient());
			ClientDocumentExceptionObserver cdeo = new ClientDocumentExceptionObserver(cc.getClient());
			ClientMapObserver cmo = new ClientMapObserver(cc.getClient());
			ClientInitiatorapprovedrequestdecisionObserver qqw = new ClientInitiatorapprovedrequestdecisionObserver(
					cc.getClient());
			ClientRejectRequestMessageSendToInitiatorObserver reject = new ClientRejectRequestMessageSendToInitiatorObserver(
					cc.getClient());
			ClientApproveDuratinObserver approveDuratin = new ClientApproveDuratinObserver(cc.getClient());
			ClientCheckApproveDuratinObserver check = new ClientCheckApproveDuratinObserver(cc.getClient());
			ClientLogOutObserver logout = new ClientLogOutObserver(cc.getClient());
			ClientInspectorfreazerequestObserver change = new ClientInspectorfreazerequestObserver(cc.getClient());
			ClientGetEvaluationReportObserver evaluat = new ClientGetEvaluationReportObserver(cc.getClient());
			ClientServerDisconnectedObserver Disscon = new ClientServerDisconnectedObserver(cc.getClient());
			ClientProfileSettingObserver cpso = new ClientProfileSettingObserver(cc.getClient());
			ClientLoginByAnotherClientObserver clbaco = new ClientLoginByAnotherClientObserver(cc.getClient());
			ClientApproveEvaluatorObserver capoo = new ClientApproveEvaluatorObserver(cc.getClient());

			ClientInitPermissionsPageObserver cippo = new ClientInitPermissionsPageObserver(cc.getClient());
			ClientChangePermissionsObserver ccpo = new ClientChangePermissionsObserver(cc.getClient());
			ClientPeriodricReportObserver cpro = new ClientPeriodricReportObserver(cc.getClient());
			ClientPerformanceReportObserver cproo = new ClientPerformanceReportObserver(cc.getClient());
			ClientDelaysReportObserver cdro = new ClientDelaysReportObserver(cc.getClient());
			ClientFileTobigObserver sxxx=new ClientFileTobigObserver(cc.getClient());
			ClientGetFullNameEmployeeObserver ddde = new ClientGetFullNameEmployeeObserver(cc.getClient());
			ClientExtendRequestTimeObserver certo = new ClientExtendRequestTimeObserver(cc.getClient());
			ClientGetExtensionDataObsaerver cedo = new ClientGetExtensionDataObsaerver(cc.getClient());
			cc.getClient().openConnection();
			wrong.setVisible(false);
			login = new LoginController();
			login.start(MainForClient.stage, cc);
		} catch (Exception e) {
			ip.clear();
			System.out.println("connection failed");
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Connection Error");
			alert.setHeaderText("Connection Error");
			Text headerText = new Text("Connection Error");
			headerText.setFont(Font.font("System", FontWeight.BOLD, FontPosture.REGULAR, 20));
			VBox dialogPaneContent = new VBox();
			Label label1 = new Label("wrong IP");
			Label label2 = new Label("Please Try again!");

			dialogPaneContent.getChildren().addAll(label1, label2);
			// onClicking OK button the system will exit!
			// END
			alert.setOnHiding(click -> {
				System.out.println("Try again!");
			});
			// Set content for Dialog Pane
			alert.getDialogPane().setContent(dialogPaneContent);
			alert.showAndWait();

		}
	}
}
