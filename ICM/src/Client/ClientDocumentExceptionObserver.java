package Client;

import java.util.Observable;
import java.util.Observer;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class ClientDocumentExceptionObserver implements Observer {
	public ClientDocumentExceptionObserver(Observable client) {
		client.addObserver(this);
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		if(arg1 instanceof Object[]) {
			Object[] arg2=(Object[])arg1;
			if(arg2[0] instanceof String) {
				String keymessage=(String)arg2[0];
				if(keymessage.equals("document")) {
					Platform.runLater(new Runnable() {

						@Override
						public void run() {
							Alert alert = new Alert(AlertType.INFORMATION);
					        alert.setTitle("Alert Title");
					        alert.setHeaderText("success");
					        alert.setContentText("The exception is documented successfully");
					        alert.showAndWait();
							
						}
						
					});
				}
			}
		}
		
	}

}
