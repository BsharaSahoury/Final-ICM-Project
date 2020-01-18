package Boundary;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Entity.MyFile;
import Entity.Request;
import Entity.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class RequestSubmissionController implements Initializable {
	@FXML
	Label fileName;
	@FXML
	Button browse;
	@FXML
	Button submit;
	@FXML
	TextArea existingSituation;
	@FXML
	TextArea requestedChange;
	@FXML
	TextArea requestReason;
	@FXML
	TextArea comment;
	@FXML
	ComboBox<String> chosenCombo;
	@FXML
	CheckBox agree;
	File fileToChoose;
	
	private static User user;
	ObservableList<String> list=FXCollections.observableArrayList("Moodle","Student information system","Lecturer information system","Employee information system","Library system","Computers in the classroom","Labs and computer farms","College official site");
	public static Stage primaryStage;
	private AnchorPane lowerAnchorPane;
	public void start(SplitPane splitpane,User user) {
		this.user=user;
		primaryStage=LoginController.primaryStage;
		try{	
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/Boundary/Request Submission.fxml"));
			lowerAnchorPane = loader.load();
			splitpane.getItems().set(1, lowerAnchorPane);		
		} catch(Exception e) {
			e.printStackTrace();
		}			
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		chosenCombo.setItems(list);
		
	}
	public void attachFile(ActionEvent e) {
		FileChooser fc=new FileChooser();
		fc.getExtensionFilters().addAll(new ExtensionFilter("PDF Files","*.pdf"));
		File selectedFile=fc.showOpenDialog(null);
		if(selectedFile != null) {
			fileToChoose=selectedFile;
			fileName.setText(fileToChoose.getName());
		}
		
	}
	public void submitRequest(ActionEvent e) {
		boolean flag1=chosenCombo.getSelectionModel().getSelectedItem()==null;
		boolean flag2=existingSituation.getText().equals("");
		boolean flag3=requestedChange.getText().equals("");
		boolean flag4=requestReason.getText().equals("");
		boolean flag5=!agree.isSelected();
		if(flag1 || flag2 || flag3 || flag4 || flag5) {
			Alert alert = new Alert(AlertType.INFORMATION);
	        alert.setTitle("TEST");
	        alert.setHeaderText("ERROR");
	        alert.setContentText("please fill all the fields need the red star");
	        alert.showAndWait();
	        return;
		}
		MyFile msg=null;
		String filename=null;
		if(fileToChoose != null) {
			msg=new MyFile();
			try {
				File newFile=fileToChoose;
				filename=newFile.getName();
				byte[] mybytearray=new byte[(int)newFile.length()];
				FileInputStream fis=new FileInputStream(newFile);
				BufferedInputStream bis=new BufferedInputStream(fis);
				msg.initArray(mybytearray.length);
				bis.read(msg.getMybyterray(),0,mybytearray.length);
			}catch(Exception e1) {
				System.out.println("we're fucked!");
			}
		}
		    long millis=System.currentTimeMillis();
			Request request=new Request(chosenCombo.getSelectionModel().getSelectedItem(),existingSituation.getText(),requestedChange.getText(),requestReason.getText(),comment.getText(),new java.sql.Date(millis),user,msg,filename);
			Object[] message= {"submitRequest",request};
			try {
				LoginController.cc.getClient().sendToServer(message);
				submit.setDisable(true);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		
	}

}
