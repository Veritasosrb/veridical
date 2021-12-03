package nlp;

import java.io.*;
import java.util.*;
import com.opencsv.CSVWriter;
import edu.stanford.nlp.coref.CorefCoreAnnotations;
import edu.stanford.nlp.coref.data.CorefChain;
import edu.stanford.nlp.io.*;
import edu.stanford.nlp.ling.*;
import edu.stanford.nlp.pipeline.*;
import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.semgraph.SemanticGraphCoreAnnotations;
import edu.stanford.nlp.semgraph.SemanticGraphEdge;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.trees.*;
import edu.stanford.nlp.util.*;
// import net.didion.jwnl.JWNL;

// This class demonstrates building and using a Stanford CoreNLP pipeline.
public class CreateNlpPipeline {
	
	String user="";
	String password="";
	public void setUser(String user){
		this.user=user;
		String[] user1= {this.user};
		
		
		
		System.out.println("user"+user);
		
		
	}
	public void setPassword(String passwprd){
		this.password=password;
		System.out.println (password);
	}
	public String getUser(){
		
		String[] user1= {this.user};
		return  this.user;
		
	}
	
	
	void dependencies(String functionality,String userStory) throws Exception {

		// Reads text from a character-input stream, buffering characters so as to
		// provide for the efficient reading of characters, arrays, and lines.
		
		File file = new File("./input/product_"+functionality+"_input.txt");  // replace cortex everywhere
	    Scanner sc = new Scanner(file);
	  
	    // we just need to use \\Z as delimiter
	    sc.useDelimiter("\\Z");
	  userStory=sc.next();
				Properties props = new Properties();
		props.setProperty("annotators", "tokenize, ssplit, pos, lemma, ner,depparse,kbp,parse,dcoref,sentiment");
		props.setProperty("ner.useSUTime", "false");
		
		StanfordCoreNLP pipeline = null;
		try {
		pipeline = new StanfordCoreNLP(props);
		} catch (OutOfMemoryError oome) {
            //Log the info
            System.err.println("Array size too large");
            System.err.println("Max JVM memory: " + Runtime.getRuntime().maxMemory());
        }

		// Initialize an Annotation with some text to be annotated. The text is the
		// argument to the constructor.
		Annotation annotation;
		System.out.println("Usr Stroy"+userStory);
		annotation = new Annotation(userStory);

		// run all the selected Annotators on this text
		pipeline.annotate(annotation);

		
		// Access the Annotation in code
		// The toString() method on an Annotation just prints the text of the Annotation
		// The toShorterString() method Attempt to provide a briefer and more human
		// readable String for the contents ofa CoreMap
		System.out.println();
		System.out.println("The top level annotation");
		// It prints entire text, tokens present in it and its position in text
		// Prints entity mentions and coref chains

		System.out.println(annotation.toShorterString());
		System.out.println();

		// An Annotation is a Map with Class keys for the linguistic analysis types.
		// You can get and use the various analyses individually.
		// CoreAnnotations.SentencesAnnotation.class is the CoreMap key for getting the
		// sentences contained by an annotation
		// CoreMap is base type for all annotatable core objects
		CSVWriter writer = new CSVWriter(new FileWriter("./intermediate/UserStoryJiraNewOutputtrial2.csv")); // changed t to small in trial
		String line[] = { "Test case ID", "User Story", "Keys of sentence", "Sentence analysis", "Basic Dependencies",
				"Enhanced Dependencies", "Enhanced Plus Plus dependencies", "Sentiment" };
		writer.writeNext(line);

		List<CoreMap> sentences = annotation.get(CoreAnnotations.SentencesAnnotation.class);
		for (CoreMap sentence : sentences) {
			// Returns Collection of keys currently held in this map
			System.out.println("The keys of the sentence's CoreMap are:");
			System.out.println(sentence.keySet());
			System.out.println();
			// It returns the plain text,tokens present in it,start and end postion of
			// sentence,sentence index.
			// Also returns Normal Dependencies,Basic Dependencies,Collapsed
			// Dependencies,Enhanced Dependencies,Enhanced plus plus dependencies,KBP
			// Triples,Sentiment annotated tree
			System.out.println("The sentence is:");
			System.out.println(sentence.toShorterString());
			System.out.println();

			// It returns text,begin and end positon,sentence index,parts of
			// speech,Lemma,Named Entity tag,Coarse and fine grained named entity,Coref
			// Mention Index,Sentiment class
			System.out.println("The sentence tokens are:");
			for (CoreMap token : sentence.get(CoreAnnotations.TokensAnnotation.class)) {
				System.out.println(token.toShorterString());
			}
			// To print parse tree for sentence
			Tree tree = sentence.get(TreeCoreAnnotations.TreeAnnotation.class);
			System.out.println();
			System.out.println("The sentence parse tree is:");
			tree.pennPrint(System.out);
			System.out.println();

			// Basic dependencies present in sentence
			// The basic typed dependencies are all binary relations: a grammatical relation
			// holds between a governor (also known as a regent or a head) and a
			// dependent.Each
			// word in the sentence is the dependent of exactly one thing, either another
			// word in the sentence or the distingushed �ROOT-0� token
			// For E.g nsubj-A nominal subject is a noun phrase which is the syntactic
			// subject of a clause
			System.out.println("The sentence basic dependencies in typed dependancy format are:");
			System.out.println(sentence.get(SemanticGraphCoreAnnotations.BasicDependenciesAnnotation.class)
					.toString(SemanticGraph.OutputFormat.LIST));

			// Enhanced dependencies present in sentence which contain additional and
			// augmented relations that explicitly capture otherwise implicit relation
			// between content word
			// The enhanced English UD representation aims to make
			// implicit relations between content words more explicit by
			// adding relations and augmenting relation names

			// This helps to disambiguate the type of modifier and further facilitates the
			// extraction of relationships
			// between content words, especially if a system incorporates
			// dependency information using very simple methods such as
			// by only considering paths between two nodes
			System.out.println("The sentence Enhanced dependencies in typed dependancy format are:");
			System.out.println(sentence.get(SemanticGraphCoreAnnotations.EnhancedDependenciesAnnotation.class)
					.toString(SemanticGraph.OutputFormat.LIST));

			// Enhanced ++ dependencies present in sentence
			System.out.println("The sentence Enhanced plus plus dependencies in typed dependancy format are:");
			System.out.println(sentence.get(SemanticGraphCoreAnnotations.EnhancedPlusPlusDependenciesAnnotation.class)
					.toString(SemanticGraph.OutputFormat.LIST));

			// Basic dependencies present in sentence in graph structure
			System.out.println("Basic dependencies present in sentence in graph format are:");
			SemanticGraph graph = sentence.get(SemanticGraphCoreAnnotations.BasicDependenciesAnnotation.class);
			System.out.println(graph);

			// Print out dependency structure around one word
			// This give some idea of how to navigate the dependency structure in a
			// SemanticGraph
			// IndexedWord class provides a CoreLabel that uses itsDocIDAnnotation,
			// SentenceIndexAnnotation, and IndexAnnotation to
			// implementComparable/compareTo, hashCode, and equals.
			// getNodeByIndexSafe() returns the first IndexedWord in this SemanticGraph
			// having the given integer index and returns null if the index does not exist.
			IndexedWord node = graph.getNodeByIndexSafe(3);
			System.out.println("Printing dependencies around \"" + node.word() + "\" index " + node.index());
			List<SemanticGraphEdge> edgeList = graph.getIncomingEdgesSorted(node);
			// getIncomingEdgesSorted() returns incoming edges to that node in sorted manner
			// Identifies parent of node.
			// getGovenor() returns left part node of edge
			if (!edgeList.isEmpty()) {
				int head = edgeList.get(0).getGovernor().index();
				String headWord = edgeList.get(0).getGovernor().word();
				String deprel = edgeList.get(0).getRelation().toString();
				System.out.println("Parent is word \"" + headWord + "\" index " + head + " via " + deprel);
			} else {
				System.out.println("Parent is ROOT via root");
			}
			// getOutEdgesSorted() returns outgoing edges to that node in sorted manner
			// Identifies children of that node
			// getDependent() returns right part node of edge
			edgeList = graph.getOutEdgesSorted(node);
			System.out.println(edgeList);
			for (SemanticGraphEdge edge : edgeList) {
				String depWord = edge.getDependent().word();
				int depIdx = edge.getDependent().index();
				String deprel = edge.getRelation().toString();
				System.out.println("Child is \"" + depWord + "\" (" + depIdx + ") via " + deprel);
			}
			System.out.println();
			System.out.println("The sentence overall sentiment rating is "
					+ sentence.get(SentimentCoreAnnotations.SentimentClass.class));
			System.out.println();
			String linet[] = { "CORTEX-235", userStory, sentence.keySet().toString(), sentence.toShorterString(),
					sentence.get(SemanticGraphCoreAnnotations.BasicDependenciesAnnotation.class)
							.toString(SemanticGraph.OutputFormat.LIST),
					sentence.get(SemanticGraphCoreAnnotations.EnhancedDependenciesAnnotation.class)
							.toString(SemanticGraph.OutputFormat.LIST),
					sentence.get(SemanticGraphCoreAnnotations.EnhancedPlusPlusDependenciesAnnotation.class)
							.toString(SemanticGraph.OutputFormat.LIST),
					sentence.get(SentimentCoreAnnotations.SentimentClass.class) };
			writer.writeNext(linet);
			writer.flush();

		}

		// Coreference resolution is the task of finding all expressions that refer to
		// the same entity in a text
		System.out.println("---");
		System.out.println("coref chains");
		for (CorefChain cc : annotation.get(CorefCoreAnnotations.CorefChainAnnotation.class).values()) {
			System.out.println("\t" + cc);
		}
		System.out.println();

		// Creating object of class Get_Synonyms class to print the synonyms
		System.out.println("The synonyms of tokens are as follows:");
		Get_Synonyms gt = new Get_Synonyms();
		gt.get_synonyms();
	}
	Vector<String> pos_data(String functionality,String userStory) throws Exception
	{
	    // display tokens
		File file = new File("./input/product_"+functionality+"_input.txt");
	    Scanner sc = new Scanner(file);
	  
	    // we just need to use \\Z as delimiter
	    sc.useDelimiter("\\Z");
	  userStory=sc.next();
	      Vector<String> l=new Vector<String>();
	     // String text="\0";
		  String s="";
		  try { 
			 //method to retrieve data from excel sheet
			// set up pipeline properties
			 
		      Properties props = new Properties();
		      // set the list of annotators to run
		      props.setProperty("annotators", "tokenize,ssplit,pos");
		      // build pipeline
		      StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
		      // create a document object
		      CoreDocument document = new CoreDocument(userStory);
		      pipeline.annotate(document);
	
		      for(CoreSentence sent : document.sentences())
		      {
		      
		      for (CoreLabel tok : sent.tokens()) {
		    	if(tok.word().equals(","))
		    	{
		    		s+=tok.word()+" ";
		    	}
		    	else
		    	{
		        s+=tok.word()+" "+tok.tag()+" ";
		    	}
		        
		      }
		    
		      l.add(s);
		      
		      s="\0";
		      }
		      
		  }
		  catch(Exception e)
		  {
			  e.printStackTrace();
		  }
		return l;
	}
}
