package Server;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Observable;
import java.util.Observer;

import DBconnection.mysqlConnection;
import Entity.Notification;
import ocsf.server.ConnectionToClient;
/**
 * in this observer we handle the situation the request didn't passed the testing phase
 *
 */
public class ServerTestFailedObserver implements Observer {
	public ServerTestFailedObserver(Observable server) {
		server.addObserver(this);
	}
/**
 * in this function we return the request phase to performance 
 * and we send notification to the inspector in order to recruit a performance leader and enigneers
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
					if (keymessage.equals("send Failure test result")) {

						int requestId = (int) arg3[1];
						String failureDetails = (String) arg3[2];
						Connection con = mysqlConnection.makeAndReturnConnection();
						mysqlConnection.updateDBdueToFailTest(con, requestId);
						long millis = System.currentTimeMillis();
						Notification n = new Notification(
								"test for request#" + requestId
										+ "failed, request returned to performance phase, please select a performer",
								new java.sql.Date(millis), "fail message sent to Inspector");
						n = mysqlConnection.insertNotificationToDB(con, n);
						mysqlConnection.insertNotificationDetailsToDB(con, n, failureDetails);
						mysqlConnection.sendFailDetailsToInspector(con, n);

					}
				}
			}
		}

	}

}
