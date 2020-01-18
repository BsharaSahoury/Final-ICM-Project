package Server;

import java.io.IOException;

import java.sql.Connection;
import java.util.Observable;
import java.util.Observer;

import DBconnection.mysqlConnection;
import Entity.Employee;
import Entity.Notification;
import ocsf.server.ConnectionToClient;

public class ServerAutomaticRecruitObserver implements Observer {
	public ServerAutomaticRecruitObserver(Observable server) {
		server.addObserver(this);
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		if(arg1 instanceof Object[]) {
			Object[] arg2=(Object[])arg1;
			ConnectionToClient client=(ConnectionToClient)arg2[0];
			if(arg2[1] instanceof Object[]) {
				Object[] arg3=(Object[])arg2[1];
				if(arg3[0] instanceof String) {
					String keymessage=(String)arg3[0];
					if(keymessage.equals("automatic")) {
						int id=(int)arg3[1];
						Connection con=mysqlConnection.makeAndReturnConnection();
                        Employee evaluator=mysqlConnection.recruitAutomatically(con,id);
						if(evaluator != null) {
							Object[] message= {"evaluatorRecruit"};
							try {
								client.sendToClient(message);
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							long millis=System.currentTimeMillis();
							Notification n1=new Notification(
									"You've been recruited to evaluate request#"+id,
									new java.sql.Date(millis),
									"recruitNotificationForEvaluator");
							n1=mysqlConnection.insertNotificationToDB(con, n1);
							mysqlConnection.insertNotificationForUserToDB(con, n1,evaluator);
						}
					}
				}
			}
			
		}
		
	}

}
