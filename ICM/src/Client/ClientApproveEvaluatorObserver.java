package Client;

import java.util.Observer;

import Entity.RequestPhase;
import javafx.application.Platform;
import messages.AutomaticRecruitMessageController;
import messages.approveDuratinController;
import ocsf.client.ObservableClient;

public class ClientApproveEvaluatorObserver implements Observer {

	public ClientApproveEvaluatorObserver(ObservableClient client) {
		client.addObserver(this);
	}

	@Override
	public void update(java.util.Observable arg0, Object arg1) {
		if (arg1 instanceof Object[]) {
			Object[] arg2 = (Object[]) arg1;
			if (arg2[0] instanceof String) {
				String keymessage = (String) arg2[0];
				if (keymessage.equals("evaluatorapprove")) {
					if (arg2[1] instanceof RequestPhase) {
						RequestPhase request = (RequestPhase) arg2[1];
						Platform.runLater(new Runnable() {
							@Override
							public void run() {
								AutomaticRecruitMessageController.ctrl.checkEvaluator(request);
							}
						});
					}

				}
			}
		}

	}

}
