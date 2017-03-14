package AIpackage;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import org.apache.logging.log4j.Logger;

class RWConfig {

	public static  void ReadConfig () {
	Properties prop = new Properties();
	InputStream input = null;

	try {

		input = new FileInputStream("src/AIpackage/AIpackage.properties");
		// load a properties file
		prop.load(input);
		// get the property value and print it out
		//System.out.println("Output1     "+prop.getProperty("AnalyzeTextInputSaveToDB"));
		//System.out.println("Output2     "+prop.getProperty("AnalyzeFileInputSaveToDB"));
		Intermediate.setsAnalyzeTextInputSaveToDB(Integer.valueOf(prop.getProperty("AnalyzeTextInputSaveToDB")));
		Intermediate.setsAnalyzeFileInputSaveToDB(Integer.valueOf(prop.getProperty("AnalyzeFileInputSaveToDB")));
	      ((Logger) Intermediate.getloggerObject()).info("Config data successfully read.");		
	      //System.out.println("Output1     "+Intermediate.getAnalyzeTextInputSaveToDB());
	      //System.out.println("Output2     "+Intermediate.getAnalyzeFileInputSaveToDB());
	      ((Logger) Intermediate.getloggerObject()).info("Config data successfully set.");		
	} catch (IOException ex) {
		ex.printStackTrace();
	      ((Logger) Intermediate.getloggerObject()).error("Error reading config file! "+ex);	
	} finally {
		if (input != null) {
			try {
				input.close();
			} catch (IOException e) {
				e.printStackTrace();
			      ((Logger) Intermediate.getloggerObject()).error("Error reading config file! "+e);
			}
		}
	}

    
}

	public static  void WriteConfig () {
	
	Properties prop = new Properties();
	OutputStream output = null;

	try {
		output = new FileOutputStream("src/AIpackage/AIpackage.properties");
		// set the properties value
		prop.setProperty("AnalyzeTextInputSaveToDB", Integer.toString(Intermediate.getAnalyzeTextInputSaveToDB() ));
		prop.setProperty("AnalyzeFileInputSaveToDB", Integer.toString(Intermediate.getAnalyzeFileInputSaveToDB() ));

		// save properties to project root folder
		prop.store(output, null);
	      ((Logger) Intermediate.getloggerObject()).info("Config file written successfully! ");	
	} catch (IOException ex) {
		ex.printStackTrace();
	      ((Logger) Intermediate.getloggerObject()).error("Error writing config file! "+ex);	
	} finally {
		if (output != null) {
			try {
				output.close();
			} catch (IOException e) {
				e.printStackTrace();
			      ((Logger) Intermediate.getloggerObject()).error("Error writing config file! "+e);
			}
		}
	}	
	}
}