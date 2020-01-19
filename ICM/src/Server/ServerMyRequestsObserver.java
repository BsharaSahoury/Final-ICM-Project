package Server;

import java.io.IOException;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import DBconnection.mysqlConnection;
import Entity.Request;
import Entity.User;
import ocsf.server.ConnectionToClient;

public class ServerMyRequestsObserver implements Observer {
	public ServerMyRequestsObserver(Observable server) {
		server.addObserver(this);
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		Object[] args = null;
		if (arg instanceof Object[]) {
			args = (Object[]) arg;
			ConnectionToClient client = (ConnectionToClient) args[0];
			if (args[1] instanceof String[]) {
				String[] Message = (String[]) args[1];
				if (Message.length == 3 && Message[0].equals("my Requests")) {
					Connection con = mysqlConnection.makeAndReturnConnection();
					ArrayList<Request> arr = mysqlConnection.getmyRequestFromDB(con, Message[1]);
					Object[] send = new Object[3];
					send[0] = "myRequests";
					send[1] = arr;
					send[2] = Message[2];// here i send the job of the username
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
