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
    private static int flag=0;
	public void start(Employee employee) {
		this.comitteeMember = employee;
		this.employee=employee;
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
						logOut.exit(primaryStage,comitteeMember);
					});
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
 public static void setChairman(Employee user) {
	 employee=user;
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
     	
	public void RequestForTestOnAction(ActionEvent event) throws Exception {
		flag=1;
		RequestWorkON = new RequestsWorkedOnController();
		runLater(() -> {
			RequestWorkON.start(splitpane, "/Boundary/RequestsWorkOnTester.fxml",comitteeMember,"Comittee Member","testing");
		});			
		}
	public void RequestWorkedOnAction(ActionEvent event) throws Exception {
		flag=0;
		RequestWorkON = new RequestsWorkedOnController();
		runLater(() -> {
			RequestWorkON.start(splitpane, "/Boundary/RequestWorkOnCommittemember.fxml",comitteeMember,"Comittee Member","decision");
		});
	}
	public static int getFlag() {
		return flag;
	}
    
	public static Employee getcomitteeMember() {
		return comitteeMember;
	}
	
	
	public void RequestSubmissionAction(ActionEvent event) throws Exception {
		RequestSubmissionController Submit = new RequestSubmissionController();
		runLater(() -> {
			Submit.start(splitpane,comitteeMember);
		});
	}

	public void ProfileSettingAction(ActionEvent event) throws Exception {
		ProfileSetting = new ProfileSettingController();
		if(comitteeMember.getJob().equals("comittee member"))
		runLater(() -> {
				ProfileSetting.start(splitpane,comitteeMember,"Committee member");
		});
		else
			runLater(() -> {
					ProfileSetting.start(splitpane,employee,"Chairman");
			});


	}

	public void MyRequestsAction(ActionEvent event) throws Exception {
		MyRequests = new MyRequestsController();
		runLater(() -> {
			MyRequests.start(splitpane, comitteeMember,"Comittee Member");
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
			logOut.start(primaryStage,comitteeMember);
		});
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		UserNameMenu.setText(comitteeMember.getFirstName()+" "+comitteeMember.getLastName());
		role.setText("Role : "+comitteeMember.getJob());
		String msg="get ChairMan";
		try {
			LoginController.cc.getClient().sendToServer(msg);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void clickNotifications(ActionEvent event) throws Exception {
		NotificationsController notific=new NotificationsController();
		runLater(() -> {
			notific.start(splitpane,comitteeMember);
		});
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

