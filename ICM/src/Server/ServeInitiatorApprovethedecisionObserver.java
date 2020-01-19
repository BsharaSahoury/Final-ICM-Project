package Server;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import DBconnection.mysqlConnection;
import Entity.Employee;
import Entity.Notification;
import ocsf.server.ConnectionToClient;

public class ServeInitiatorApprovethedecisionObserver implements Observer {
	public ServeInitiatorApprovethedecisionObserver(Observable server) {
		server.addObserver(this);
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		if (arg1 instanceof Object[]) {
			Object[] arg2 = (Object[]) arg1;
			ConnectionToClient client = (ConnectionToClient) arg2[0];
			if (arg2[1] instanceof Object[]) {
				Object[] arg3 = (Object[]) arg2[1];
				if (arg3[0] instanceof String) {
					String keymessage = (String) arg3[0];
					if (keymessage.equals("initiator approved the decision of the request")) {
						if (arg3[2] instanceof String) {
							String decision = (String) arg3[2];
							if (decision.equals("reject")) {
								int id = (int) arg3[1];
								Connection con = mysqlConnection.makeAndReturnConnection();
								Boolean found = mysqlConnection.changestatus(con, id, "rejected");
								if (found) {
									long millis = System.currentTimeMillis();
									String not = "the initiator approved the decision for request id#" + id
											+ "and the request rejected";
									Notification decisionnot = new Notification(not, new java.sql.Date(millis),
											"initiator approved the reject");
									decisionnot = mysqlConnection.insertNotificationToDB(con, decisionnot);
									ArrayList<Employee> Inspector = mysqlConnection.getEmployees(con, "inspector");
									mysqlConnection.insertNotificationForUserToDB(con, decisionnot, Inspector.get(0));
								} else {
									keymessage = "already approved and finished";
								}
								try {
									client.sendToClient(keymessage);
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							} else if (decision.equals("passed")) {
								int id = (int) arg3[1];
								Connection con = mysqlConnection.makeAndReturnConnection();
								Boolean found = mysqlConnection.changestatus(con, id, "closed");
								if (found) {
									long millis = System.currentTimeMillis();
									String not = "the initiator approved the decision for request id#" + id
											+ "and the request closed";
									Notification decisionnot = new Notification(not, new java.sql.Date(millis),
											"initiator approved the reject");
									decisionnot = mysqlConnection.insertNotificationToDB(con, decisionnot);
									ArrayList<Employee> Inspector = mysqlConnection.getEmployees(con, "inspector");
									mysqlConnection.insertNotificationForUserToDB(con, decisionnot, Inspector.get(0));
								} else {
									keymessage = "already approved and finished";
								}
								try {
									client.sendToClient(keymessage);
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
	}
}
