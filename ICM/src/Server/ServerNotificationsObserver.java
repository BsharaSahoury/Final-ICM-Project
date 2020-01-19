package Server;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import DBconnection.mysqlConnection;
import Entity.Notification;
import ocsf.server.ConnectionToClient;

public class ServerNotificationsObserver implements Observer {
	public ServerNotificationsObserver(Observable server) {
		server.addObserver(this);
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		if (arg1 instanceof Object[]) {
			Object[] arg2 = (Object[]) arg1;
			ConnectionToClient client = (ConnectionToClient) arg2[0];
			if (arg2[1] instanceof Object[]) {
				Object[] arg3 = (Object[]) arg2[1];
				String keymessage = (String) arg3[0];
				if (keymessage.equals("notification")) {
					String username = (String) arg3[1];
					Connection con = mysqlConnection.makeAndReturnConnection();
					ArrayList<Notification> Nlist = mysqlConnection.getNotificationsForUser(con, username);
					keymessage = "getNotific";
					Object[] message = { keymessage, Nlist };
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
