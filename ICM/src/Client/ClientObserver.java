package Client;

import java.util.Observable;
import java.util.Observer;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
/**
 * in this observer we show the user that the connection with the server failed or disconnected
 *
 */
public class ClientObserver implements Observer {
	public ClientObserver(Observable client) {
		client.addObserver(this);
	}

	@Override
	public void update(Observable o, Object arg) {
		if (arg instanceof String) {
			String s = (String) arg;
			System.out.println(s);
			if (s.equals("#OC:Connection error.")) {
				System.out.println("Client Stopped!!");
				/// CONNECTION ERROR alert
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						Alert alert = new Alert(Alert.AlertType.WARNING);
						alert.setTitle("Connection Error");
						alert.setHeaderText("Connection Error");
						Text headerText = new Text("Connection Error");
						headerText.setFont(Font.font("System", FontWeight.BOLD, FontPosture.REGULAR, 20));
						VBox dialogPaneContent = new VBox();
						Label label1 = new Label("There was a problem connecting to the server.");
						Label label2 = new Label("The server may not exist or it is unavailable at this time.");
						dialogPaneContent.getChildren().addAll(label1, label2);
						// onClicking OK button the system will exit!
						// END
						alert.setOnHiding(click -> {
							System.out.println("ICM-Client-Exit");
							System.exit(0);
						});
						// Set content for Dialog Pane
						alert.getDialogPane().setContent(dialogPaneContent);
						alert.showAndWait();
					}
				});
			}
		}

	}

}
