package Server;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import DBconnection.mysqlConnection;
import ocsf.server.ConnectionToClient;
import Entity.Request;

public class ServerRequestInfoObserver implements Observer {
	public ServerRequestInfoObserver(Observable server) {
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
				if (keymessage.equals("Request Info")) {
					int id = (int) arg3[1];
					Connection con = mysqlConnection.makeAndReturnConnection();
					Request r = mysqlConnection.getRequestInfo(con, id);
					Object[] send = new Object[2];
					send[0] = "Request Info";
					send[1] = r;
					try {
						client.sendToClient(send);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
	}
}
