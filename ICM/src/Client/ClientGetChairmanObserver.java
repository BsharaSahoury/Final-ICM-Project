package Client;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import Boundary.ChairmanHomeController;
import Boundary.ComitteeMemberHomeController;
import Boundary.RequestsWorkedOnController;
import Entity.Employee;

public class ClientGetChairmanObserver implements Observer {
	public ClientGetChairmanObserver(Observable server) {
		server.addObserver(this);
	}

	@Override
	public void update(Observable o, Object arg) {

		if (arg instanceof Object[]) {
			Object[] arg1 = (Object[]) arg;
			if (arg1[0] instanceof String) {
				String keymessage = (String) arg1[0];
				if (keymessage.equals("get ChairMan")) {
					if (arg1[1] instanceof Employee) {
						Employee chairman = (Employee) arg1[1];
						ComitteeMemberHomeController.setChairman(chairman);
					}
				}
			}
		}

	}
}
