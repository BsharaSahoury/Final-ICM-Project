package Boundary;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;

import Entity.ExtensionDuration;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;


public class ReportsActiveController {
	private Date startdate;
	private Date duedate;
	private String selected;
	private String Rtype;
	private ArrayList<Long> arr;
	public ReportsActiveController(Date startdate,Date duedate,String selected,String Rtype,ArrayList<Long> arr)
	{
		this.startdate=startdate;
		this.duedate=duedate;
		this.selected=selected;
		this.Rtype=Rtype;
	}
	public void generatePReportAction() {
		if (!selected.equals("day") && !selected.equals("month")&& !selected.equals("year")) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("TEST");
			alert.setHeaderText("error");
			alert.setContentText("You have to choose distribution category!");
			alert.showAndWait();
			return;
		}
		if (startdate == null || duedate== null) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("TEST");
			alert.setHeaderText("error");
			alert.setContentText("You have to choose a specific period!");
			alert.showAndWait();
			return;
		}
		if (Rtype == null) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("TEST");
			alert.setHeaderText("error");
			alert.setContentText("You have to choose distribution category!");
			alert.showAndWait();
			return;
		}
		long millis = System.currentTimeMillis();
		Date today = new java.sql.Date(millis);
		if (startdate.after(today) || duedate.after(today)) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("TEST");
			alert.setHeaderText("error");
			alert.setContentText("Please enter Valid dates!");
			alert.showAndWait();
			return;
		}
		if (duedate.before(startdate)) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("TEST");
			alert.setHeaderText("error");
			alert.setContentText("Please enter Valid period!");
			alert.showAndWait();
			return;
		}
		String keymessage = null;
		if (selected.equals("day")) {
			keymessage = "Pdays";
		}
		if (selected.equals("month")) {
			keymessage = "Pmonths";
		}
		if (selected.equals("year")) {
			keymessage = "Pyears";
		}
		Object[] msg = { keymessage, startdate, duedate, Rtype };
		try {
			LoginController.cc.getClient().sendToServer(msg);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	private float calculateStd(ArrayList<Long> arr, long median) {
		float segma = 0;
		for (long a : arr) {
			segma += Math.pow((a - median), 2);
		}
		float rebo3 = segma / arr.size();
		float std = (float) Math.pow(rebo3, 0.5);
		return std;
	}

	private Long calculateMedian(ArrayList<Long> arr) {
		Long median;
		java.util.Collections.sort(arr);
		if (arr.size() % 2 == 1) {
			median = arr.get(arr.size() / 2);
		} else {
			median = (arr.get(arr.size() / 2) + arr.get(arr.size() / 2 - 1)) / 2;
		}
		return median;
	}
}
