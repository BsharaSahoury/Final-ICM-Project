package Server;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import DBconnection.mysqlConnection;
import Entity.Employee;
import javafx.scene.control.cell.PropertyValueFactory;
import ocsf.server.ConnectionToClient;
/**
 * this observer handle the situation of ask for details about employees in the permission page.
 *
 */
public class ServerInitPermissionsPageObserver implements Observer {
	public ServerInitPermissionsPageObserver(Observable server) {
		server.addObserver(this);
	}
/**
 * this function return all the details about the all of the engineers,committee members,
 * chairman and current inspector,and send it to the administrator permission page.
 */
	@Override
	public void update(Observable arg0, Object arg1) {
		if (arg1 instanceof Object[]) {
			Object[] arg2 = (Object[]) arg1;
			ConnectionToClient client = (ConnectionToClient) arg2[0];
			if (arg2[1] instanceof Object[]) {
				Object[] arg3 = (Object[]) arg2[1];
				if (arg3[0] instanceof String) {
					String keymessage = (String) arg3[0];
					if (keymessage.equals("employees&permissions")) {
						Connection con = MainForServer.con;
						ArrayList<Employee> list = mysqlConnection.getAllengineers(con);
						Employee inspector = mysqlConnection.getInspector(con);
						Employee chairman = mysqlConnection.getChairman(con);
						Employee[] comittee = mysqlConnection.getComittee(con);
						Object[] msg = { "employees&permissions", list };
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
