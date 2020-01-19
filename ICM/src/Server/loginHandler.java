package Server;

import java.io.IOException;
import java.sql.Connection;
import java.util.Observable;
import java.util.Observer;

import DBconnection.mysqlConnection;
import Entity.User;
import ocsf.server.ConnectionToClient;
/**
 *This is the log-in Observer...
 *
 */
public class loginHandler implements Observer {
	/**
	 * This Observer handle the login situation,
	 * when the user tries to log-into the system.
	 * @param server
	 */
	public loginHandler(Observable server) {
		server.addObserver(this);
	}
	
  /**
   * this update function check if the user-name and the password
   * exist in the DB and change the log-in status of the user to 'Yes'
   * and send message to the 'clienLoginObserver' in package 'Client' 
   * in order to log-in the user into the system in the relevant window.
   */
	@Override
	public void update(Observable o, Object arg) {
		Object[] args = null;
		String sendMsg = "AnotherClientIsLoggedInByThisUser";
		if (arg instanceof Object[]) {
			args = (Object[]) arg;
			ConnectionToClient client = (ConnectionToClient) args[0];

			if (args[1] instanceof String[]) {
				String[] Message = (String[]) args[1];
				String userOnline = null;
				if (Message.length == 3 && Message[0].equals("login")) {
					Connection con = mysqlConnection.makeAndReturnConnection();

					User user = mysqlConnection.isInDB(con, Message[1], Message[2]);
					if (user != null) {
						userOnline = mysqlConnection.IsConnectedByAnotherClient(con, Message[1], Message[2]);
					}
					if (userOnline != null)
						if (userOnline.equals("false"))
							mysqlConnection.updateUSerLoggedInToYes(con, Message[1], Message[2]);
					try {
						if (user == null) {
							client.sendToClient(user);
						} else {
							if (userOnline.equals("false"))
								client.sendToClient(user);
							else
								client.sendToClient(sendMsg);
						}

					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			}

		}
	}

}
