package nlp;
//To use standford NLP
import edu.stanford.nlp.ling.*;
import edu.stanford.nlp.pipeline.*;
import java.util.Properties;
public class tokenization 
{
	public static void main(String[] args) throws Exception{
	  try 
	  { 
		  //Object created to fetch data
		  Fetch_data fd=new Fetch_data();
		  //method to retrieve data from excel sheet
		  //String text=fd.retrieve_data();
		  String text="Having a progress upoon a time going to market on once having";
	      // set up pipeline properties
	      Properties props = new Properties();
	      // set the list of annotators to run
	      props.setProperty("annotators", "tokenize,ssplit,pos");
	      // example of how to customize the PTBTokenizer (these are just random example settings!!)
	      props.setProperty("tokenize.options", "splitHyphenated=false,americanize=false");
	      // build pipeline
	      StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
	      // create a document object
	      CoreDocument doc = new CoreDocument(text);
	      // annotate
	      pipeline.annotate(doc);
	      // display tokens
	      for (CoreLabel tok : doc.tokens()) 
	      {
	        System.out.println(String.format("%s\t%d\t%d\t%s", tok.word(), tok.beginPosition(), tok.endPosition(),tok.tag()));
	      }
	   }
	   catch (Exception e)
	   { 
	       e.printStackTrace(); 
	   } 
	}
}