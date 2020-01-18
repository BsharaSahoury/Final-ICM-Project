package Client;

import java.util.Observable;
import java.util.Observer;

import Boundary.RequestInfoController;
import Boundary.RequestsWorkedOnController;
import Entity.Request;
import Entity.RequestPhase;
import ocsf.client.ObservableClient;

public class ClientGetDurationObserver implements Observer {
	public ClientGetDurationObserver(ObservableClient client) {
		client.addObserver(this);
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		if (arg1 instanceof Object[]) {
			Object[] arg2 = (Object[]) arg1;
			if (arg2[0] instanceof String) {
				String keymessage = (String) arg2[0];
				if (keymessage.equals("get duration")) {
					if (arg2[1] instanceof RequestPhase) {
						RequestPhase request = (RequestPhase) arg2[1];
						RequestsWorkedOnController.ctrl1.SetDurationHelp(request);
					}

				}
			}
		}

	}

}
