package Client;

import java.util.Observable;
import java.util.Observer;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import ocsf.client.ObservableClient;

public class ClientExtendRequestTimeObserver implements Observer {
	public ClientExtendRequestTimeObserver(ObservableClient client) {
		client.addObserver(this);
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		if (arg1 instanceof Object[]) {
			Object[] arg2 = (Object[]) arg1;
			if (arg2[0] instanceof String) {
				String keymessage = (String) arg2[0];
				if (keymessage.equals("its ok")) {
					Platform.runLater(new Runnable() {

						@Override
						public void run() {
							Alert alert = new Alert(AlertType.INFORMATION);
							alert.setTitle("SAVE");
							alert.setHeaderText("Success");
							alert.setContentText("Request Extension has been sent to Inspector");
							alert.showAndWait();
						}

					});

				} else if (keymessage.equals("Extension possible once per stage !")) {
					Platform.runLater(new Runnable() {

						@Override
						public void run() {
							Alert alert = new Alert(AlertType.WARNING);
							alert.setTitle("ERROR");
							alert.setHeaderText("failed");
							alert.setContentText("Extension possible once per stage !");
							alert.showAndWait();
						}

					});

				}
			}

		}
	}
}
