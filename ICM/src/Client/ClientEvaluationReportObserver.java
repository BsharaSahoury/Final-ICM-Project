package Client;

import java.util.Observable;
import java.util.Observer;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import ocsf.client.ObservableClient;
/**
 * in this observer we show the evaluator that his report save successfully in DB
 *
 */
public class ClientEvaluationReportObserver implements Observer {
	public ClientEvaluationReportObserver(ObservableClient client) {
		client.addObserver(this);
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		if (arg1 instanceof Object[]) {
			Object[] arg2 = (Object[]) arg1;
			if (arg2[0] instanceof String) {
				String keymessage = (String) arg2[0];
				if (keymessage.equals("send the report")) {
					Platform.runLater(new Runnable() {

						@Override
						public void run() {
							Alert alert = new Alert(AlertType.INFORMATION);
							alert.setTitle("SAVE");
							alert.setHeaderText("Success");
							alert.setContentText("saved successfully");
							alert.showAndWait();
						}

					});

				}
			}

		}
	}
}
