package Server;

import java.io.IOException;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import DBconnection.mysqlConnection;
import Entity.Employee;
import ocsf.server.ConnectionToClient;

public class ServerGetChairManObserver implements Observer {
	public ServerGetChairManObserver(Observable server) {
		server.addObserver(this);
	}

	@Override
	public void update(Observable arg0, Object arg) {
		if (arg instanceof Object[]) {
			Object[] arg1 = (Object[]) arg;
			ConnectionToClient client = (ConnectionToClient) arg1[0];
			if (arg1[1] instanceof String) {
				String keymessage = (String) arg1[1];
				if (keymessage.equals("get ChairMan")) {
					Connection con = mysqlConnection.makeAndReturnConnection();
					Employee chairman = mysqlConnection.getChairman(con);
					Object[] msg = { keymessage, chairman };
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
