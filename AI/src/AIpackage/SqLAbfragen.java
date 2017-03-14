package AIpackage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javafx.collections.ObservableList;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.beans.binding.StringExpression;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import java.util.List;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.control.TextField;


public  class SqLAbfragen {
public static ResultSet rs=null;  
public static PreparedStatement st = null;
static Connection conn = null;
static String query=null;
static int ReturnValue = 0;
static int i=0;
static int j=0;
static int ID=0;
static int  [][] WordCounter=new int [50] [50];
static String columns="";
static String values="";
static String Message=null;
static String sbackticks=null;
static String sdownticks=null;
static String[] BufferArray = new String[40];

	   public static  int CheckIfObjectExists(String Buffer) throws SQLException {
	//	   PreparedStatement st = null;
		   ReturnValue = 0;
		   conn=OpenMyAIConnection.openDB() ;
		   query = "SELECT ObjectNumber FROM MyAi.BaseTable WHERE ObjectName = ? LIMIT 1";//query String erstellen, "?" als Platzhalter für Abfrage		
		   st = conn.prepareStatement(query);
		   st.setString(1, Buffer);
		   rs = st.executeQuery();	
		   if (!rs.isBeforeFirst() ) {    //isBeforeFirst returned False, wenn der Pointer nicht direkt vor der ersten Zeile steht UND: wenn das rs leer ist!
			   System.out.println("No data in rs. ReturnValue=   "+ReturnValue); } 
		   else {
		   rs.next();//Resultset auf erste Zeile setzen
		   ReturnValue = rs.getInt(1);//Returnwert aus erster Spalte als Integer auslesen
			System.out.println(Buffer +"Returnwert ob in DB vorhanden :  "+ReturnValue+"   ");}
		   
			conn.close();//Connection schließen
		   return ReturnValue;
		}

		public static int AddNewObjectInDatabase(String ObjectName)  {
			st = null;
			conn=null;
			conn = OpenMyAIConnection.openDB(); //Connection String deklarieren
			
			query=("INSERT INTO MyAi.BaseTable (ObjectName) VALUES (?) ");	//in die Datenbank BaseTable wird eine neue Zeile geschrieben
			try{
			st = conn.prepareStatement(query);
			st.setString(1, ObjectName);
			st.executeUpdate();	
				
			query=("SELECT ObjectNumber FROM MyAi.BaseTable WHERE ObjectName = (?) ");//Aus der Datenbank BaseTable wird die ID ausgelesen unter der das Objekt angelegt worden ist
			st = conn.prepareStatement(query);
			st.setString(1, ObjectName);
			System.out.println(st);
			rs=st.executeQuery();	
			rs.next();
			ID=rs.getInt("ObjectNumber");
			System.out.println("ID is:   "+ID);
			sbackticks="`"+Integer.toString(ID)+"`";//ID wird mit Backticks versehen und in einen String übernommen

			query=("ALTER TABLE MyAi.BeforeObject ADD COLUMN  ")+sbackticks+(" INT(11) NOT NULL DEFAULT '0' ");//In der Datenbank BeforeObject wird eine column mit der ID als Namen angelegt 
			st = conn.prepareStatement(query);
			System.out.println(sbackticks);
			System.out.println(st);
			st.executeUpdate();	
			
			sdownticks="'"+Integer.toString(ID)+"'";//ID wird mit down ticks versehen und in einen String übernommen
			query=("INSERT INTO MyAi.BeforeObject  (ObjectNumber) VALUES (")+sdownticks+(")");	//in die Datenbank BeforeObject wird eine neue Zeile geschrieben mit Object ID
			st = conn.prepareStatement(query);
			System.out.println(st);
			st.executeUpdate();	
			System.out.println("ID der Zeile   "+ObjectName+ "   ist   "+sdownticks);		
			
			query=("ALTER TABLE MyAi.AfterObject ADD COLUMN  ")+sbackticks+(" INT(11) NOT NULL DEFAULT '0' ");//In der Datenbank AfterObject wird eine column mit der ID als Namen angelegt 
			st = conn.prepareStatement(query);
			System.out.println(st);
			st.executeUpdate();	
			
			query=("INSERT INTO MyAi.AfterObject  (ObjectNumber) VALUES (")+sdownticks+(")");	//in die Datenbank AfterObject wird eine neue Zeile geschrieben mit Object ID
			st = conn.prepareStatement(query);
			System.out.println(st);
			st.executeUpdate();	
			
			query=("ALTER TABLE MyAi.Phrases ADD COLUMN  ")+sbackticks+(" INT(11) NOT NULL DEFAULT '0' ");//In der Datenbank Phrases wird eine column mit der ID als Namen angelegt 
			st = conn.prepareStatement(query);
			System.out.println(st);		
			st.executeUpdate();	
								
			conn.close();	}		
			catch(SQLException e){			
				Message=e.getMessage();
				ErrorBox.OpenErrorBox("Achtung!"," Fehler beim Anlegen eines Objektes in der Datenbank",  Message); 
			}
			
			return ID;
		}
		
		public static void UpdateObjectInDatabase(String [][] FullArray,  int [][] ObjectNumberArray, int PhraseCounter, int [] PhraseLength) throws SQLException  {	
			st = null;
			conn=null;
			conn = OpenMyAIConnection.openDB(); //Connection String deklarieren
			
          	for (i = 0; i <PhraseCounter; i++) {//Anzahl der Phrasen wird duchrlaufen
          		j=0;
       		do {
				System.out.println(i+"   "+j+"   "+"ObjectNumberArray :  "+ObjectNumberArray[i][j]+"   "+"FullArray :  "+FullArray[i][j]+"  PhraseCounter :   "+PhraseCounter+" Länge PhraseLength :  "+PhraseLength[i]);	

				sdownticks="'"+Integer.toString(ObjectNumberArray[i][j])+"'";//Spaltennummer wird mit down ticks versehen und in einen String übernommen				

				if (j==0) {	//erstes Wort in einer Phrase
				query=("UPDATE MyAi.BaseTable  SET IsStarter = IsStarter+1 WHERE ObjectNumber= (")+sdownticks+(")");	//in die Datenbank BaseTable wird der IsStarter Wert um 1 erhöht
				st = conn.prepareStatement(query);
				System.out.println(st);
				st.executeUpdate();	}	
				
				if (j==PhraseLength[i]-1){//letztes Wort in einer Phrase
				query=("UPDATE MyAi.BaseTable  SET IsEndSign = IsEndSign+1 WHERE ObjectNumber= (")+sdownticks+(")");	//in die Datenbank BaseTable wird der IsEndSign Wert um 1 erhöht
				st = conn.prepareStatement(query);
				System.out.println(st);
				st.executeUpdate();	}			
				
				if (j<PhraseLength[i]-1){//alle Wörter ausser dem Letzten, das hat keinen Nachfolger
				sbackticks="`"+Integer.toString(ObjectNumberArray[i][j+1])+"`";//Spaltennummer wird mit Backticks versehen und in einen String übernommen
				query=("UPDATE  MyAi.AfterObject  SET  ")+sbackticks+("=")+sbackticks+("+'1' WHERE ObjectNumber  =")+sdownticks;	//in die Datenbank AfterObject wird Nachfolger column Wert um 1 erhöht
				st = conn.prepareStatement(query);
				System.out.println(st);
				st.executeUpdate();}	
				
				if (j>0){//alle Wörter ausser dem Ersten, das hat keinen Vorgänger								
				sbackticks="`"+Integer.toString(ObjectNumberArray[i][j-1])+"`";//Spaltennummer wird mit Backticks versehen und in einen String übernommen
				query=("UPDATE  MyAi.BeforeObject SET  ")+sbackticks+("=")+sbackticks+("+'1' WHERE ObjectNumber  =")+sdownticks;	//in die Datenbank BeforeObject wird ein Nachfolger column Wert um 1 erhöht
				st = conn.prepareStatement(query);
				System.out.println(st);
				st.executeUpdate();}
				
				query=("UPDATE MyAi.BaseTable  SET Frequency = Frequency+1 WHERE ObjectNumber= (")+sdownticks+(")");	//in die Datenbank BaseTable wird der Frequency Wert um 1 erhöht
				st = conn.prepareStatement(query);
				System.out.println(st);
				st.executeUpdate();		
													
				j++;				
       		}  while (j<PhraseLength[i]);       			
          	}
			conn.close();			
		}				
			public static  int CheckIfPhraseExists(int [][] ObjectNumberArray, int PhraseNumber, int PhraseLength) throws SQLException {
//			Phrasen zusammenbauen 				   
					ReturnValue = 0;
					conn=OpenMyAIConnection.openDB() ;
					query = "SELECT PhraseNumber FROM MyAi.Phrases WHERE ";//erster Teil des query String erstellen, 	
			for (i=0; i<PhraseLength; i++){
					query=query+"`"+(i+1)+"`"+"="+"'"+ObjectNumberArray[PhraseNumber][i]+"'";
					if (i<PhraseLength-1){query=query+" AND ";}
					else {query=query+" AND WordCount= "+ "'"+PhraseLength+"'"+" Limit 1 ";}
					}   
					System.out.println(query);
					st = conn.prepareStatement(query);
					rs = st.executeQuery();	
				   if (!rs.isBeforeFirst() ) {    //isBeforeFirst returned False, wenn der Pointer nicht direkt vor der ersten Zeile steht UND: wenn das rs leer ist!
				   System.out.println("No data in rs. ReturnValue=   "+ReturnValue); } 
				   else {
				   rs.next();//Resultset auf erste Zeile setzen
				   ReturnValue = rs.getInt(1);//Returnwert aus erster Spalte als Integer auslesen
					System.out.println(query);
				   }
					conn.close();//Connection schließen
					System.out.println("CheckIfPhraseExists ReturnValue ist :   "+ReturnValue);
					return ReturnValue;
			}
					
		
			public static  int AddNewPhraseInDatabase(int [][] ObjectNumberArray, int PhraseNumber, int PhraseLength) throws SQLException {
				ReturnValue = 0;
				columns="";
				values="";
				conn=OpenMyAIConnection.openDB() ;
				query = "INSERT INTO MyAi.Phrases ";//erster Teil des query String erstellen, 	
				for (i=0; i<PhraseLength; i++){
							columns=columns+"`"+(i+1)+"`";
							values=values+"'"+ObjectNumberArray[PhraseNumber][i]+"'";
										if (i<PhraseLength-1){columns=columns+",";values=values+",";}//columns erstellen
					
										else query=query+"("+columns+",`WordCount`"+")"+" VALUES "+"("+values+","+"'"+PhraseLength+"'"+")";//Werte für columns erstellen
																}			
				System.out.println(query); 
				st = conn.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);		//Abfrage und Verfügbarmachen der neu erstellten ID
				st.executeUpdate();	
				rs = st.getGeneratedKeys();
				if (rs.next()) {//Resultset auf erste Spalte stellen
				    ID = rs.getInt(1);//Ergebnis des Resultset auslesen und autogenerated ID übergebemn
				}				 
				System.out.println("Inserted Phrase ID: " + ID);
				
				sbackticks="`"+Integer.toString(ID)+"`";//Spaltennummer wird mit Backticks versehen und in einen String übernommen
				query=("ALTER TABLE MyAi.BeforePhrases ADD COLUMN  ")+sbackticks+(" INT(11) NOT NULL DEFAULT '0' ");//In der Datenbank BeforePhrases wird eine column mit der ID als Namen angelegt 
				st = conn.prepareStatement(query);
				System.out.println(st);		
				st.executeUpdate();	
				
				query=("ALTER TABLE MyAi.AfterPhrases ADD COLUMN  ")+sbackticks+(" INT(11) NOT NULL DEFAULT '0' ");//In der Datenbank AfterPhrases wird eine column mit der ID als Namen angelegt 
				st = conn.prepareStatement(query);
				System.out.println(st);		
				st.executeUpdate();	
				
				sdownticks="'"+Integer.toString(ID)+"'";//ID wird mit down ticks versehen und in einen String übernommen
				query=("INSERT INTO MyAi.BeforePhrases  (PhraseNumber) VALUES (")+sdownticks+(")");	//in die Datenbank BeforePhrase wird eine neue Zeile geschrieben mit Object ID
				st = conn.prepareStatement(query);
				System.out.println(st);
				st.executeUpdate();	
				System.out.println("ID der Zeile   ist   "+sdownticks);		
				
				query=("INSERT INTO MyAi.AfterPhrases  (PhraseNumber) VALUES (")+sdownticks+(")");	//in die Datenbank AfterPhrase wird eine neue Zeile geschrieben mit Object ID
				st = conn.prepareStatement(query);
				System.out.println(st);
				st.executeUpdate();	
				System.out.println("ID der Zeile   ist   "+sdownticks);		

//				columns="";
//				values="";
	
			return ID;
			}		
			
			public static void UpdatePhraseInDatabase(int [] PhraseID, int PhraseCounter) throws SQLException {
				st = null;
				conn=null;
				conn = OpenMyAIConnection.openDB(); //Connection String deklarieren
				
	          	for (i = 0; i <PhraseCounter; i++) {//Anzahl der Phrasen wird duchrlaufen
	       	
					System.out.println("PhraseID :  "+ PhraseID[i]+"   "+"  PhraseCounter :   "+PhraseCounter+"   i  :  "+i);	

					sdownticks="'"+Integer.toString(PhraseID[i])+"'";//Spaltennummer wird mit down ticks versehen und in einen String übernommen				

//					if (i==0) {	//erste Phrase in einer Phrasenabfolge
//					query=("UPDATE MyAi.BaseTable  SET IsStarter = IsStarter+1 WHERE ObjectNumber= (")+sdownticks+(")");	//in die Datenbank BaseTable wird der IsStarter Wert um 1 erhöht
//					st = conn.prepareStatement(query);
//					System.out.println(st);
//					st.executeUpdate();	}	
					
//					if (j==PhraseCounter-1){//letztes Phrase in einer Phraseabfolge
//					query=("UPDATE MyAi.BaseTable  SET IsEndSign = IsEndSign+1 WHERE ObjectNumber= (")+sdownticks+(")");	//in die Datenbank BaseTable wird der IsEndSign Wert um 1 erhöht
//					st = conn.prepareStatement(query);
//					System.out.println(st);
//					st.executeUpdate();	}			
					
					if (i<PhraseCounter-1){//alle Phrasen ausser der Letzten, die hat keinen Nachfolger
					sbackticks="`"+Integer.toString(PhraseID[i+1])+"`";//Spaltennummer wird mit Backticks versehen und in einen String übernommen
					query=("UPDATE  MyAi.AfterPhrases SET  ")+sbackticks+("=")+sbackticks+("+'1' WHERE PhraseNumber  =")+sdownticks;	//in die Datenbank AfterObject wird Nachfolger column Wert um 1 erhöht
					st = conn.prepareStatement(query);
					System.out.println(st);
					st.executeUpdate();}	

					
					if (i>0){//alle Phrasen  ausser der Ersten, die hat keinen Vorgänger								
					sbackticks="`"+Integer.toString(PhraseID[i-1])+"`";//Spaltennummer wird mit Backticks versehen und in einen String übernommen
					query=("UPDATE  MyAi.BeforePhrases SET  ")+sbackticks+("=")+sbackticks+("+'1' WHERE PhraseNumber  =")+sdownticks;	//in die Datenbank BeforeObject wird ein Nachfolger column Wert um 1 erhöht
					st = conn.prepareStatement(query);
					System.out.println(st);
					st.executeUpdate();}
					
					query=("UPDATE MyAi.Phrases  SET Frequency = Frequency+1 WHERE PhraseNumber= (")+sdownticks+(")");	//in die Datenbank Phrases wird der Frequency Wert um 1 erhöht
					st = conn.prepareStatement(query);
					System.out.println(st);
					st.executeUpdate();		   			
	          	}
				conn.close();	
			}
		
}
