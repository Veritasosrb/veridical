package nlp;

import java.io.File;
import java.net.URL;
import java.util.Properties;

import org.apache.poi.util.SystemOutLogger;

import edu.mit.jwi.IDictionary;
import edu.mit.jwi.item.IIndexWord;
import edu.mit.jwi.item.ISynset;
import edu.mit.jwi.item.IWord;
import edu.mit.jwi.item.IWordID;
import edu.mit.jwi.item.POS;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.mit.jwi.Dictionary;

public class Get_Synonyms
{

	public void get_synonyms() throws Exception 
	{

		// construct the URL to the Wordnet dictionary directory
		//String wnhome = "C:\\Program Files (x86)\\WordNet\\2.1";
		String wnhome = "/usr/local/WordNet-3.0";
		String path = wnhome + File.separator + "dict";
		URL url = new URL("file", null, path);
		// construct the dictionary object and open it
		System.out.println("url from wnhome: " + url);
		IDictionary dict = new Dictionary(url);
		dict.open();

		// Object created to fetch data
		Fetch_data fd = new Fetch_data();
		// method to retrieve data from excel sheet
		String text = fd.retrieve_data();
		// set up pipeline properties
		Properties props = new Properties();
		// set the list of annotators to run
		props.setProperty("annotators", "tokenize");
		// example of how to customize the PTBTokenizer (these are just random example
		// settings!!)
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
			IIndexWord idxWord = dict.getIndexWord(tok.word(),POS.NOUN);
			if (idxWord == null) 
			{
				System.out.print("The token is not a noun");
			} 
			else 
			{
				IWordID wordID = idxWord.getWordIDs().get(0); // 1st meaning
				IWord word = dict.getWord(wordID);
				ISynset synset = word.getSynset();

				// iterate over words associated with the synset
				for (IWord w : synset.getWords())
				{
					System.out.print(w.getLemma() + ',');
				}
			}
			System.out.println();

		}

	}
}