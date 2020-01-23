package Server;

import java.io.IOException;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import DBconnection.mysqlConnection;
import Entity.Employee;
import Entity.Notification;
import Entity.Phase;
import Entity.RequestPhase;
import ocsf.server.ConnectionToClient;
/**
 * in this observer we handle the situation when the inspector approve the extension
 * for specific request
 *
 */
public class ServerRequestExtensionApproveToAdminObserver implements Observer {
	public ServerRequestExtensionApproveToAdminObserver(Observable server) {
		server.addObserver(this);
	}
/**
 * In this function we send notification to the administrator and to the phaseadministrator of the relevant request
 *  when the inspector approve or reject the extension of specific request phase
 */
	@Override
	public void update(Observable arg0, Object arg1) {
		if (arg1 instanceof Object[]) {
			Object[] arg2 = (Object[]) arg1;
			ConnectionToClient client = (ConnectionToClient) arg2[0];
			if (arg2[1] instanceof Object[]) {
				Object[] arg3 = (Object[]) arg2[1];
				String keymessage = (String) arg3[0];
				if (keymessage.equals("send Request extension approve to Admin")) {
					int id = (int) arg3[1];
					String RequestPhase = (String) arg3[2];
					LocalDate dueDate = (LocalDate) arg3[3];
					String explain = (String) arg3[4];
					String phaseAdministrator = (String) arg3[5];
					Connection con = mysqlConnection.makeAndReturnConnection();
					mysqlConnection.updateDuedate(con, id, RequestPhase, dueDate);
					long millis = System.currentTimeMillis();
					Notification n = new Notification(
							"Extension request# " + id + " time on phase " + RequestPhase
									+ ", Do Has been Approved by Inspector",
							new java.sql.Date(millis),
							"Extension Confirmation message sent to Admin after inspector confirmation");
					n = mysqlConnection.insertNotificationToDB(con, n);

					mysqlConnection.insertNotificationDetailsToDB(con, n, explain);

					mysqlConnection.sendExtensionConfiramtionToAdmin(con, n);
					Employee e = mysqlConnection.getMyEmployee(con, phaseAdministrator);
					String notifcation = "the Inspector approved your request to extend the duration for the request number "
							+ id;
					Notification not = new Notification(notifcation, new java.sql.Date(millis),
							"answer to extension request");
					not = mysqlConnection.insertNotificationToDB(con, not);
					mysqlConnection.insertNotificationForUserToDB(con, not, e);

				} else if (keymessage.equals("send Request extension reject to Admin")) {
					int id = (int) arg3[1];
					String phaseAdministrator = (String) arg3[2];
					Connection con = mysqlConnection.makeAndReturnConnection();
					long millis = System.currentTimeMillis();
					Employee e = mysqlConnection.getMyEmployee(con, phaseAdministrator);
					String notifcation = "the Inspector reject your request to extend the duration for the request number "
							+ id;
					Notification not = new Notification(notifcation, new java.sql.Date(millis),
							"answer to extension request");
					not = mysqlConnection.insertNotificationToDB(con, not);
					mysqlConnection.insertNotificationForUserToDB(con, not, e);

				}
			}
		}
	}

}
