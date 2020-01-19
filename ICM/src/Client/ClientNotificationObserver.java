package Client;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import Boundary.NotificationsController;
import Entity.Notification;
/**
 * in this observer we handle to show the notifications that the user received 
 * and he can open the notification to see the details
 *
 */
public class ClientNotificationObserver implements Observer {
	public ClientNotificationObserver(Observable client) {
		client.addObserver(this);
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		if (arg1 instanceof Object[]) {
			Object[] arg2 = (Object[]) arg1;
			if (arg2[0] instanceof String) {
				String keymessage = (String) arg2[0];
				if (keymessage.equals("getNotific")) {
					if (arg2[1] instanceof ArrayList<?>) {
						ArrayList<Notification> Nlist = (ArrayList<Notification>) arg2[1];
						NotificationsController.ctrl.insertNotificToTable(Nlist);
					}

				}
			}
		}

	}

}
