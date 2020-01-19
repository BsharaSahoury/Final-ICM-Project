package Client;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import Boundary.AdministratorHomeController;
import Boundary.ChairmanHomeController;
import Boundary.ComitteeMemberHomeController;
import Boundary.EvaluatorHomeController;
import Boundary.InspectorHomeController;
import Boundary.LecturerHomeController;
import Boundary.PerformanceLeaderHomeController;
import Boundary.StudentHomeController;
import Boundary.TesterHomeController;
import Entity.Request;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
/**
 * here in this observer we show message to the inspector that he approved 
 * the decision of the request and and message send to the initiator waiting for his approve
 * to close the request
 *
 */
public class ClientMessageSentToInitiatorObserver implements Observer {
	public ClientMessageSentToInitiatorObserver(Observable client) {
		client.addObserver(this);
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		if (arg1 instanceof String) {
			String arg = (String) arg1;
			if (arg.equals("Message send to initiator to approve the decision")) {
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						Alert alertSuccess = new Alert(AlertType.INFORMATION);
						alertSuccess.setTitle("Success");
						alertSuccess.setHeaderText("Success");
						alertSuccess.setContentText(
								"Your approve saved and notification and email and SMS\n sent to initiator to approve the decision");
						alertSuccess.showAndWait();
					}
				});
			}
		}
	}
}
