package Server;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import DBconnection.mysqlConnection;
import ocsf.server.ConnectionToClient;
import Entity.Request;
import Entity.RequestPhase;

public class ServerRequestTrackObserver implements Observer {
	public ServerRequestTrackObserver(Observable server) {
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
				if (keymessage.equals("Track request")) {
					int id = (int) arg3[1];				
					Connection con = mysqlConnection.makeAndReturnConnection();			
					RequestPhase rp1 = mysqlConnection.getRequestTrack(con, id);
					Object[] send = new Object[2];
					send[0] = "Track request";
					send[1] = rp1;
					System.out.println("xxx");
					System.out.println(rp1.getCurrentPhase());
					
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
