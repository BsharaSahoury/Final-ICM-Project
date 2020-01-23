package Server;

import java.io.IOException;
import java.sql.Connection;
import java.util.Observable;
import java.util.Observer;

import DBconnection.mysqlConnection;
import Entity.Employee;
import Entity.Notification;
import ocsf.server.ConnectionToClient;
/**
 * this observer assign the assign the performer to the relevant request.
 *
 */
public class ServerChoosedPerformerObserver implements Observer {
	public ServerChoosedPerformerObserver(Observable server) {
		server.addObserver(this);
	}
/**
 * This function search for the performer that had this full name and recruit him
 * to lead the relevant request and notifies him about the recruit.
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
					if (keymessage.equals("Performer confirmation for step")) {
						String fullname = (String) arg3[1];
						int id = (int) arg3[2];
						Connection con = mysqlConnection.makeAndReturnConnection();
						Employee performer = mysqlConnection.getSpecificEmployee(con, fullname);
						boolean flag = mysqlConnection.assignPerformerToRequest(con, performer, id);
						if (flag) {
							Object[] message = { "PerformerRecruit" };
							try {
								client.sendToClient(message);
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							long millis = System.currentTimeMillis();
							Notification n1 = new Notification("You've been recruited to perform request#" + id,
									new java.sql.Date(millis), "recruitNotificationForPerformance");
							n1 = mysqlConnection.insertNotificationToDB(con, n1);
							mysqlConnection.insertNotificationForUserToDB(con, n1, performer);
						}
					}
				}
			}
		}
	}
}
