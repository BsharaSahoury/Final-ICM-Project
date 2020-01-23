package Boundary;

import java.io.IOException;

import java.net.URL;
import java.util.ResourceBundle;

import Client.ClientConsole;
import Client.Func;
import Client.MainForClient;
import Entity.Employee;
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
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
/**
 * AdministratorHomeController :A controller implementing and showing 
 * the Adminstrator Home with all the permissions for them
 * @author Arkan Muhammad
 *
 */
public class AdministratorHomeController implements Initializable {
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
	private MenuButton UserNameMenu;
	@FXML
	private SplitPane splitpane;
	@FXML
	private AnchorPane lowerAnchorPane;
	public static Stage primaryStage;
	private static Employee Administrator;
	public static MyRequestsController MyRequests; 
	public static AllRequestsController AllRequests;
	public static ProfileSettingController ProfileSetting;
/**
 * @param Administrator
 */
	public void start(Employee Administrator) {
		this.Administrator = Administrator;
		primaryStage = LoginController.primaryStage;
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				try {
					Parent root = FXMLLoader.load(getClass().getResource("/Boundary/newAdHome.fxml"));
					Scene scene = new Scene(root);
					primaryStage.setScene(scene);
					primaryStage.setResizable(false);
					primaryStage.setTitle("ICM-Home");
					primaryStage.show();
					primaryStage.setOnCloseRequest(event -> {
						System.out.println("EXIT ICM");
						System.exit(0);
					});
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
/**
 * getPrimaryStage: method to return the primaryStage of AdminsitratorHomeController
 * @return primaryStage
 */
	public Stage getPrimaryStage() {
		return primaryStage;
	}
/**
 * Method for An action Event ,Clicking on Permissions Button
 * @param e
 */
	public void PermissionsAction(ActionEvent e) {
		PermissionsController pc = new PermissionsController();
			pc.start(splitpane);
	}
/**
 * Method for An action Event ,Clicking on generateReport button 
 * @param e
 */
	public void generateReportAction(ActionEvent e) {
		ReportController rc = new ReportController();
			rc.start(splitpane);
	}
/**
 * Method for An action Event ,Clicking on Home button 
 * @param event
 * @throws Exception
 */
	public void GoToHome(ActionEvent event) throws Exception {
		HomeController home = new HomeController();
			home.start(splitpane);
	}
/**
 * Method for An action Event ,Clicking on AllRequests button 
 * to displaying all the requests in ICM-SYSTEM
 * @param event
 * @throws Exception
 */
	public void AllRequestsAction(ActionEvent event) throws Exception {
		AllRequests = new AllRequestsController();
			AllRequests.start(splitpane, "/Boundary/allRequests-for-Admin.fxml", "Administrator");
	}
/**
 * Method for An action Event ,Clicking on RequestSubmissionAction button 
 * to submit a request 
 * @param event
 * @throws Exception
 */
	public void RequestSubmissionAction(ActionEvent event) throws Exception {
		RequestSubmissionController Submit = new RequestSubmissionController();
			Submit.start(splitpane, Administrator);
	}
/**
 * Method for An action Event ,Clicking on ProfileSettingAction button 
 * to display the profileSetting of Administrator
 * @param event
 * @throws Exception
 */
	public void ProfileSettingAction(ActionEvent event) throws Exception {

		ProfileSetting = new ProfileSettingController();
			ProfileSetting.start(splitpane, Administrator, "Administrator");
	}
/**
 * Method for An action Event ,Clicking on MyRequestsAction button 
 * to displaying the requests that Administrator submitted
 * @param event
 * @throws Exception
 */
	public void MyRequestsAction(ActionEvent event) throws Exception {
		MyRequests = new MyRequestsController();
			MyRequests.start(splitpane, Administrator, "Administrator");
	}
/**
 * Method for An action Event ,Clicking on AboutICMAction button 
 * to displaying data of ICM-System
 *  * @param event
 * @throws Exception
 */
	public void AboutICMAction(ActionEvent event) throws Exception {
		AboutICMController about = new AboutICMController();
			about.start(splitpane);
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
			logOut.start(primaryStage, Administrator);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		UserNameMenu.setText(Administrator.getFirstName() + " " + Administrator.getLastName());
	}
	/**
	 * Method for An action Event ,Clicking on clickNotifications button 
	 * to display your notification that you received
	 *  * @param event
	 * @throws Exception
	 */
	public void clickNotifications(ActionEvent event) throws Exception {
		NotificationsController notific = new NotificationsController();
			notific.start(splitpane, Administrator);
	}
/**
 * Method that returns the User Administrator
 * @return Administrator
 */
	public static Employee getAdministrator() {
		return Administrator;
	}

}