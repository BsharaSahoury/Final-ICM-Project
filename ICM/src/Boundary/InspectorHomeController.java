package Boundary;

import java.awt.Label;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Client.ClientConsole;
import Client.Func;
import Client.MainForClient;
import Entity.Employee;
import Entity.Request;
import javafx.application.Application;
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
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
/**
 * InspectorHomeController :A controller implementing and showing 
 * the Home with all the permissions for them
 * @author Arkan Muhammad
 *
 */
public class InspectorHomeController implements Initializable {
	@FXML
	private Button notifications;
	@FXML
	private Button Homebtn;
	@FXML
	private Button Allrequestbtn;
	@FXML
	private Button RequestSubmissionbtn;
	@FXML
	private Button ProfileSettingbtn;
	@FXML
	private Button AboutICMbtn;
	@FXML
	private ComboBox Usercombobtn;
	@FXML
	public SplitPane splitpane;
	@FXML
	private AnchorPane lowerAnchorPane;
	@FXML
	private MenuButton UserNameMenu;
	public static Stage primaryStage;
	private static Employee inspector;
	private MenuItem btlogOut;
	public static AllRequestsController AllRequests;
	public static MyRequestsController MyRequests;
	public static ProfileSettingController ProfileSetting;

	private ArrayList<Request> arr;
	public static InspectorHomeController s;
/**
 * 
 * @param inspector
 */
	public void start(Employee inspector) {
		this.inspector = inspector;
		s = this;
		primaryStage = LoginController.primaryStage;
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					Parent root = FXMLLoader.load(getClass().getResource("/Boundary/Inspector-Home.fxml"));
					Scene scene = new Scene(root);
					primaryStage.setScene(scene);
					primaryStage.setResizable(false);
					primaryStage.setTitle("ICM");
					primaryStage.show();
					primaryStage.setOnCloseRequest(event -> {
						System.out.println("EXIT ICM");
						LogOutController logOut = new LogOutController();
						logOut.exit(primaryStage, inspector);
					});
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		});
	}
	/**
	 * getPrimaryStage: method to return the primaryStage of InspectorHomeController
	 * @return primaryStage
	 */
	public Stage getPrimaryStage() {
		return primaryStage;
	}
/**
 * setarr : Method sets arr => arrayList of Request
 * @param arr
 */
	public void setarr(ArrayList<Request> arr) {
		this.arr = arr;
	}
	/**
	 * Method for An action Event ,Clicking on Home button 
	 * @param event
	 * @throws Exception
	 */
	public void GoToHome(ActionEvent event) throws Exception {
		HomeController home = new HomeController();
		runLater(() -> {
			home.start(splitpane);
		});

	}
	/**
	 * Method for An action Event ,Clicking on AllRequests button 
	 * to displaying all the requests in ICM-SYSTEM
	 * @param event
	 * @throws Exception
	 */
	public void AllRequestsAction(ActionEvent event) throws Exception {
		AllRequests = new AllRequestsController();
		runLater(() -> {
			AllRequests.start(splitpane, "/Boundary/allRequests.fxml", "Inspector");
		});
	}
	/**
	 * Method for An action Event ,Clicking on RequestSubmissionAction button 
	 * to submit a request 
	 * @param event
	 * @throws Exception
	 */
	public void RequestSubmissionAction(ActionEvent event) throws Exception {
		RequestSubmissionController Submit = new RequestSubmissionController();
		runLater(() -> {
			Submit.start(splitpane, inspector);
		});
	}
	/**
	 * Method for An action Event ,Clicking on ProfileSettingAction button 
	 * to display the profileSetting of user
	 * @param event
	 * @throws Exception
	 */
	public void ProfileSettingAction(ActionEvent event) throws Exception {
		ProfileSetting = new ProfileSettingController();
		runLater(() -> {
			ProfileSetting.start(splitpane, inspector, "Inspector");
		});
	}
	/**
	 * Method for An action Event ,Clicking on MyRequestsAction button 
	 * to displaying the requests that user submitted
	 * @param event
	 * @throws Exception
	 */
	public void MyRequestsAction() throws Exception {
		MyRequests = new MyRequestsController();
		runLater(() -> {
			MyRequests.start(splitpane, inspector, "Inspector");
		});
	}
	/**
	 * Method for An action Event ,Clicking on AboutICMAction button 
	 * to displaying data of ICM-System
	 *  * @param event
	 * @throws Exception
	 */
	public void AboutICMAction(ActionEvent event) throws Exception {
		AboutICMController about = new AboutICMController();
		runLater(() -> {
			about.start(splitpane);
		});
	}

/**
 * Method for An action Event ,Clicking on LogOutAction button 
 * to Logout from the ICM-System
 *  * @param event
 * @throws Exception
 */
	public void LogOutAction(ActionEvent event) throws Exception {
		LogOutController logOut = new LogOutController();
		primaryStage.close();
		runLater(() -> {
			logOut.start(primaryStage, inspector);
		});
	}
/**
 * method to return the current user that logged-in (inspector)
 * @return
 */
	public static Employee getinspector() {
		return inspector;
	}
/**
 * Method to set All request another time in the table
 */
	public void setallrequest() {
		AllRequests = new AllRequestsController();
		runLater(() -> {
			AllRequests.start(splitpane, "/Boundary/allRequests.fxml", "Inspector");
		});
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		UserNameMenu.setText(inspector.getFirstName() + " " + inspector.getLastName());
	}
	/**
	 * Method for An action Event ,Clicking on clickNotifications button 
	 * to display your notification that you received
	 *  * @param event
	 * @throws Exception
	 */
	public void clickNotifications(ActionEvent event) throws Exception {
		NotificationsController notific = new NotificationsController();
		runLater(() -> {
			notific.start(splitpane, inspector);
		});
	}
	/**
	 * method to wait 5millisecond
	 * @param f
	 */
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