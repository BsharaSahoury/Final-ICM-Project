package Client;

import java.awt.Button;
import java.awt.TextField;
import java.lang.reflect.InvocationTargetException;
import java.net.SocketException;

import Boundary.MainClientController;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainForClient extends Application {
	public static Stage stage;
	public static String[] args;

	public static void main(String[] args) {
		MainForClient.args = args;
		System.out.println(args.toString());
		MainForClient.launch(args);
		// TODO Auto-generated method stub

	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		try {
			// TODO Auto-generated method stub
			Parent root = FXMLLoader.load(getClass().getResource("/Boundary/MainClient.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.setTitle("ICM-Client");
			primaryStage.show();
			stage = primaryStage;
		} catch (SocketException e1) {
			System.out.println("Client Stopped!!");
		} catch (RuntimeException e2) {
			System.out.println("Client Stopped!!");
		}
	}

}
