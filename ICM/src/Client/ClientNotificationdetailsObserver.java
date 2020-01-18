package Client;

import java.util.ArrayList;

import java.util.Observable;
import java.util.Observer;

import Boundary.NotificationsController;
import Entity.Notification;
import javafx.application.Platform;
import messages.CommitteeDecisionApproveController;
import messages.CommitteeDecisionAskForaddInfoController;
import messages.CommitteeDecisionRejectController;
import messages.DecisionCommitteeMemberMessageController;
import messages.ExtensionConfirmationMessage;
import messages.FailedTestMessageController;
import messages.RejectMessageInitiatorController;
import messages.massageToAdmenToApproveExtension;

public class ClientNotificationdetailsObserver implements Observer {
	public ClientNotificationdetailsObserver(Observable client) {
		client.addObserver(this);
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		if (arg1 instanceof Object[]) {
			Object[] arg2 = (Object[]) arg1;
			if (arg2[0] instanceof String) {
				String keymessage = (String) arg2[0];
				if (keymessage.equals("notification details")) {
					if (arg2[1] instanceof String) {
						String details = (String) arg2[1];
						String job = (String) arg2[2];
						Platform.runLater(new Runnable() {
							@Override
							public void run() {
								if (job.equals("Chairman to approve the decision"))
									DecisionCommitteeMemberMessageController.ctrl.setdetails(details);
								else if (job.equals("inspector to recruit performance"))
									CommitteeDecisionApproveController.ctrl.setdetails(details);
								else if (job.equals("inspector to recruit evaluator"))
									CommitteeDecisionAskForaddInfoController.ctrl.setdetails(details);
								else if (job.equals("inspector to close the request"))
									CommitteeDecisionRejectController.ctrl.setdetails(details);
								else if (job.equals("Initiator to approve the reject message"))
									RejectMessageInitiatorController.ctrl.setdetails(details);
								else if (job.equals("Inspector to approve the Extension"))
									ExtensionConfirmationMessage.ctrl.setdetails(details);
								else if (job.equals("FailedTestDetails")) {
									System.out.println(details);
									FailedTestMessageController.ctrl.setdetails(details);
								}
								else if (job.equals("admin message")) {
									System.out.println(details);
									System.out.println("888888888888888");
									massageToAdmenToApproveExtension.ctrl.setdetails(details);}
							}
						});
					}

				}
			}

		}
	}
}
