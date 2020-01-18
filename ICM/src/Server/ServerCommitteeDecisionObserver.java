package Server;
import java.io.IOException;


import java.sql.Connection;
import java.util.Observable;
import java.util.Observer;

import DBconnection.mysqlConnection;
import Entity.Employee;
import Entity.Notification;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import ocsf.server.ConnectionToClient;
public class ServerCommitteeDecisionObserver implements Observer{
	
		public ServerCommitteeDecisionObserver(Observable server) {
			server.addObserver(this);
		}

		@Override
		public void update(Observable arg0, Object arg1) {
			// TODO Auto-generated method stub
			Object[] args = null;
			if(arg1 instanceof Object[]) {
				args=(Object[])arg1;
				ConnectionToClient client=(ConnectionToClient)args[0];
				if(args[1] instanceof String[]) {
					String[] Message=(String[])args[1];
					if(Message[0].equals("Committee Member Decision")) {
						Connection con=mysqlConnection.makeAndReturnConnection();
						int Requestid=Integer.parseInt(Message[3]);
						Employee ChairManUsername=mysqlConnection.FindEmployee(con,Requestid,"decision");
						if(ChairManUsername.equals(null)) {
							 Alert alertWarning = new Alert(AlertType.WARNING);
						     alertWarning.setTitle("Warning Alert Title");
						     alertWarning.setHeaderText("Warning!");
						     alertWarning.setContentText("There is no ChairMan recuried for this request");
						     alertWarning.showAndWait();
						}
						else {
							long millis=System.currentTimeMillis();
							
							String not="Comittee Members Decision is '"+Message[1]+"' for request id="+Message[3]+"\n";
							Notification decisionnot=new Notification(not,new java.sql.Date(millis),"Decision of Committee Member");
							decisionnot=mysqlConnection.insertNotificationToDB(con,decisionnot);
							mysqlConnection.insertNotificationForUserToDB(con,decisionnot,ChairManUsername);
							mysqlConnection.insertNotificationDetailsToDB(con, decisionnot, not+"Explain the decision:"+Message[2]);
							try {
								client.sendToClient("Committee Member Decision");
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

