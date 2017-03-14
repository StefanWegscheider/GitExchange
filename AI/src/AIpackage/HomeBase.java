package AIpackage;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;

import AIpackage.Intermediate;
import AIpackage.OpenMyAIConnection;
import javafx.application.Application;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.web.HTMLEditor;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class HomeBase extends Application {
	static Connection conn = null;
    private static Stage primaryStage;
    //Objekt, um diese Stage später auch ansprechen zu können
  //  public  static Object StageHTMLViewerObject; 
    private static BorderPane rootLayout;
    Logger logger=null;
//    public static Object EditorObject;
 //   public static  Object controller;
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("AI Base");
        this.primaryStage.getIcons().add(new Image("AIpackage/MorpheusIcon.jpg"));

        initRootLayout();
        logger = LogManager.getLogger("Global");//Initialize Logger
		   Intermediate.setsloggerObject(logger);//Logger Object setzen um später darauf zugreifen zu können 
		      ((Logger) Intermediate.getloggerObject()).info("Application started.");		   
		      
		      conn=DataBase.openDB() ;//Open Database Connection
		      
			   Integer exists1= DataBase.CheckIfColumnsExistsInDB(conn, "ner");
			    if (exists1 == 0) {DataBase.InsertColumnInDB(conn, "ner");}
			    else {}		      
		      Integer exists0=DataBase.CheckIfWordExistsInDB(conn, "Stefan");
		        if (exists0 == 0) {DataBase.InsertWordInDB(conn, "Stefan", "ner");}
		        else {DataBase.WordUpdateInDB(conn, "Stefan", "ner");}

		    /*
		     InsertWordInDB(conn, word);
		      returnYN=CheckIfColumnsExistsInDB(conn, pos);
		      InsertColumnInDB(conn, pos);
		     UpdateDB(conn, word, pos);
		      */
        RWConfig.ReadConfig();	 //Read Config File  
        
    }

    /**
     * Initializes the root layout.
     */
    public static void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(HomeBase.class.getResource("RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();
            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            scene.setFill(Color.TRANSPARENT);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static  void initEditorLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(HomeBase.class.getResource("EditorLayout.fxml"));
            rootLayout = (BorderPane) loader.load();
            final HTMLEditor htmlEditor = new HTMLEditor();
      //      EditorObject=htmlEditor;
            Intermediate.seteObject(htmlEditor);//Html Editor Object Controller übergeben um später darauf zugreifen zu können
            rootLayout.setCenter(htmlEditor);//Lädt den Editor in den Center Bereich der Border Pane aus FXM
    //        htmlEditor.getStylesheets("htmlEditor.css");
       //     if (textRead != null) {htmlEditor.setHtmlText(textRead);}; 
            Scene scene = new Scene(rootLayout);
            scene.setFill(Color.TRANSPARENT);
            primaryStage.setScene(scene);
            primaryStage.show();                 

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void InitAnalyzeDirectInput(){
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(HomeBase.class.getResource("AnalyzeDirectInput.fxml"));
            rootLayout = (BorderPane) loader.load();
          //  Intermediate.setscObject(loader.load());
            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            Intermediate.setscObject((RootLayoutController)loader.getController());//FXML Controller auslesen, um diesen danach verwenden zu können
            scene.setFill(Color.TRANSPARENT);
            primaryStage.setScene(scene);
            primaryStage.sizeToScene();
            primaryStage.show();
         //   ((RootLayoutController) controller).SetAnalyzeInputOutputText("Hier bin ich!");
        } catch (IOException e) {
            e.printStackTrace();
        }	
    }
    
    public static void InitAnalyzeFileInput(){
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(HomeBase.class.getResource("AnalyzeDateiInput.fxml"));
            rootLayout = (BorderPane) loader.load();
          //  Intermediate.setscObject(loader.load());
            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            Intermediate.setscObject((RootLayoutController)loader.getController());//FXML Controller auslesen, um diesen danach verwenden zu können
            scene.setFill(Color.TRANSPARENT);
            primaryStage.setScene(scene);
            primaryStage.sizeToScene();
            primaryStage.show();
         //   ((RootLayoutController) controller).SetAnalyzeInputOutputText("Hier bin ich!");
        } catch (IOException e) {
            e.printStackTrace();
        }	
    }
    
    /**
     * Returns the main stage.
     * @return
     */
    public static Stage getPrimaryStage() {
        return primaryStage;
    }
    
    public static void HTMLShowFromFile(String filename, String Title)  {
    	{
    	    Stage Stage2=new Stage();
    	    Stage2.setWidth(800);
    	    Stage2.setHeight(700);
    	    Scene scene2 = new Scene(new Group());
    	    
    	    final WebView browser = new WebView();
    	    final WebEngine webEngine = browser.getEngine();

    	    ScrollPane scrollPane = new ScrollPane();
    	    scrollPane.setContent(browser);
    	    File file = new File(filename);
    	    URL url = null;
			try {
				url = file.toURI().toURL();
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	     
    	    // file:/C:/test/a.html
    	    webEngine.load(url.toString());
    	/*    URL url = this.getClass().getResource("/com/cds/gui/webView/main.html");
    	    webEngine.load(url.toString());*/
    	//    webEngine.load("//Users/stefanwegscheider/eclipse/Workspace/AI/src/application/MyHomeBase");
    	    scene2.setRoot(scrollPane);
    	    Stage2.setTitle(Title);
            Stage2.setX(primaryStage.getWidth() +100); 
            Stage2.setY(primaryStage.getHeight() -100); 
    	    Stage2.setScene(scene2);
    	    Stage2.show();
    	//    StageHTMLViewerObject = Stage2; //Übergibt Stage an ein Objekt, damit diees dann beim Schließen des Submenüs auch dieses Unterfenster zumachen kann
    	    Intermediate.setsObject(Stage2);//Übergibt Controller zur Stage, damit diese später zum Schliessen direkt angesprochen werden kann
    	  
			} 
						
	 }

    
  /*  public static  void OpenEditor(){   
		System.out.println("OpenEditor");
		Stage stage=new Stage();   
		stage.setTitle("HTMLEditor Sample");
	    stage.setWidth(500);
	    stage.setHeight(300);   
	    final HTMLEditor htmlEditor = new HTMLEditor();
	    htmlEditor.setPrefHeight(245);
	    Scene scene = new Scene(htmlEditor);       
	    stage.setScene(scene);
	    stage.show();
	    htmlEditor.setStyle(
	    	    "-fx-font: 12 cambria;"
	    	    + "-fx-border-color: brown; "
	    	    + "-fx-border-style: dotted;"
	    	    + "-fx-border-width: 2;"
	    	);
    }*/
    
    public static void main(String[] args) {
        launch(args);
        RWConfig.ReadConfig();	
    }
}