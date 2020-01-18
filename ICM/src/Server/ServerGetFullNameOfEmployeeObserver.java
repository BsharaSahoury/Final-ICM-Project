package Server;

import java.io.IOException;
import java.sql.Connection;
import java.util.Observable;
import java.util.Observer;

import DBconnection.mysqlConnection;
import ocsf.server.ConnectionToClient;

public class ServerGetFullNameOfEmployeeObserver implements Observer {
	public ServerGetFullNameOfEmployeeObserver(Observable server) {
		server.addObserver(this);
	}

	@Override
	public void update(Observable arg0, Object arg) {
		// TODO Auto-generated method stub
		if(arg instanceof Object[]) {
			Object[] arg1=(Object[])arg;
			ConnectionToClient client=(ConnectionToClient)arg1[0];
			if(arg1[1] instanceof Object[]) {
				Object[] arg2=(Object[])arg1[1];
				if(arg2[0] instanceof String) {
					String keymessage=(String)arg2[0];
					if(keymessage.equals("getFullNameOfEmployee")) {
						String classname=(String)arg2[1];				
						String username=(String)arg2[2];
						if(username!=null) {
						Connection con=mysqlConnection.makeAndReturnConnection();
						String fullname=mysqlConnection.getinitiatorname(con, username);
						Object[] msg= {keymessage,classname,fullname};
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
}

