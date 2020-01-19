package Boundary;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Client.Func;
import Entity.Employee;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuButton;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class EngineerHomeController implements Initializable {

	@FXML
	private Button notification;
	@FXML
	private Button Homebtn;
	@FXML
	private Button RequestWorkedOnbtn;
	@FXML
	private Button RequestSubmissionbtn;
	@FXML
	private Button ProfileSettingbtn;
	@FXML
	private Button AboutICMbtn;
	@FXML
	private ComboBox Usercombobtn;
	@FXML
	private SplitPane splitpane;
	@FXML
	private AnchorPane lowerAnchorPane;
	@FXML
	private MenuButton UserNameMenu;
	public static Stage primaryStage;
	private static Employee performanceLeader;
	public static MyRequestsController MyRequests;
	public static RequestsWorkedOnController RequestWorkON;
	public static ProfileSettingController ProfileSetting;

	public void start(Employee performanceLeader) {
		this.performanceLeader = performanceLeader;
		primaryStage = LoginController.primaryStage;
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				try {
					Parent root = FXMLLoader.load(getClass().getResource("/Boundary/Engineer-Home.fxml"));
					Scene scene = new Scene(root);
					primaryStage.setScene(scene);
					primaryStage.setResizable(false);
					primaryStage.setTitle("ICM-Home");
					primaryStage.show();
					primaryStage.setOnCloseRequest(event -> {
						System.out.println("EXIT ICM");
						LogOutController logOut = new LogOutController();
						logOut.exit(primaryStage, performanceLeader);
					});
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Stage getPrimaryStage() {
		return primaryStage;
	}

	public void GoToHome(ActionEvent event) throws Exception {
		HomeController home = new HomeController();
		runLater(() -> {
			home.start(splitpane);
		});
	}

	public void RequestThatIlead(ActionEvent event) throws Exception {
		RequestWorkON = new RequestsWorkedOnController();
		runLater(() -> {
			RequestWorkON.start(splitpane, "/Boundary/RequestWorkOnPerformer.fxml", performanceLeader,
					"Performance Leader", "performance");
		});
	}

	public void RequestWorkedOnAction(ActionEvent event) throws Exception {
		RequestWorkON = new RequestsWorkedOnController();
		runLater(() -> {
			RequestWorkON.start(splitpane, "/Boundary/Engineer-Home.fxml", performanceLeader, "Performance Leader",
					"performance");
		});
	}

	public void RequestSubmissionAction(ActionEvent event) throws Exception {
		RequestSubmissionController Submit = new RequestSubmissionController();
		runLater(() -> {
			Submit.start(splitpane, performanceLeader);
		});
	}

	public void ProfileSettingAction(ActionEvent event) throws Exception {
		ProfileSetting = new ProfileSettingController();
		runLater(() -> {
			ProfileSetting.start(splitpane, performanceLeader, "Engineer");
		});
	}

	public void MyRequestsAction(ActionEvent event) throws Exception {
		MyRequests = new MyRequestsController();
		runLater(() -> {
			MyRequests.start(splitpane, performanceLeader, "Performance Leader");
		});
	}

	public void AboutICMAction(ActionEvent event) throws Exception {
		AboutICMController about = new AboutICMController();
		runLater(() -> {
			about.start(splitpane);
		});
	}

	public void LogOutAction(ActionEvent event) throws Exception {
		LogOutController logOut = new LogOutController();
		primaryStage.close();
		runLater(() -> {
			logOut.start(primaryStage, performanceLeader);
		});
	}

	public void clickNotifications(ActionEvent event) throws Exception {
		NotificationsController notific = new NotificationsController();
		runLater(() -> {
			notific.start(splitpane, performanceLeader);
		});
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		UserNameMenu.setText(performanceLeader.getFirstName() + " " + performanceLeader.getLastName());
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
