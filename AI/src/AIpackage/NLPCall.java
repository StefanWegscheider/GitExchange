package AIpackage;
import edu.stanford.nlp.io.IOUtils;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreAnnotations.NamedEntityTagAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.PartOfSpeechAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.*;
import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.semgraph.SemanticGraphCoreAnnotations.CollapsedCCProcessedDependenciesAnnotation;
import edu.stanford.nlp.simple.*;
import edu.stanford.nlp.trees.*;
import edu.stanford.nlp.trees.TreeCoreAnnotations.TreeAnnotation;
import edu.stanford.nlp.util.CoreMap;
import edu.stanford.nlp.util.PropertiesUtils;
import edu.stanford.nlp.util.StringUtils;
import edu.stanford.nlp.dcoref.CorefChain;
import edu.stanford.nlp.dcoref.CorefCoreAnnotations.CorefChainAnnotation;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class NLPCall {
	/** This class demonstrates building and using a Stanford CoreNLP pipeline. */
	  /** Usage: java -cp "*" StanfordCoreNlpDemo [inputFile [outputTextFile [outputXmlFile]]] 
	 * @throws IOException */
    public static  void Base(String InputFile, String OutputFileText, String OutputFileXML, String textinput) throws IOException {
    	if (Intermediate.getsObject() != null) {Intermediate.getsObject().close();}
    	else {} //eventuell vorhandenes HTML Viewer Fenster schliessen
    String text="Die Welt ist voller schöner Menschen";
    	if (textinput != "") { text=textinput; } else {}
   	// set up optional output files
    PrintWriter out;
    if (OutputFileText != null) {
      out = new PrintWriter(OutputFileText);
    } else {
      out = new PrintWriter(System.out);
    }
    PrintWriter xmlOut = null;
    if (OutputFileXML != null) {
      xmlOut = new PrintWriter(OutputFileXML);
    }
    System.out.println(out+OutputFileText+xmlOut+OutputFileXML);
   	Annotation document = new Annotation(text);
        Properties germanProperties = StringUtils.argsToProperties(new String[]{"-props", "StanfordCoreNLP-german.properties"});
        StanfordCoreNLP pipeline = new StanfordCoreNLP(germanProperties);
        System.out.println(germanProperties);
        System.out.println(document);
        System.out.println(text);
        ((RootLayoutController) Intermediate.getscObject()).SetAnalyzeInputOutputText(text+"\n");
    	pipeline.annotate(document);  
    	
        // this prints out the results of sentence analysis to file(s) in good formats
       pipeline.prettyPrint(document, out);
    	pipeline.xmlPrint(document, xmlOut);
    	
        // these are all the sentences in this document
        // a CoreMap is essentially a Map that uses class objects as keys and has values with custom types
        List<CoreMap> sentences = document.get(SentencesAnnotation.class);

        for(CoreMap sentence: sentences) {
          // traversing the words in the current sentence
          // a CoreLabel is a CoreMap with additional token-specific methods
          for (CoreLabel token: sentence.get(TokensAnnotation.class)) 
          {
            // this is the text of the token
            String word = token.get(TextAnnotation.class);
            // this is the POS tag of the token
            String pos = token.get(PartOfSpeechAnnotation.class);
            // this is the NER label of the token
            String ne = token.get(NamedEntityTagAnnotation.class);
            
            System.out.println("word: " + word + " pos: " + pos + " ne:" + ne);
            ((RootLayoutController) Intermediate.getscObject()).SetAnalyzeInputOutputText("word: " + word + " pos: " + pos + " ne:" + ne+"\n");
          }

          // this is the parse tree of the current sentence
          Tree tree = sentence.get(TreeAnnotation.class);
          System.out.println("parse tree:\n" + tree);
          ((RootLayoutController) Intermediate.getscObject()).SetAnalyzeInputOutputText("\n"+"parse tree:\n" + tree+"\n");
          

          // this is the Stanford dependency graph of the current sentence
          SemanticGraph dependencies = sentence.get(CollapsedCCProcessedDependenciesAnnotation.class);
          System.out.println("dependency graph:\n" + dependencies);
          ((RootLayoutController) Intermediate.getscObject()).SetAnalyzeInputOutputText("dependency graph:\n" + dependencies+"\n");
        }

        // This is the coreference link graph
        // Each chain stores a set of mentions that link to each other,
        // along with a method for getting the most representative mention
        // Both sentence and token offsets start at 1!
        Map<Integer, CorefChain> graph = 
            document.get(CorefChainAnnotation.class);
       
       //Ruft Methode auf um lokales xml file in einem Fenster anzuzeigen
  	  HomeBase.HTMLShowFromFile("/Users/stefanwegscheider/eclipse/Workspace/AI/src/application/MyHomeBase/xmlOutput.xml", "Results from Output File");
        
    }  
}