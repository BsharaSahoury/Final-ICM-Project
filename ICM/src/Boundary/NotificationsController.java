package Boundary;

import java.io.IOException;

import java.net.URL;
import java.sql.Date;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Client.Func;
import Entity.Employee;
import Entity.Notification;
import Entity.Phase;
import Entity.User;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import messages.AnswerExtension;
import messages.AutomaticRecruitMessageController;
import messages.CommitteeDecisionApproveController;
import messages.CommitteeDecisionAskForaddInfoController;
import messages.CommitteeDecisionRejectController;
import messages.ChooseTesterMessageController;
import messages.FailedTestMessageController;
import messages.Passedmessagecontroller;
import messages.RecruitMessageController;
import messages.SuccessTestMessageController;
import messages.approveDuratinController;
import messages.massageToAdmenToApproveExtension;
import messages.DecisionCommitteeMemberMessageController;
import messages.ExceptionDocumentController;
import messages.ExceptionMessageController;
import messages.ExtensionConfirmationMessage;
import messages.RecruitMessageController;
import messages.RecruitPerformanceMessageController;
import messages.RejectMessageInitiatorController;
import messages.ReminderMessageController;
import messages.newRequestforcommitte;

public class NotificationsController implements Initializable {

	public static Stage primaryStage;
	private AnchorPane lowerAnchorPane;
	private static User user;
	@FXML
	private TableView<Notification> table;
	@FXML
	private TableColumn<Notification, String> content;
	@FXML
	TableColumn<Notification, String> date;

	public ObservableList<Notification> list;

	private static Phase phase;

	public static NotificationsController ctrl;
	@FXML
	public static SplitPane splitpane;
	private static int IDRequestForDecision;
	private static String CommittteDecision;
	private static String ExplainDecision;
	private static int idnotification;
	private static String myPhase;
	private static int id;

	public void start(SplitPane splitpane, User user) {
		this.user = user;
		primaryStage = LoginController.primaryStage;
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/Boundary/All-Notifications.fxml"));
			lowerAnchorPane = loader.load();
			ctrl = loader.getController();
			splitpane.getItems().set(1, lowerAnchorPane);
			this.splitpane = splitpane;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		String keymessage = "notification";
		Object[] message = { keymessage, user.getUsername() };
		try {
			LoginController.cc.getClient().sendToServer(message);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void insertNotificToTable(ArrayList<Notification> nlist) {
		list = FXCollections.observableArrayList(nlist);
		content.setCellValueFactory(new PropertyValueFactory<Notification, String>("content"));
		date.setCellValueFactory(new PropertyValueFactory<Notification, String>("dateStr"));
		table.setItems(list);

	}

	@FXML
	public void clickCell(MouseEvent e) {
		Notification n2 = table.getSelectionModel().getSelectedItem();
		String content;
		String[] b;
		String phase1;
		if (n2 != null) {
			idnotification = n2.getId();
			switch (n2.getType()) {
			case "recruitForInspector":
				content = n2.getContent();
				b = new String[2];
				b = content.split("#");
				id = Integer.valueOf(b[1]);
				b = b[0].split(": ");
				b = b[1].split(" for");
				b = b[0].split(" ");
				String fullname = b[0] + " " + b[1];
				AutomaticRecruitMessageController armc = new AutomaticRecruitMessageController();
					armc.start(splitpane, id, fullname);
				break;
			case "recruitNotificationForEvaluator":
				content = n2.getContent();
				b = new String[2];
				b = content.split("#");
				id = Integer.valueOf(b[1]);
				RecruitMessageController rmc = new RecruitMessageController();
					rmc.start(splitpane, id);
				break;

			case "choose tester":
				content = n2.getContent();
				b = new String[2];
				b = content.split("#");
				b = b[1].split(" is");
				id = Integer.valueOf(b[0]);
				ChooseTesterMessageController ctmc = new ChooseTesterMessageController();
					ctmc.start(splitpane, id);
				break;

			case "fail message sent to Inspector":
				content = n2.getContent();
				String[] b2 = new String[2];
				b2 = content.split("#");
				b2 = b2[1].split("failed");
				int id1 = Integer.valueOf(b2[0]);
				FailedTestMessageController ftmc = new FailedTestMessageController();
					ftmc.start(splitpane, id1);
				break;
			case "success message sent to Inspector":
				content = n2.getContent();
				String[] b3 = new String[2];
				b3 = content.split("#");
				b3 = b3[1].split("passed");
				IDRequestForDecision = Integer.valueOf(b3[0]);
				SuccessTestMessageController stmc = new SuccessTestMessageController();
					stmc.start(splitpane, IDRequestForDecision);
				break;
			case "Decision of Committee Member":
				content = n2.getContent();
				b = new String[2];
				b = content.split("id=");
				b = b[1].split("\n");
				IDRequestForDecision = Integer.valueOf(b[0]);
				b = content.split("is '");
				b = b[1].split("' for");
				CommittteDecision = b[0];
				DecisionCommitteeMemberMessageController obj = new DecisionCommitteeMemberMessageController();
					obj.start(splitpane);
				break;
			case "Chairman Approved Comittee Members Decision is approve":
				content = n2.getContent();
				b = new String[2];
				b = content.split("id=");
				b = b[1].split("\n");
				IDRequestForDecision = Integer.valueOf(b[0]);
				b = content.split("is '");
				b = b[1].split("' for");
				CommittteDecision = b[0];
				CommitteeDecisionApproveController obj2 = new CommitteeDecisionApproveController();
					obj2.start(splitpane, "/messages/RecruitPerformanceLeader.fxml");
				break;
			case "Chairman Approved Comittee Members Decision is ask for additional Information":
				content = n2.getContent();
				b = new String[2];
				b = content.split("id=");
				b = b[1].split("\n");
				IDRequestForDecision = Integer.valueOf(b[0]);
				b = content.split("is '");
				b = b[1].split("' for");
				CommittteDecision = b[0];
				CommitteeDecisionAskForaddInfoController obj3 = new CommitteeDecisionAskForaddInfoController();
					obj3.start(splitpane, "/messages/RecruitEvaluator.fxml");
				break;
			case "Chairman Approved Comittee Members Decision is reject":
				content = n2.getContent();
				b = new String[2];
				b = content.split("id=");
				b = b[1].split("\n");
				IDRequestForDecision = Integer.valueOf(b[0]);
				b = content.split("is '");
				b = b[1].split("' for");
				CommittteDecision = b[0];
				CommitteeDecisionRejectController obj4 = new CommitteeDecisionRejectController();
					obj4.start(splitpane, "/messages/RejectTheRequest-message.fxml");
				break;
			case "new request for committe":
				content = n2.getContent();
				String numberOnly = content.replaceAll("[^0-9]", "");
				id = Integer.valueOf(numberOnly);
				newRequestforcommitte r = new newRequestforcommitte();
					r.start(splitpane, id);
				break;

			case "Exception message":
				content = n2.getContent();
				numberOnly = content.replaceAll("[^0-9]", "");
				id = Integer.valueOf(numberOnly);
				b = new String[2];
				b = content.split("in phase ");
				b = b[1].split(" ");
				String phase = b[0];
				ExceptionMessageController emc = new ExceptionMessageController();
					emc.start(splitpane, id, phase);
				break;

			case "Exception document":
				content = n2.getContent();
				b = new String[2];
				String[] a = new String[2];
				String[] c = new String[2];
				b = content.split("#");
				b = b[1].split(":");
				id = Integer.valueOf(b[0]);
				content = n2.getContent();
				b = content.split("phase ");
				b = b[1].split(" with");
				phase = b[0];
				b = content.split("repetion ");
				b = b[1].split(" is");
				int repetion = Integer.valueOf(b[0]);
				ExceptionDocumentController edc = new ExceptionDocumentController();
					edc.start(splitpane, id, phase, repetion);
				break;
			case "Extension Confirmation message sent to Inspector":
				content = n2.getContent();
				String[] b4 = new String[2];
				b4 = content.split("# ");
				b4 = b4[1].split(" time");
				int id3 = Integer.valueOf(b4[0]);
				String[] b5 = new String[2];
				b5 = content.split("time on phase ");
				b5 = b5[1].split(", Do");
				phase1 = b5[0];
				ExtensionConfirmationMessage ecm = new ExtensionConfirmationMessage();

				ecm.start(splitpane, id3, content, phase1);

				break;

			case "reminder to finish work":
				content = n2.getContent();
				numberOnly = content.replaceAll("[^0-9]", "");
				id = Integer.valueOf(numberOnly);
				b = content.split(": ");
				b = b[1].split("!");
				phase = b[0];
				ReminderMessageController rmc8 = new ReminderMessageController();
					rmc8.start(splitpane, id, phase);
				break;
			case "Duratin of evaluator":
				content = n2.getContent();
				b = new String[2];
				b = content.split("from: ");
				b = b[1].split(" to: ");
				String start = b[0];
				b = b[1].split(",");
				String due = b[0];
				numberOnly = b[1].replaceAll("[^0-9]", "");
				id = Integer.valueOf(numberOnly);
				c = new String[2];
				c = content.split("for the ");
				c = c[1].split(" phase");
				String p = c[0];
				this.myPhase = p;
				due = due.replaceAll("(\\r|\\n)", "");
				start = start.replaceAll("(\\r|\\n)", "");
				approveDuratinController ad = new approveDuratinController();
				ad.start(splitpane, content, start, due, id, p);
				break;
			case "Committee reject the request and wait for initiator approve":
				content = n2.getContent();
				b = new String[2];
				b = content.split("#");
				b = b[1].split("has");
				IDRequestForDecision = Integer.valueOf(b[0]);
				CommittteDecision = "reject";
				RejectMessageInitiatorController obj5 = new RejectMessageInitiatorController();
					obj5.start(splitpane, "/messages/Rejectmessageinitiator.fxml");
				break;
			case "Request test passed and wait for intitiator approve to close":
				content = n2.getContent();
				b = new String[2];
				b = content.split("#");
				b = b[1].split("has");
				IDRequestForDecision = Integer.valueOf(b[0]);
				CommittteDecision = "passed";
				Passedmessagecontroller obj6 = new Passedmessagecontroller();
					obj6.start(splitpane, "/messages/approvemessageinitiator.fxml");
				break;
			case "recruitNotificationForPerformEngineer":
				RecruitPerformanceMessageController ttr = new RecruitPerformanceMessageController();
				content = n2.getContent();
				String[] b10 = new String[2];
				b10 = content.split("#");
				int id = Integer.valueOf(b10[1]);
				ttr.start(splitpane, id, "/messages/RecruitEngineerPerform.fxml", content);
				break;
			case "recruitNotificationForPerformance":
				RecruitPerformanceMessageController ttr1 = new RecruitPerformanceMessageController();
				content = n2.getContent();
				String[] b11 = new String[2];
				b11 = content.split("#");
				int id2 = Integer.valueOf(b11[1]);
				ttr1.start(splitpane, id2, "/messages/RecruitPerformer-message.fxml", content);
				break;
			case "ReplaceNotificationforlastperformanceadmin":
				RecruitPerformanceMessageController ttr2 = new RecruitPerformanceMessageController();
				content = n2.getContent();
				String[] b12 = new String[2];
				b12 = content.split("#");
				int id5 = Integer.valueOf(b12[1]);
				ttr2.start(splitpane, id5, "/messages/ReplacePerformanceLeader-Message.fxml", content);
				break;
			case "Extension Confirmation message sent to Admin after inspector confirmation":
				content = n2.getContent();
				String[] b6 = new String[2];
				b6 = content.split("# ");
				b6 = b6[1].split(" time");
				int id4 = Integer.valueOf(b6[0]);
				IDRequestForDecision = id4;
				String[] b7 = new String[2];
				b7 = content.split("time on phase ");
				b7 = b7[1].split(", Do");
				phase1 = b7[0];
				massageToAdmenToApproveExtension ecm1 = new massageToAdmenToApproveExtension();
				ecm1.start(splitpane, id4, content, phase1);
				break;
			case "answer to extension request":
				content = n2.getContent();
				String myid = content.replaceAll("[^0-9]", "");
				id = Integer.valueOf(myid);
				AnswerExtension aa = new AnswerExtension();
				aa.start(splitpane, content);
				break;
			}
		}
	}

	public static String getMyPhase() {
		return myPhase;
	}

	public static int getId() {
		return id;
	}

	public static int getidofrequestforDecision() {
		return IDRequestForDecision;
	}

	public static String getDecisionofcommitteemember() {
		return CommittteDecision;
	}

	public static int getidnotification() {
		return idnotification;
	}
}
