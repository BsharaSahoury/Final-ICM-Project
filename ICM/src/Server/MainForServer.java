
package Server;

import java.io.IOException;
import java.sql.Connection;

import Client.ClientObserver;
import DBconnection.mysqlConnection;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ocsf.server.ObservableServer;
 
public class MainForServer extends Application{
	public static Connection con;
	private static Stage ServerStage;
	private static ObservableServer sv;

	public static void main(String[] args) {
			launch(args);
}
	public static ObservableServer get_ObservableServer() {
			 return sv;
		 }
	public static Connection get_Connection() {
		 return con;
	 }
	public static void set_Connection(Connection con1) {
		  con=con1;
	 }	
	  public static void ConnectAfterDBPassword()
	  {
		    int port = 0; //Port to listen ons
		    try
		    {    
		      port = 5555;
		    }
		    catch(Throwable t)
		    {
		      port = 5555; //Set port to 5555
		    }
		    sv = new ObservableServer(port);
		    ServerObserver so=new ServerObserver(sv);
		    loginHandler loginHandler=new loginHandler(sv);
		    serverSubmissionObserver sso=new serverSubmissionObserver(sv);
		    ServerAllRequestsObserver serverallrequestobserver=new ServerAllRequestsObserver(sv);
		    ServerNotificationsObserver sno=new ServerNotificationsObserver(sv);
	        ServerMyRequestsObserver myrequest=new ServerMyRequestsObserver(sv);
	        ServerApproveDecsionCommitteeobserver approvedecision=new ServerApproveDecsionCommitteeobserver(sv);
	        ServerAutomaticRecruitObserver saro=new ServerAutomaticRecruitObserver(sv);
	        ServerGetEvaluatorsObserver sgeo=new ServerGetEvaluatorsObserver(sv);
	        ServerManualRecruitObserver smro=new ServerManualRecruitObserver(sv);
	        ServerGetChairManObserver chair=new ServerGetChairManObserver(sv);
	        ServerRequestInfoObserver requestInfo =new ServerRequestInfoObserver(sv);
	        RequestsWorkedOnObserver RequestWorkOn=new RequestsWorkedOnObserver(sv);
	        ServerRequestTrackObserver requestTrack= new ServerRequestTrackObserver(sv);
	        ServerSetDuratinObserver duration=new ServerSetDuratinObserver(sv);
	        ServerCommitteeDecisionObserver CommitteeDecision=new ServerCommitteeDecisionObserver(sv);
	        ServerChoosedPerformerObserver scpo= new ServerChoosedPerformerObserver(sv); 
	        ServerApprovePerformanceObserver sapo=new ServerApprovePerformanceObserver(sv);
	        ServerTestFailedObserver stfo=new ServerTestFailedObserver(sv);
	        ServerTestSuccessObserver stso=new ServerTestSuccessObserver(sv);
	        ServerNotificationdetailsObserver details=new ServerNotificationdetailsObserver(sv);
	        ServerCreateEvaluationReportObserver report=new ServerCreateEvaluationReportObserver(sv);
			ServerGetDurationObserver duratin = new ServerGetDurationObserver(sv);
			ServerDetectorObserver sdo=new ServerDetectorObserver(sv);
			ServerDocumentExceptionObserver sdeo=new ServerDocumentExceptionObserver(sv);
			ServerApproveDuratinObserver approveDuratin=new ServerApproveDuratinObserver(sv);
			ServerCheckAprproveDurationObserver check=new ServerCheckAprproveDurationObserver(sv);
			ServerGetInitiatorObserver init=new ServerGetInitiatorObserver(sv);
			ServeInitiatorApprovethedecisionObserver approve=new ServeInitiatorApprovethedecisionObserver(sv);
			ServerLogOutObserver logout=new ServerLogOutObserver(sv);
			ServerInspectorFrozeRequestObserver change=new ServerInspectorFrozeRequestObserver(sv);
			ServerGetEvaluationReportObserver evaluationreport=new ServerGetEvaluationReportObserver(sv);
		    ServerExtendRequestTimeObserver serto=new ServerExtendRequestTimeObserver(sv);
		    ServerRequestExtensionApproveToAdminObserver sreatao= new ServerRequestExtensionApproveToAdminObserver(sv);
		    ServerProfileSettingObserver spso=new ServerProfileSettingObserver(sv);
		    ServerApproveEvaluatorObserver saeo = new ServerApproveEvaluatorObserver(sv);
		    ServerInitPermissionsPageObserver sippo=new ServerInitPermissionsPageObserver(sv);
		    ServerChangePermissionObserver scpoo=new ServerChangePermissionObserver(sv);
		    ServerChangeEvaluatorObserver sceo=new ServerChangeEvaluatorObserver(sv);
		    ServerPeriodricReportObserver spro=new ServerPeriodricReportObserver(sv);
		    ServerPeriodricReportForDaysObserver sprrfdo=new ServerPeriodricReportForDaysObserver(sv);
		    ServerPerformanceReportObserver sproo=new ServerPerformanceReportObserver(sv);
		    ServerDelaysReportObserver sdro=new ServerDelaysReportObserver(sv);
		    ServerAdminActiveRequestObserver saaro = new ServerAdminActiveRequestObserver(sv);
		    ServerGetFullNameOfEmployeeObserver eeddd=new ServerGetFullNameOfEmployeeObserver(sv);
		    SeverGetExtensionDataObsaerver sgedo=new SeverGetExtensionDataObsaerver(sv);

			try {
			   sv.listen();
			   //ServerWindow.launchMain(sv, args);//we launch the server's window//
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} //Start listening for connections	  	
		  }	  
		@Override
		public void start(Stage primaryStage) throws Exception {
			// TODO Auto-generated method stub
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/Server/Server_Configure.fxml"));

		//		Parent root = FXMLLoader.load(getClass().getResource("/Server/Server_Configure.fxml"));
				Parent root = loader.load();
				ServerController controller = loader.getController();
				Scene scene = new Scene(root);
				primaryStage.setScene(scene);
				primaryStage.setResizable(false);		
				primaryStage.setTitle("ICM-Server");
				primaryStage.show();
				this.ServerStage=primaryStage;
				ServerStage.setOnHidden(e -> {
					 try {
						controller.shutdown();
						System.exit(1);
					  //  Platform.exit();
					} catch (IOException e1) {}
					 catch(NullPointerException e2) {}
					 
				});
		} catch(Exception e) {
			e.printStackTrace();
		} 
		}//start

}
