package Boundary;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils.Collections;

import Entity.Employee;
import Entity.ExtensionDuration;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ReportController implements Initializable {
	@FXML
	ComboBox<String> combo3;
	@FXML
	BarChart<String,Long> Bd;
	@FXML
	CategoryAxis x3;
	@FXML
	NumberAxis y3;
	@FXML
	Label medianL3;
	@FXML
	Label stdL3;
	@FXML
	Label medianL2;
	@FXML
	Label stdL2;
	@FXML
	TableView<ExtensionDuration> tableE;
	@FXML
	TableColumn<ExtensionDuration,Integer> idCol;
	@FXML
	TableColumn<ExtensionDuration,Long> durCol;
	@FXML
	ComboBox<String> combo2;
	@FXML
	Label medianL;
	@FXML
	Label stdL;
	@FXML
	RadioButton rdDays;
	@FXML
	RadioButton rdMonths;
	@FXML
	RadioButton rdYears;
	@FXML
	ComboBox<String> combo;
	@FXML
	BarChart<String,Long> BC;
	@FXML
	CategoryAxis x;
	@FXML
	NumberAxis y;
	@FXML
	DatePicker dpFrom;
	@FXML
	DatePicker dpTo;
	ObservableList<ExtensionDuration> Elist;
	ObservableList<String> list3=FXCollections.observableArrayList("No.Delays","Delays Durations");

	ObservableList<String> list2=FXCollections.observableArrayList("Extension durations","repetion durations");

	ObservableList<String> list=FXCollections.observableArrayList("No. Active Requests","No. Frozen Requests","No. Closed Requests","No. Rejected Requests","No. days of Treatments");
	@FXML
	public static ReportController ctrl;
	@FXML
	public static SplitPane splitpane;
	public static Stage primaryStage;
	private AnchorPane lowerAnchorPane;

	public void start(SplitPane splitpane)  {
		primaryStage = LoginController.primaryStage;
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/Boundary/reports.fxml"));
			lowerAnchorPane = loader.load();
			ctrl = loader.getController();
			splitpane.getItems().set(1, lowerAnchorPane);
			this.splitpane = splitpane;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		combo.setItems(list);
		combo2.setItems(list2);
		combo3.setItems(list3);
		
	}
	public void generatePReportAction() {
		if(!rdDays.isSelected() && !rdMonths.isSelected() && !rdYears.isSelected()) {
			Alert alert = new Alert(AlertType.ERROR);
	        alert.setTitle("TEST");
	        alert.setHeaderText("error");
	        alert.setContentText("You have to choose distribution category!");
	        alert.showAndWait();
	        return;
		}
		if(dpFrom.getValue()==null || dpTo.getValue()==null) {
			Alert alert = new Alert(AlertType.ERROR);
	        alert.setTitle("TEST");
	        alert.setHeaderText("error");
	        alert.setContentText("You have to choose a specific period!");
	        alert.showAndWait();
	        return;
		}
		if(combo.getSelectionModel().getSelectedItem()==null) {
			Alert alert = new Alert(AlertType.ERROR);
	        alert.setTitle("TEST");
	        alert.setHeaderText("error");
	        alert.setContentText("You have to choose distribution category!");
	        alert.showAndWait();
	        return;
		}
		long millis=System.currentTimeMillis();
		Date today=new java.sql.Date(millis);
		if(dpFrom.getValue().isAfter(today.toLocalDate()) || dpTo.getValue().isAfter(today.toLocalDate())) {
			Alert alert = new Alert(AlertType.ERROR);
	        alert.setTitle("TEST");
	        alert.setHeaderText("error");
	        alert.setContentText("Please enter Valid dates!");
	        alert.showAndWait();
	        return;
		}
		if(dpTo.getValue().isBefore(dpFrom.getValue())) {
			Alert alert = new Alert(AlertType.ERROR);
	        alert.setTitle("TEST");
	        alert.setHeaderText("error");
	        alert.setContentText("Please enter Valid period!");
	        alert.showAndWait();
	        return;
		}
		String keymessage = null;
		if(rdDays.isSelected()) {
			keymessage="Pdays";
		}
		if(rdMonths.isSelected()) {
			keymessage="Pmonths";
		}
		if(rdYears.isSelected()) {
			keymessage="Pyears";
		}
		String Rtype=combo.getSelectionModel().getSelectedItem();
		Object[] msg= {keymessage,dpFrom.getValue(),dpTo.getValue(),Rtype};
		try {
			LoginController.cc.getClient().sendToServer(msg);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	@FXML
	public void generatePeriodricReport1(ActionEvent e) {
		if(!rdDays.isSelected() && !rdMonths.isSelected() && !rdYears.isSelected()) {
			Alert alert = new Alert(AlertType.ERROR);
	        alert.setTitle("TEST");
	        alert.setHeaderText("error");
	        alert.setContentText("You have to choose distribution category!");
	        alert.showAndWait();
	        return;
		}
		if(dpFrom.getValue()==null || dpTo.getValue()==null) {
			Alert alert = new Alert(AlertType.ERROR);
	        alert.setTitle("TEST");
	        alert.setHeaderText("error");
	        alert.setContentText("You have to choose a specific period!");
	        alert.showAndWait();
	        return;
		}
		if(combo.getSelectionModel().getSelectedItem()==null) {
			Alert alert = new Alert(AlertType.ERROR);
	        alert.setTitle("TEST");
	        alert.setHeaderText("error");
	        alert.setContentText("You have to choose distribution category!");
	        alert.showAndWait();
	        return;
		}
		long millis=System.currentTimeMillis();
		Date today=new java.sql.Date(millis);
		if(dpFrom.getValue().isAfter(today.toLocalDate()) || dpTo.getValue().isAfter(today.toLocalDate())) {
			Alert alert = new Alert(AlertType.ERROR);
	        alert.setTitle("TEST");
	        alert.setHeaderText("error");
	        alert.setContentText("Please enter Valid dates!");
	        alert.showAndWait();
	        return;
		}
		if(dpTo.getValue().isBefore(dpFrom.getValue())) {
			Alert alert = new Alert(AlertType.ERROR);
	        alert.setTitle("TEST");
	        alert.setHeaderText("error");
	        alert.setContentText("Please enter Valid period!");
	        alert.showAndWait();
	        return;
		}
		String keymessage = null;
		if(rdDays.isSelected()) {
			keymessage="Pdays";
		}
		if(rdMonths.isSelected()) {
			keymessage="Pmonths";
		}
		if(rdYears.isSelected()) {
			keymessage="Pyears";
		}
			long period=dpTo.getValue().toEpochDay()-dpFrom.getValue().toEpochDay();
			String Rtype=combo.getSelectionModel().getSelectedItem();
			Object[] msg= {keymessage,dpFrom.getValue(),dpTo.getValue(),Rtype};
			try {
				LoginController.cc.getClient().sendToServer(msg);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	}

	public void buildGraph(ArrayList<Long> arr, Date from, Date to, String per, String rtype) {
		if(arr==null) {
			Alert alert = new Alert(AlertType.ERROR);
	        alert.setTitle("TEST");
	        alert.setHeaderText("error");
	        alert.setContentText("There is no Data to be shown!");
	        alert.showAndWait();
	        return;
		}
		XYChart.Series<String, Long> set1=new XYChart.Series<>();
		if(rtype.contains("No. days of Treatments")) {
			String numberOnly=rtype.replaceAll("[^0-9]", "");
			int val=Integer.valueOf(numberOnly);
			int n=arr.size();
			int i=1;
			for(int j=0;j<n;j++) {
			set1.getData().add(new XYChart.Data<String, Long>(String.valueOf(i)+"-"+String.valueOf(i+val-1), arr.get(j)));
			i=i+val;
		}
		}
		else if(per.equals("Pdays")) {
			int n=(int) (to.toLocalDate().toEpochDay()-from.toLocalDate().toEpochDay())+1;
			Date date=from;
			for(int i=0;i<n;i++) {
				set1.getData().add(new XYChart.Data<String,Long>(date.toString(),arr.get(i)));
				date=Date.valueOf(date.toLocalDate().plusDays(1));
			}
		}
		else if(per.equals("Pmonths")) {
			int n=arr.size();
			Date date=from;
			LocalDate ldate=from.toLocalDate();
			for(int i=0;i<n;i++) {
				set1.getData().add(new XYChart.Data<>(ldate.getMonth().toString(),arr.get(i)));
				ldate=ldate.plusMonths(1);
			}
		}
		else if(per.equals("Pyears")) {
			int n=arr.size();
			LocalDate ldate=from.toLocalDate();
			for(int i=0;i<n;i++) {
				set1.getData().add(new XYChart.Data<>(String.valueOf(ldate.getYear()),arr.get(i)));
				ldate=ldate.plusYears(1);
			}
		}
		long median=calculateMedian(arr);
		float std=calculateStd(arr,median);
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				BC.setTitle(rtype);
				x.setLabel(per.substring(1, per.length()));
				BC.getData().clear();
				BC.getData().add(set1);
				medianL.setText("Median: "+median);
				stdL.setText("Std: "+std);
			}
			
		});
	}

	private float calculateStd(ArrayList<Long> arr, long median) {
		float segma=0;
		for(long a : arr) {
			segma+=Math.pow((a-median), 2);
		}
		float rebo3=segma/arr.size();
		float std=(float) Math.pow(rebo3, 0.5);
		return std;
	}

	private Long calculateMedian(ArrayList<Long> arr) {
		Long median;
		java.util.Collections.sort(arr);
		if(arr.size()%2==1) {
			median=arr.get(arr.size()/2);
		}
		else {
			median=(arr.get(arr.size()/2)+arr.get(arr.size()/2-1))/2;
		}
		return median;
	}
	public void generatePerformanceReport() {
		if(combo2.getSelectionModel().getSelectedItem()==null) {
			Alert alert = new Alert(AlertType.ERROR);
	        alert.setTitle("TEST");
	        alert.setHeaderText("error");
	        alert.setContentText("You have to choose the type of the report!");
	        alert.showAndWait();
	        return;
		}
		String chosen=combo2.getSelectionModel().getSelectedItem();
		Object[] msg= {chosen};
		try {
			LoginController.cc.getClient().sendToServer(msg);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void getPerformanceData(ArrayList<ExtensionDuration> arr, String keymessage) {
		if(arr==null) {
			Alert alert = new Alert(AlertType.ERROR);
	        alert.setTitle("TEST");
	        alert.setHeaderText("error");
	        alert.setContentText("There is no Data to be shown!");
	        alert.showAndWait();
	        return;
		}
		Elist=FXCollections.observableArrayList(arr);
		idCol.setCellValueFactory(new PropertyValueFactory<ExtensionDuration, Integer>("request_id"));
		durCol.setCellValueFactory(new PropertyValueFactory<ExtensionDuration, Long>("duration"));
		tableE.setItems(Elist);
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				durCol.setText(keymessage);
				ArrayList<Long> list=new ArrayList<>();
				for(ExtensionDuration ed : arr) {
					list.add(ed.getDuration());
				}
				long median=calculateMedian(list);
				medianL2.setText("Median: "+median);
				float std=calculateStd(list, median);
				stdL2.setText("Std: "+std);
			}
			
		});
		
		

	}
	public void generateDelaysReport() {
		System.out.println("sss");
		if(combo3.getSelectionModel().getSelectedItem()==null) {
			Alert alert = new Alert(AlertType.ERROR);
	        alert.setTitle("TEST");
	        alert.setHeaderText("error");
	        alert.setContentText("You have to choose the type of the report!");
	        alert.showAndWait();
	        return;
		}
		String chosen=combo3.getSelectionModel().getSelectedItem();
		Object[] msg= {chosen};
		try {
			LoginController.cc.getClient().sendToServer(msg);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void buildDelayGraph(String keymessage, ArrayList<Long> arr) {
		if(arr==null) {
			Alert alert = new Alert(AlertType.ERROR);
	        alert.setTitle("TEST");
	        alert.setHeaderText("error");
	        alert.setContentText("There is no Data to be shown!");
	        alert.showAndWait();
	        return;
		}
		XYChart.Series<String, Long> set3=new XYChart.Series<>();
		set3.getData().add(new XYChart.Data<>("Moodle",arr.get(0)));
		set3.getData().add(new XYChart.Data<>("Student information system",arr.get(1)));
		set3.getData().add(new XYChart.Data<>("Lecturer information system",arr.get(2)));
		set3.getData().add(new XYChart.Data<>("Employee information system",arr.get(3)));
		set3.getData().add(new XYChart.Data<>("Library system",arr.get(4)));
		set3.getData().add(new XYChart.Data<>("Computers in the classroom",arr.get(5)));
		set3.getData().add(new XYChart.Data<>("Labs and computer farms",arr.get(6)));
		set3.getData().add(new XYChart.Data<>("College official site",arr.get(7)));
		long median=calculateMedian(arr);
		float std=calculateStd(arr, median);
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				medianL3.setText("Median: "+median);
				stdL3.setText("Std: "+std);
				Bd.setTitle(keymessage);
				x3.setLabel("Information Systems");
				y3.setLabel(keymessage);
				Bd.getData().clear();
				Bd.getData().add(set3);
				
				
				
			}
			
		});

		
		
		
		
	}

}