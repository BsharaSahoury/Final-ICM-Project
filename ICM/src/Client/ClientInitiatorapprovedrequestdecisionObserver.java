package Client;

import java.util.Observable;
import java.util.Observer;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
/**
 * in this observer we show message to the initiator that his approved for the decision saved sucessfully
 *
 */
public class ClientInitiatorapprovedrequestdecisionObserver implements Observer {
	public ClientInitiatorapprovedrequestdecisionObserver(Observable client) {
		client.addObserver(this);
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		if (arg1 instanceof String) {
			String arg = (String) arg1;
			if (arg.equals("initiator approved the decision of the request")) {
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						Alert alertSuccess = new Alert(AlertType.INFORMATION);
						alertSuccess.setTitle("Success");
						alertSuccess.setHeaderText("Success");
						alertSuccess.setContentText("Your approve saved Succesfully");
						alertSuccess.showAndWait();
					}
				});
			} else if (arg.equals("already approved and finished")) {
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						Alert alertSuccess = new Alert(AlertType.WARNING);
						alertSuccess.setTitle("Already");
						alertSuccess.setHeaderText("Warning");
						alertSuccess.setContentText("You already approved the decision");
						alertSuccess.showAndWait();
					}
				});
			}
		}
	}

}
