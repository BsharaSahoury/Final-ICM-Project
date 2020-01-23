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
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
/**
 * ComitteeMemberHomeController :A controller implementing and showing 
 * the ComitteeMember Home with all the permissions for them
 * @author Arkan Muhammad
 *
 */
public class ComitteeMemberHomeController implements Initializable {
	@FXML
	private Button notifications;
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
	private SplitPane splitpane;
	@FXML
	private AnchorPane lowerAnchorPane;
	@FXML
	private MenuButton MBusername;
	@FXML
	private MenuButton UserNameMenu;
	@FXML
	private MenuItem role;
	public static Stage primaryStage;
	private static Employee comitteeMember;
	public static MyRequestsController MyRequests;
	public static ProfileSettingController ProfileSetting;
	public static RequestsWorkedOnController RequestWorkON;
	public static Employee employee;
	public static Employee Chairman;
	/**
	 * @param flag => if flag =1 : requests that I lead
	 * else requests that i work on
	 */
	private static int flag = 0;
	private static String myjob;
/**
 * 
 * @param employee
 */
	public void start(Employee employee) {
		this.comitteeMember = employee;
		this.employee = employee;
		this.myjob = employee.getJob();
		primaryStage = LoginController.primaryStage;
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				try {
					Parent root = FXMLLoader.load(getClass().getResource("/Boundary/CommitteeMember-Home.fxml"));
					Scene scene = new Scene(root);
					primaryStage.setScene(scene);
					primaryStage.setResizable(false);
					primaryStage.setTitle("ICM");
					primaryStage.show();
					primaryStage.setOnCloseRequest(event -> {
						System.out.println("EXIT ICM");
						LogOutController logOut = new LogOutController();
						logOut.exit(primaryStage, comitteeMember);
					});
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
/**
 * Method to Sets the user that logged-in
 * @param user
 */
	public static void setChairman(Employee user) {
		employee = user;
	}
	/**
	 * getPrimaryStage: method to return the primaryStage of ComitteeMemberHomeController
	 * @return primaryStage
	 */
	public Stage getPrimaryStage() {
		return primaryStage;
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
	 * Method for An action Event ,Clicking on RequestForTestOnAction button 
	 * to displaying all the requests that in the Test phase in ICM-SYSTEM
	 * @param event
	 * @throws Exception
	 */
	public void RequestForTestOnAction(ActionEvent event) throws Exception {
		flag = 1;
		RequestWorkON = new RequestsWorkedOnController();
			RequestWorkON.start(splitpane, "/Boundary/RequestsWorkOnTester.fxml", comitteeMember, "Comittee Member",
					"testing");
	}
	/**
	 * Method for An action Event ,Clicking on RequestWorkedOnAction button 
	 * to displaying all the requests that the user work on
	 * @param event
	 * @throws Exception
	 */
	public void RequestWorkedOnAction(ActionEvent event) throws Exception {
		flag = 0;
		RequestWorkON = new RequestsWorkedOnController();
		if (myjob.equals("chairman")) {
				RequestWorkON.start(splitpane, "/Boundary/RequestWorkOnCommittemember.fxml", comitteeMember, "Chairman",
						"decision");
		} else {
				RequestWorkON.start(splitpane, "/Boundary/RequestWorkOnCommittemember.fxml", comitteeMember,
						"Comittee Member", "decision");
		}

	}
/**
 * getFlag:Method returning a flag,
 * @return
 */
	public static int getFlag() {
		return flag;
	}
	/**
	 * Method to gets the committeeMember
	 * @param comitteeMember
	 */
	public static Employee getcomitteeMember() {
		return comitteeMember;
	}
	/**
	 * Method for An action Event ,Clicking on RequestSubmissionAction button 
	 * to submit a request 
	 * @param event
	 * @throws Exception
	 */
	public void RequestSubmissionAction(ActionEvent event) throws Exception {
		RequestSubmissionController Submit = new RequestSubmissionController();
			Submit.start(splitpane, comitteeMember);
	}
	/**
	 * Method for An action Event ,Clicking on ProfileSettingAction button 
	 * to display the profileSetting of user
	 * @param event
	 * @throws Exception
	 */
	public void ProfileSettingAction(ActionEvent event) throws Exception {
		ProfileSetting = new ProfileSettingController();
		if (comitteeMember.getJob().equals("comittee member"))
				ProfileSetting.start(splitpane, comitteeMember, "Committee member");
		else
				ProfileSetting.start(splitpane, employee, "Chairman");
	}
	/**
	 * Method for An action Event ,Clicking on MyRequestsAction button 
	 * to displaying the requests that user submitted
	 * @param event
	 * @throws Exception
	 */
	public void MyRequestsAction(ActionEvent event) throws Exception {
		MyRequests = new MyRequestsController();
			MyRequests.start(splitpane, comitteeMember, "Comittee Member");
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
			logOut.start(primaryStage, comitteeMember);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		UserNameMenu.setText(comitteeMember.getFirstName() + " " + comitteeMember.getLastName());
		role.setText("Role : " + comitteeMember.getJob());
		String msg = "get ChairMan";
		try {
			LoginController.cc.getClient().sendToServer(msg);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * Method for An action Event ,Clicking on clickNotifications button 
	 * to display your notification that you received
	 *  * @param event
	 * @throws Exception
	 */
	public void clickNotifications(ActionEvent event) throws Exception {
		NotificationsController notific = new NotificationsController();
			notific.start(splitpane, comitteeMember);
	}
}
