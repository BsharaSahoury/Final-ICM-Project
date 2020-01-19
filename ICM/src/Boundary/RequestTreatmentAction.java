package Boundary;

import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Client.ClientConsole;
import Client.Func;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import Entity.Request;
import Entity.RequestPhase;
import Entity.Phase;
/**
 * 
 * on request treatment can change status of request and assign phase administrator for phase
 *
 */
public class RequestTreatmentAction extends AllRequestsController implements Initializable, Serializable {
	public static Stage primaryStage;
	private static ClientConsole cc;
	private AnchorPane lowerAnchorPane;
	@FXML
	private static SplitPane splitpane;
	@FXML
	private Label currentphase;
	@FXML
	private ComboBox PhaseAdministrator;
	@FXML
	private DatePicker DatePickerFrom;
	@FXML
	private DatePicker DatePickerTo;
	@FXML
	private Label statuslable;
	@FXML
	private TextArea Explaintxt;
	@FXML
	private TextArea Explaintxt2;
	@FXML
	private Label phaseadminlable;
	@FXML
	private ListView<String> chooseengineer;
	@FXML
	private Label label1;
	@FXML
	private Label label2;
	private static String lastadmin;
	private int chosenindex;
	private static int indexphase = -1;
	private static int indexadmin = -1;
	private RequestPhase chosenRequest;
	public static RequestTreatmentAction ctrl;
	ObservableList<String> engineerslist;
	private ObservableList<String> list;
	private ObservableList<String> selected;
/**
 * initialize choosen request from table list 
 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		chooseengineer.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		chosenindex = AllRequestsController.getselectedindex();
		chosenRequest = AllRequestsController.getList().get(chosenindex);
		statuslable.setText(chosenRequest.getStatus());

	}
/**
 * pen Request Treatment GUI with suitable chosen request
 * @param splitpane GUI
 */
	public void start(SplitPane splitpane) {
		this.splitpane = splitpane;
		primaryStage = LoginController.primaryStage;
		this.cc = LoginController.cc;
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/Boundary/Update.fxml"));
			lowerAnchorPane = loader.load();
			ctrl = loader.getController();
			splitpane.getItems().set(1, lowerAnchorPane);
			ctrl.currentphase.setText(ctrl.chosenRequest.getPhase().toString());
			if (ctrl.currentphase.getText().equals("performance")) {
				ctrl.label1.setVisible(true);
				ctrl.label2.setVisible(true);
				ctrl.chooseengineer.setVisible(true);
			} else {
				ctrl.label1.setVisible(false);
				ctrl.label2.setVisible(false);
				ctrl.chooseengineer.setVisible(false);
			}

			Object[] msg1 = { "getFullNameOfEmployee", getClass().getName(), ctrl.chosenRequest.getEmployee() };
			try {
				LoginController.cc.getClient().sendToServer(msg1);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (ctrl.currentphase.getText().equals("evaluation")) {
				Object[] msg = { "evaluators", getClass().getName() };
				try {
					LoginController.cc.getClient().sendToServer(msg);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else if (ctrl.currentphase.getText().equals("performance")) {
				Object[] msg = { "Performance leaders", getClass().getName() };
				try {
					LoginController.cc.getClient().sendToServer(msg);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (ctrl.chosenRequest.getStartDate() != null && ctrl.chosenRequest.getDueDate() != null) {
				ctrl.DatePickerFrom.setPromptText(ctrl.chosenRequest.getStartDate().toString());
				ctrl.DatePickerTo.setPromptText(ctrl.chosenRequest.getDueDate().toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
/**
 * 
 */
	public void ApplyAction() {
		String explain = null;
		explain = Explaintxt.getText();
		if (explain.equals("")) {
			Alert alertSuccess = new Alert(AlertType.WARNING);
			alertSuccess.setTitle("Warning");
			alertSuccess.setHeaderText("Miss");
			alertSuccess.setContentText("PLease fill explain for your decision");
			alertSuccess.showAndWait();
		} else {
			statuslable.setText("frozen");
			RequestPhase chosen = AllRequestsController.getselectedRequest();
			Object[] send = new Object[4];
			send[0] = "Inspector changed status to Frozen";
			send[1] = chosen.getR().getId();
			send[2] = InspectorHomeController.getinspector();
			send[3] = explain;
			try {
				LoginController.cc.getClient().sendToServer(send);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
/**
 * back to last stage
 * @param e Action Event ( Clicking on button ) 
 */
	public void BackBtnAction(ActionEvent e) {
		runLater(() -> {
			InspectorHomeController.AllRequests.start(splitpane, "/Boundary/allRequests.fxml", "Inspector");
		});
	}
/**
 * Fill comboBox with current phase administrator 
 * @param currentadmin
 */
	public void setcombotext(String currentadmin) {
		if (currentadmin != null) {
			ctrl.PhaseAdministrator.setPromptText(currentadmin);
			ctrl.lastadmin = ctrl.PhaseAdministrator.getPromptText();
		}
	}
/**
 * fill ComboBox with phase Administator 
 * @param names
 */
	public void fillCombo(ArrayList<String> names) {
		list = FXCollections.observableArrayList(names);
		ctrl.PhaseAdministrator.setItems(list);
		ctrl.chooseengineer.setItems(list);
	}
/**
 * update the request with the entered informations.
 */
	public void updateandsaveaction() {
		if (ctrl.PhaseAdministrator.getSelectionModel().getSelectedItem() == null
				&& ctrl.DatePickerFrom.getValue() == null && ctrl.DatePickerTo.getValue() == null) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Warning");
			alert.setContentText("You didn't update anything");
			alert.showAndWait();
		} else if (ctrl.DatePickerFrom.getValue() == null && ctrl.DatePickerTo.getValue() != null
				&& ctrl.DatePickerFrom.getPromptText().equals("") && ctrl.DatePickerTo.getPromptText().equals("")) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Warning");
			alert.setContentText("If you chose 'to' date you must choose 'start' date");
			alert.showAndWait();
		} else if (ctrl.DatePickerFrom.getValue() != null && ctrl.DatePickerTo.getValue() == null
				&& ctrl.DatePickerFrom.getPromptText().equals("") && ctrl.DatePickerTo.getPromptText().equals("")) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Warning");
			alert.setContentText("If you chose 'start' date you must choose 'to' date");
			alert.showAndWait();
		} else if (ctrl.DatePickerTo.getValue() != null && ctrl.DatePickerFrom.getValue() != null
				&& (ctrl.DatePickerFrom.getValue().compareTo(ctrl.DatePickerTo.getValue()) > 0
						|| LocalDate.now().compareTo(ctrl.DatePickerFrom.getValue()) > 0)) {
			Alert alertSuccess = new Alert(AlertType.WARNING);
			alertSuccess.setTitle("Warning");
			alertSuccess.setHeaderText("Wrong dates");
			alertSuccess.setContentText("Cheak the dates that you specified");
			alertSuccess.showAndWait();
		} else if (Explaintxt2.getText().equals("")) {
			Alert alertSuccess = new Alert(AlertType.WARNING);
			alertSuccess.setTitle("Warning");
			alertSuccess.setHeaderText("Miss");
			alertSuccess.setContentText("PLease fill explain for your update");
			alertSuccess.showAndWait();
		} else if (ctrl.currentphase.getText().equals("performance")
				&& (ctrl.PhaseAdministrator.getSelectionModel().getSelectedIndex() >= 0)) {
			ctrl.selected = chooseengineer.getSelectionModel().getSelectedItems();
			if (selected.size() == 0) {
				Alert alertSuccess = new Alert(AlertType.WARNING);
				alertSuccess.setTitle("Warning");
				alertSuccess.setHeaderText("Miss");
				alertSuccess.setContentText("You must choose engineers for performance phase");
				alertSuccess.showAndWait();
			} else {
				ArrayList<String> arr = new ArrayList<String>();
				for (int i = 0; i < selected.size(); i++) {
					arr.add(selected.get(i));
				}
				String phase = ctrl.currentphase.getText();
				String phaseadmin = ctrl.PhaseAdministrator.getSelectionModel().getSelectedItem().toString();
				LocalDate start = null;
				LocalDate end = null;
				if (DatePickerFrom.getValue() != null) {
					start = DatePickerFrom.getValue();

				} else {
					start = null;
				}
				if (DatePickerTo.getValue() != null) {
					end = DatePickerTo.getValue();
				} else {
					end = null;
				}
				int id = ctrl.chosenRequest.getId();
				int repetion = ctrl.chosenRequest.getRepetion();
				String explain = Explaintxt2.getText();
				Object[] msg = { "manualRequestTreatmentRecruitPerformer", phaseadmin, id, phase, repetion, start, end,
						explain, arr, ctrl.lastadmin };
				try {
					LoginController.cc.getClient().sendToServer(msg);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} else {
			String phase = ctrl.currentphase.getText();
			String phaseadmin = null;
			if (ctrl.PhaseAdministrator.getSelectionModel().getSelectedItem() == null) {
				phaseadmin = ctrl.PhaseAdministrator.getPromptText();
				if (phaseadmin.equals("Choose phase administrator"))
					phaseadmin = null;
			} else {
				phaseadmin = ctrl.PhaseAdministrator.getSelectionModel().getSelectedItem().toString();
			}
			LocalDate start = null;
			LocalDate end = null;
			if (DatePickerFrom.getValue() != null) {
				start = DatePickerFrom.getValue();

			} else {
				start = null;
			}
			if (DatePickerTo.getValue() != null) {
				end = DatePickerTo.getValue();
			} else {
				end = null;
			}
			int id = ctrl.chosenRequest.getId();
			int repetion = ctrl.chosenRequest.getRepetion();
			String explain = Explaintxt2.getText();
			Object[] msg = { "manualRequestTreatmentRecruitEvaluator", phaseadmin, id, phase, repetion, start, end,
					explain, ctrl.lastadmin };
			try {
				LoginController.cc.getClient().sendToServer(msg);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void runLater(Func f) {
		f.call();
		Platform.runLater(() -> {
			try {
				Thread.sleep(5);
				f.call();

			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
	}
}
