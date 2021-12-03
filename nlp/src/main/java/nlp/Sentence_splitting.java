package nlp;
//To use Standford NLP
import edu.stanford.nlp.pipeline.*;
import java.util.Properties;


public class Sentence_splitting 
{
	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub
		  try 
		  { 
			  // object created to fetch the data
			  Fetch_data fd=new Fetch_data();
			  // method to retrive data from excel sheet
			  String text=fd.retrieve_data();
			  // set up pipeline properties
		      Properties props = new Properties();
		      // set the list of annotators to run
		      props.setProperty("annotators", "tokenize,ssplit");
		      // build pipeline
		      StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
		      // create a document object
		      CoreDocument doc = new CoreDocument(text);
		      // annotate
		      pipeline.annotate(doc);
		      // display sentences
		      for (CoreSentence sent : doc.sentences()) {
		          System.out.println(sent.text());
		      }
		  }
		  catch(Exception e)
		  {
			  e.printStackTrace(); 
		  }
	}
}
	