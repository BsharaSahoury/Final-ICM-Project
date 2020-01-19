package Client;

import java.util.Observable;
import java.util.Observer;
import java.util.Optional;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
/**
 * in this observer we show the performance that the request moved to the next phase
 *
 */
public class ClientApprovePerformanceObserver implements Observer {
	public ClientApprovePerformanceObserver(Observable client) {
		client.addObserver(this);
	}
	/**
	 * in this function we message the performance leader that the request passed to the next phase.
	 *
	 */
	@Override
	public void update(Observable arg0, Object arg1) {
		if (arg1 instanceof Object[]) {
			Object[] arg2 = (Object[]) arg1;
			if (arg2[0] instanceof String) {
				String keymessage = (String) arg2[0];
				if (keymessage.equals("performance done")) {
					Platform.runLater(new Runnable() {

						@Override
						public void run() {
							Alert alert = new Alert(AlertType.INFORMATION);
							alert.setTitle("confirmation Alert Title");
							alert.setHeaderText("confirm");
							alert.setContentText("Request is moved to the next phase successfully");
							alert.showAndWait();

						}

					});
				}
			}
		}

	}

}
