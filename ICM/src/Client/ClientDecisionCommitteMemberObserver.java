package Client;

import java.util.Observable;
import java.util.Observer;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class ClientDecisionCommitteMemberObserver implements Observer {
	public ClientDecisionCommitteMemberObserver(Observable client) {
		client.addObserver(this);
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		if (arg1 instanceof String) {
			String arg = (String) arg1;
			if (arg.equals("Committee Member Decision")) {
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						Alert alertSuccess = new Alert(AlertType.INFORMATION);
						alertSuccess.setTitle("Success");
						alertSuccess.setHeaderText("Success");
						alertSuccess.setContentText("Decision sent to chairman to approve the decision");
						alertSuccess.showAndWait();
					}
				});
			}
		}
	}
}
