package Server;

import java.io.IOException;
import java.sql.Connection;
import java.util.Observable;
import java.util.Observer;

import DBconnection.mysqlConnection;
import Entity.Employee;
import Entity.Notification;
import ocsf.server.ConnectionToClient;

public class ServerApprovePerformanceObserver implements Observer {
	public ServerApprovePerformanceObserver(Observable server) {
		server.addObserver(this);
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		if (arg1 instanceof Object[]) {
			Object[] arg2 = (Object[]) arg1;
			ConnectionToClient client = (ConnectionToClient) arg2[0];
			if (arg2[1] instanceof Object[]) {
				Object[] arg3 = (Object[]) arg2[1];
				if (arg3[0] instanceof String) {
					String keymessage = (String) arg3[0];
					if (keymessage.equals("performance done")) {
						int id = (int) arg3[1];
						Connection con = mysqlConnection.makeAndReturnConnection();
						mysqlConnection.updatePerfomanceFinishedInDB(con, id);
						Employee chairman = mysqlConnection.getChairman(con);
						long millis = System.currentTimeMillis();
						Notification n = new Notification(
								"perfomance phase for request#" + id
										+ " is over, Please choose a tester for the request",
								new java.sql.Date(millis), "choose tester");
						n = mysqlConnection.insertNotificationToDB(con, n);
						mysqlConnection.insertNotificationForUserToDB(con, n, chairman);
						Object[] msg = { "performance done" };
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
