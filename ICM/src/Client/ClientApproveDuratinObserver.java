package Client;

import java.time.LocalDate;
import java.util.Observable;
import java.util.Observer;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import ocsf.client.ObservableClient;
/**
 * in this observer we we notifies the user that his approved saved succesfully 
 *
 */
public class ClientApproveDuratinObserver implements Observer {
	public ClientApproveDuratinObserver(ObservableClient client) {
		client.addObserver(this);
	}
/**
 * in this function we show message to the user that his approve of the duration saved successfully in DB
 */
	@Override
	public void update(Observable arg0, Object arg1) {
		if (arg1 instanceof Object[]) {
			Object[] arg2 = (Object[]) arg1;
			if (arg2[0] instanceof String) {
				String keymessage = (String) arg2[0];
				if (keymessage.equals("approvedDuration")) {
					if (arg2[1] instanceof LocalDate[]) {
						Platform.runLater(new Runnable() {

							@Override
							public void run() {
								Alert alert = new Alert(AlertType.INFORMATION);
								alert.setTitle("SAVE");
								alert.setHeaderText("Success");
								alert.setContentText("successful saved");
								alert.showAndWait();
							}

						});

					} else {
						Platform.runLater(new Runnable() {

							@Override
							public void run() {
								Alert alert = new Alert(AlertType.INFORMATION);
								alert.setTitle("SAVE");
								alert.setHeaderText("failed");
								alert.setContentText("this request is frozen!");
								alert.showAndWait();
							}

						});

					}
				}
			}
		}
	}
}
