package Server;

import java.io.IOException;
import java.sql.Connection;
import java.util.Observable;
import java.util.Observer;

import DBconnection.mysqlConnection;
import Entity.Employee;
import Entity.Phase;
import Entity.RequestPhase;
import Entity.State;
import ocsf.server.ConnectionToClient;
/**
 * In this observer we handle the situation when we need to see the duration of request in relevant phase.
 *
 */
public class ServerGetDurationObserver implements Observer {
	public ServerGetDurationObserver(Observable server) {
		server.addObserver(this);
	}
/**
 * In this function we return the request in the relevant phase with the duration of the phase.
 */
	@Override
	public void update(Observable arg0, Object arg1) {
		if (arg1 instanceof Object[]) {
			Object[] arg2 = (Object[]) arg1;
			ConnectionToClient client = (ConnectionToClient) arg2[0];
			if (arg2[1] instanceof Object[]) {
				Object[] arg3 = (Object[]) arg2[1];
				String keymessage = (String) arg3[0];
				if (keymessage.equals("get duration")) {
					int id = (int) arg3[1];
					Phase phase = (Phase) arg3[2];
					Connection con = mysqlConnection.makeAndReturnConnection();
					RequestPhase request = mysqlConnection.getRequestPhase(con, id, phase.toString());
					Object[] msg = { keymessage, request };
					try {
						client.sendToClient(msg);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			}
		}
	}

}
