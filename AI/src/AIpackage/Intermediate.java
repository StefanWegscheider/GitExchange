package AIpackage;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.HTMLEditor;
import javafx.stage.Stage;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

class Intermediate {
    /*********************HTML Editor**********************************************************************************************/	  	
	  private final static ObjectProperty<HTMLEditor> eObject = new SimpleObjectProperty<>();

	    public static ObjectProperty<HTMLEditor> optioneProperty() {
	         return eObject ;
	    }
	    public static HTMLEditor geteObject() {
	        return optioneProperty().get();
	    }
	    public static void seteObject(HTMLEditor input) {
	        optioneProperty().set(input);
	    }
	    /*********************HTML Viewer**********************************************************************************************/	    
	    private final static ObjectProperty<Stage> sObject = new SimpleObjectProperty<>();

	    public static ObjectProperty<Stage> optionsProperty() {
	         return sObject ;
	    }
	    public static Stage getsObject() {
	        return optionsProperty().get();
	    }
	    public static void setsObject(Stage input) {
	        optionsProperty().set(input);
	    }        
	    /*********************Schreiben in Analyzer Oberfläche************************************************************************/	     
	    private final static ObjectProperty<Object> cObject = new SimpleObjectProperty<>();

	    public static ObjectProperty<Object> optionscProperty() {
	         return cObject ;
	    }
	    public static Object getscObject() {
	        return optionscProperty().get();
	    }
	    public static void setscObject(Object input) {
	        optionscProperty().set(input);
	    }     
	    /*********************Logger Object************************************************************************************************/	     
	    private final static ObjectProperty<Logger> loggerObject = new SimpleObjectProperty<>();

	    public static ObjectProperty<Logger> loggerProperty() {
	         return loggerObject ;
	    }
	    public static Logger getloggerObject() {
	        return loggerProperty().get();
	    }
	    public static void setsloggerObject(Logger input) {
	        loggerProperty().set(input);
	    }     
	    /***************AnalyzeTextInputSaveToDB-Switch if to write to DB or not***************************************************/
	    private final static IntegerProperty AnalyzeTextInputSaveToDB = new SimpleIntegerProperty();

	    public static IntegerProperty  AnalyzeTextInputSaveToDBProperty() {
	         return AnalyzeTextInputSaveToDB ;
	    }
	    public static Integer getAnalyzeTextInputSaveToDB () {
	        return AnalyzeTextInputSaveToDBProperty().get();
	    }
	    public static void setsAnalyzeTextInputSaveToDB (Integer input) {
	    	AnalyzeTextInputSaveToDBProperty().set(input);
	    }     
	    /***************AnalyzeTextInputSaveToDB-Switch if to write to DB or not***************************************************/
	    private final static IntegerProperty AnalyzeFileInputSaveToDB = new SimpleIntegerProperty();

	    public static IntegerProperty  AnalyzeFileInputSaveToDBProperty() {
	         return AnalyzeFileInputSaveToDB ;
	    }
	    public static Integer getAnalyzeFileInputSaveToDB () {
	        return AnalyzeFileInputSaveToDBProperty().get();
	    }
	    public static void setsAnalyzeFileInputSaveToDB (Integer input) {
	    	AnalyzeFileInputSaveToDBProperty().set(input);
	    }     
	}
