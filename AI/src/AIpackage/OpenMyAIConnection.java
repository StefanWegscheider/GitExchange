package AIpackage;

//STEP 1. Import required packages
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public  class OpenMyAIConnection {
  
   
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
      return conn;
   }catch(SQLException se){
      //Handle errors for JDBC
	   System.out.println("JDBC Error");
      se.printStackTrace();
   }catch(Exception e){
      //Handle errors for Class.forName
	   System.out.println("Class.forName Error");
      e.printStackTrace();
   }
   return conn;
   }
}