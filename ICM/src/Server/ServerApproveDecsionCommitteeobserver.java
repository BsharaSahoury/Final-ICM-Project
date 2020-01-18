package Server;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import DBconnection.mysqlConnection;
import Entity.Employee;
import Entity.Notification;
import ocsf.server.ConnectionToClient;

public class ServerApproveDecsionCommitteeobserver implements Observer {
	public ServerApproveDecsionCommitteeobserver(Observable server) {
		server.addObserver(this);
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		if(arg1 instanceof Object[]) {
			Object[] arg2=(Object[])arg1;
			ConnectionToClient client=(ConnectionToClient)arg2[0];
			if(arg2[1] instanceof Object[]) {
				Object[] arg3=(Object[])arg2[1];
				if(arg3[0] instanceof String) {
					String keymessage=(String)arg3[0];
					if(keymessage.equals("approve committee decision")) {
						int id=(int)arg3[1];
						Connection con=mysqlConnection.makeAndReturnConnection();
						String dec=(String)arg3[2];
						mysqlConnection.addRequestToDB(con,id,dec);
						String Explaindec=(String)arg3[3];
						long millis=System.currentTimeMillis();	
						String not="Chairman Approved Comittee Members Decision is '"+dec+"' for request id="+id+"\n";
						Notification decisionnot=new Notification(not,new java.sql.Date(millis),"Chairman Approved Comittee Members Decision is "+dec);
						decisionnot=mysqlConnection.insertNotificationToDB(con,decisionnot);
						ArrayList<Employee> Inspector=mysqlConnection.getEmployees(con, "inspector");
						mysqlConnection.insertNotificationForUserToDB(con,decisionnot,Inspector.get(0));
						String[] b=new String[2];
						b=Explaindec.split("\n");
						mysqlConnection.insertNotificationDetailsToDB(con, decisionnot, not+"\n"+b[1]);
						try {
							client.sendToClient("Chairman approve successul");
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
