package messages;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import Boundary.LoginController;
import Boundary.NotificationsController;
import Boundary.RequestsWorkedOnController;
import Client.ClientConsole;
import Entity.Phase;
import Entity.RequestPhase;
import Entity.State;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class approveDuratinController implements Initializable {
	@FXML
	Label label;
	@FXML
	DatePicker start;
	@FXML
	DatePicker due;
	@FXML
	Button approve;
	@FXML
	private Label note;
	@FXML
	public static approveDuratinController ctrl;
	public static Stage primaryStage;
	private AnchorPane lowerAnchorPane;
	public static SplitPane splitpane;
	public static int id;
	public static String phase;

	public void start(SplitPane splitpane, String content, String start, String due, int id, String p) {
		this.id = id;
		this.phase = p;
		primaryStage = LoginController.primaryStage;
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/messages/approveDuration.fxml"));
			lowerAnchorPane = loader.load();
			ctrl = loader.getController();
			splitpane.getItems().set(1, lowerAnchorPane);
			this.splitpane = splitpane;
			ctrl.label.setVisible(false);
			ctrl.label.setText(content);
			ctrl.label.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void approveAction() {
		if (ClientConsole.map.get(id).equals("frozen")) {
			ClientConsole.displayFreezeError();
			return;
		}
		LocalDate startDate = start.getValue();
		LocalDate dueDate = due.getValue();
		LocalDate today = LocalDate.now();
		if (startDate != null && dueDate != null & dueDate.compareTo(startDate) >= 0
				&& startDate.compareTo(today) >= 0) {
			String keymessage = "ispector duration";
			LocalDate d[] = { startDate, dueDate };
			Object[] message = { keymessage, id, d, Enum.valueOf(Phase.class, phase), State.wait };

			try {
				LoginController.cc.getClient().sendToServer(message);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			approve.setDisable(true);

		} else {
			Alert alertWarning = new Alert(AlertType.WARNING);
			alertWarning.setHeaderText("Warning!");
			alertWarning.setContentText("Please check the dates correctly");
			alertWarning.showAndWait();
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		String keymessage = "checkAprproveDuration";
		Object[] message = { keymessage, id, NotificationsController.getMyPhase() };
		try {
			LoginController.cc.getClient().sendToServer(message);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void checkApprove(RequestPhase rp) {
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				if (rp.getState().equals(State.work) || rp.getState().equals(State.wait)) {
					note.setVisible(false);
					note.setText("* You already approved duration");
					note.setVisible(true);
					approve.setDisable(true);
				}
				LocalDate startDate = rp.getStartDate().toLocalDate();
				LocalDate dueDate = rp.getDueDate().toLocalDate();
				start.setValue(startDate);
				due.setValue(dueDate);

			}
		});

	}
}
