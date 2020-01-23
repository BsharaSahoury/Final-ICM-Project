package Server;

import java.io.IOException;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.Observable;
import java.util.Observer;

import DBconnection.mysqlConnection;
import Entity.Phase;
import Entity.State;
import ocsf.server.ConnectionToClient;
/**
 * This is the observer that handle the approve of the Inspector to the duration
 * that has been set by the phase administrator.
 *
 */
public class ServerApproveDuratinObserver implements Observer {
	public ServerApproveDuratinObserver(Observable server) {
		server.addObserver(this);
	}
/**
 * In this function we insert the date the has been approved from the Inspector to DB
 * and when the start date arrived we change the state of the request to work,
 * So the employees could start work on the request.
 */
	@Override
	public void update(Observable arg0, Object arg1) {
		if (arg1 instanceof Object[]) {
			Object[] arg2 = (Object[]) arg1;
			ConnectionToClient client = (ConnectionToClient) arg2[0];
			if (arg2[1] instanceof Object[]) {
				Object[] arg3 = (Object[]) arg2[1];
				String keymessage = (String) arg3[0];
				if (keymessage.equals("ispector duration")) {
					int id = (int) arg3[1];
					LocalDate[] d = (LocalDate[]) arg3[2];
					Phase p = (Phase) arg3[3];
					State s = (State) arg3[4];
					Connection con = mysqlConnection.makeAndReturnConnection();
					boolean b = mysqlConnection.insertDate(con, id, d, p);
					Object[] send = new Object[2];
					send[0] = "approvedDuration";
					if (b == false) {
						send[1] = false;
					} else {
						send[1] = d;
						mysqlConnection.changeState(con, id, p, s);
					}
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
