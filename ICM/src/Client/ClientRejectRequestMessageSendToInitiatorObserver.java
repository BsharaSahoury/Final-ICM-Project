package Client;

import java.util.Observable;
import java.util.Observer;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class ClientRejectRequestMessageSendToInitiatorObserver implements Observer {
	public ClientRejectRequestMessageSendToInitiatorObserver(Observable client) {
		client.addObserver(this);
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		if (arg1 instanceof String) {
			String arg = (String) arg1;
			if (arg.equals("Send to initiator that committee rejected the request")) {
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						Alert alertSuccess = new Alert(AlertType.INFORMATION);
						alertSuccess.setTitle("Success");
						alertSuccess.setHeaderText("Success");
						alertSuccess.setContentText(
								"Reject request message sent to the initiator by SMS email and notification");
						alertSuccess.showAndWait();
					}
				});
			}
		}
	}
}
