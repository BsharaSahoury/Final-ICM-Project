package Boundary;

import java.net.SocketException;
import java.net.URL;

import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

import Client.ClientConsole;
import Client.MainForClient;
import Entity.Employee;
import Entity.Inspector;
import Entity.Student;
import Entity.User;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import ocsf.client.ObservableClient;

/**
 * LoginController :A controller for logging in 
 * User-Log-In
 * @author Ayman Odeh
 *
 */
public class LoginController {
	@FXML
	public Label error;
	@FXML
	private Button Loginbtn;
	@FXML
	private TextField Username;
	@FXML
	private PasswordField Password;
	@FXML
	private CheckBox Remember;
	@FXML
	private Button ForgetPass;
	public static boolean flag = false;

	public static ClientConsole cc;
	public static Stage primaryStage;
/**
 * 
 * @param primaryStage
 * @param cc
 */
	public void start(Stage primaryStage, ClientConsole cc) {
		this.cc = cc;
		this.primaryStage = primaryStage;
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/Boundary/Login.fxml"));
			Scene scene = new Scene(root);
			this.primaryStage.setScene(scene);
			this.primaryStage.setResizable(false);
			this.primaryStage.setTitle("ICM-Login");
			this.primaryStage.show();
			this.primaryStage.setOnCloseRequest(event -> {
				System.out.println("EXIT ICM");
				System.exit(0);
			});

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * method to sending to server username,password to check legally
	 * @param event if the user clicked on logging button
	 * @throws Exception
	 */
	public void LoginAction(ActionEvent event) throws Exception {
		error.setVisible(false);
		String username = Username.getText();
		String password = Password.getText();
		String[] loginMessage = new String[3];
		loginMessage[0] = "login";
		loginMessage[1] = username;
		loginMessage[2] = password;
		cc.getClient().sendToServer(loginMessage);
		Username.clear();
		Password.clear();
	}

	/**
	 * 
	 * @param event if the user clicked on Forget Password button
	 * @throws Exception
	 */
	public void ForgetPassAction(ActionEvent event) throws Exception {
		// open new window to get the password by enter the email address and then send
		// link to reset the password
	}
	/**
	 * getPrimaryStage: method to return the primaryStage of of current Controller
	 * @return primaryStage
	 */
	public Stage getStage() {
		return primaryStage;
	}

}
