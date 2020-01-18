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

public class EvaluatorHomeController implements Initializable {
	
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
	private SplitPane splitpane ;
	@FXML
    private AnchorPane lowerAnchorPane;
	@FXML
	private MenuButton UserNameMenu;
	public static Stage primaryStage;
	public static MyRequestsController MyRequests;
	public static ProfileSettingController ProfileSetting;
	public static RequestsWorkedOnController RequestWorkON;
	public static EvaluatorHomeController e;
	private static Employee evaluator;

	public void start(Employee evaluator) {
		this.evaluator=evaluator;
		primaryStage=LoginController.primaryStage;
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
		try{
			Parent root = FXMLLoader.load(getClass().getResource("/Boundary/Evaluator-Home.fxml"));			
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.setTitle("ICM-Home");			
			primaryStage.show();
			primaryStage.setOnCloseRequest( event ->
		    {
				System.out.println("EXIT ICM");
				LogOutController logOut = new LogOutController();
				logOut.exit(primaryStage,evaluator);
		    });			
		} catch(Exception e) {
			e.printStackTrace();
		}		
	}
		});
	}
	
public Stage getPrimaryStage() {
		return primaryStage;
	}

public void GoToHome(ActionEvent event) throws Exception {
	try {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/Boundary/Home.fxml"));
		lowerAnchorPane = loader.load();
		splitpane.getItems().set(1, lowerAnchorPane);
	} catch (IOException e) {		
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}

public void RequestWorkedOnAction(ActionEvent event) throws Exception {
	RequestWorkON = new RequestsWorkedOnController();
	RequestWorkON.start(splitpane, "/Boundary/RequestsWorkOnEvaluator.fxml",evaluator,"Evaluator","evaluation");}

public void RequestSubmissionAction(ActionEvent event) throws Exception {
	RequestSubmissionController Submit=new RequestSubmissionController();
	Submit.start(splitpane,evaluator);
}
public void MyRequestsAction() throws Exception {
	MyRequests = new MyRequestsController();
	MyRequests.start(splitpane,evaluator,"Evaluator");
}

public void ProfileSettingAction(ActionEvent event) throws Exception {
	ProfileSetting = new ProfileSettingController();
	runLater(() -> {
		ProfileSetting.start(splitpane,evaluator,"Evaluator");
	});
}

public void AboutICMAction(ActionEvent event) throws Exception {
	AboutICMController about=new AboutICMController();
	about.start(splitpane);
}
public void clickNotifications(ActionEvent event) throws Exception {
	NotificationsController notific=new NotificationsController();
	notific.start(splitpane,evaluator);
}
public void LogOutAction(ActionEvent event) throws Exception {
	LogOutController logOut = new LogOutController();
	primaryStage.close();
	logOut.start(primaryStage,evaluator);
}
@Override
public void initialize(URL location, ResourceBundle resources) {
	// TODO Auto-generated method stub
	UserNameMenu.setText(evaluator.getFirstName()+" "+evaluator.getLastName());
}
private void runLater(Func f) {
	f.call();
	Platform.runLater(() -> {
		try {
			Thread.sleep(10);
			f.call();

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	});
}
}