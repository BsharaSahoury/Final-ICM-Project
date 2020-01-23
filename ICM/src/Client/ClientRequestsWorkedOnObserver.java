package Client;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import Boundary.AdministratorHomeController;
import Boundary.AllRequestsController;
import Boundary.ChairmanHomeController;
import Boundary.ComitteeMemberHomeController;
import Boundary.EvaluatorHomeController;
import Boundary.InspectorHomeController;
import Boundary.LecturerHomeController;
import Boundary.NotificationsController;
import Boundary.PerformanceLeaderHomeController;
import Boundary.RequestsWorkedOnController;
import Boundary.StudentHomeController;
import Boundary.TesterHomeController;
import Entity.Request;
import Entity.RequestPhase;
import Entity.User;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
/**
 * in this observer we show the request that the user work on depends on his job
 *
 */
public class ClientRequestsWorkedOnObserver implements Observer {
	public ClientRequestsWorkedOnObserver(Observable client) {
		client.addObserver(this);
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		if (arg1 instanceof Object[]) {
			Object[] send = (Object[]) arg1;
			if (send[0] instanceof String) {
				String message = (String) send[0];
				if (message.equals("Requests worked on")) {
					if (send[1] instanceof ArrayList<?>) {
						ArrayList<RequestPhase> arr = (ArrayList<RequestPhase>) send[1];
						if (send[2] instanceof String) {
							String job = (String) send[2];
							if (!arr.equals(null)) {
								if (job.equals("Evaluator"))
									EvaluatorHomeController.RequestWorkON.fillTable(arr);
								else if (job.equals("Comittee Member"))
									ComitteeMemberHomeController.RequestWorkON.fillTable(arr);
								else if (job.equals("Performance Leader"))
									PerformanceLeaderHomeController.RequestWorkON.fillTable(arr);
								else if (job.equals("Tester"))
									TesterHomeController.RequestWorkON.fillTable(arr);
								else if (job.equals("Chairman"))
									ChairmanHomeController.RequestWorkON.fillTable(arr);
								else if (job.equals("Engineer"))
									PerformanceLeaderHomeController.RequestWorkON.fillTable(arr);
							}
						}
					}
				}
			}
		}
	}
}
