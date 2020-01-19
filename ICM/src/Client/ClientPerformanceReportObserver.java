package Client;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import Boundary.ReportController;
import Entity.ExtensionDuration;

public class ClientPerformanceReportObserver implements Observer {
	public ClientPerformanceReportObserver(Observable client) {
		client.addObserver(this);
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		if (arg1 instanceof Object[]) {
			Object[] arg2 = (Object[]) arg1;
			if (arg2[0] instanceof String) {
				String keymessage = (String) arg2[0];
				if (keymessage.equals("Extension durations") || keymessage.equals("repetion durations")) {
					ArrayList<ExtensionDuration> arr = (ArrayList<ExtensionDuration>) arg2[1];
					ReportController.ctrl.getPerformanceData(arr, keymessage);
				}
			}
		}

	}
}
