package Server;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
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
 * In this observer we handle a situation of ask for extension by the phase administrator
 *
 */
public class ServerExtendRequestTimeObserver implements Observer {
	public ServerExtendRequestTimeObserver(Observable server) {
		server.addObserver(this);
	}
/**
 * In this function we add a extension request in the DB 
 * and we send notification for the inspector in order to approve the extension.
 */
	@Override
	public void update(Observable arg0, Object arg1) {
		if (arg1 instanceof Object[]) {
			Object[] arg2 = (Object[]) arg1;
			ConnectionToClient client = (ConnectionToClient) arg2[0];
			if (arg2[1] instanceof Object[]) {
				Object[] arg3 = (Object[]) arg2[1];
				String keymessage = (String) arg3[0];
				if (keymessage.equals("send Request extension to inspector for confirm")) {
					RequestPhase rp = (RequestPhase) arg3[1];
					LocalDate newDue = (LocalDate) arg3[2];
					String Reason = (String) arg3[3];
					int id = (int) rp.getId();
					String d1 = Reason + "#" + newDue.toString();
					Connection con = mysqlConnection.makeAndReturnConnection();
					boolean flag = mysqlConnection.extendTime(con, rp, newDue);
					if (flag) {
						long millis = System.currentTimeMillis();
						Notification n = new Notification(
								"Extension request# " + id + " time on phase " + rp.getPhase().toString()
										+ ", Do you confirm extension?",
								new java.sql.Date(millis), "Extension Confirmation message sent to Inspector");
						n = mysqlConnection.insertNotificationToDB(con, n);
						mysqlConnection.insertNotificationDetailsToDB(con, n, d1);
						mysqlConnection.sendExtensionConfiramtionToInspector(con, n);
						Object[] message = { "its ok" };
						try {
							client.sendToClient(message);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					} else {
						Object[] message = { "Extension possible once per stage !" };
						try {
							client.sendToClient(message);
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
