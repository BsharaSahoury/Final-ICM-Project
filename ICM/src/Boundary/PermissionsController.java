package Boundary;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Entity.Employee;
import Entity.Notification;
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
import javafx.scene.control.RadioButton;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class PermissionsController implements Initializable {
	@FXML
	TableView<Employee> table1;
	@FXML
	TableView<Employee> table2;
	@FXML
	TableColumn<Employee,Integer> idCol1;
	@FXML
	TableColumn<Employee,Integer> idCol2;
	@FXML
	TableColumn<Employee,String> fullNameCol1;
	@FXML
	TableColumn<Employee,String> fullNameCol2;
	@FXML
	TableColumn<Employee,String> jobCol1;
	@FXML
	TableColumn<Employee,String> jobCol2;
	@FXML
	RadioButton rbInspector;
	@FXML
	RadioButton rbCom2;
	@FXML
	RadioButton rbCom3;
	@FXML
	RadioButton rbChair;
	@FXML
	Label lbInspector;
	@FXML
	TextField tfInspector;
	@FXML
	TextField tfCom2;
	@FXML
	TextField tfCom3;
	@FXML
	TextField tfChair;
	@FXML
	Button save1;
	@FXML
	Button save2;
	@FXML
	ComboBox<String> privCombo;
	@FXML
	TextField tfoSystem;
	@FXML
	TextField tfnSystem;
	@FXML
	Button get;
	@FXML
	public static PermissionsController ctrl;
	@FXML
	public static SplitPane splitpane;
	public static Stage primaryStage;
	private AnchorPane lowerAnchorPane;
	private ObservableList<Employee> Blist;
	private Employee selectedE;
	private Employee evaluator2Set;
	private String selectedJ;
	private Employee newEmploye;
	private String employe2replace;
	private String sys;
	ObservableList<String> list=FXCollections.observableArrayList("Moodle","Student information system","Lecturer information system","Employee information system","Library system","Computers in the classroom","Labs and computer farms","College official site");
	public void start(SplitPane splitpane) {
		primaryStage = LoginController.primaryStage;
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/Boundary/permissions.fxml"));
			lowerAnchorPane = loader.load();
			ctrl = loader.getController();
			splitpane.getItems().set(1, lowerAnchorPane);
			this.splitpane = splitpane;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		privCombo.setItems(list);
		Object[] msg= {"employees&permissions"};
		try {
			LoginController.cc.getClient().sendToServer(msg);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}


	public void recieveData(ArrayList<Employee> engineers) {
		Blist=FXCollections.observableArrayList(engineers);
		idCol1.setCellValueFactory(new PropertyValueFactory<Employee, Integer>("id"));
		idCol2.setCellValueFactory(new PropertyValueFactory<Employee, Integer>("id"));
		fullNameCol1.setCellValueFactory(new PropertyValueFactory<Employee, String>("fullname"));
		fullNameCol2.setCellValueFactory(new PropertyValueFactory<Employee, String>("fullname"));
		jobCol1.setCellValueFactory(new PropertyValueFactory<Employee, String>("job"));
		jobCol2.setCellValueFactory(new PropertyValueFactory<Employee, String>("job"));
		table1.setItems(Blist);
		table2.setItems(Blist);
		Employee inspector,chairman,com2 = null,com3;
		for(Employee employe : engineers) {
			if(employe.getJob().equals("inspector")) {
				inspector=employe;
				tfInspector.setText(inspector.getFullname());
			}
			if(employe.getJob().equals("chairman")) {
				chairman=employe;
				tfChair.setText(chairman.getFullname());
			}
			if(employe.getJob().equals("comittee member") && com2==null) {
				com2=employe;
				tfCom2.setText(com2.getFullname());
			}
			if(employe.getJob().equals("comittee member") && !employe.equals(com2)) {
				com3=employe;
				tfCom3.setText(com3.getFullname());
			}
			
		}
		
	}
	public void set(ActionEvent e) {
		selectedE=table1.getSelectionModel().getSelectedItem();
		if(selectedE==null) {
			Alert alert = new Alert(AlertType.ERROR);
	        alert.setTitle("TEST");
	        alert.setHeaderText("fail");
	        alert.setContentText("You must select an employee first!");
	        alert.showAndWait();
	        return;
		}
		if(rbInspector.isSelected()) {
			employe2replace=tfInspector.getText();
			selectedJ="inspector";
			tfInspector.setText(selectedE.getFullname());
		}
		if(rbChair.isSelected()) {
			employe2replace=tfChair.getText();
			selectedJ="chairman";
			tfChair.setText(selectedE.getFullname());
		}
		if(rbCom2.isSelected()) {
			selectedJ="comittee member";
			employe2replace=tfCom2.getText();
			tfCom2.setText(selectedE.getFullname());
		}
		if(rbCom3.isSelected()) {
			selectedJ="comittee member";
			employe2replace=tfCom3.getText();
			tfCom3.setText(selectedE.getFullname());
		}
	}
	public void save1Action(ActionEvent e) {
		if(selectedE==null && selectedJ==null) {
			Alert alert = new Alert(AlertType.ERROR);
	        alert.setTitle("TEST");
	        alert.setHeaderText("fail");
	        alert.setContentText("You must select an employee and specific job first!");
	        alert.showAndWait();
	        return;
		}
		Employee e1 = null;
		for(Employee em : Blist) {
			if(employe2replace.equals(em.getFullname())) {
				e1=em;
			}
		}
		Object[] msg= {"permissions tab1",e1,selectedE,selectedJ};
		try {
			LoginController.cc.getClient().sendToServer(msg);
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
	}
	public void getEvaluatorAction(ActionEvent e) {
		String sys=privCombo.getSelectionModel().getSelectedItem();
		if(sys==null) {
			Alert alert = new Alert(AlertType.ERROR);
	        alert.setTitle("TEST");
	        alert.setHeaderText("fail");
	        alert.setContentText("You must select a system first!");
	        alert.showAndWait();
	        return;
		}
		for(Employee e1 : Blist) {
			if(e1.getSupportSystem() != null) {
				if(e1.getSupportSystem().equals(sys)) {
					tfoSystem.setText(e1.getFullname());
				}
			
			}
		}
	}
	public void setNewEvaluatorAction(ActionEvent e) {
		newEmploye=table2.getSelectionModel().getSelectedItem();
		sys=privCombo.getSelectionModel().getSelectedItem();
		if(newEmploye==null || sys==null) {
			Alert alert = new Alert(AlertType.ERROR);
	        alert.setTitle("TEST");
	        alert.setHeaderText("fail");
	        alert.setContentText("You must select an employee and specific system first!");
	        alert.showAndWait();
	        return;
		}
		tfnSystem.setText(newEmploye.getFullname());
	}
	public void save2Action(ActionEvent e) {
		if(newEmploye==null || sys==null) {
			Alert alert = new Alert(AlertType.ERROR);
	        alert.setTitle("TEST");
	        alert.setHeaderText("fail");
	        alert.setContentText("You must select an employee and specific system first!");
	        alert.showAndWait();
	        return;
		}
		Employee oldEmploye = null;
		for(Employee e1 : Blist) {
			if(e1.getSupportSystem() != null) {
				if(e1.getSupportSystem().equals(sys)) {
					oldEmploye=e1;
				}
			
			}
		}
		if(oldEmploye==null) {
			newEmploye.setSupportSystem(sys);
		}
		Object[] msg= {"change ev",oldEmploye,newEmploye};
		try {
			LoginController.cc.getClient().sendToServer(msg);
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
	}
	
	

}
