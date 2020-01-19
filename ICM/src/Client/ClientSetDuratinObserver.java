package Client;

import java.time.LocalDate;
import java.util.Observable;
import java.util.Observer;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import ocsf.client.ObservableClient;
/**
 * in this observer we show the phase administrator that message send to the inspector
 * in order to approve the duration that the phase administrator set
 * and if the request frozen we show him message that the request is frozen
 *
 */
public class ClientSetDuratinObserver implements Observer {
	public ClientSetDuratinObserver(ObservableClient client) {
		client.addObserver(this);
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		if (arg1 instanceof Object[]) {
			Object[] arg2 = (Object[]) arg1;
			if (arg2[0] instanceof String) {
				String keymessage = (String) arg2[0];
				if (keymessage.equals("duration")) {
					if (arg2[1] instanceof LocalDate[]) {
						Platform.runLater(new Runnable() {

							@Override
							public void run() {
								Alert alert = new Alert(AlertType.INFORMATION);
								alert.setTitle("SAVE");
								alert.setHeaderText("Success");
								alert.setContentText("We sent the duratin to inspector,plese wait for aprove");
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
