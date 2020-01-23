package Boundary;

import javafx.stage.Stage;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import Client.ClientConsole;
import Entity.MyFile;
import Entity.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class ProfileSettingController {

	private static ClientConsole cc;

	@FXML
	private TextField id_txt;
	@FXML
	private TextField username_txt;
	@FXML
	private TextField fullname_txt;
	@FXML
	private TextField email_txt;
	@FXML
	private TextField faculty_txt;
	@FXML
	private TextField role_txt;
	@FXML
	private CheckBox allowrecSMS;
	@FXML
	private CheckBox allowrecGmail;
	@FXML
	private TextField dest_txt;
	@FXML
	private Button browsebtn;
	@FXML
	private Button updateandsavebtn;
	@FXML
	private ImageView userImage;
	public static Stage primaryStage;
	private AnchorPane lowerAnchorPane;

	@FXML
	private static SplitPane splitpane;
	private FXMLLoader loader;
	private static User user;
	private static String userjob;
	private static ArrayList<String> arrOfProfileSetting;
	File choosenFile;
	public static ProfileSettingController ProfileSetting;

	public void start(SplitPane splitpane, User user, String userJob) {
		this.splitpane = splitpane;
		this.user = user;
		this.userjob = userJob;
		primaryStage = LoginController.primaryStage;
		String[] msg = new String[3];
		this.cc = LoginController.cc;
		try {
			loader = new FXMLLoader(getClass().getResource("/Boundary/ProfileSetting.fxml"));
			lowerAnchorPane = loader.load();
			splitpane.getItems().set(1, lowerAnchorPane);
			msg[0] = "ProfileSetting";
			msg[1] = user.getUsername();
			msg[2] = userjob;
			cc.getClient().sendToServer(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void fillProfileSettingData(ArrayList<String> arr1) {
		arrOfProfileSetting = arr1;
		loader.<ProfileSettingController>getController().setProfileSetting(arr1);
	}

	public void setProfileSetting(ArrayList<String> arr) {
		if (arr != null) {
			id_txt.setText(arr.get(0));
			id_txt.setEditable(false);
			username_txt.setText(arr.get(1));
			username_txt.setEditable(false);
			fullname_txt.setText(arr.get(2));
			fullname_txt.setEditable(false);
			email_txt.setText(arr.get(3));
			email_txt.setEditable(false);
			faculty_txt.setText(arr.get(4));
			faculty_txt.setEditable(false);
			role_txt.setText(arr.get(5));
			role_txt.setEditable(false);
		}
	}
}
