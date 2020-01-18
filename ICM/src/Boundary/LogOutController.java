package Boundary;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Client.ClientConsole;
import Entity.User;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class LogOutController implements Initializable {
	private Stage primaryStage;
	private static ClientConsole cc;

	public void start(Stage primaryStage, User user) {
		this.cc = LoginController.cc;
		String[] LogOutArgs = new String[4];
		try {
			this.primaryStage = primaryStage;
			Parent root = FXMLLoader.load(getClass().getResource("/Boundary/Login.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.setTitle("ICM-Login");
			primaryStage.show();
			primaryStage.setOnCloseRequest(event -> {
				System.out.println("EXIT ICM");
				LogOutController logOut = new LogOutController();
				logOut.exit(primaryStage, user);
				// System.exit(0);
			});
			String keymessage = "LogOut";
			String[] message = { keymessage, user.getUsername(), user.getPassword(), "NOT-End" };
			cc.getClient().sendToServer(message);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void exit(Stage primaryStage, User user) {
		this.cc = LoginController.cc;
		String[] LogOutArgs = new String[4];
		try {
			String keymessage = "LogOut";
			String[] message = { keymessage, user.getUsername(), user.getPassword(), "End" };
			cc.getClient().sendToServer(message);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
	}
}
