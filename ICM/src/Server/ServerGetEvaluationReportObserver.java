package Server;

import java.io.IOException;
import java.sql.Connection;
import java.util.Observable;
import java.util.Observer;

import DBconnection.mysqlConnection;
import Entity.EvaluationReport;
import ocsf.server.ConnectionToClient;
/**
 * In this observer we handle the situation when we need to see the evaluation report
 *
 */
public class ServerGetEvaluationReportObserver implements Observer {
	public ServerGetEvaluationReportObserver(Observable server) {
		server.addObserver(this);
	}
/**
 * this function return the evaluation report of a relevant request to the client
 */
	@Override
	public void update(Observable arg0, Object arg) {
		// TODO Auto-generated method stub
		if (arg instanceof Object[]) {
			Object[] arg1 = (Object[]) arg;
			ConnectionToClient client = (ConnectionToClient) arg1[0];
			if (arg1[1] instanceof Object[]) {
				Object[] arg2 = (Object[]) arg1[1];
				if (arg2[0] instanceof String) {
					String keymessage = (String) arg2[0];
					if (keymessage.equals("get evaluation report")) {
						int id = (int) arg2[1];
						Connection con = mysqlConnection.makeAndReturnConnection();
						EvaluationReport report = mysqlConnection.getevaluationreport(con, id);
						try {
							Object[] send = new Object[2];
							send[0] = keymessage;
							send[1] = report;
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
}
