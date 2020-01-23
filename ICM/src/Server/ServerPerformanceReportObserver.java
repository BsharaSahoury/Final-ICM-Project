package Server;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import DBconnection.mysqlConnection;
import Entity.ExtensionDuration;
import ocsf.server.ConnectionToClient;
/**
 * this observer handle the situation of ask for the performance report.
 */
public class ServerPerformanceReportObserver implements Observer {
	public ServerPerformanceReportObserver(Observable server) {
		server.addObserver(this);
	}
/**
 * in this function we return all of the reports of the type in keymessage from the DB
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
					Connection con = MainForServer.con;
					if (keymessage.equals("Extension durations") || keymessage.equals("repetion durations")) {
						ArrayList<ExtensionDuration> arr = mysqlConnection.getPerformanceReport(con, keymessage);
						Object[] msg = { keymessage, arr };
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
