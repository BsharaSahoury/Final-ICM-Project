package Server;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.util.Observable;
import java.util.Observer;

import DBconnection.mysqlConnection;
import Entity.Employee;
import Entity.Notification;
import Entity.Request;
import Entity.User;
import ocsf.server.ConnectionToClient;

public class serverSubmissionObserver implements Observer {
	public serverSubmissionObserver(Observable server) {
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
					if (keymessage.equals("submitRequest")) {
						Request newRequest = (Request) arg3[1];
						Connection con = mysqlConnection.makeAndReturnConnection();
						newRequest = mysqlConnection.insertRequestToDB(con, newRequest);
						if (newRequest != null) {
							keymessage = "sumbissionSucceeded";
							Object[] message = { keymessage };
							try {
								client.sendToClient(message);
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

							Employee evaluator = mysqlConnection.getAutomaticRecruit(con,
									newRequest.getPrivilegedInfoSys());
							String content = "automatic recruit employee: " + evaluator.getFirstName() + " "
									+ evaluator.getLastName() + " for request#" + newRequest.getId();
							Date date = newRequest.getDate();
							String type = "recruitForInspector";

							Notification n1 = new Notification(content, date, type);
							n1 = mysqlConnection.insertNotificationToDB(con, n1);
							mysqlConnection.insertRecruitNotificationForInspectorToDB(con, n1);

						}
					}
				}
			}
		}

	}

}
