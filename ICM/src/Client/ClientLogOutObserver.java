package Client;

import java.util.Observable;
import java.util.Observer;
/**
 * in this observer we print on the console that the client logged out
 *
 */
public class ClientLogOutObserver implements Observer {
	public ClientLogOutObserver(Observable client) {
		client.addObserver(this);
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		if (arg1 instanceof String[]) {
			String[] send = (String[]) arg1;
			if (send[0] instanceof String) {
				String message = (String) send[0];
				if (message.equals("LogOut")) {
					if (send[1] instanceof String) {
						String result = (String) send[1];
						if (result.equals("true")) {
							System.out.print("LogOut Client established");
						}

						else if (result.equals("false"))
							System.out.print("LogOut Client failed");
						else if (result.equals("true1")) {
							System.out.print("LogOut Client established And Exit");
						}

					}

				}

			}
		}
	}
}
