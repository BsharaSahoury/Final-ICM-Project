package Server;

import java.io.IOException;
import java.sql.Connection;
import java.util.Observable;
import java.util.Observer;

import DBconnection.mysqlConnection;
import Entity.Employee;
import ocsf.server.ConnectionToClient;
/**
 * This observer handle the situation when the inspector decided to replace the evaluator.
 *
 */
public class ServerChangeEvaluatorObserver implements Observer {
	public ServerChangeEvaluatorObserver(Observable server) {
		server.addObserver(this);
	}
/**
 * This function change the last evaluator and recruit the new evaluator that the inspector
 * chose to recruit for the relevant request.
 */
	@Override
	public void update(Observable arg0, Object arg1) {
		if (arg1 instanceof Object[]) {
			Object[] arg2 = (Object[]) arg1;
			ConnectionToClient client = (ConnectionToClient) arg2[0];
			if (arg2[1] instanceof Object[]) {
				Object[] arg3 = (Object[]) arg2[1];
				if (arg3[0] instanceof String) {
					Connection con = MainForServer.con;
					String keymessage = (String) arg3[0];
					if (keymessage.equals("change ev")) {
						Employee newEv = (Employee) arg3[2];
						if (arg3[1] != null) {
							Employee oldEv = (Employee) arg3[1];
							mysqlConnection.changeEvaluator(con, oldEv, newEv);
						} else {
							mysqlConnection.setNewJob(con, newEv, "evaluator");
						}
						Object[] msg = { "permissions tab1" };
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

}
