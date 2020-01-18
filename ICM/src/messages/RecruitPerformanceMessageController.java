package messages;

import Boundary.LoginController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class RecruitPerformanceMessageController {
	@FXML
	Label label1;
	@FXML
	public static RecruitPerformanceMessageController ctrl;
	public static Stage primaryStage;
	private AnchorPane lowerAnchorPane;
	public  static SplitPane splitpane;
	public void start(SplitPane splitpane,int id,String path,String content) {
		primaryStage=LoginController.primaryStage;
		try{	
			System.out.println("oiii");
			FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
			lowerAnchorPane = loader.load();
			ctrl=loader.<RecruitPerformanceMessageController>getController();
			System.out.println("zzz");
			splitpane.getItems().set(1, lowerAnchorPane);
			this.splitpane=splitpane;
			ctrl.label1.setVisible(false);
			System.out.println("mmmm");
			if(path.equals("/messages/RecruitEngineerPerform.fxml"))
			ctrl.label1.setText(content);
			else if(path.equals("/messages/ReplacePerformanceLeader-Message.fxml")) {
				System.out.println("fff");
				ctrl.label1.setText(content);		
			}
			
			else
			ctrl.label1.setText(content);	
			
			ctrl.label1.setVisible(true);
			
		} catch(Exception e) {
			e.printStackTrace();
		}			
	}

}
