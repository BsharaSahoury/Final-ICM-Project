package Client;

import java.util.Observable;
import java.util.Observer;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
/**
 * in this observer we handle the situation when we want to save the administrator update in DB
 *
 */
public class ClientAdministratorActiveRequestObserver implements Observer {
	public ClientAdministratorActiveRequestObserver(Observable client) {
		client.addObserver(this);
	}
/**
 * in this function we notifies the administrator that his update has been saved or already saved in DB
 */
	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		if (arg instanceof Object[]) {
			Object[] arr = (Object[]) arg;
			if (arr[0] instanceof String) {
				String arg1 = (String) arr[0];
				if (arg1.equals("Admin changed status to Active")) {
					boolean state = (boolean) arr[1];
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							if (state) {
								Alert alertSuccess = new Alert(AlertType.INFORMATION);
								alertSuccess.setTitle("Success");
								alertSuccess.setHeaderText("Success");
								alertSuccess.setContentText("Your change saved Succesfully");
								alertSuccess.showAndWait();
							} else {
								Alert alertSuccess = new Alert(AlertType.WARNING);
								alertSuccess.setTitle("Warrning");
								alertSuccess.setContentText("you already changed the status");
								alertSuccess.showAndWait();
							}
						}
					});
				}
			}
		}
	}
}
