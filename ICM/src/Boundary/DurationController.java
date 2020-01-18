package Boundary;

import java.net.URL;
import java.util.ResourceBundle;
import Client.ClientConsole;
import Entity.Phase;
import Entity.RequestPhase;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class DurationController implements Initializable {
	public static DurationController duration;
	public static Stage primaryStage;
	private static ClientConsole cc;
	private AnchorPane lowerAnchorPane;
	@FXML
	private static SplitPane splitpane;
	@FXML
	private DatePicker startDate;
	@FXML
	private DatePicker dueDate;

	private static RequestPhase rp;
	private static Phase phase;

	public void start(SplitPane splitpane, String path, RequestPhase rp) {
		this.splitpane = splitpane;
		primaryStage = LoginController.primaryStage;
		this.cc = LoginController.cc;
		this.rp = rp;
		this.phase = rp.getPhase();

		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
			lowerAnchorPane = loader.load();
			duration = loader.getController();
			splitpane.getItems().set(1, lowerAnchorPane);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		dueDate.setValue(RequestsWorkedOnController.getRP().getDueDate().toLocalDate());
		startDate.setValue(RequestsWorkedOnController.getRP().getStartDate().toLocalDate());
	}
}
