package Client;

import java.util.Observable;
import java.util.Observer;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
/**
 * in this observer we show the administrator that his permission decision save in DB successfully
 *
 */
public class ClientChangePermissionsObserver implements Observer {
	public ClientChangePermissionsObserver(Observable client) {
		client.addObserver(this);
	}

	@Override
	public void update(Observable o, Object arg) {
		if (arg instanceof Object[]) {
			Object[] arg1 = (Object[]) arg;
			if (arg1[0] instanceof String) {
				String keymessage = (String) arg1[0];
				if (keymessage.equals("permissions tab1")) {
					Platform.runLater(new Runnable() {

						@Override
						public void run() {
							Alert alert = new Alert(AlertType.INFORMATION);
							alert.setTitle("TEST");
							alert.setHeaderText("success");
							alert.setContentText("The permissions set successfully!");
							alert.showAndWait();
						}

					});
				}
			}
		}

	}

}
