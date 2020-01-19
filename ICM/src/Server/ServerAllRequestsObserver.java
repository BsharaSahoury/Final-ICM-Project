package Server;

import java.io.IOException;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import DBconnection.mysqlConnection;
import Entity.Request;
import Entity.RequestPhase;
import Entity.User;
import ocsf.server.ConnectionToClient;
/**
 * This is the observer that handle getting all the requests
 * with the relevant details from the DB.
 * This observer start when the inspector or the administrator need to see all the requests.
 */
public class ServerAllRequestsObserver implements Observer {
	public ServerAllRequestsObserver(Observable server) {
		server.addObserver(this);
	}
/**
 * In this update function we get all the requests from the DB
 * with the current phase,Initiator name,the submission date,id and status
 * and send them to the client(administrator or inspector).
 */
	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		Object[] args = null;
		if (arg instanceof Object[]) {
			args = (Object[]) arg;
			ConnectionToClient client = (ConnectionToClient) args[0];
			if (args[1] instanceof String[]) {
				String[] Message = (String[]) args[1];
				if (Message[0].equals("All Requests")) {

					Connection con = mysqlConnection.makeAndReturnConnection();
					ArrayList<RequestPhase> arr = mysqlConnection.getDataFromDB(con);
					Object[] send = new Object[3];
					send[0] = "All Requests";
					send[1] = arr;
					send[2] = Message[1];
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
