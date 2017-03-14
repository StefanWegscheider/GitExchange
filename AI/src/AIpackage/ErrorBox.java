package AIpackage;

import java.util.Optional;
import javafx.scene.control.DialogPane;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;

public  class ErrorBox {
  static String BoxName;
  static String BoxMessage;
  static String BoxMessage2;
   
  public  static int OpenErrorBox (String a, String b, String c )  {

	  BoxName = a;
	  BoxMessage = b;
	  BoxMessage2 = c;

	   int ReturnFromAskBox = 0;
  
	   Alert alert = new Alert(AlertType.ERROR);
	   alert.setTitle(BoxName);
	   alert.setHeaderText(BoxMessage);
	   alert.setContentText(BoxMessage2);
	   
	   
	  // DialogPane dialogPane = alert.getDialogPane();
	   //  dialogPane.setStyle("-fx-background-color: greenyellow;");
	   
	   alert.getDialogPane().getScene().getStylesheets().add("AIpackage/ErrorBox.css");
	   
	   //// GridPane grid = (GridPane)dialogPane.lookup(".header-panel"); 
	   // grid.setStyle("-fx-background-color: cadetblue; "      + "-fx-font-style: italic;");
	   //dialogPane.lookup(".content.label").setStyle("-fx-font-size: 16px; "+ "-fx-font-weight: bold; -fx-fill: blue;");
	   ////alert.getButtonTypes().clear();
	   //alert.getButtonTypes().addAll(ButtonType.YES, ButtonType.NO);

	   Optional<ButtonType> result = alert.showAndWait();
	   if (result.get() == ButtonType.OK){
		   ReturnFromAskBox = 1;//  // ... user chose OK
	   } else {
		   ReturnFromAskBox = 0;// ... user chose CANCEL or closed the dialog
	   }
   return ReturnFromAskBox;
   }
}