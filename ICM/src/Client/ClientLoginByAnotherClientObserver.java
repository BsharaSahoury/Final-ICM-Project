package Client;

import java.util.ArrayList;
import java.util.Observable;

import java.util.Observer;

import Boundary.AdministratorHomeController;
import Boundary.ChairmanHomeController;
import Boundary.ComitteeMemberHomeController;
import Boundary.EvaluatorHomeController;
import Boundary.InspectorHomeController;
import Boundary.LecturerHomeController;
import Boundary.LoginController;
import Boundary.MainClientController;
import Boundary.PerformanceLeaderHomeController;
import Boundary.StudentHomeController;
import Boundary.TesterHomeController;
import Entity.Employee;
import Entity.Student;
import Entity.User;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ClientLoginByAnotherClientObserver implements Observer{
	private static InspectorHomeController  inspector;
	private Employee employee1;
	
	public ClientLoginByAnotherClientObserver(Observable client) {
		client.addObserver(this);
	}

	
	
	@Override
	public void update(Observable arg0, Object arg1) {
		if(arg1 instanceof String) {
			String s=(String)arg1;
			if(s.equals("AnotherClientIsLoggedInByThisUser"))
			Platform.runLater(new Runnable() {

				@Override
				public void run() {
					Alert alert = new Alert(AlertType.WARNING);
					alert.setTitle("Login Failed!");
					Text headerText=new Text("Login Failed!");
					headerText.setFont(Font.font("System", FontWeight.BOLD, FontPosture.REGULAR, 20));
					alert.setHeaderText("Login Failed!");
					VBox dialogPaneContent = new VBox();
					Label label1 = new Label("Another computer is currently logged into this account!");
					label1.setFont(Font.font("System", FontWeight.BOLD, FontPosture.REGULAR, 12)); 
					Label label2 = new Label("Please Try again!");
						
					dialogPaneContent.getChildren().addAll(label1, label2);
			 		alert.getDialogPane().setContent(dialogPaneContent);
					alert.showAndWait();
					
				}
				
			});
		}
	}
}
