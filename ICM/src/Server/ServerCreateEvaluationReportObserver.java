package Server;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import DBconnection.mysqlConnection;
import Entity.Employee;
import Entity.EvaluationReport;
import Entity.Notification;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import ocsf.server.ConnectionToClient;
/**
 * in this observer we handle to enter the evaluation report into the DB 
 *
 */
public class ServerCreateEvaluationReportObserver implements Observer {
	public ServerCreateEvaluationReportObserver(Observable server) {
		server.addObserver(this);
	}
/**
 * In this function we save the evaluation report in the DB
 * and we send notification to the committee members that the request passed to the decision phase
 */
	@Override
	public void update(Observable arg0, Object arg1) {
		if (arg1 instanceof Object[]) {
			Object[] arg2 = (Object[]) arg1;
			ConnectionToClient client = (ConnectionToClient) arg2[0];
			if (arg2[1] instanceof Object[]) {
				Object[] arg3 = (Object[]) arg2[1];
				String keymessage = (String) arg3[0];
				if (keymessage.equals("send the report")) {
					EvaluationReport er = (EvaluationReport) arg3[1];
					Connection con = mysqlConnection.makeAndReturnConnection();
					mysqlConnection.insertReport(con, er);
					Object[] send = new Object[2];
					send[0] = "send the report";
					try {
						client.sendToClient(send);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					Employee chairman = mysqlConnection.getChairman(con);
					ArrayList<Employee> committeMember = mysqlConnection.getEmployees(con, "comittee member");
					long millis = System.currentTimeMillis();
					String notifcation = "you have new Request: request with id " + " " + er.getRequestID()
							+ " start work";
					Notification not = new Notification(notifcation, new java.sql.Date(millis),
							"new request for committe");
					not = mysqlConnection.insertNotificationToDB(con, not);
					mysqlConnection.insertNotificationForUserToDB(con, not, chairman);
					mysqlConnection.insertNotificationForUserToDB(con, not, committeMember.get(0));
					mysqlConnection.insertNotificationForUserToDB(con, not, committeMember.get(1));

				}
			}
		}
	}

}
