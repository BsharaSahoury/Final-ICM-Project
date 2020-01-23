package Client;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import Boundary.AdministratorHomeController;
import Boundary.ChairmanHomeController;
import Boundary.ComitteeMemberHomeController;
import Boundary.EvaluatorHomeController;
import Boundary.InspectorHomeController;
import Boundary.LecturerHomeController;
import Boundary.PerformanceLeaderHomeController;
import Boundary.StudentHomeController;
import Boundary.TesterHomeController;
/**
 * in this observer we handle to set all the details that we received from the DB
 * about the user in the profile setting window
 *
 */
public class ClientProfileSettingObserver implements Observer {
	public ClientProfileSettingObserver(Observable client) {
		client.addObserver(this);
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		if (arg1 instanceof Object[]) {
			Object[] getMsg = (Object[]) arg1;
			if (getMsg.length == 3 && getMsg[0] instanceof String) {
				String message = (String) getMsg[0];
				if (message.equals("ProfileSetting")) {
					if (getMsg[1] instanceof ArrayList<?>) {
						ArrayList<String> arr = (ArrayList<String>) getMsg[1];
						if (getMsg[2] instanceof String) {
							String job = (String) getMsg[2];
							if (job.equals("Inspector"))
								InspectorHomeController.ProfileSetting.fillProfileSettingData(arr);
							else if (job.equals("Evaluator"))
								EvaluatorHomeController.ProfileSetting.fillProfileSettingData(arr);
							else if (job.equals("Committee member"))
								ComitteeMemberHomeController.ProfileSetting.fillProfileSettingData(arr);
							else if (job.equals("Administrator"))
								AdministratorHomeController.ProfileSetting.fillProfileSettingData(arr);
							else if (job.equals("Student"))
								StudentHomeController.ProfileSetting.fillProfileSettingData(arr);
							else if (job.equals("Performance Leader"))
								PerformanceLeaderHomeController.ProfileSetting.fillProfileSettingData(arr);
							else if (job.equals("Tester"))
								TesterHomeController.ProfileSetting.fillProfileSettingData(arr);
							else if (job.equals("Chairman"))
								ComitteeMemberHomeController.ProfileSetting.fillProfileSettingData(arr);
							else if (job.equals("Lecturer"))
								LecturerHomeController.ProfileSetting.fillProfileSettingData(arr);
						}
					}
				}
			}
		}
	}
}