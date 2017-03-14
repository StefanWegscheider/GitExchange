package AIpackage;

import java.io.File;
import java.io.IOException;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;

public class FileIO{
	
    static String readFile(File file){
        StringBuilder stringBuffer = new StringBuilder();
        BufferedReader bufferedReader = null;
         
        try {
 
            bufferedReader = new BufferedReader(new FileReader(file));
             
            String text;
            while ((text = bufferedReader.readLine()) != null) {
                stringBuffer.append(text);
            }
 
        } catch (FileNotFoundException ex) {
        } catch (IOException ex) {
        } finally {
            try {
                bufferedReader.close();
            } catch (IOException ex) {
            }
        } 
         
        return stringBuffer.toString();
    }
static void saveFile( File file, String content){
	  try {
          FileWriter fileWriter = null;
           
          fileWriter = new FileWriter(file);
          fileWriter.write(content);
          fileWriter.close();
      } catch (IOException ex) {
      }
       
		}

}