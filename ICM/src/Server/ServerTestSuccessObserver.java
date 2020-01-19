package Server;

import java.io.IOException;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Observable;
import java.util.Observer;

import DBconnection.mysqlConnection;
import Entity.Employee;
import Entity.Notification;
import Entity.Phase;
import ocsf.server.ConnectionToClient;

public class ServerTestSuccessObserver implements Observer {
	public ServerTestSuccessObserver(Observable server) {
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
					if (keymessage.equals("send Passed test result")) {
						int requestId = (int) arg3[1];
						Connection con = mysqlConnection.makeAndReturnConnection();
						mysqlConnection.updateDBdueToSuccessTest(con, requestId);
						mysqlConnection.updateCurrentPhase(con, requestId, Phase.valueOf("closing"));	
						//mysqlConnection.updateDBdueToFailTest(con,requestId);
						long millis = System.currentTimeMillis();
						Notification n = new Notification(
								"test for request#" + requestId + "passed, request moved to close phase",
								new java.sql.Date(millis), "success message sent to Inspector");
						n = mysqlConnection.insertNotificationToDB(con, n);
						ArrayList<Employee> inspector = mysqlConnection.getEmployees(con, "inspector");
						mysqlConnection.insertNotificationForUserToDB(con, n, inspector.get(0));

					}
				}
			}
		}

	}

}
