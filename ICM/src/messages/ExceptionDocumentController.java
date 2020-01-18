
package messages;

import java.io.IOException;

import Boundary.LoginController;
import Client.ClientConsole;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ExceptionDocumentController {
	@FXML
	private Label idLabel;
	@FXML
	private Label phaseLabel;
	@FXML
	private TextArea document;
	@FXML
	private Button save;
	@FXML
	public static ExceptionDocumentController ctrl;
	public static int repetion;
	public static int id;
	public static String phase;
	public static Stage primaryStage;
	private AnchorPane lowerAnchorPane;
	public  static SplitPane splitpane;

	public void start(SplitPane splitpane, int id, String phase, int repetion) {
		primaryStage=LoginController.primaryStage;
		try{	
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/messages/Exception documentation.fxml"));
			lowerAnchorPane = loader.load();
			ctrl=loader.getController();
			splitpane.getItems().set(1, lowerAnchorPane);
			this.splitpane=splitpane;
			ctrl.idLabel.setText(String.valueOf(id));
			ctrl.phaseLabel.setText(phase);
			this.repetion=repetion;
			this.id=id;
			this.phase=phase;
			
		} catch(Exception e) {
			e.printStackTrace();
		}	
		
	}
	public void saveDocument(ActionEvent e) {
		if(ClientConsole.map.get(id).equals("frozen")) {
			ClientConsole.displayFreezeError();
			return;
		}
		if(ctrl.document.getText().equals("")) {
			Alert alert = new Alert(AlertType.ERROR);
	        alert.setTitle("Alert Title");
	        alert.setHeaderText("error");
	        alert.setContentText("you haven't filled the document yet!");
	        alert.showAndWait();
		}
		else {
			Object[] msg= {"document",id,phase,repetion,ctrl.document.getText()};
			try {
				LoginController.cc.getClient().sendToServer(msg);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
	}

}

