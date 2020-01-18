
package messages;

import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Boundary.LoginController;
import Boundary.NotificationsController;
import Boundary.RequestsWorkedOnController;
import Client.ClientConsole;
import Entity.Phase;
import Entity.RequestPhase;
import Entity.User;
import javafx.application.Platform;
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

public class AutomaticRecruitMessageController implements Initializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@FXML
	Label requestLabel;
	@FXML
	Label evaluatorLabel;
	@FXML
	Button approve;
	@FXML
	Button other;
	@FXML
	ComboBox<String> combo;
	@FXML
	Label Label;
	
	public static AutomaticRecruitMessageController ctrl;
	public static Stage primaryStage;
	private AnchorPane lowerAnchorPane;
	public  static SplitPane splitpane;
	private int requestID;
	private String fullname;
	private ObservableList<String> list;
	public void start(SplitPane splitpane,int id, String fullname) {
		primaryStage=LoginController.primaryStage;
		try{	
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/messages/automaticRecruit-message.fxml"));
			lowerAnchorPane = loader.load();
			ctrl=loader.getController();
			splitpane.getItems().set(1, lowerAnchorPane);
			this.splitpane=splitpane;
			ctrl.requestLabel.setVisible(false);
			ctrl.requestLabel.setText("New request with id #"+id+" has been submitted recently,");
			ctrl.requestLabel.setVisible(true);
			ctrl.evaluatorLabel.setVisible(false);
			ctrl.evaluatorLabel.setText("Evaluator "+fullname+" will be recruited automatically to evaluate the request.would you like to approve?");
			ctrl.evaluatorLabel.setVisible(true);
			ctrl.requestID=id;
			this.fullname=fullname;
			
		} catch(Exception e) {
			e.printStackTrace();
		}			
	}
	public void approveAction(ActionEvent e) {
		if(ClientConsole.map.get(requestID).equals("frozen")) {
			ClientConsole.displayFreezeError();
			return;
		}
		Object[] message= {"automatic",requestID};
		try {
			LoginController.cc.getClient().sendToServer(message);
			ctrl.approve.setDisable(true);
			other.setDisable(true);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}	
	}
	public void chooseOtherAction(ActionEvent e) {
		String fullname=combo.getSelectionModel().getSelectedItem();
		if(fullname==null) {
			Alert alert = new Alert(AlertType.INFORMATION);
	        alert.setTitle("TEST");
	        alert.setHeaderText("ERROR");
	        alert.setContentText("please choose an evaluator");
	        alert.showAndWait();
	        return;
		}
		Object[] msg= {"manualEvaluator",fullname,requestID};
		try {
			LoginController.cc.getClient().sendToServer(msg);
			other.setDisable(true);
			ctrl.approve.setDisable(true);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		
	}
	
	
	
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Object[] msg= {"evaluators",getClass().getName()};
		try {
			LoginController.cc.getClient().sendToServer(msg);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	
	
	public void fillCombo(ArrayList<String> names) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				list = FXCollections.observableArrayList(names);
				combo.setItems(list);
				System.out.println(NotificationsController.getId());
				Object[] msg = { "evaluatorapproves", NotificationsController.getId(),"evaluation" };
				try {
					LoginController.cc.getClient().sendToServer(msg);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}
			
	public void checkEvaluator(RequestPhase request) {
		if(request.getEmployee()!=null) {
			approve.setDisable(true);
			other.setDisable(true);
			Label.setVisible(false);
			Label.setText("* You already set evaluator");
			Label.setVisible(true);
		}
	}
	
	

}

