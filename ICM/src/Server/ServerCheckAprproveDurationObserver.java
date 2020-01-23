package Server;

import java.io.IOException;
import java.sql.Connection;
import java.util.Observable;
import java.util.Observer;

import DBconnection.mysqlConnection;
import Entity.Request;
import Entity.RequestPhase;
import ocsf.server.ConnectionToClient;
/**
 * This observer check if the inspector already approved the duration of the relevant request.
 *
 */
public class ServerCheckAprproveDurationObserver implements Observer {
	public ServerCheckAprproveDurationObserver(Observable server) {
		server.addObserver(this);
	}
/**
 * This function send to the client the request with the relevant information
 * to check if the inspector already approved the duration that has been set to the request.
 */
	@Override
	public void update(Observable arg0, Object arg1) {
		if (arg1 instanceof Object[]) {
			Object[] arg2 = (Object[]) arg1;
			ConnectionToClient client = (ConnectionToClient) arg2[0];
			if (arg2[1] instanceof Object[]) {
				Object[] arg3 = (Object[]) arg2[1];
				String keymessage = (String) arg3[0];
				if (keymessage.equals("checkAprproveDuration")) {
					int id = (int) arg3[1];
					String phase = (String) arg3[2];
					Connection con = mysqlConnection.makeAndReturnConnection();
					RequestPhase r = mysqlConnection.getRequestPhase(con, id, phase);
					Object[] send = new Object[2];
					send[0] = "requestphase to approve";
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
