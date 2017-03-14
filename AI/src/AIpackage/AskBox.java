package AIpackage;

import java.util.Optional;
import javafx.scene.control.DialogPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;

public  class AskBox {
  static String BoxName;
  static String BoxMessage;
  static String BoxMessage2;
   
  public  static int OpenBox (String a, String b, String c )  {

	  BoxName = a;
	  BoxMessage = b;
	  BoxMessage2 = c;

	   int ReturnFromAskBox = 0;
  
	   Alert alert = new Alert(AlertType.CONFIRMATION);
	   alert.setTitle(BoxName);
	   alert.setHeaderText(BoxMessage);
	   alert.setContentText(BoxMessage2);
	   
	  
	  // DialogPane dialogPane = alert.getDialogPane();
	   //What ever
	   //  dialogPane.setStyle("-fx-background-color: greenyellow;");
	   
	   alert.getDialogPane().getScene().getStylesheets().add("AIpackage/AskBox.css");
	   
	   ButtonType buttonTypeOK = new ButtonType("OK");
	   ButtonType buttonTypeCancel = new ButtonType("Cancel");
	      
	   alert.getButtonTypes().setAll(buttonTypeOK, buttonTypeCancel);
//	   alert.getButtonTypes().clear();
	//   alert.getButtonTypes().addAll(ButtonType.YES, ButtonType.NO);

	   Optional<ButtonType> result = alert.showAndWait();
	   if (result.get() == buttonTypeOK){
		   ReturnFromAskBox = 1;//  // ... user chose OK
	   } else {
		   ReturnFromAskBox = 0;// ... user chose CANCEL or closed the dialog
	   }
   return ReturnFromAskBox;
   }
}
