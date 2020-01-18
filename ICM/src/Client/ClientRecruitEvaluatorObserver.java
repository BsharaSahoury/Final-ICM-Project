package Client;

import java.util.Observable;
import java.util.Observer;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class ClientRecruitEvaluatorObserver implements Observer{
	public ClientRecruitEvaluatorObserver(Observable client) {
		client.addObserver(this);
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		if(arg1 instanceof Object[]) {
			Object[] arg2=(Object[])arg1;
			if(arg2[0] instanceof String) {
				String keymessage=(String)arg2[0];
				if(keymessage.equals("evaluatorRecruit")) {
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							Alert alert = new Alert(AlertType.INFORMATION);
					        alert.setTitle("TEST");
					        alert.setHeaderText("Success");
					        alert.setContentText("evaluator has been recruited successfully");
					        alert.showAndWait();
						}
						
					});
				}
				else if(keymessage.equals("performerRecruit")) {
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							Alert alert = new Alert(AlertType.INFORMATION);
					        alert.setTitle("TEST");
					        alert.setHeaderText("Success");
					        alert.setContentText("Performer has been recruited successfully");
					        alert.showAndWait();
						}				
					});
				}
				else if(keymessage.equals("evaluatorRecruitAgain")) {
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							Alert alert = new Alert(AlertType.INFORMATION);
					        alert.setTitle("TEST");
					        alert.setHeaderText("Success");
					        alert.setContentText("evaluator has been recruited successfully");
					        alert.showAndWait();
						}
						
					});	
				}
				else if(keymessage.equals("testerRecruit")) {
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							Alert alert = new Alert(AlertType.INFORMATION);
					        alert.setTitle("TEST");
					        alert.setHeaderText("Success");
					        alert.setContentText("tester has been recruited successfully");
					        alert.showAndWait();
						}
						
					});	
				}
				else if(keymessage.equals("isRecruited")) {
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							Alert alert = new Alert(AlertType.WARNING);
					        alert.setTitle("TEST");
					        alert.setHeaderText("failed");
					        alert.setContentText("you already recruited him");
					        alert.showAndWait();
						}
						
					});	
				}
				else if(keymessage.equals("manualRequestTreatmentRecruitEvaluator")) {
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							Alert alert = new Alert(AlertType.INFORMATION);
					        alert.setTitle("TEST");
					        alert.setHeaderText("Success");
					        alert.setContentText("Update saved succesfully");
					        alert.showAndWait();
						}			
					});
				}
				
			}
		}
		
	}

}
