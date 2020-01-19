package Server;

import java.io.IOException;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import DBconnection.mysqlConnection;
import ocsf.server.ConnectionToClient;
/**
 * in this observer we handle the situation when we need to see the profile setting of the the specific user
 * 
 *
 */
public class ServerProfileSettingObserver implements Observer {
	public ServerProfileSettingObserver(Observable server) {
		server.addObserver(this);
	}
/**
 * In this function we return all of the details of the relevant user in order to display
 * them on the profile setting window.
 */
	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		Object[] args = null;
		if (arg instanceof Object[]) {
			args = (Object[]) arg;
			ConnectionToClient client = (ConnectionToClient) args[0];
			if (args[1] instanceof String[]) {
				String[] Message = (String[]) args[1];
				if (Message[0] instanceof String) {
					String keyMessage = Message[0];
					if (Message.length == 3 && keyMessage.equals("ProfileSetting"))
						if (Message[1] instanceof String && Message[2] instanceof String) {
							Connection con = mysqlConnection.makeAndReturnConnection();
							ArrayList<String> arr = mysqlConnection.getUserData(con, Message[1], Message[2]);
							Object[] send = new Object[3];
							send[0] = "ProfileSetting";
							send[1] = arr;// profile setting of user
							send[2] = Message[2];// Job of user
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
}
