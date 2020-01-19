package Server;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import DBconnection.mysqlConnection;
import Entity.Notification;
import ocsf.server.ConnectionToClient;
/**
 * In this observer we handle the situation when we want to see the details of any nitifiaction.
 *
 */
public class ServerNotificationdetailsObserver implements Observer {
	public ServerNotificationdetailsObserver(Observable server) {
		server.addObserver(this);
	}
/**
 * In this function we return the details of the notification with the 'notid' id
 * in order to display it to the user.
 */
	@Override
	public void update(Observable arg0, Object arg1) {
		if (arg1 instanceof Object[]) {
			Object[] arg2 = (Object[]) arg1;
			ConnectionToClient client = (ConnectionToClient) arg2[0];
			if (arg2[1] instanceof Object[]) {
				Object[] arg3 = (Object[]) arg2[1];
				String keymessage = (String) arg3[0];
				if (keymessage.equals("get explain notification")) {
					int notid = (int) arg3[1];
					String job = (String) arg3[2];
					Connection con = mysqlConnection.makeAndReturnConnection();
					String details = mysqlConnection.getnotificationdetails(con, notid);
					keymessage = "notification details";
					Object[] message = { keymessage, details, job };
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
