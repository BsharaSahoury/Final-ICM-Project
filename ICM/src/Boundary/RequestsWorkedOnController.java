
package Boundary;

import java.io.IOException;
import java.net.URL;

import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;

import Client.ClientConsole;
import Client.Func;
import Entity.Phase;
import Entity.Employee;
import Entity.Request;
import Entity.RequestPhase;
import Entity.User;
import Entity.State;
import Entity.Student;
import javafx.application.Platform;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import messages.AutomaticRecruitMessageController;

/**
 * show the list request work on for each phase administrator
 * 
 *
 */
public class RequestsWorkedOnController implements Initializable {
	public static Stage primaryStage;
	private static ClientConsole cc;
	private AnchorPane lowerAnchorPane;
	@FXML
	private TableView<RequestPhase> tableRequests;
	@FXML
	private TableColumn colID;
	@FXML
	private TableColumn colName;
	@FXML
	private TableColumn colStatus;
	@FXML
	private TableColumn colPriflig;
	@FXML
	private TableColumn colSubDate;
	@FXML
	private TableColumn colPhase;
	@FXML
	private TableColumn colState;
	@FXML
	private Button RequestInfo;
	@FXML
	private static SplitPane splitpane;
	@FXML
	private Button MakeDecision;
	@FXML
	private ComboBox Groupby;
	@FXML
	private Button approveFinish;
	@FXML
	private TextField search_text;
	private static int chosengroupbytype = -1;
	private static int chosen = -1;
	private static ObservableList<RequestPhase> list;
	private static ArrayList<RequestPhase> arrofRequests;
	private static String job;
	public static RequestsWorkedOnController ctrl1;
	public static MakeDicisionController decision;
	private static int id;
	private static String system;
	ObservableList<String> statuslist = FXCollections.observableArrayList("work", "waitingForApprove", "wait", "over",
			"All");
	public static FXMLLoader loader;
	private static User user;
	private static RequestPhase rp;
	private static String newjob;

	/**
	 * start GUI
	 * 
	 * @param splitpane GUI
	 * @param path      path of the gui
	 * @param user      Username
	 * @param job       job
	 * @param phase     Request Phase
	 */
	public void start(SplitPane splitpane, String path, User user, String job, String phase) {
		if (job.equals("Chairman")) {
			this.newjob = job;
			job = "Comittee Member";
		} else
			this.newjob = "???";
		this.job = job;
		this.user = user;
		primaryStage = LoginController.primaryStage;
		this.cc = LoginController.cc;
		String[] RequestWorkedON = new String[4];
		try {
			loader = new FXMLLoader(getClass().getResource(path));
			lowerAnchorPane = loader.load();
			ctrl1 = loader.getController();
			splitpane.getItems().set(1, lowerAnchorPane);
			this.splitpane = splitpane;
			RequestWorkedON[0] = "Requests worked on";
			if (job.equals("Comittee Member") && phase.equals("decision")) {
				RequestWorkedON[1] = user.getUsername();
			} else if (job.equals("Engineer")) {
				RequestWorkedON[0] = "engineer request work on";
				RequestWorkedON[1] = user.getUsername();
			} else {
				RequestWorkedON[1] = user.getUsername();
			}
			RequestWorkedON[2] = job;
			RequestWorkedON[3] = phase;
			cc.getClient().sendToServer(RequestWorkedON);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * set table data
	 * 
	 * @param arr1
	 */
	public void setTableRequests(ArrayList<RequestPhase> arr1) {
		if (!arr1.equals(null)) {
			list = FXCollections.observableArrayList(arr1);
			tableRequests.setItems(list);
		}
	}

	/**
	 * search button will search on the table
	 */
	public void searchaction() {
		if (!search_text.getText().equals("")) {
			try {
				int searchid = Integer.valueOf(search_text.getText());
				long x = tableRequests.getItems().stream().filter(item -> item.getId() == searchid).count();
				if (x > 0) {
					tableRequests.getItems().stream().filter(item -> item.getId() == searchid).findAny()
							.ifPresent(item -> {
								tableRequests.getSelectionModel().select(item);
								tableRequests.scrollTo(item);
							});
				} else {
					Alert alert = new Alert(AlertType.WARNING);
					alert.setTitle("Warning");
					alert.setHeaderText("Not exist");
					alert.setContentText("The ID doesn't exist");
					alert.showAndWait();
				}
			} catch (NumberFormatException e) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Warning");
				alert.setContentText("Enter ID number in search field");
				alert.showAndWait();
			}
		} else {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Warning");
			alert.setHeaderText("Enter ID");
			alert.setContentText("Please Enter ID to the search field");
			alert.showAndWait();
		}
	}

	/**
	 * fill the table list
	 * 
	 * @param arr1 list of table data
	 */
	public void fillTable(ArrayList<RequestPhase> arr1) {
		arrofRequests = arr1;
		loader.<RequestsWorkedOnController>getController().setTableRequests(arr1);
	}

	/**
	 * group by button action will sort the table list * @param e
	 */
	public void GroupbyAction(ActionEvent e) {
		chosengroupbytype = Groupby.getSelectionModel().getSelectedIndex();
		String groupbystatus = null;
		ArrayList<RequestPhase> arr = new ArrayList<RequestPhase>();
		if (chosengroupbytype != -1) {
			if (chosengroupbytype == 0)
				groupbystatus = "work";
			else if (chosengroupbytype == 1)
				groupbystatus = "waitingForApprove";
			else if (chosengroupbytype == 2)
				groupbystatus = "wait";
			else if (chosengroupbytype == 3)
				groupbystatus = "over";
			else if (chosengroupbytype == 4)
				groupbystatus = "All";
			if (groupbystatus.equals("All")) {
				arr = arrofRequests;
			} else {
				if (arrofRequests != null) {
					for (int i = 0; i < arrofRequests.size(); i++)
						if ((arrofRequests.get(i)).getState().equals(State.valueOf(groupbystatus)))
							arr.add(arrofRequests.get(i));
				}
				if (!arr.equals(null)) {
					switch (job) {
					case "Evaluator":
						EvaluatorHomeController.RequestWorkON.loader
								.<RequestsWorkedOnController>getController().tableRequests
										.setItems(FXCollections.observableArrayList(arr));
						break;
					case "Comittee Member":
						ComitteeMemberHomeController.RequestWorkON.loader
								.<RequestsWorkedOnController>getController().tableRequests
										.setItems(FXCollections.observableArrayList(arr));
						break;
					case "Chairman":
						ChairmanHomeController.RequestWorkON.loader
								.<RequestsWorkedOnController>getController().tableRequests
										.setItems(FXCollections.observableArrayList(arr));
						break;
					case "Tester":
						TesterHomeController.RequestWorkON.loader
								.<RequestsWorkedOnController>getController().tableRequests
										.setItems(FXCollections.observableArrayList(arr));
						break;
					}
				}
			}
		}
	}

	/**
	 * will open set duration GUI to set new duration or to show the request
	 * duration
	 */
	public void SetDuration() {
		chosen = tableRequests.getSelectionModel().getSelectedIndex();
		if (chosen != -1) {
			RequestPhase s = tableRequests.getSelectionModel().getSelectedItem();
			id = s.getId();
            if(s.getState().toString().equals("over")) {
            	Alert alertWarning = new Alert(AlertType.WARNING);
    			alertWarning.setTitle("Warning Alert Title");
    			alertWarning.setHeaderText("Warning!");
    			alertWarning.setContentText("You can't set duration, the phase is over");
    			alertWarning.showAndWait();	
            }
            else {
			String keymessage = "get duration";
			Object[] message = { keymessage, s.getId(), s.getPhase() };
			try {
				LoginController.cc.getClient().sendToServer(message);
			} catch (IOException e) {
				e.printStackTrace();
			}
            }

		} else {
			Alert alertWarning = new Alert(AlertType.WARNING);
			alertWarning.setTitle("Warning Alert Title");
			alertWarning.setHeaderText("Warning!");
			alertWarning.setContentText("please choose requset");
			alertWarning.showAndWait();
		}
	}

	/**
	 * method for setDuration that return after return from server
	 * 
	 * @param s Request Phase
	 */
	public void SetDurationHelp(RequestPhase s) {
		this.rp = s;
		rp.setId(id);
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				DurationController Duration = new DurationController();
				SetDurationController setDuration = new SetDurationController();
				if (newjob.equals("Chairman")) {
						setDuration.start(splitpane, "/Boundary/DuratinForEvaluator.fxml", rp);
				} else if (job.equals("Comittee Member") || job.equals("Engineer")) {
						Duration.start(splitpane, "/Boundary/Duration.fxml", rp);
				} else {
						setDuration.start(splitpane, "/Boundary/DuratinForEvaluator.fxml", rp);
				}
			}
		});
	}

	/**
	 * open request info GUI that contains info about the request
	 */
	public void RequestInfoAction() {
		chosen = tableRequests.getSelectionModel().getSelectedIndex();
		if (chosen != -1) {
			Request s = tableRequests.getSelectionModel().getSelectedItem();
			RequestInfoController requestifo = new RequestInfoController();
				requestifo.start(splitpane, s, job);
		} else {
			Alert alertWarning = new Alert(AlertType.WARNING);
			alertWarning.setTitle("Warning Alert Title");
			alertWarning.setHeaderText("Warning!");
			alertWarning.setContentText("please choose requset");
			alertWarning.showAndWait();
		}
	}

	/**
	 * performance approve for phase finish
	 * 
	 * @param e Action Event (Clicking on button)
	 */
	public void approveFinishAction(ActionEvent e) {
		chosen = tableRequests.getSelectionModel().getSelectedIndex();
		if (chosen != -1) {
			RequestPhase r = tableRequests.getSelectionModel().getSelectedItem();
			if (!r.getState().toString().equals("work")) {
				Alert alertWarning = new Alert(AlertType.WARNING);
				alertWarning.setTitle("Warning Alert Title");
				alertWarning.setHeaderText("Warning!");
				alertWarning.setContentText("the request is not at performance phase!!");
				alertWarning.showAndWait();
			} else {
				Alert alertWarning = new Alert(AlertType.CONFIRMATION);
				alertWarning.setTitle("Warning Alert Title");
				alertWarning.setHeaderText("confirm!");
				alertWarning.setContentText("are you sure that you've finished working on this request?");
				Optional<ButtonType> result = alertWarning.showAndWait();
				ButtonType button = result.orElse(ButtonType.CANCEL);
				if (button == ButtonType.OK) {
					Object[] msg = { "performance done", r.getId() };
					try {
						LoginController.cc.getClient().sendToServer(msg);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		}
	}

	/**
	 * Refresh button for refresh the table
	 */
	public void refresh() {
		Employee employee = (Employee) user;
		switch (job) {
		case "Inspector":
			try {
					InspectorHomeController.AllRequests.start(splitpane, "/Boundary/allRequests.fxml", "Inspector");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case "Evaluator":
			try {
					EvaluatorHomeController.RequestWorkON.start(splitpane, "/Boundary/RequestsWorkOnEvaluator.fxml",
							employee, "Evaluator", "evaluation");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;

		case "Comittee Member":

			try {
				if (ComitteeMemberHomeController.getFlag() == 0) {
						ComitteeMemberHomeController.RequestWorkON.start(splitpane,
								"/Boundary/RequestWorkOnCommittemember.fxml", employee, "Comittee Member", "decision");
				}
				if (ComitteeMemberHomeController.getFlag() == 1) {
						ComitteeMemberHomeController.RequestWorkON.start(splitpane,
								"/Boundary/RequestsWorkOnTester.fxml", employee, "Comittee Member", "testing");
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case "Chairman":
			try {
					ChairmanHomeController.RequestWorkON.start(splitpane, "/Boundary/RequestWorkOnChairman.fxml",
							employee, "Chairman", "decision");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case "Performance Leader":
			try {
					PerformanceLeaderHomeController.RequestWorkON.start(splitpane,
							"/Boundary/RequestWorkOnPerformer.fxml", employee, "Performance Leader", "performance");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		case "Engineer":
			try {
					PerformanceLeaderHomeController.RequestWorkON.start(splitpane,
							"/Boundary/RequestWorkOnPerformer.fxml", employee, "Performance Leader", "performance");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case "Administrator":
			try {
					AdministratorHomeController.AllRequests.start(splitpane, "/Boundary/allRequests.fxml",
							"Administrator");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		}
	}

	/**
	 * instruction button to show instructions about buttons operations
	 */
	public void instructionsAction() {
		Employee employee = (Employee) user;
		Label label1, label2, label3, label4, label5, label6, label7, label8, label9, label10;
		Label label11, label12, label13, label14;
		Alert alert;
		Text headerText;
		VBox dialogPaneContent;
		switch (job) {
		case "Evaluator":
			alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Instuctions");
			headerText = new Text("Instuctions!");
			headerText.setFont(Font.font("System", FontWeight.BOLD, FontPosture.REGULAR, 20));
			alert.setHeaderText("Instuctions!");
			dialogPaneContent = new VBox();
			label1 = new Label("* This table contains the requests that you work on!");
			label1.setFont(Font.font("System", FontWeight.BOLD, FontPosture.REGULAR, 12));
			label2 = new Label("");
			label3 = new Label("- You can access to informaion of a specific request by:");
			label3.setFont(Font.font("System", FontWeight.BOLD, FontPosture.REGULAR, 12));
			label4 = new Label("1. Sellecting a specific request");
			label5 = new Label("2. Clicking the 'Request Info' button !");
			label6 = new Label("3.You can choose displaying 'request Info' or 'Initiator Info'");
			label7 = new Label("");
			/////////////////////
			label8 = new Label("- You can set duration of Evaluation phase for a specific request by:");
			label8.setFont(Font.font("System", FontWeight.BOLD, FontPosture.REGULAR, 12));
			label9 = new Label("1. Sellecting a specific request");
			label10 = new Label("2. Clicking the 'Duration' button !");
			label11 = new Label("");
			////////////////////////////////
			label12 = new Label("- You can create an Evaluation Report of a specific request by:");
			label12.setFont(Font.font("System", FontWeight.BOLD, FontPosture.REGULAR, 12));
			label13 = new Label("1. Sellecting a specific request");
			label14 = new Label("2. Clicking the 'Create Evaluation Report' button !");

			dialogPaneContent.getChildren().addAll(label1, label2, label3, label4, label5, label6, label7, label8,
					label9, label10, label11, label12, label13, label14);
			alert.getDialogPane().setContent(dialogPaneContent);
			alert.showAndWait();
			break;
		case "Comittee Member":
			if (ComitteeMemberHomeController.getFlag() == 0) {
				alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Instuctions");
				headerText = new Text("Instuctions!");
				headerText.setFont(Font.font("System", FontWeight.BOLD, FontPosture.REGULAR, 20));
				alert.setHeaderText("Instuctions!");
				dialogPaneContent = new VBox();
				label1 = new Label("* This table contains the Requests that you work on!");
				label1.setFont(Font.font("System", FontWeight.BOLD, FontPosture.REGULAR, 12));
				label2 = new Label("");
				label3 = new Label("- You can access to info of a specific of request by:");
				label3.setFont(Font.font("System", FontWeight.BOLD, FontPosture.REGULAR, 12));
				label4 = new Label("1. Sellecting a specific request");
				label5 = new Label("2. Clicking the 'Request Info' button !");
				label6 = new Label("3.You can choose displaying 'request Info' or 'Initiator Info'");
				label7 = new Label("");
				label8 = new Label("- You can set Duration of this phase for a specific request by:");
				label8.setFont(Font.font("System", FontWeight.BOLD, FontPosture.REGULAR, 12));
				label9 = new Label("1. Sellecting a specific request");
				label10 = new Label("2. Clicking the 'Duration' button !");
				label11 = new Label("");
				label12 = new Label(
						"- You can 'Make a decision' OR 'See the Evaluation Report' for a specific request by:");
				label12.setFont(Font.font("System", FontWeight.BOLD, FontPosture.REGULAR, 12));
				label13 = new Label("1. Sellecting a specific request");
				label14 = new Label("2. Clicking the 'Make a decision' button !");

				dialogPaneContent.getChildren().addAll(label1, label2, label3, label4, label5, label6, label7, label8,
						label9, label10, label11, label12, label13, label14);
				alert.getDialogPane().setContent(dialogPaneContent);
				alert.showAndWait();

				break;
			} else if (ComitteeMemberHomeController.getFlag() == 1) {// RequestsWorkOnTester

				alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Instuctions");
				headerText = new Text("Instuctions!");
				headerText.setFont(Font.font("System", FontWeight.BOLD, FontPosture.REGULAR, 20));
				alert.setHeaderText("Instuctions!");
				dialogPaneContent = new VBox();
				label1 = new Label("* This table contains the Requests For Testing!");
				label1.setFont(Font.font("System", FontWeight.BOLD, FontPosture.REGULAR, 12));
				label2 = new Label("");
				label3 = new Label("- You can access to info of a specific of request by:");
				label3.setFont(Font.font("System", FontWeight.BOLD, FontPosture.REGULAR, 12));
				label4 = new Label("1. Sellecting a specific request");
				label5 = new Label("2. Clicking the 'Request Info' button !");
				label6 = new Label("3.You can choose displaying 'request Info' or 'Initiator Info'");
				label7 = new Label("");
				label8 = new Label("- You can set Duration of Testing phase for a specific request by:");
				label8.setFont(Font.font("System", FontWeight.BOLD, FontPosture.REGULAR, 12));
				label9 = new Label("1. Sellecting a specific request");
				label10 = new Label("2. Clicking the 'Duration' button !");
				label11 = new Label("");
				label12 = new Label("- You can Insert Test Result for a specific request by:");
				label12.setFont(Font.font("System", FontWeight.BOLD, FontPosture.REGULAR, 12));
				label13 = new Label("1. Sellecting a specific request");
				label14 = new Label("2. Clicking the 'Insert Test Result' button !");

				dialogPaneContent.getChildren().addAll(label1, label2, label3, label4, label5, label6, label7, label8,
						label9, label10, label11, label12, label13, label14);
				alert.getDialogPane().setContent(dialogPaneContent);
				alert.showAndWait();
				break;
			}
		case "Chairman":
			alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Instuctions");
			headerText = new Text("Instuctions!");
			headerText.setFont(Font.font("System", FontWeight.BOLD, FontPosture.REGULAR, 20));
			alert.setHeaderText("Instuctions!");
			dialogPaneContent = new VBox();
			label1 = new Label("* This table contains the Requests For Testing!");
			label1.setFont(Font.font("System", FontWeight.BOLD, FontPosture.REGULAR, 12));
			label2 = new Label("");
			label3 = new Label("- You can access to info of a specific of request by:");
			label3.setFont(Font.font("System", FontWeight.BOLD, FontPosture.REGULAR, 12));
			label4 = new Label("1. Sellecting a specific request");
			label5 = new Label("2. Clicking the 'Request Info' button !");
			label6 = new Label("3.You can choose displaying 'request Info' or 'Initiator Info'");
			label7 = new Label("");
			label8 = new Label("- You can set Duration of this phase for a specific request by:");
			label8.setFont(Font.font("System", FontWeight.BOLD, FontPosture.REGULAR, 12));
			label9 = new Label("1. Sellecting a specific request");
			label10 = new Label("2. Clicking the 'Duration' button !");
			label11 = new Label("");
			label12 = new Label(
					"- You can 'Make a decision' OR 'See the Evaluation Report' for a specific request by:");
			label12.setFont(Font.font("System", FontWeight.BOLD, FontPosture.REGULAR, 12));
			label13 = new Label("1. Sellecting a specific request");
			label14 = new Label("2. Clicking the 'Make a decision' button !");

			dialogPaneContent.getChildren().addAll(label1, label2, label3, label4, label5, label6, label7, label8,
					label9, label10, label11, label12, label13, label14);
			alert.getDialogPane().setContent(dialogPaneContent);
			alert.showAndWait();
			break;
		case "Performance Leader":
			alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Instuctions");
			headerText = new Text("Instuctions!");
			headerText.setFont(Font.font("System", FontWeight.BOLD, FontPosture.REGULAR, 20));
			alert.setHeaderText("Instuctions!");
			dialogPaneContent = new VBox();
			label1 = new Label("* This table contains the Requests that you lead!");
			label1.setFont(Font.font("System", FontWeight.BOLD, FontPosture.REGULAR, 12));
			label2 = new Label("");
			label3 = new Label("- You can access to info of a specific of request by:");
			label3.setFont(Font.font("System", FontWeight.BOLD, FontPosture.REGULAR, 12));
			label4 = new Label("1. Sellecting a specific request");
			label5 = new Label("2. Clicking the 'Request Info' button !");
			label6 = new Label("3.You can choose displaying 'request Info' or 'Initiator Info'");
			label7 = new Label("");
			label8 = new Label("- You can set Duration of Performance phase for a specific request by:");
			label8.setFont(Font.font("System", FontWeight.BOLD, FontPosture.REGULAR, 12));
			label9 = new Label("1. Sellecting a specific request");
			label10 = new Label("2. Clicking the 'Duration' button !");
			label11 = new Label("");
			label12 = new Label("- You can 'Approve finishing performance phase' for a specific request by:");
			label12.setFont(Font.font("System", FontWeight.BOLD, FontPosture.REGULAR, 12));
			label13 = new Label("1. Sellecting a specific request");
			label14 = new Label("2. Clicking the 'Approve finishing performance phase' button !");

			dialogPaneContent.getChildren().addAll(label1, label2, label3, label4, label5, label6, label7, label8,
					label9, label10, label11, label12, label13, label14);
			alert.getDialogPane().setContent(dialogPaneContent);
			alert.showAndWait();
			break;
		case "Engineer":
			alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Instuctions");
			headerText = new Text("Instuctions!");
			headerText.setFont(Font.font("System", FontWeight.BOLD, FontPosture.REGULAR, 20));
			alert.setHeaderText("Instuctions!");
			dialogPaneContent = new VBox();
			label1 = new Label("* This table contains the Requests that you work on!");
			label1.setFont(Font.font("System", FontWeight.BOLD, FontPosture.REGULAR, 12));
			label2 = new Label("");
			label3 = new Label("- You can access to info of a specific of request by:");
			label3.setFont(Font.font("System", FontWeight.BOLD, FontPosture.REGULAR, 12));
			label4 = new Label("1. Sellecting a specific request");
			label5 = new Label("2. Clicking the 'Request Info' button !");
			label6 = new Label("3.You can choose displaying 'request Info' or 'Initiator Info'");
			label7 = new Label("");
			label8 = new Label("- You can set Duration of phase for a specific request by:");
			label8.setFont(Font.font("System", FontWeight.BOLD, FontPosture.REGULAR, 12));
			label9 = new Label("1. Sellecting a specific request");
			label10 = new Label("2. Clicking the 'Duration' button !");

			dialogPaneContent.getChildren().addAll(label1, label2, label3, label4, label5, label6, label7, label8,
					label9, label10);
			alert.getDialogPane().setContent(dialogPaneContent);
			alert.showAndWait();
			break;
		case "Administrator":
			alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Instuctions");
			headerText = new Text("Instuctions!");
			headerText.setFont(Font.font("System", FontWeight.BOLD, FontPosture.REGULAR, 20));
			alert.setHeaderText("Instuctions!");
			dialogPaneContent = new VBox();
			label1 = new Label("* This table contains the Requests For Testing!");
			label1.setFont(Font.font("System", FontWeight.BOLD, FontPosture.REGULAR, 12));
			label2 = new Label("");
			label3 = new Label("- You can access to info of a specific of request by:");
			label3.setFont(Font.font("System", FontWeight.BOLD, FontPosture.REGULAR, 12));
			label4 = new Label("1. Sellecting a specific request");
			label5 = new Label("2. Clicking the 'Request Info' button !");
			label6 = new Label("3.You can choose displaying 'request Info' or 'Initiator Info'");
			label7 = new Label("");
			label8 = new Label("- You can Change the status to 'Active' of a specific request by:");
			label8.setFont(Font.font("System", FontWeight.BOLD, FontPosture.REGULAR, 12));
			label9 = new Label("1. Sellecting a specific request");
			label10 = new Label("2. Clicking the 'Change Status' button !");

			dialogPaneContent.getChildren().addAll(label1, label2, label3, label4, label5, label6, label7, label8,
					label9, label10);
			alert.getDialogPane().setContent(dialogPaneContent);
			alert.showAndWait();
			break;
		}

	}

	/**
	 * Insert Test Result button by tester ( committee member )
	 */
	public void InsertTestResultAction() {
		chosen = tableRequests.getSelectionModel().getSelectedIndex();
		if (chosen != -1) {
			RequestPhase s = tableRequests.getSelectionModel().getSelectedItem();
			if (s.getState().equals(State.work)) {
				TestResultController setTestResult = new TestResultController();
					setTestResult.start(splitpane, s);
			} else {
				Alert alertWarning = new Alert(AlertType.ERROR);
				alertWarning.setContentText("The request state must be work");
				alertWarning.showAndWait();
			}
		} else {
			Alert alertWarning = new Alert(AlertType.WARNING);
			alertWarning.setTitle("Warning Alert Title");
			alertWarning.setHeaderText("Warning!");
			alertWarning.setContentText("please choose requset");
			alertWarning.showAndWait();
		}
	}

	/**
	 * get selected index for chosen request
	 */
	public static int getselectedindex() {
		return chosen;
	}

	/**
	 * get request id
	 * 
	 * @return
	 */
	public static int getId() {
		return id;
	}

	/**
	 * get System
	 * 
	 * @return
	 */
	public static String getSystem() {
		return system;
	}

	/**
	 * make Decision button
	 */
	public void MakeDecisionAction() {
		chosen = tableRequests.getSelectionModel().getSelectedIndex();
		if (chosen == -1) {
			Alert alertWarning = new Alert(AlertType.WARNING);
			alertWarning.setTitle("Warning Alert Title");
			alertWarning.setHeaderText("Warning!");
			alertWarning.setContentText("please choose requset");
			alertWarning.showAndWait();
		} else if (!tableRequests.getSelectionModel().getSelectedItem().getState().equals(State.work)) {
			Alert alertWarning = new Alert(AlertType.WARNING);
			alertWarning.setTitle("Warning Alert Title");
			alertWarning.setHeaderText("Warning!");
			alertWarning.setContentText("This Request is not on work");
			alertWarning.showAndWait();
		} else {
			RequestPhase selected = tableRequests.getSelectionModel().getSelectedItem();
			decision = new MakeDicisionController();
				decision.start(splitpane, selected, user,ctrl1.job);
		}
	}

	/**
	 * to create evaluation report
	 */
	public void EvaluationReportAction() {
		chosen = tableRequests.getSelectionModel().getSelectedIndex();
		if (chosen != -1) {
			RequestPhase s = tableRequests.getSelectionModel().getSelectedItem();
			if (s.getState().toString().equals(State.work.toString())) {
				CreateEvaluationReportController requestifo = new CreateEvaluationReportController();
					requestifo.start(splitpane, s.getId());
			} else {
				Alert alertWarning = new Alert(AlertType.WARNING);
				alertWarning.setTitle("Warning Alert Title");
				alertWarning.setHeaderText("Warning!");
				alertWarning.setContentText("The Request should be in state work");
				alertWarning.showAndWait();
			}
		} else {
			Alert alertWarning = new Alert(AlertType.WARNING);
			alertWarning.setTitle("Warning Alert Title");
			alertWarning.setHeaderText("Warning!");
			alertWarning.setContentText("please choose requset");
			alertWarning.showAndWait();
		}
	}

	public static ObservableList<RequestPhase> getList() {
		return list;
	}

	public static RequestPhase getRP() {
		return rp;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Groupby.setItems(statuslist);
		colID.setCellValueFactory(new PropertyValueFactory<RequestPhase, Integer>("id"));
		colName.setCellValueFactory(new PropertyValueFactory<RequestPhase, Integer>("initiatorName"));
		colStatus.setCellValueFactory(new PropertyValueFactory<RequestPhase, String>("status"));
		colPriflig.setCellValueFactory(new PropertyValueFactory<RequestPhase, String>("privilegedInfoSys"));
		colSubDate.setCellValueFactory(new PropertyValueFactory<RequestPhase, Date>("date"));
		colState.setCellValueFactory(new PropertyValueFactory<RequestPhase, Integer>("State"));
	}
}
