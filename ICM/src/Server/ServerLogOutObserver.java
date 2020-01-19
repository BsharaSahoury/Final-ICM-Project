package Server;

import java.io.IOException;
import java.sql.Connection;
import java.util.Observable;
import java.util.Observer;

import DBconnection.mysqlConnection;
import ocsf.server.ConnectionToClient;

public class ServerLogOutObserver implements Observer {
	public ServerLogOutObserver(Observable server) {
		server.addObserver(this);
	}

	@Override
	public void update(Observable o, Object arg) {
		Object[] args = null;
		if (arg instanceof Object[]) {
			args = (Object[]) arg;
			ConnectionToClient client = (ConnectionToClient) args[0];

			if (args[1] instanceof String[]) {
				String[] Message = (String[]) args[1];

				String[] send = new String[2];

				if (Message.length == 4 && Message[0].equals("LogOut")) {
					Connection con = mysqlConnection.makeAndReturnConnection();
					String result = mysqlConnection.logOutUser(con, Message[1], Message[2], Message[3]);
					send[0] = "LogOut";
					send[1] = result;
					try {
						client.sendToClient(send);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			}

		}
	}// update
}