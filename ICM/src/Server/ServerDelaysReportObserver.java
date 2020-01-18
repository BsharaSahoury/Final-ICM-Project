package Server;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import DBconnection.mysqlConnection;
import ocsf.server.ConnectionToClient;

public class ServerDelaysReportObserver implements Observer {
	public ServerDelaysReportObserver(Observable server) {
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
					if(keymessage.equals("No.Delays") || keymessage.equals("Delays Durations")) {
						Connection con=MainForServer.con;
						ArrayList<Long> arr=mysqlConnection.getDelaysReportData(con,keymessage);
						Object[] msg= {keymessage,arr};
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
