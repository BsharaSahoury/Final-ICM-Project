package messages;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Boundary.LoginController;
import Client.ClientConsole;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * Notification for choosing tester when performance phase completed the tester
 * is committee member
 */
public class ChooseTesterMessageController implements Initializable {
	@FXML
	Label label1;
	@FXML
	ComboBox<String> combo;
	@FXML
	Button recruit;

	public static ChooseTesterMessageController ctrl;
	public static Stage primaryStage;
	private AnchorPane lowerAnchorPane;
	public static SplitPane splitpane;
	private ObservableList<String> list;
	private static int requestID;

	/**
	 * open chooseTesterMessage
	 * 
	 * @param splitpane Choose Tester Notification
	 * @param id        Request Id
	 */
	public void start(SplitPane splitpane, int id) {
		primaryStage = LoginController.primaryStage;
		this.requestID = id;
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/messages/ChooseTester-message.fxml"));
			lowerAnchorPane = loader.load();
			ctrl = loader.getController();
			splitpane.getItems().set(1, lowerAnchorPane);
			this.splitpane = splitpane;
			ctrl.label1.setVisible(false);
			ctrl.label1.setText("Performance phase is complete for request#" + requestID + ".");
			ctrl.label1.setVisible(true);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * get committee members for choosing tester
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Object[] msg = { "comittee members", getClass().getName() };
		try {
			LoginController.cc.getClient().sendToServer(msg);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Recruit button will recruit chosen tester
	 * @param e Action Event ( click on button)
	 */
	public void recruitAction(ActionEvent e) {
		if (ClientConsole.map.get(requestID).equals("frozen")) {
			ClientConsole.displayFreezeError();
			return;
		}
		String fullname = combo.getSelectionModel().getSelectedItem();
		if (fullname == null) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("TEST");
			alert.setHeaderText("ERROR");
			alert.setContentText("please choose an tester");
			alert.showAndWait();
			return;
		}
		Object[] msg = { "manualTester", fullname, requestID };
		try {
			LoginController.cc.getClient().sendToServer(msg);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
/**
 *  Fill the combobox committee members for choose tester (tester is a committee member)
 * @param names
 */
	public void fillCombo(ArrayList<String> names) {
		list = FXCollections.observableArrayList(names);
		combo.setItems(list);
	}

}
