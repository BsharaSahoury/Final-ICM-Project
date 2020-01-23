package Client;

import java.util.ArrayList;

import java.util.Observable;
import java.util.Observer;

import Boundary.RequestTreatmentAction;
import Entity.Employee;
import javafx.application.Platform;
import messages.AutomaticRecruitMessageController;
import messages.CommitteeDecisionApproveController;
import messages.CommitteeDecisionAskForaddInfoController;
import messages.CommitteeDecisionRejectController;
import messages.ChooseTesterMessageController;
import messages.DecisionCommitteeMemberMessageController;
import messages.FailedTestMessageController;
/**
 * in this observer we fill the combo of the relevant employees that we received from the DB
 * for the user that want to recruit an employee
 *
 */
public class ClientGetEvaluatorsObserver implements Observer {
	public ClientGetEvaluatorsObserver(Observable server) {
		server.addObserver(this);
	}

	@Override
	public void update(Observable o, Object arg) {
		if (arg instanceof Object[]) {
			Object[] arg1 = (Object[]) arg;
			if (arg1[0] instanceof String) {
				String keymessage = (String) arg1[0];
				if (keymessage.equals("employees")) {
					String classname = (String) arg1[2];
					if (arg1[1] instanceof ArrayList<?>) {
						ArrayList<Employee> Elist = (ArrayList<Employee>) arg1[1];
						ArrayList<String> names = new ArrayList<>();
						for (Employee f1 : Elist) {
							names.add(f1.getFirstName() + " " + f1.getLastName());
						}
						Platform.runLater(new Runnable() {
							@Override
							public void run() {
								switch (classname) {
								case "messages.AutomaticRecruitMessageController":
									AutomaticRecruitMessageController.ctrl.fillCombo(names);
									break;
								case "messages.CommitteeDecisionAskForaddInfoController":
									CommitteeDecisionAskForaddInfoController.ctrl.fillCombo(names);
									break;
								case "messages.ChooseTesterMessageController":
									ChooseTesterMessageController.ctrl.fillCombo(names);
									break;
								case "Boundary.RequestTreatmentAction":
									RequestTreatmentAction.ctrl.fillCombo(names);
									break;
								}
							}
						});
					}
					/*
					 * else if(keymessage.equals("Performance leaders")) { if(arg1[1] instanceof
					 * ArrayList<?>) { ArrayList<Employee> Elist=(ArrayList<Employee>)arg1[1];
					 * ArrayList<String> names=new ArrayList<>(); for(Employee f1 : Elist) {
					 * names.add(f1.getFirstName()+" "+f1.getLastName()); } Platform.runLater(new
					 * Runnable() {
					 * 
					 * @Override public void run() {
					 * CommitteeDecisionApproveController.ctrl.fillCombo(names); } }); } }
					 */
				} else if (keymessage.equals("evaluatorsagain")) {
					if (arg1[1] instanceof ArrayList<?>) {
						ArrayList<Employee> Elist = (ArrayList<Employee>) arg1[1];
						ArrayList<String> names = new ArrayList<>();
						for (Employee f1 : Elist) {
							names.add(f1.getFirstName() + " " + f1.getLastName());
						}
						Platform.runLater(new Runnable() {

							@Override
							public void run() {
								CommitteeDecisionAskForaddInfoController.ctrl.fillCombo(names);
							}
						});
					}
				}
			}

		}
	}
}
