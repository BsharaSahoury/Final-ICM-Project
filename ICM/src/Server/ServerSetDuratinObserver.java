package Server;

import java.io.IOException;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import DBconnection.mysqlConnection;
import Entity.Employee;
import Entity.Notification;
import Entity.Phase;
import Entity.RequestPhase;
import ocsf.server.ConnectionToClient;

public class ServerSetDuratinObserver implements Observer {
	public ServerSetDuratinObserver(Observable server) {
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
				if (keymessage.equals("save duration")) {
					int id = (int) arg3[1];
					LocalDate d[] = (LocalDate[]) arg3[2];
					Phase p = (Phase) arg3[3];
					Connection con = mysqlConnection.makeAndReturnConnection();
					boolean b = mysqlConnection.insertDate(con, id, d, p);
					Object[] send = new Object[2];
					send[0] = "duration";
					if (b == false) {
						send[1] = false;
					} else {
						ArrayList<Employee> inspector = mysqlConnection.getEmployees(con, "inspector");
						RequestPhase rp = mysqlConnection.getRequestPhase(con, id, p.toString());
						long millis = System.currentTimeMillis();
						Notification n = new Notification(
								"You have duration from  " + rp.getEmployee() + " from: " + rp.getStartDate() + " to: "
										+ rp.getDueDate() + System.lineSeparator() + ", for the " + rp.getPhase()
										+ " phase with the request number " + rp.getId(),
								new java.sql.Date(millis), "Duratin of evaluator");
						n = mysqlConnection.insertNotificationToDB(con, n);
						mysqlConnection.insertNotificationForUserToDB(con, n, inspector.get(0));
						send[1] = d;
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
