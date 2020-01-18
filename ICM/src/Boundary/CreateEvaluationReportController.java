package Boundary;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.sun.xml.internal.fastinfoset.util.DuplicateAttributeVerifier;

import Client.ClientConsole;
import Entity.EvaluationReport;
import Entity.Request;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class CreateEvaluationReportController implements Initializable {
	@FXML
	private TextField lbId;
	@FXML
	private TextArea Location;
	@FXML
	private TextArea DescriptionOfChange;
	@FXML
	private TextArea ExpectedResult;
	@FXML
	private TextArea constraints;
	@FXML
	private TextArea Risks;
	@FXML
	private TextField duration;
	@FXML
	private Button save;
	public static Stage primaryStage;
	private AnchorPane lowerAnchorPane;
	private static ClientConsole cc;
	private int chosenindex;
	private Request chosenRequest;
	private static int id;
	public static CreateEvaluationReportController evaluationReport;

	public void start(SplitPane splitpane, int id) {
		try {
			this.id = id;
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/Boundary/Evaluator-EvaluationReport.fxml"));
			lowerAnchorPane = loader.load();
			evaluationReport = loader.getController();
			splitpane.getItems().set(1, lowerAnchorPane);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void sendReport() {
		if(ClientConsole.map.get(id).equals("frozen")) {
			ClientConsole.displayFreezeError();
			return;
		}
		boolean flag0 = Location.getText().equals("");
		boolean flag1 = DescriptionOfChange.getText().equals("");
		boolean flag2 = ExpectedResult.getText().equals("");
		boolean flag3 = constraints.getText().equals("");
		boolean flag4 = Risks.getText().equals("");
		boolean flag5 = duration.getText().equals("");

		if (flag0 || flag1 || flag2 || flag3 || flag4 || flag5) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("NULL");
			alert.setHeaderText("ERROR");
			alert.setContentText("please fill all the fields need the red star");
			alert.showAndWait();
			return;
		}
		 try 
	        { 
	            // checking valid integer using parseInt() method 
	            int t=Integer.parseInt(duration.getText());
	            if(t<=0)
{
	            	Alert alert = new Alert(AlertType.WARNING);
					alert.setTitle("Wrong Input");
					alert.setHeaderText("ERROR");
					alert.setContentText("Please fill a positive number in duration");
					alert.showAndWait();
					return;
}
	        }  
	        catch (NumberFormatException e)  
	        { 
	        	Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Wrong Input");
				alert.setHeaderText("ERROR");
				alert.setContentText("Please fill a number in duration");
				alert.showAndWait();
				return;	        } 
		EvaluationReport er = new EvaluationReport(Location.getText(), DescriptionOfChange.getText(),
				ExpectedResult.getText(), constraints.getText(), Risks.getText(), Integer.valueOf(duration.getText()),
				id);
		Object[] message = { "send the report", er };
		try {
			LoginController.cc.getClient().sendToServer(message);
			save.setDisable(true);
		} catch (IOException e) {
			e.printStackTrace();
		}
		

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		chosenindex = RequestsWorkedOnController.getselectedindex();
		chosenRequest = RequestsWorkedOnController.getList().get(chosenindex);
		lbId.setText(Integer.toString(chosenRequest.getId()));
	}

}
