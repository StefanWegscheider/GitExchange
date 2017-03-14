package AIpackage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.apache.logging.log4j.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Labeled;
import javafx.scene.control.Menu;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.web.HTMLEditor;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import AIpackage.HomeBase;
import AIpackage.Intermediate;
//import apple.laf.JRSUIUtils.Tree;
import javafx.stage.FileChooser;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

/**
 * The controller for the root layout. The root layout provides the basic
 * application layout containing a menu bar and space where other JavaFX
 * elements can be placed.
 * 
 */

public class RootLayoutController  implements Initializable{
	

	@FXML
    private void CloseEditor() {
		int returnValue = 0;
		returnValue = AskBox.OpenBox("Editor"," Warning"," Sure you saved your stuff?");
		if (returnValue == 1) {HomeBase.initRootLayout();}
		else{}
    }
	
	@FXML Button AnalyzeDirectInputButton;
	@FXML TextField DirectInputTextField;
	@FXML TextArea AnalyzeInputOutput;
	@FXML  CheckBox CheckBoxDB1;
	@FXML CheckBox CheckBoxDB2;


	public void AnalyzeDirectInput() throws IOException{
		 AnalyzeInputOutput.setEditable(true);
		 System.out.println(DirectInputTextField.getText());
		 AnalyzeInputOutput.appendText("\n"+DirectInputTextField.getText());
		 NLPCall.Base("Input.txt","txtOutput.txt", "xmlOutput.xml",DirectInputTextField.getText());
	}
	
	public void SetAnalyzeInputOutputText(String text){
	//	Pane p = fxmlLoader.load(getClass().getResource("foo.fxml").openStream());
		AnalyzeInputOutput.appendText(text);
	}
	
	@FXML
	public void BackToHome() {
//	((Stage) HomeBase.StageHTMLViewerObject).close();
    if (Intermediate.getsObject() != null) {
    Intermediate.getsObject().close();}//Controller zu Objeckt aufrufen um HTML Viewer, sofern offen, zu schliessen
    else {}
	HomeBase.initRootLayout();	
	}
	
	@FXML
	public void OpenAnalyzeDirectinput(){
		HomeBase.InitAnalyzeDirectInput();
	}
	
	@FXML
	public void OpenAnalyzeFileinput(){
		HomeBase.InitAnalyzeFileInput();
	}
	
	@FXML
    public void OpenFile() {
		 FileChooser fileChooser = new FileChooser();
         
         //Set extension filter
         FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("HTML files (*.html)", "*.html");           
         fileChooser.getExtensionFilters().add(extFilter);
         fileChooser.setTitle("Open File"); 
         Stage currentStage = HomeBase.getPrimaryStage(); 
         //Show open file dialog
         File file = fileChooser.showOpenDialog(currentStage);
         if(file != null){
             String textRead = FileIO.readFile(file);
         //    ((HTMLEditor) HomeBase.EditorObject).setHtmlText(textRead);
            Intermediate.geteObject().setHtmlText(textRead);//sets Controller to set Text to Text Field of HTML Editor
        	// HomeBase.initEditorLayout(textRead);

}
	}
	
	@FXML
    public void OpenFileForAnalyzer() {
		 FileChooser fileChooser = new FileChooser();
         
         //Set extension filter
      //   FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("HTML files (*.html)", "*.html");           
   //      fileChooser.getExtensionFilters().add(extFilter);
         fileChooser.setTitle("Open File"); 
         Stage currentStage = HomeBase.getPrimaryStage(); 
         //Show open file dialog
         File file = fileChooser.showOpenDialog(currentStage);
         if(file != null){
             String textRead = FileIO.readFile(file);
         //    ((HTMLEditor) HomeBase.EditorObject).setHtmlText(textRead);
         //    Intermediate.geteObject().setHtmlText(textRead);//sets Controller to set Text to Text Field of HTML Editor
        	// HomeBase.initEditorLayout(textRead);
            DirectInputTextField.setText(file.getPath());
            
            if (textRead != null) { try {
				NLPCall.Base("Input.txt","txtOutput.txt", "xmlOutput.xml",textRead);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}}
            else {}
}
	}
	
	@FXML
    private void SaveFile() {
	       FileChooser fileChooser = new FileChooser();	       
           //Set extension filter
           FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("HTML files (*.html)", "*.html");
           fileChooser.getExtensionFilters().add(extFilter);
           fileChooser.setTitle("Save File");         
           Stage currentStage = HomeBase.getPrimaryStage(); 
           //Show save file dialog
           File file = fileChooser.showSaveDialog(currentStage);         
           if(file != null){
               String textSave =   Intermediate.geteObject().getHtmlText();
               FileIO.saveFile(file, textSave);
           }
    }
 
	@FXML
    private void handleAbout() {
   InfoBox.OpenInfoBox("Information", "Information about this program.", "   ");
    }
    
    @FXML
    private void handleEditor() {
	 HomeBase.initEditorLayout();
	}
    
   @FXML
    private void handleExit() {
    	RWConfig.WriteConfig();
	      ((Logger) Intermediate.getloggerObject()).info("Application closed.");		  
       System.exit(0);
    }

@Override
public void initialize(URL location, ResourceBundle resources) {
	// TODO Auto-generated method stub
}
}