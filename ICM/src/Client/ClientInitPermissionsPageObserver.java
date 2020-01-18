package Client;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import Boundary.PermissionsController;
import Entity.Employee;
import javafx.scene.control.cell.PropertyValueFactory;

public class ClientInitPermissionsPageObserver implements Observer {
	public ClientInitPermissionsPageObserver(Observable client) {
		client.addObserver(this);
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		if(arg1 instanceof Object[]) {
			Object[] arg2=(Object[])arg1;
			if(arg2[0] instanceof String) {
				String keymessage=(String)arg2[0];
				if(keymessage.equals("employees&permissions")) {
					ArrayList<Employee> engineers=(ArrayList<Employee>)arg2[1];
					PermissionsController.ctrl.recieveData(engineers);
					
				}
			}
		}
		
	}

}
