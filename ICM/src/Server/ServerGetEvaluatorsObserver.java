package Server;

import java.io.IOException;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import DBconnection.mysqlConnection;
import Entity.Employee;
import Entity.Phase;
import ocsf.server.ConnectionToClient;

public class ServerGetEvaluatorsObserver implements Observer {
	public ServerGetEvaluatorsObserver(Observable server) {
		server.addObserver(this);
	}

	@Override
	public void update(Observable o, Object arg) {
		if(arg instanceof Object[]) {
			Object[] arg1=(Object[])arg;
			ConnectionToClient client=(ConnectionToClient)arg1[0];
			if(arg1[1] instanceof Object[]) {
				Object[] arg2=(Object[])arg1[1];
				if(arg2[0] instanceof String) {
					String keymessage=(String)arg2[0];
					if(keymessage.equals("evaluators")) {
						String classname=(String)arg2[1];
						Connection con=mysqlConnection.makeAndReturnConnection();
						ArrayList<Employee> evaluators=mysqlConnection.getEmployees(con,"evaluator");
						
						Object[] msg= {"employees",evaluators,classname};
						try {
							client.sendToClient(msg);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					else if(keymessage.equals("Performance leaders")) {
						String classname=(String)arg2[1];
						Connection con=mysqlConnection.makeAndReturnConnection();
						ArrayList<Employee> performers=mysqlConnection.getEmployees(con,"engineer");
						Object[] msg= {"employees",performers,classname};
						try {
							client.sendToClient(msg);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					else if(keymessage.equals("comittee members")) {
						String classname=(String)arg2[1];
						Connection con=mysqlConnection.makeAndReturnConnection();
						ArrayList<Employee> comitteMembers=mysqlConnection.getEmployees(con, "comittee member");
						Employee chairman=mysqlConnection.getChairman(con);
						comitteMembers.add(chairman);
						Object[] msg= {"employees",comitteMembers,classname};
						try {
							client.sendToClient(msg);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					else if(keymessage.equals(("evaluatorsagain"))){
						Connection con=mysqlConnection.makeAndReturnConnection();
						ArrayList<Employee> evaluators=mysqlConnection.getEmployees(con,"evaluator");
						Object[] msg= {keymessage,evaluators};
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
