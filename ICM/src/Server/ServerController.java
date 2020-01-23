package Server;

import java.awt.event.WindowEvent;
import java.io.IOException;
import java.lang.Thread.UncaughtExceptionHandler;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ResourceBundle;

import DBconnection.mysqlConnection;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.event.ActionEvent;

import java.sql.Connection;
/**
 * this is the controller of the main server window.
 *
 */
public class ServerController implements Initializable {
/**
 * here we annoys the parameters that we user in the server window.
 */
	private static String db_Password;
	private static String db_Username;
	private static String IP;
	@FXML
	private TextField IP_txt;
	@FXML
	private TextField Port_txt;
	@FXML
	private TextField dbHostname;
	@FXML
	private TextField dbSchema;
	@FXML
	private TextField dbUsername;
	@FXML
	private PasswordField dbPassword;
	@FXML
	private Button connectBtn;
	@FXML
	Label wrong;
/**
 * In this function we call the editTextFields method.
 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		editTextFeilds();

	}
/**
 * In this function we set the text fields in the server window with the relevant details.
 */
	public void editTextFeilds() {
		try {
			IP_txt.setText(InetAddress.getLocalHost().getHostAddress());// set the label of the IP in the window for the
																		// IP of the computer that the server runs on//
			IP = IP_txt.getText();
			IP_txt.setEditable(false);
			Port_txt.setText("5555");
			Port_txt.setEditable(false);
			dbHostname.setText("localhost:3306");
			dbHostname.setEditable(false);
			dbSchema.setText("icm");
			dbSchema.setEditable(false);
			dbUsername.setText("root");
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
/**
 * 
 * @return the ip of the server
 */
	public static String getIp() {
		return IP;
	}
/**
 * 
 * @return return db_password
 */
	public static String get_DB_Password() {
		return db_Password;
	}
/**
 * 
 * @return return DB username
 */
	public static String get_DB_UserName() {
		return db_Username;
	}
/**
 * in this function we try to change the server to listening with the
 * details that we have in the parameters at the server window
 * and if we had a wrong parameter we display alert that contain the wrong field 
 * of the server window details.
 * @param event
 */
	@FXML
	private void connecting(ActionEvent event) {
		db_Password = dbPassword.getText();
		db_Username = dbUsername.getText();
		if ((!(dbPassword.getText().equals(""))) && (!(dbUsername.getText().equals("")))) {
			Connection con1 = mysqlConnection.makeAndReturnConnection();
			if (con1 == null) {
				dbUsername.clear();
				dbPassword.clear();
				System.out.println("mySqlPassword OR mySqlUsername is wrong");
				/// CONNECTION ERROR alert
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setTitle("Connection Error To DB");
				alert.setHeaderText("Connection Error To DB");
				Text headerText = new Text("Connection Error To DB");
				headerText.setFont(Font.font("System", FontWeight.BOLD, FontPosture.REGULAR, 20));
				VBox dialogPaneContent = new VBox();
				Label label1 = new Label("There was a problem connecting to the DB.");
				Label label2 = new Label("Your MySQLPassword OR MySQLUsername is wrong!!");
				Label label3 = new Label("Please Try again!");

				dialogPaneContent.getChildren().addAll(label1, label2, label3);
				// onClicking OK button the system will exit!
				// END
				alert.setOnHiding(click -> {
					System.out.println("Try again!");
				});
				// Set content for Dialog Pane
				alert.getDialogPane().setContent(dialogPaneContent);
				alert.showAndWait();

			} else// conEstablished
			{
				MainForServer.con = con1;
				MainForServer.ConnectAfterDBPassword();
				wrong.setText("Connection established");
				wrong.setVisible(true);
				dbUsername.setEditable(false);
				dbPassword.setEditable(false);
				connectBtn.setDisable(true);
				DBconnection.mysqlConnection.SetAllUsersLoginToNo(con1);
			}
		} else {
			dbUsername.clear();
			dbPassword.clear();

			System.out.println("mySqlPassword OR mySqlUsername is wrong");
			/// CONNECTION ERROR alert
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Connection Error To DB");
			alert.setHeaderText("Connection Error To DB");
			Text headerText = new Text("Connection Error To DB");
			headerText.setFont(Font.font("System", FontWeight.BOLD, FontPosture.REGULAR, 20));
			VBox dialogPaneContent = new VBox();
			Label label1 = new Label("There was a problem connecting to the DB.");
			Label label2 = new Label("Your MySQLPassword OR MySQLUsername is wrong!!");
			Label label3 = new Label("Please Try again!");

			dialogPaneContent.getChildren().addAll(label1, label2, label3);
			// onClicking OK button the system will exit!
			// END
			alert.setOnHiding(click -> {
				System.out.println("Try again!");
			});
			// Set content for Dialog Pane
			alert.getDialogPane().setContent(dialogPaneContent);
			alert.showAndWait();

		}

	}

	public static class NewHandler implements Thread.UncaughtExceptionHandler {
		public NewHandler() {
		}

		@Override
		public void uncaughtException(Thread t, Throwable e) {
			// do something here - log to file and upload to server/close resources/delete
			// files...
		}// uncaughtException
	}// NewHandler
	/**
	 * here we shut down the server
	 *
	 */
	@SuppressWarnings("deprecation")
	public static void shutdown() throws IOException {
		// cleanup code here...
		String msg = "#OS:Server stopped.";
		MainForServer.get_ObservableServer().sendToAllClients(msg);
		try {
			System.out.println("StopAll");

			// AllUsers-Login=No => logging-out
			Connection con = DBconnection.mysqlConnection.makeAndReturnConnection();
			DBconnection.mysqlConnection.SetAllUsersLoginToNo(con);
			if (MainForServer.get_ObservableServer().isListening()) {
				MainForServer.get_ObservableServer().close();
			} // if
		} // try
		catch (ClassCastException e1) {
		}
	}

}
