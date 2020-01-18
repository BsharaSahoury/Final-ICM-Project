package Server;

import java.io.IOException;
import java.sql.Connection;
import java.util.Observable;
import java.util.Observer;

import DBconnection.mysqlConnection;
import Entity.Employee;
import ocsf.server.ConnectionToClient;

public class ServerChangePermissionObserver implements Observer {
	public ServerChangePermissionObserver(Observable server) {
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
					if(keymessage.equals("permissions tab1")) {
						Employee newE=(Employee)arg3[2];
						Connection con=MainForServer.con;
						String job=(String)arg3[3];
						if(arg3[1]==null) {
							mysqlConnection.setNewJob(con, newE, job);
						}
						else {
						Employee oldE=(Employee)arg3[1];
						mysqlConnection.changePermission(con,oldE,newE);
						}
						Object[] msg= {"permissions tab1"};
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
