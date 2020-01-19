package Client;

import java.util.Observer;

import Boundary.RequestInfoController;
import Entity.Request;
import Entity.RequestPhase;
import messages.approveDuratinController;
import ocsf.client.ObservableClient;
/**
 * in this observer we show the user that he already approved the duration.
 *
 */
public class ClientCheckApproveDuratinObserver implements Observer {

	public ClientCheckApproveDuratinObserver(ObservableClient client) {
		client.addObserver(this);
	}

	@Override
	public void update(java.util.Observable arg0, Object arg1) {
		if (arg1 instanceof Object[]) {
			Object[] arg2 = (Object[]) arg1;
			if (arg2[0] instanceof String) {
				String keymessage = (String) arg2[0];
				if (keymessage.equals("requestphase to approve")) {
					if (arg2[1] instanceof RequestPhase) {
						RequestPhase request = (RequestPhase) arg2[1];
						approveDuratinController.ctrl.checkApprove(request);
					}

				}
			}
		}

	}

}
