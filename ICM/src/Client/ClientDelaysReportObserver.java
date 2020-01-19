package Client;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import Boundary.ReportController;
/**
 * in this observer we build the graph of the delay report with the details that we received from the DB
 *
 */
public class ClientDelaysReportObserver implements Observer {
	public ClientDelaysReportObserver(Observable client) {
		client.addObserver(this);
	}

	@Override
	public void update(Observable o, Object arg) {
		if (arg instanceof Object[]) {
			Object[] arg1 = (Object[]) arg;
			if (arg1[0] instanceof String) {
				String keymessage = (String) arg1[0];
				if (keymessage.equals("No.Delays") || keymessage.equals("Delays Durations")) {
					ArrayList<Long> arr = (ArrayList<Long>) arg1[1];
					ReportController.ctrl.buildDelayGraph(keymessage, arr);
				}
			}
		}

	}

}
