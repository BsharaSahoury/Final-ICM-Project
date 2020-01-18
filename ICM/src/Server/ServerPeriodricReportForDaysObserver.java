package Server;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import DBconnection.mysqlConnection;
import ocsf.server.ConnectionToClient;

public class ServerPeriodricReportForDaysObserver implements Observer {
	public ServerPeriodricReportForDaysObserver(Observable server) {
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
					if(keymessage.equals("Pdays") || keymessage.equals("Pmonths") || keymessage.equals("Pyears")) {
						Connection con=MainForServer.con;
						Date from=Date.valueOf((LocalDate)arg3[1]);
						Date to=Date.valueOf((LocalDate)arg3[2]);
						String Rtype=(String)arg3[3];
						if(Rtype.equals("No. days of Treatments")) {
							ArrayList<Long> arr=mysqlConnection.getFourthReportData(con,keymessage,from,to);
							int max=mysqlConnection.getMaxTreatment(con, from, to);
							int k=max/arr.size();
							Rtype=Rtype+k;
							 Object[] msg= {keymessage,from,to,arr,Rtype}; 
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
