package messages;
import java.io.IOException; 
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Boundary.LoginController;
import Boundary.NotificationsController;
import Client.ClientConsole;
import Entity.User;
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

public class SuccessTestMessageController implements Initializable {
	@FXML
	Label requestLabel;
	
	
	@FXML
	Button other;
	@FXML
	ComboBox<String> combo;
	
	public static SuccessTestMessageController ctrl;
	public static Stage primaryStage;
	private AnchorPane lowerAnchorPane;
	public  static SplitPane splitpane;
	private int requestID;
	private ObservableList<String> list;
	private int flag=-1;
	public void start(SplitPane splitpane,int id) {
		primaryStage=LoginController.primaryStage;
		try{	
			
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/messages/SuccessTest-message.fxml"));
			lowerAnchorPane = loader.load();
			ctrl=loader.getController();
			splitpane.getItems().set(1, lowerAnchorPane);
			this.splitpane=splitpane;
			System.out.println("okay3");
			ctrl.requestLabel.setVisible(false);
			ctrl.requestLabel.setText("Request with id #"+id+", Test Passed!");
			ctrl.requestLabel.setVisible(true);
			System.out.println("okayfinal");
			
		} catch(Exception e) {
			e.printStackTrace();
		}			
	}
	
	public void SendToInitiatorAction(ActionEvent e) {	
		if(ClientConsole.map.get(requestID).equals("frozen")) {
			ClientConsole.displayFreezeError();
			return;
		}
		
		if(flag==-1) {
			flag=0;
			Object[] msg= {"Send to initiator that request approved",requestID,"passed"};
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
			 alertSuccess.setHeaderText("Already Sent");
			 alertSuccess.setContentText("You already sent the decision to the initiator");
			 alertSuccess.showAndWait();
		}
	}
				
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		requestID=NotificationsController.getidofrequestforDecision();
		System.out.println(requestID+"Sx");
	}
	public void fillCombo(ArrayList<String> names) {
		list=FXCollections.observableArrayList(names);
		combo.setItems(list);
		
	}
	
	

}


