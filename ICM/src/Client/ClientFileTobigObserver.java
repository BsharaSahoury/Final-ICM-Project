package Client;

import java.util.Observable;
import java.util.Observer;

import Boundary.RequestSubmissionController;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import ocsf.client.ObservableClient;

public class ClientFileTobigObserver implements Observer {
	public ClientFileTobigObserver(ObservableClient client) {
		client.addObserver(this);
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		if(arg1 instanceof String) {
			String keymessage=(String)arg1;
		if(keymessage.equals("File is to large")) {
			Platform.runLater(new Runnable() {

				@Override
				public void run() {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error");
					alert.setHeaderText("File is too large");
					alert.setContentText("Change the file it's to large");
					alert.showAndWait();
					RequestSubmissionController.ctrlSubmission.disablethebtn();
				}
			});
		}
		}
	}
}
