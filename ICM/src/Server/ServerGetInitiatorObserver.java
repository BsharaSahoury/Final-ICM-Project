package Server;

import java.io.IOException;
import java.sql.Connection;
import java.util.Observable;
import java.util.Observer;
import DBconnection.mysqlConnection;
import Entity.Notification;
import Entity.User;
import ocsf.server.ConnectionToClient;

public class ServerGetInitiatorObserver implements Observer {
	public ServerGetInitiatorObserver(Observable server) {
		server.addObserver(this);
	}

	@Override
	public void update(Observable arg0, Object arg) {
		// TODO Auto-generated method stub
		if (arg instanceof Object[]) {
			Object[] arg1 = (Object[]) arg;
			ConnectionToClient client = (ConnectionToClient) arg1[0];
			if (arg1[1] instanceof Object[]) {
				Object[] arg2 = (Object[]) arg1[1];
				if (arg2[0] instanceof String) {
					String keymessage = (String) arg2[0];
					if (keymessage.equals("Send to initiator that committee rejected the request")) {
						int id = (int) arg2[1];
						Connection con = mysqlConnection.makeAndReturnConnection();
						User initiator = mysqlConnection.getInitiatorUser(con, id);
						if (!initiator.equals(null)) {
							String decision = (String) arg2[2];
							String explain = (String) arg2[3];
							long millis = System.currentTimeMillis();
							Notification n1 = new Notification("Youre request #" + id + "has been rejected",
									new java.sql.Date(millis),
									"Committee reject the request and wait for initiator approve");
							n1 = mysqlConnection.insertNotificationToDB(con, n1);
							mysqlConnection.insertNotificationForUserToDB(con, n1, initiator);
							String[] b = new String[2];
							b = explain.split("\n");
							mysqlConnection.insertNotificationDetailsToDB(con, n1,
									"Youre request #" + id + "has been rejected" + "\n" + b[1]);
							try {
								client.sendToClient("Message send to initiator to approve the decision");
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					} else if (keymessage.equals("Send to initiator that request approved")) {
						int id = (int) arg2[1];
						Connection con = mysqlConnection.makeAndReturnConnection();
						User initiator = mysqlConnection.getInitiatorUser(con, id);
						if (!initiator.equals(null)) {

							String decision = (String) arg2[2];
							long millis = System.currentTimeMillis();
							Notification n1 = new Notification("Youre request #" + id + "has been approved",
									new java.sql.Date(millis),
									"Request test passed and wait for intitiator approve to close");
							n1 = mysqlConnection.insertNotificationToDB(con, n1);
							mysqlConnection.insertNotificationForUserToDB(con, n1, initiator);
							try {
								client.sendToClient("Message send to initiator to approve the decision");
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