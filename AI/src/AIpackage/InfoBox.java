package AIpackage;

import java.util.Optional;
import javafx.scene.control.DialogPane;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;

public  class InfoBox {
  static String BoxName;
  static String BoxMessage;
  static String BoxMessage2;
   
  public  static void OpenInfoBox (String a, String b, String c )  {

	  BoxName = a;
	  BoxMessage = b;
	  BoxMessage2 = c;

	   int ReturnFromAskBox = 0;
  
	   Alert alert = new Alert(AlertType.INFORMATION);
	   alert.setTitle(BoxName);
	   alert.setHeaderText(BoxMessage);
	   alert.setContentText(BoxMessage2);
	   
	   
	  // DialogPane dialogPane = alert.getDialogPane();
	   //  dialogPane.setStyle("-fx-background-color: greenyellow;");
	   
	   alert.getDialogPane().getScene().getStylesheets().add("AIpackage/InfoBox.css");
	   
	   ButtonType buttonTypeOK = new ButtonType("OK");
	      
	   alert.getButtonTypes().setAll(buttonTypeOK);
	   
	   //// GridPane grid = (GridPane)dialogPane.lookup(".header-panel"); 
	   // grid.setStyle("-fx-background-color: cadetblue; "      + "-fx-font-style: italic;");
	   //dialogPane.lookup(".content.label").setStyle("-fx-font-size: 16px; "+ "-fx-font-weight: bold; -fx-fill: blue;");
	   ////alert.getButtonTypes().clear();
	   //alert.getButtonTypes().addAll(ButtonType.YES, ButtonType.NO);

	   alert.showAndWait();

   }
}
