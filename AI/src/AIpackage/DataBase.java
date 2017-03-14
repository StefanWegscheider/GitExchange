package AIpackage;

//STEP 1. Import required packages
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import org.apache.logging.log4j.Logger;

import com.mysql.jdbc.DatabaseMetaData;

import org.apache.logging.log4j.LogManager;

public  class DataBase {
  
   
   public  static  Connection openDB() {
  
   Connection conn = null;

   try{ 
      //STEP 2: Register JDBC driver
      Class.forName("com.mysql.jdbc.Driver").newInstance();

      //STEP 3: Open a connection
      System.out.println("Connecting to MyAI database...");
      conn = DriverManager.getConnection("jdbc:mysql://192.168.122.22:3306/Test?autoReconnect=true&useSSL=false", "Developer", "lena080402");
      System.out.println("Connected database successfully...");
      System.out.printf("Nach Datenbankanbinfung conn=", conn);
      ((Logger) Intermediate.getloggerObject()).info("Database connection established");
      return conn;
   }catch(SQLException se){
      //Handle errors for JDBC
	   System.out.println("JDBC Error");
	   ErrorBox.OpenErrorBox("Database Error","Error Connecting to Database", se.toString());
	      ((Logger) Intermediate.getloggerObject()).error("Database connection failed   :"+se);
      se.printStackTrace();
   }catch(Exception e){
      //Handle errors for Class.forName
	   System.out.println("Class.forName Error");
	   ErrorBox.OpenErrorBox("Database Error","Error Connecting to Database", e.toString());
	      ((Logger) Intermediate.getloggerObject()).error("Database connection failed  :"+e);
      e.printStackTrace();
   }
   return conn;
   }
   
   public static  int CheckIfWordExistsInDB(Connection conn, String Buffer) throws SQLException {
	   Integer ReturnValue = 0;
	   String query = "SELECT BaseWords FROM Test.BaseWord WHERE BaseWords = ? LIMIT 1";//query String erstellen, "?" als Platzhalter für Abfrage		
	   PreparedStatement st = conn.prepareStatement(query);
	   st.setString(1, Buffer);
	   System.out.println("st =   "+st); 
	   ResultSet rs = st.executeQuery();	
	   if (!rs.isBeforeFirst() ) {    //isBeforeFirst returned False, wenn der Pointer nicht direkt vor der ersten Zeile steht UND: wenn das rs leer ist!
		   System.out.println("Wort ReturnValue=   "+ReturnValue); } 
	   else {ReturnValue=1;}
//	   rs.next();//Resultset auf erste Zeile setzen
//	   ReturnValue = rs.getInt(1);//Returnwert aus erster Spalte als Integer auslesen
		System.out.println(Buffer +"Returnwert ob in DB vorhanden :  "+ReturnValue+"   ");   
	   return ReturnValue;
	}
   
   public static int CheckIfColumnsExistsInDB(Connection conn, String pos) throws SQLException{
	   Integer ReturnValue = 0;
	   DatabaseMetaData metadata = (DatabaseMetaData) conn.getMetaData();
	   ResultSet resultSet;
	   resultSet = metadata.getColumns(null, null, "BaseWord", pos);
	   if(resultSet.next()){ReturnValue= 1;}
	       // Column exists
	   else {ReturnValue=0;}
	   
		System.out.println(pos+"Returnwert ob col in DB vorhanden :  "+ReturnValue+"   ");   
	  return ReturnValue;
   }
   
   public static void InsertColumnInDB(Connection conn, String Buffer) throws SQLException {
	   String query = "ALTER TABLE Test.BaseWord ADD "+Buffer+"   int(11) DEFAULT 0";//query String erstellen, "?" als Platzhalter für Abfrage		
	   PreparedStatement st = conn.prepareStatement(query);
	//   st.setString(1, Buffer);
	   System.out.println("query für insert column =   "+st); 
	   st.executeUpdate();	
   }
   
   public static void InsertWordInDB(Connection conn, String word, String column) throws SQLException {
	   String query = "INSERT INTO Test.BaseWord(BaseWords,"+column+") VALUES('"+word+"', '1')";//query String erstellen, "?" als Platzhalter für Abfrage		
	   PreparedStatement st = conn.prepareStatement(query);
	//   st.setString(1, Buffer);
	   System.out.println("query für insert word =   "+st); 
	   st.executeUpdate();	
   }
  
   public static void WordUpdateInDB(Connection conn, String word, String col) throws SQLException{
	   String query = "UPDATE Test.BaseWord SET "+col+"="+col+"+1 WHERE BaseWords='"+word+"' LIMIT 1";    //query String erstellen, "?" als Platzhalter für Abfrage		
	   PreparedStatement st = conn.prepareStatement(query);
	//   st.setString(1, Buffer);
	   System.out.println("query für update word =   "+st); 
	   st.executeUpdate();	
   }
}