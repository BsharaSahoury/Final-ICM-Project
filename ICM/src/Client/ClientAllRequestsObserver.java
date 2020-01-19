package Client;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import Boundary.AdministratorHomeController;
import Boundary.AllRequestsController;
import Boundary.InspectorHomeController;
import Entity.Request;
import Entity.RequestPhase;
import Entity.User;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;

public class ClientAllRequestsObserver implements Observer {
	public ClientAllRequestsObserver(Observable client) {
		client.addObserver(this);
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		if (arg1 instanceof Object[]) {
			Object[] send = (Object[]) arg1;
			if (send[0] instanceof String) {
				String message = (String) send[0];
				if (message.equals("All Requests") && send[1] instanceof ArrayList<?>) {
					ArrayList<RequestPhase> arr = (ArrayList<RequestPhase>) send[1];
					if (send[2] instanceof String) {
						String job = (String) send[2];
						if (job.equals("Inspector"))
							InspectorHomeController.AllRequests.fillTable(arr);
						else if (job.equals("Administrator"))
							AdministratorHomeController.AllRequests.fillTable(arr);
					}
				}
			}
		}
	}
}
