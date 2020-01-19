package Client;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import Boundary.LoginController;
import Boundary.RequestSubmissionController;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
/**
 * in this observer we show the initiator that his submission saved successfully in the DB
 *
 */
public class ClientSubmissionObserver implements Observer {
	public ClientSubmissionObserver(Observable client) {
		client.addObserver(this);
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		if (arg1 instanceof Object[]) {
			Object[] arg2 = (Object[]) arg1;
			if (arg2[0] instanceof String) {
				String keymessage = (String) arg2[0];
				if (keymessage.equals("sumbissionSucceeded")) {
					Platform.runLater(new Runnable() {

						@Override
						public void run() {
							Alert alert = new Alert(AlertType.INFORMATION);
							alert.setTitle("TEST");
							alert.setHeaderText("Success");
							alert.setContentText("your request has been submitted successfully");
							alert.showAndWait();
						}

					});

				}
			}

		}
	}
}
