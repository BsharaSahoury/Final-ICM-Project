package Server;

import java.sql.Connection;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import DBconnection.mysqlConnection;
import Entity.Employee;
import Entity.Notification;
import Entity.RequestPhase;
import ocsf.server.ObservableServer;

public class ServerDetectorObserver implements Observer {
	public ServerDetectorObserver(Observable server) {
		server.addObserver(this);
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		Connection con=mysqlConnection.makeAndReturnConnection();
		long millis=System.currentTimeMillis();
		Date today=new java.sql.Date(millis);
		mysqlConnection.getToWork(con,today);
		ArrayList<RequestPhase> RPlist=mysqlConnection.detectExceptions(con,today);
		for(RequestPhase rp : RPlist) {
			Notification n1=new Notification("Exception in request#"+rp.getId()+" in phase "+rp.getPhase().toString()+" .",today,"Exception message");
			n1=mysqlConnection.insertNotificationToDB(con, n1);
			Employee inspector=mysqlConnection.getInspector(con);
			Employee admin=mysqlConnection.getAdmin(con);
			Employee phase_administrator=mysqlConnection.getPhaseAdministrator(con,rp.getId(),rp.getPhase(),rp.getRepetion());
			mysqlConnection.insertNotificationForUserToDB(con, n1, inspector);
			mysqlConnection.insertNotificationForUserToDB(con, n1, admin);
			mysqlConnection.insertNotificationForUserToDB(con, n1, phase_administrator);
		}
		ArrayList<RequestPhase> RPlist1=mysqlConnection.findWhatNeedsDocument(con,today);
		for(RequestPhase rp : RPlist1) {
			Notification n2=new Notification("Request#"+rp.getId()+": phase "+rp.getPhase()+" with repetion "+rp.getRepetion()+" is over with an exception, needs documentation.",today,"Exception document");
			Employee inspector=mysqlConnection.getInspector(con); 
			n2=mysqlConnection.insertNotificationToDB(con, n2);
			mysqlConnection.insertNotificationForUserToDB(con, n2, inspector);
		}
		ArrayList<RequestPhase> RPlist2=mysqlConnection.whoNeedsReminding(con,today);
		for(RequestPhase rp: RPlist2) {
			Notification n2=new Notification("reminder for request#"+rp.getId()+" phase: "+rp.getPhase()+"!. ",today,"reminder to finish work");
			Employee reciever=mysqlConnection.getPhaseAdministrator(con, rp.getId(), rp.getPhase(), rp.getRepetion());
			n2=mysqlConnection.insertNotificationToDB(con, n2);
			mysqlConnection.insertNotificationForUserToDB(con, n2, reciever);
		}
		Map<Integer,String> requestMap=mysqlConnection.getMap(con);
		ObservableServer server=(ObservableServer)arg0;
		Object[] msg= {"requests map",requestMap};
		server.sendToAllClients(msg);	
	}
}
