package Server;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import DBconnection.mysqlConnection;
import ocsf.server.ConnectionToClient;
/**
 * In this observer we handle the situation of ask for details to the perioduc report
 *
 */
public class ServerPeriodricReportObserver implements Observer {
	public ServerPeriodricReportObserver(Observable server) {
		server.addObserver(this);
	}
/**
 * in this function we return all of the relevant details  the we need to create the periodic report
*/
	@Override
	public void update(Observable arg0, Object arg1) {
		if (arg1 instanceof Object[]) {
			Object[] arg2 = (Object[]) arg1;
			ConnectionToClient client = (ConnectionToClient) arg2[0];
			if (arg2[1] instanceof Object[]) {
				Object[] arg3 = (Object[]) arg2[1];
				if (arg3[0] instanceof String) {
					String keymessage = (String) arg3[0];
					if (keymessage.equals("Pdays") || keymessage.equals("Pmonths") || keymessage.equals("Pyears")) {
						Connection con = MainForServer.con;
						Date from = Date.valueOf((LocalDate) arg3[1]);
						Date to = Date.valueOf((LocalDate) arg3[2]);
						String Rtype = (String) arg3[3];
						if (Rtype.equals("No. days of Treatments"))
							return;
						ArrayList<Long> arr = mysqlConnection.getPeriodricReportData(con, keymessage, from, to, Rtype);
						Object[] msg = { keymessage, from, to, arr, Rtype };
						try {
							client.sendToClient(msg);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				}
			}
		}

	}

}
