package Boundary;

import javafx.collections.FXCollections;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.scene.layout.Pane;
import Client.ClientConsole;
import Client.Func;
import Entity.Phase;
import Entity.Request;
import Entity.RequestPhase;

public class AllRequestsController implements Initializable {
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
	private Button RequestInfo;
	private static SplitPane splitpane;
	@FXML
	private ComboBox Groupby;
	@FXML
	private TextField search_text;
	private FXMLLoader loader;
	private TableView<Request> tabl;
	private static int chosenRequest = -1;
	private static int chosengroupbytype = -1;
	private static ObservableList<RequestPhase> list;
	private static ArrayList<RequestPhase> arrofRequests;
	private static String job;
	private static RequestPhase chosenR;
	ObservableList<String> statuslist = FXCollections.observableArrayList("Active", "Frozen", "Closed", "All");

	public void start(SplitPane splitpane, String path, String job) {
		this.job = job;

		primaryStage = LoginController.primaryStage;
		this.cc = LoginController.cc;
		String[] AllRequests = new String[2];
		try {
			loader = new FXMLLoader(getClass().getResource(path));
			lowerAnchorPane = loader.load();
			splitpane.getItems().set(1, lowerAnchorPane);
			this.splitpane = splitpane;
			AllRequests[0] = "All Requests";
			AllRequests[1] = job;
			cc.getClient().sendToServer(AllRequests);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setTableRequests(ArrayList<RequestPhase> arr1) {
		list = FXCollections.observableArrayList(arr1);
		tableRequests.setItems(list);
	}

	public void fillTable(ArrayList<RequestPhase> arr1) {
		arrofRequests = arr1;
		loader.<AllRequestsController>getController().setTableRequests(arr1);
	}

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

	public void GroupbyAction(ActionEvent e) {
		chosengroupbytype = Groupby.getSelectionModel().getSelectedIndex();
		String groupbystatus = null;
		ArrayList<RequestPhase> arr = new ArrayList<RequestPhase>();
		if (chosengroupbytype != -1) {
			if (chosengroupbytype == 0)
				groupbystatus = "active";
			else if (chosengroupbytype == 1)
				groupbystatus = "frozen";
			else if (chosengroupbytype == 2)
				groupbystatus = "closed";
			else if (chosengroupbytype == 3)
				groupbystatus = "All";
			if (groupbystatus.equals("All") && job.equals("Inspector")) {
				InspectorHomeController.AllRequests.loader.<AllRequestsController>getController().tableRequests
						.setItems(FXCollections.observableArrayList(arrofRequests));
			} else if (groupbystatus.equals("All") && job.equals("Administrator")) {
				AdministratorHomeController.AllRequests.loader.<AllRequestsController>getController().tableRequests
						.setItems(FXCollections.observableArrayList(arrofRequests));
			} else {
				if (arrofRequests != null) {
					for (int i = 0; i < arrofRequests.size(); i++)
						if ((arrofRequests.get(i)).getStatus().equals(groupbystatus))
							arr.add(arrofRequests.get(i));
					if (job.equals("Inspector"))
						InspectorHomeController.AllRequests.loader.<AllRequestsController>getController().tableRequests
								.setItems(FXCollections.observableArrayList(arr));
					else
						AdministratorHomeController.AllRequests.loader
								.<AllRequestsController>getController().tableRequests
										.setItems(FXCollections.observableArrayList(arr));
				}
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
				e.printStackTrace();
			}
		});
	}

	public void RequestInfoAction(ActionEvent e) {
		chosenRequest = tableRequests.getSelectionModel().getSelectedIndex();
		if (chosenRequest != -1) {
			RequestPhase s = tableRequests.getSelectionModel().getSelectedItem();
			if (ClientConsole.map.get(s.getId()).equals("frozen")) {
				ClientConsole.displayFreezeError();
				return;
			}

			RequestInfoController requestifo = new RequestInfoController();
			requestifo.start(splitpane, s.getR(), job);
			runLater(() -> {
				requestifo.start(splitpane, s.getR(), job);
			});
		} else {
			Alert alertWarning = new Alert(AlertType.WARNING);
			alertWarning.setTitle("Warning Alert Title");
			alertWarning.setHeaderText("Warning!");
			alertWarning.setContentText("please choose requset");
			alertWarning.showAndWait();
		}
	}

	public static int getselectedindex() {
		return chosenRequest;
	}

	public static ObservableList<RequestPhase> getList() {
		return list;
	}

	public static RequestPhase getselectedRequest() {
		return chosenR;
	}

	public void RequestTreatmentAction(ActionEvent e) {

		chosenRequest = tableRequests.getSelectionModel().getSelectedIndex();
		if (chosenRequest != -1) {
			chosenR = tableRequests.getSelectionModel().getSelectedItem();
			if (ClientConsole.map.get(chosenR.getId()).equals("frozen")) {
				ClientConsole.displayFreezeError();
				return;
			}
			RequestTreatmentAction Treatment = new RequestTreatmentAction();
			runLater(() -> {
				Treatment.start(splitpane);
			});
		} else {
			Alert alertWarning = new Alert(AlertType.WARNING);
			alertWarning.setTitle("Warning Alert Title");
			alertWarning.setHeaderText("Warning!");
			alertWarning.setContentText("please choose requset");
			alertWarning.showAndWait();
		}
	}

	public void ChangeStatusAction(ActionEvent e) {
		chosenRequest = tableRequests.getSelectionModel().getSelectedIndex();
		if (chosenRequest != -1) {
			chosenR = tableRequests.getSelectionModel().getSelectedItem();
			RequestChangesStatusToActiveForAdmin ChangeStatus = new RequestChangesStatusToActiveForAdmin();
			runLater(() -> {
				ChangeStatus.start(splitpane);
			});
		} else {
			Alert alertWarning = new Alert(AlertType.WARNING);
			alertWarning.setTitle("Warning Alert Title");
			alertWarning.setHeaderText("Warning!");
			alertWarning.setContentText("please choose requset");
			alertWarning.showAndWait();
		}
	}

	public void refresh() {
		try {
			InspectorHomeController.AllRequests.start(splitpane, "/Boundary/allRequests.fxml", "Inspector");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Groupby.setItems(statuslist);
		colID.setCellValueFactory(new PropertyValueFactory<RequestPhase, Integer>("id"));
		colName.setCellValueFactory(new PropertyValueFactory<RequestPhase, String>("initiatorName"));
		colStatus.setCellValueFactory(new PropertyValueFactory<RequestPhase, String>("status"));
		colPriflig.setCellValueFactory(new PropertyValueFactory<RequestPhase, String>("privilegedInfoSys"));
		colSubDate.setCellValueFactory(new PropertyValueFactory<RequestPhase, Date>("date"));
		colPhase.setCellValueFactory(new PropertyValueFactory<RequestPhase, Phase>("phase"));
	}
}
