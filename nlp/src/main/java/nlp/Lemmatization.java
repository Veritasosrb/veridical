package nlp;
import edu.stanford.nlp.ling.*;
import edu.stanford.nlp.pipeline.*;
import java.io.*;  

import java.io.FileReader;
import java.util.Properties;

import com.opencsv.CSVReader;

public class Lemmatization {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try
		{
		  //method to retrive data from excel sheet
		  Fetch_data fd=new Fetch_data();
	      String text=fd.retrieve_data();
	      // set up pipeline properties
	      Properties props = new Properties();
	      // set the list of annotators to run
	      props.setProperty("annotators", "tokenize,ssplit,pos,lemma");
	      // build pipeline
	      StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
	      // create a document object
	      CoreDocument document = new CoreDocument(text);
	      pipeline.annotate(document);
	      // display tokens
	      for (CoreLabel tok : document.tokens()) {
	        System.out.println(String.format("%s %s", tok.word(), tok.lemma()));
	      }
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

}
