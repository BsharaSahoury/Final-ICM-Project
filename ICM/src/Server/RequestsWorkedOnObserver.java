package Server;
import java.io.IOException;


import java.sql.Connection;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import DBconnection.mysqlConnection;
import Entity.Request;
import Entity.RequestPhase;
import Entity.User;
import ocsf.server.ConnectionToClient;

public class RequestsWorkedOnObserver implements Observer {
	public RequestsWorkedOnObserver(Observable server) {
		server.addObserver(this);
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		Object[] args = null;
		if(arg instanceof Object[]) { 
		args=(Object[])arg;
		ConnectionToClient client=(ConnectionToClient)args[0];
		if(args[1] instanceof String[]) {
			String[] Message=(String[])args[1];
			if(Message.length==4 && Message[0].equals("Requests worked on")) {
				Connection con=MainForServer.con;
				ArrayList<RequestPhase> arr=null;
				if(Message[2].equals("Comittee Member")&&Message[3].equals("decision")) {
				String userChairman=mysqlConnection.getChairman(con).getUsername();
				if(userChairman!=null)
				arr=mysqlConnection.getRequestsWorkOn(con,userChairman,Message[3]);
				}
				else if(Message[2].equals("Comittee Member")&&Message[3].equals("testing")) {
					arr=mysqlConnection.getRequestsWorkOn(con,Message[1],Message[3]);	
				}
				else {
				arr=mysqlConnection.getRequestsWorkOn(con,Message[1],Message[3]);
				}
				Object[] send=new Object[4];
				send[0]="Requests worked on";
				send[1]=arr;
				send[2]=Message[2];//here i send the job of the username
				send[3]=Message[3];
			try {
				client.sendToClient(send);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}
			else if(Message.length==4 && Message[0].equals("engineer request work on")) {

				Connection con=MainForServer.con;
				ArrayList<RequestPhase> arr;
				System.out.println(Message[1]);
				arr=mysqlConnection.getrequestEngineerworkon(con, Message[1]);
				Object[] send=new Object[5];
				send[0]="Requests worked on";
				send[1]=arr;
				send[2]=Message[2];//here i send the job of the username
				send[3]=Message[3];
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
