package Client;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import Boundary.ReportController;

public class ClientPeriodricReportObserver implements Observer {
	public ClientPeriodricReportObserver(Observable client) {
		client.addObserver(this);
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		if (arg1 instanceof Object[]) {
			Object[] arg2 = (Object[]) arg1;
			if (arg2[0] instanceof String) {
				String keymessage = (String) arg2[0];
				if (keymessage.equals("Pdays") || keymessage.equals("Pmonths") || keymessage.equals("Pyears")) {
					Date from = (Date) arg2[1];
					Date to = (Date) arg2[2];
					ArrayList<Long> arr = (ArrayList<Long>) arg2[3];
					String Rtype = (String) arg2[4];
					ReportController.ctrl.buildGraph(arr, from, to, keymessage, Rtype);
				}
			}
		}

	}

}
