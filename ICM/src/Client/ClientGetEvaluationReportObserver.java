package Client;

import java.util.Observable;
import java.util.Observer;

import Boundary.CommitteeEvaluationController;
import Entity.EvaluationReport;
import javafx.application.Platform;
/**
 * in this observer we put the evaluation report that we received from the DB
 * in the evaluation report window at committee member user to allow them to see the evaluation report
 *
 */
public class ClientGetEvaluationReportObserver implements Observer {
	public ClientGetEvaluationReportObserver(Observable server) {
		server.addObserver(this);
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		if (arg instanceof Object[]) {
			Object[] arg2 = (Object[]) arg;
			if (arg2[0] instanceof String) {
				String keymessage = (String) arg2[0];
				if (keymessage.equals("get evaluation report")) {
					if (arg2[1] instanceof EvaluationReport) {
						EvaluationReport report = (EvaluationReport) arg2[1];
						Platform.runLater(new Runnable() {
							@Override
							public void run() {
								CommitteeEvaluationController.ctrl1.setlabels(report);
							}
						});
					}
				}
			}
		}
	}
}