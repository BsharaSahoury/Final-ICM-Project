package Server;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import DBconnection.mysqlConnection;
import Entity.Employee;
import Entity.Notification;
import Entity.Phase;
import javafx.collections.ObservableList;
import ocsf.server.ConnectionToClient;

public class ServerManualRecruitObserver implements Observer {
	public ServerManualRecruitObserver(Observable server) {
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
					if(keymessage.equals("manualEvaluator")) {
						String fullname=(String)arg3[1];
						int id=(int)arg3[2];
						Connection con=mysqlConnection.makeAndReturnConnection();
						Employee evaluator=mysqlConnection.getSpecificEmployee(con,fullname);
						boolean flag=mysqlConnection.assignEmployeeToPhaseRequest(con, evaluator, id,"evaluation");
						if(flag) {
							Object[] message= {"evaluatorRecruit"};
							try {
								client.sendToClient(message);
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							long millis=System.currentTimeMillis();
							Notification n1=new Notification(
									"You've been recruited to evaluate request#"+id,
									new java.sql.Date(millis),
									"recruitNotificationForEvaluator");
							n1=mysqlConnection.insertNotificationToDB(con, n1);
							mysqlConnection.insertNotificationForUserToDB(con, n1,evaluator);
						}	
						else {
							Object[] message= {"isRecruited"};
							try {
								client.sendToClient(message);
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
					else if(keymessage.equals("manualPerformer")) {
						String fullname=(String)arg3[1];
						int id=(int)arg3[2];
						Connection con=mysqlConnection.makeAndReturnConnection();
						Employee performer=mysqlConnection.getSpecificEmployee(con,fullname);
						boolean flag=mysqlConnection.assignEmployeeToPhaseRequest(con, performer, id,"performance");
						if(flag) {
							mysqlConnection.updateCurrentPhase(con, id, Phase.performance);
							Object[] message= {"performerRecruit"};
							try {
								client.sendToClient(message);
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							long millis=System.currentTimeMillis();
							Notification n1=new Notification(
									"You've been recruited to Lead performance phase for request#"+id,
									new java.sql.Date(millis),
									"recruitNotificationForPerformance");
							n1=mysqlConnection.insertNotificationToDB(con, n1);
							mysqlConnection.insertNotificationForUserToDB(con, n1,performer);
						}
						else {
							Object[] message= {"isRecruited"};
							try {
								client.sendToClient(message);
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						
					}
					else if(keymessage.equals("manualEvaluatorAgain")) {
						String fullname=(String)arg3[1];
						int id=(int)arg3[2];
						Connection con=mysqlConnection.makeAndReturnConnection();
						Employee evaluator=mysqlConnection.getSpecificEmployee(con,fullname);
						boolean flag=mysqlConnection.assignEmployeeToPhaseRequest(con, evaluator, id,"evaluation");
						if(flag) {
							Object[] message= {"evaluatorRecruitAgain"};
							try {
								client.sendToClient(message);
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							long millis=System.currentTimeMillis();
							Notification n1=new Notification(
									"You've been recruited to evaluate request#"+id,
									new java.sql.Date(millis),
									"recruitNotificationForEvaluator");
							n1=mysqlConnection.insertNotificationToDB(con, n1);
							mysqlConnection.insertNotificationForUserToDB(con, n1,evaluator);
						}
						else {
							Object[] message= {"isRecruited"};
							try {
								client.sendToClient(message);
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						
					}
					else if(keymessage.equals("manualTester") ) {
						String fullname=(String)arg3[1];
						int id=(int)arg3[2];
						Connection con=mysqlConnection.makeAndReturnConnection();
						Employee tester=mysqlConnection.getSpecificEmployee(con,fullname);
						boolean flag=mysqlConnection.assignEmployeeToPhaseRequest(con, tester, id,"testing");
						if(flag) {
							mysqlConnection.updateCurrentPhase(con, id, Phase.testing);
							Object[] message= {"testerRecruit"};
							try {
								client.sendToClient(message);
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							long millis=System.currentTimeMillis();
							Notification n1=new Notification(
									"You've been recruited to test request#"+id,
									new java.sql.Date(millis),
									"recruitNotificationForTester");
							n1=mysqlConnection.insertNotificationToDB(con, n1);
							mysqlConnection.insertNotificationForUserToDB(con, n1,tester);
						}
						else {
							Object[] message= {"isRecruited"};
							try {
								client.sendToClient(message);
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
					else if(keymessage.equals("manualRequestTreatmentRecruitEvaluator")) {
						String fullname=(String)arg3[1];				
						int id=(int)arg3[2];
						String phase=(String)arg3[3];
						int repetion=(int)arg3[4];
						LocalDate start=(LocalDate)arg3[5];
						LocalDate due=(LocalDate)arg3[6];
						String explain=(String)arg3[7];
						Connection con=mysqlConnection.makeAndReturnConnection();
						Employee employee=null;
						if(fullname!=null)
						employee=mysqlConnection.getSpecificEmployee(con,fullname);
						Employee Inspector=mysqlConnection.getInspector(con);
						if(fullname!=null)
						mysqlConnection.assignorChangeEmployee(con, employee.getUsername(), repetion, id, phase,start,due);
						else
						mysqlConnection.assignorChangeEmployee(con, null, repetion, id, phase,start,due);
						mysqlConnection.EnterUpdateToDBUpdateTable(con, Inspector,id,explain);
						if(phase.equals("evaluation"))
						mysqlConnection.updateCurrentPhase(con, id, Phase.evaluation);
						Object[] message= {"manualRequestTreatmentRecruitEvaluator"};
						try {
							client.sendToClient(message);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						if(fullname!=null) {
						long millis=System.currentTimeMillis();
						Notification n1=new Notification(
								"You've been recruited to evaluate request#"+id,
								new java.sql.Date(millis),
								"recruitNotificationForEvaluator");
						n1=mysqlConnection.insertNotificationToDB(con, n1);
						mysqlConnection.insertNotificationForUserToDB(con, n1,employee);
						}
					}
					else if(keymessage.equals("manualRequestTreatmentRecruitPerformer")) {
						String fullname=(String)arg3[1];				
						int id=(int)arg3[2];
						String phase=(String)arg3[3];
						int repetion=(int)arg3[4];
						LocalDate start=(LocalDate)arg3[5];
						LocalDate due=(LocalDate)arg3[6];
						String explain=(String)arg3[7];
						ArrayList<String> selected=null;
						if(arg3[8] instanceof ArrayList<?>) {
							selected=(ArrayList<String>)arg3[8];
						}	
						String lastadmin=(String)arg3[9];
						Connection con=mysqlConnection.makeAndReturnConnection();
						Employee employee=mysqlConnection.getSpecificEmployee(con,fullname);
						Employee employee2=null;
						if(lastadmin!=null) {
						employee2=mysqlConnection.getSpecificEmployee(con,lastadmin);
						}
						Employee Inspector=mysqlConnection.getInspector(con);
						mysqlConnection.assignorChangeEmployee(con, employee.getUsername(), repetion, id, phase,start,due);
						mysqlConnection.EnterUpdateToDBUpdateTable(con, Inspector,id,explain);
                        if(selected.size()>0)
                        	mysqlConnection.EnterrequestonengineerrequestDB(con, selected, repetion, id);
                        Object[] message= {"manualRequestTreatmentRecruitEvaluator"};
						try {
							client.sendToClient(message);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						long millis=System.currentTimeMillis();
						Notification n1=new Notification(
								"You've been recruited to Lead performance phase for request#"+id,
								new java.sql.Date(millis),
								"recruitNotificationForPerformance");
						n1=mysqlConnection.insertNotificationToDB(con, n1);
						mysqlConnection.insertNotificationForUserToDB(con, n1,employee);
						for(int i=0;i<selected.size();i++) {
							Employee eng=mysqlConnection.getSpecificEmployee(con,selected.get(i));
							Notification n2=new Notification(
									"You've been recruited to performe request#"+id,
									new java.sql.Date(millis),
									"recruitNotificationForPerformEngineer");
							n2=mysqlConnection.insertNotificationToDB(con, n2);
							mysqlConnection.insertNotificationForUserToDB(con, n2,eng);
						}
						if(lastadmin!=null) {
						if(employee2.getJob().equals("engineer")) {
						Notification n3=new Notification(
								"You have been replaced in job performance leader for request#"+id,
								new java.sql.Date(millis),
								"ReplaceNotificationforlastperformanceadmin");
						n3=mysqlConnection.insertNotificationToDB(con, n3);
						mysqlConnection.insertNotificationForUserToDB(con, n3,employee2);
						}
						else if(employee2.getJob().equals("evaluator")) {
						Notification n3=new Notification(
								"You have been replaced in job evaluator leader for request#"+id,
								new java.sql.Date(millis),
								"ReplaceNotificationforlastperformanceadmin");
						n3=mysqlConnection.insertNotificationToDB(con, n3);
						mysqlConnection.insertNotificationForUserToDB(con, n3,employee2);
						}
						}
					}
				}
			}
		}
		
	}

}
