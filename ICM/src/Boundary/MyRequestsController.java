package Boundary;
import javafx.collections.FXCollections;  
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

import Client.ClientConsole;
import Entity.Employee;
import Entity.Phase;
import Entity.Request;
import Entity.RequestPhase;
import Entity.Student;
import Entity.User;
import javafx.fxml.*;
public class MyRequestsController implements Initializable  {
		public static Stage primaryStage;
		private static ClientConsole cc;
		private AnchorPane lowerAnchorPane;
		@FXML
		private TableColumn colID;
		@FXML
		private TableColumn colName;
		@FXML
		private TableColumn colStatus;
		@FXML
		private TableColumn colPriflig;
		@FXML
		private TableColumn colSubDate;
		@FXML
		private TableColumn colPhase;
		@FXML
		private TableView<Request> tableRequests;
		@FXML
		private TextField searchID;
		@FXML
		private Button searchbtn;
		@FXML
        private Button groupbyBtn;
		@FXML
		private Button TrackRequest;
		@FXML
		private Button refresh;
		@FXML 
		private Button question;
		@FXML
		private static SplitPane splitpane;
		@FXML
		private ComboBox Groupby;
		@FXML
		private TextField search_text;
		private static int chosen=-1;
		private static ArrayList<Request> arrofRequests;
		ObservableList<String> statuslist = FXCollections.observableArrayList("Active","Frozen","Closed","All");
		private static ObservableList<Request> list;
		private FXMLLoader loader;
		private static String job;
		private static User user;
		private static int chosengroupbytype=-1;
		public void start(SplitPane splitpane,User user,String job)  {
			this.splitpane=splitpane;
			this.job=job;
			this.user=user;
			primaryStage=LoginController.primaryStage;
			String[] myRequests=new String[3];
			this.cc=LoginController.cc;
			try{					
				loader = new FXMLLoader(getClass().getResource("/Boundary/MyRequests.fxml"));
				lowerAnchorPane = loader.load();
				splitpane.getItems().set(1, lowerAnchorPane);		
				myRequests[0]="my Requests";
				myRequests[1]=user.getUsername();
				myRequests[2]=job;
				cc.getClient().sendToServer(myRequests);
			} catch(Exception e) {
				e.printStackTrace();
			}	
		}
		public void setTableRequests(ArrayList<Request> arr1){
			if(!arr1.equals(null)) {
			list=FXCollections.observableArrayList(arr1);				
			//tableRequests.setStyle("-fx-alignment: CENTER;");
	       // colName.set
			tableRequests.setItems(list);
			}
		}
		public void fillTable(ArrayList<Request> arr1) {
		arrofRequests=arr1;	
		loader.<MyRequestsController>getController().setTableRequests(arr1);		
		}
		public void searchaction() {
			if(!search_text.getText().equals("")) {
				try {
			    int searchid=Integer.valueOf(search_text.getText());
			long x=tableRequests.getItems().stream().filter(item -> item.getId()==searchid)
					.count();
					if(x>0) {
				tableRequests.getItems().stream().filter(item -> item.getId()==searchid)
				.findAny()
				   .ifPresent(item -> {
					   tableRequests.getSelectionModel().select(item);
					   tableRequests.scrollTo(item);
				    });
					}
			else {
				Alert alert = new Alert(AlertType.WARNING);
		        alert.setTitle("Warning");
		        alert.setHeaderText("Not exist");
		        alert.setContentText("The ID doesn't exist");
		        alert.showAndWait();
			}
			}catch(NumberFormatException e) {
				Alert alert = new Alert(AlertType.WARNING);
		        alert.setTitle("Warning");
		        alert.setContentText("Enter ID number in search field");
		        alert.showAndWait();	
			}
			}
			else {
				Alert alert = new Alert(AlertType.WARNING);
		        alert.setTitle("Warning");
		        alert.setHeaderText("Enter ID");
		        alert.setContentText("Please Enter ID to the search field");
		        alert.showAndWait();
			}
		}
		public void TrackRequestAction() {
			chosen=tableRequests.getSelectionModel().getSelectedIndex();
			if(chosen!=-1) {
				Request s =tableRequests.getSelectionModel().getSelectedItem();
				if(ClientConsole.map.get(s.getId()).equals("frozen")) {
					ClientConsole.displayFreezeError();
					return;
				}
				RequestTrackController requestTrack = new RequestTrackController();
				requestTrack.start(splitpane,s,job);
			}
			else {
		        Alert alertWarning = new Alert(AlertType.WARNING);
		        alertWarning.setTitle("Warning Alert Title");
		        alertWarning.setHeaderText("Warning!");
		        alertWarning.setContentText("please choose requset");
		        alertWarning.showAndWait();
		        }
		}
		public static int getselectedindex() {
			return chosen;
		}
		
		public void GroupbyAction(ActionEvent e) {
			chosengroupbytype=Groupby.getSelectionModel().getSelectedIndex();
			String groupbystatus=null;
			ArrayList<Request> arr=new ArrayList<Request>();
			if(chosengroupbytype!=-1) {
				if(chosengroupbytype==0)
					groupbystatus="active";
				else if(chosengroupbytype==1)
					groupbystatus="frozen";
				else if(chosengroupbytype==2)
					groupbystatus="closed";
				else if(chosengroupbytype==3)
					groupbystatus="All";
				if(groupbystatus.equals("All")) {
					arr=arrofRequests;
				}else {
					for(int i=0;i<arrofRequests.size();i++) 
						if((arrofRequests.get(i)).getStatus().equals(groupbystatus))
							arr.add(arrofRequests.get(i));	
				}
				if(!arr.equals(null)) {
					switch(job) {
					case "Inspector":
						InspectorHomeController.MyRequests.loader.<MyRequestsController>getController().tableRequests.setItems(FXCollections.observableArrayList(arr));
						break;
					case "Evaluator":
						EvaluatorHomeController.MyRequests.loader.<MyRequestsController>getController().tableRequests.setItems(FXCollections.observableArrayList(arr));
						break;
					case "Comittee Member":
						ComitteeMemberHomeController.MyRequests.loader.<MyRequestsController>getController().tableRequests.setItems(FXCollections.observableArrayList(arr));
						break;
					case "Chairman":
						ChairmanHomeController.MyRequests.loader.<MyRequestsController>getController().tableRequests.setItems(FXCollections.observableArrayList(arr));
						break;
					case "Lecturer":
						LecturerHomeController.MyRequests.loader.<MyRequestsController>getController().tableRequests.setItems(FXCollections.observableArrayList(arr));
						break;
					case "Tester":
						TesterHomeController.MyRequests.loader.<MyRequestsController>getController().tableRequests.setItems(FXCollections.observableArrayList(arr));
						break;
					case "Administrator":
						AdministratorHomeController.MyRequests.loader.<MyRequestsController>getController().tableRequests.setItems(FXCollections.observableArrayList(arr));
						break;
					case "Student":
						StudentHomeController.MyRequests.loader.<MyRequestsController>getController().tableRequests.setItems(FXCollections.observableArrayList(arr));
						break;
					}
				}	
		}
		}

	public void refresh() {
		if (user instanceof User) {
			if (user instanceof Employee) {
				Employee employee = (Employee) user;
				{
					switch (employee.getJob()) {
					case "inspector":
						try {
							InspectorHomeController.MyRequests.start(splitpane, user, job);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						break;
					case "evaluator":
						try {
							EvaluatorHomeController.MyRequests.start(splitpane, user, job);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						break;

					case "comittee member":

						try {
							ComitteeMemberHomeController.MyRequests.start(splitpane, user, job);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						break;
					case "chairman":
						try {
							ChairmanHomeController.MyRequests.start(splitpane, user, job);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						break;
					case "performer":
						try {
							PerformanceLeaderHomeController.MyRequests.start(splitpane, user, job);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						break;
					case "lecturer":
						try {
							LecturerHomeController.MyRequests.start(splitpane, user, job);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						break;
					case "administrator":
						try {
							AdministratorHomeController.MyRequests.start(splitpane, user, job);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						break;
					}
				}

			} else if (user instanceof Student) {
				Student student1 = (Student) user;
				StudentHomeController student = new StudentHomeController();
				student.start(student1);
			}
		}
	}
	public void instructionsAction(){
		
	}
		
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		    Groupby.setItems(statuslist);
			colID.setCellValueFactory(new PropertyValueFactory<Request,Integer>("id"));
			colName.setCellValueFactory(new PropertyValueFactory<Request,String>("initiatorName"));
			colStatus.setCellValueFactory(new PropertyValueFactory<Request,String>("status"));		
			colPriflig.setCellValueFactory(new PropertyValueFactory<Request,String>("privilegedInfoSys"));
			colSubDate.setCellValueFactory(new PropertyValueFactory<Request,Date>("date"));
			//this.tabl=tableRequests;
			//ObservableList<Request> aa=FXCollections.observableArrayList();
			//aa.add(new Request(1,"sss","xxx","Qqqq",LocalDate.of(1915, Month.SEPTEMBER, 1)));
			//tableRequests.setItems(aa);
		}
	
}
