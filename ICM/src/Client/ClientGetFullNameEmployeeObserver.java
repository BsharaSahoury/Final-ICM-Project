package Client;

import java.util.Observable;
import java.util.Observer;

import Boundary.RequestTreatmentAction;
import javafx.application.Platform;
import messages.AutomaticRecruitMessageController;
import messages.ChooseTesterMessageController;
import messages.CommitteeDecisionApproveController;
import messages.CommitteeDecisionAskForaddInfoController;
import messages.FailedTestMessageController;
/**
 * in this observer we put the full name of the relevant employee in the request treatment window
 * in to to able to the inspector to change the phase administrator
 *
 */
public class ClientGetFullNameEmployeeObserver implements Observer {
	public ClientGetFullNameEmployeeObserver(Observable server) {
		server.addObserver(this);
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		if (arg instanceof Object[]) {
			Object[] arg1 = (Object[]) arg;
			if (arg1[0] instanceof String) {
				String keymessage = (String) arg1[0];
				if (keymessage.equals("getFullNameOfEmployee")) {
					String classname = (String) arg1[1];
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							switch (classname) {
							case "Boundary.RequestTreatmentAction":
								String fullname = (String) arg1[2];
								RequestTreatmentAction.ctrl.setcombotext(fullname);
								break;
							}
						}
					});
				}
			}
		}
	}
}