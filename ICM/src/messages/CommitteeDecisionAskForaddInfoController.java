package messages;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Boundary.LoginController;
import Boundary.NotificationsController;
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

public class CommitteeDecisionAskForaddInfoController implements Initializable {
	@FXML
	Label DecisionLable;
	@FXML
	Button approve;
	@FXML
	ComboBox<String> combo;
	private ObservableList<String> list;
	public static CommitteeDecisionAskForaddInfoController ctrl;
	public static Stage primaryStage;
	private AnchorPane lowerAnchorPane;
	public  static SplitPane splitpane;
	private int requestID;
	private String CommitteeDecision;
	 public static int flag=-1;
	 private static String notdetails;
	 private static int notificationID;
	public void start(SplitPane splitpane,String path) {
		primaryStage=LoginController.primaryStage;
		try{	
			FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
			lowerAnchorPane = loader.load();
			ctrl=loader.getController();
			Object[] message= {"get explain notification",ctrl.notificationID,"inspector to recruit evaluator"};
			try {
				LoginController.cc.getClient().sendToServer(message);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}	
			Object[] msg= {"evaluatorsagain",getClass().getName()};
			try {
				LoginController.cc.getClient().sendToServer(msg);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			splitpane.getItems().set(1, lowerAnchorPane);
			this.splitpane=splitpane;			
		} catch(Exception e) {
			e.printStackTrace();
		}			
	}
	public static void setdetails(String details) {
		ctrl.notdetails=details;
		ctrl.DecisionLable.setText(ctrl.notdetails);
	}
	public void RecruitAction(ActionEvent e) {	
		if(ClientConsole.map.get(requestID).equals("frozen")) {
			ClientConsole.displayFreezeError();
			return;
		}
		String fullname=combo.getSelectionModel().getSelectedItem();
		if(fullname==null) {
			Alert alert = new Alert(AlertType.INFORMATION);
	        alert.setTitle("TEST");
	        alert.setHeaderText("ERROR");
	        alert.setContentText("please choose an evaluator");
	        alert.showAndWait();
		}
		else if(flag==-1) {
			flag=0;
				Object[] msg= {"manualEvaluatorAgain",fullname,requestID};
				try {
					LoginController.cc.getClient().sendToServer(msg);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}		
			}
			else {	
			 Alert alertSuccess = new Alert(AlertType.WARNING);
			 alertSuccess.setTitle("Warning");
			 alertSuccess.setHeaderText("Already Approve");
			 alertSuccess.setContentText("You already recruited an evaluator");
			 alertSuccess.showAndWait();
		}
	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		requestID=NotificationsController.getidofrequestforDecision();
		CommitteeDecision=NotificationsController.getDecisionofcommitteemember();
		requestID=NotificationsController.getidofrequestforDecision();
		notificationID=NotificationsController.getidnotification();
	//	DecisionLable.setText(NotificationsController.getExplainDecisionofcommitteemember());	
	}
	public void fillCombo(ArrayList<String> names) {
		list=FXCollections.observableArrayList(names);
		combo.setItems(list);
		
	}
}


