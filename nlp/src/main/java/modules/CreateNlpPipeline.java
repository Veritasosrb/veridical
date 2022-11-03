package modules;

import java.io.File;
import static constants.Constants.*;
import java.io.FileWriter;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;
import java.util.Vector;

import com.opencsv.CSVWriter;

import edu.stanford.nlp.coref.CorefCoreAnnotations;
import edu.stanford.nlp.coref.data.CorefChain;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.IndexedWord;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.CoreSentence;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.semgraph.SemanticGraphCoreAnnotations;
import edu.stanford.nlp.semgraph.SemanticGraphEdge;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreeCoreAnnotations;
import edu.stanford.nlp.util.CoreMap;

// This class demonstrates building and using a Stanford CoreNLP pipeline.
public class CreateNlpPipeline {

	// To generate different dependencies on input user story
	public void dependencies(String functionality, String userStory) throws Exception {
		StanfordCoreNLP pipeline = null;
		CSVWriter writer = null;

		// Creating a file for input
		String inputFilePath = CommonPage.getInputFilePath(functionality);
		File file = new File(inputFilePath);
		Scanner sc = new Scanner(file);
		sc.useDelimiter(Z_DELIMETER);
		userStory = sc.next();
		sc.close();

		// Set up pipeline properties
		Properties property = new Properties();
		property.setProperty("annotators", "tokenize, ssplit, pos, lemma, ner,depparse,kbp,parse,dcoref,sentiment");
		property.setProperty("ner.useSUTime", "false");

		try {
			pipeline = new StanfordCoreNLP(property);
		} catch (OutOfMemoryError oome) {
			System.err.println("Array size too large");
			System.err.println("Max JVM memory: " + Runtime.getRuntime().maxMemory());
		}

		// Initialize an Annotation with some text to be annotated
		Annotation annotation;
		annotation = new Annotation(userStory);
		pipeline.annotate(annotation);
		System.out.println();
		System.out.println("The top level annotation");
		System.out.println(annotation.toShorterString());
		System.out.println();

		try {
			writer = new CSVWriter(new FileWriter(CommonPage.getIntermediateFilePath(functionality, DEPENDENCIES)));
			String line[] = { "Test case ID", "User Story", "Keys of sentence", "Sentence analysis",
					"Basic Dependencies", "Enhanced Dependencies", "Enhanced Plus Plus dependencies", "Sentiment" };
			writer.writeNext(line);

			List<CoreMap> sentences = annotation.get(CoreAnnotations.SentencesAnnotation.class);
			for (CoreMap sentence : sentences) {
				// Returns Collection of keys currently held in this map
				System.out.println("The keys of the sentence's CoreMap are:");
				System.out.println(sentence.keySet());
				System.out.println();
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

				// Identify basic dependencies present in sentence
				System.out.println("The sentence basic dependencies in typed dependancy format are:");
				System.out.println(sentence.get(SemanticGraphCoreAnnotations.BasicDependenciesAnnotation.class)
						.toString(SemanticGraph.OutputFormat.LIST));

				// Identify enhanced dependencies present in sentence
				System.out.println("The sentence Enhanced dependencies in typed dependancy format are:");
				System.out.println(sentence.get(SemanticGraphCoreAnnotations.EnhancedDependenciesAnnotation.class)
						.toString(SemanticGraph.OutputFormat.LIST));

				// Enhanced ++ dependencies present in sentence
				System.out.println("The sentence Enhanced plus plus dependencies in typed dependancy format are:");
				System.out
						.println(sentence.get(SemanticGraphCoreAnnotations.EnhancedPlusPlusDependenciesAnnotation.class)
								.toString(SemanticGraph.OutputFormat.LIST));

				// Basic dependencies present in sentence in graph structure
				System.out.println("Basic dependencies present in sentence in graph format are:");
				SemanticGraph graph = sentence.get(SemanticGraphCoreAnnotations.BasicDependenciesAnnotation.class);
				System.out.println(graph);

				// Print out dependency structure around one word
				IndexedWord node = graph.getNodeByIndexSafe(3);
				System.out.println("Printing dependencies around \"" + node.word() + "\" index " + node.index());
				List<SemanticGraphEdge> edgeList = graph.getIncomingEdgesSorted(node);

				// Identifies parent of node.
				if (!edgeList.isEmpty()) {
					int head = edgeList.get(0).getGovernor().index();
					String headWord = edgeList.get(0).getGovernor().word();
					String deprel = edgeList.get(0).getRelation().toString();
					System.out.println("Parent is word \"" + headWord + "\" index " + head + " via " + deprel);
				} else {
					System.out.println("Parent is ROOT via root");
				}

				// Identifies children of that node
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
				String linet[] = { "CORTEX", userStory, sentence.keySet().toString(), sentence.toShorterString(),
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

			// To find all expressions that refer to the same entity in a text.
			System.out.println("---");
			System.out.println("coref chains");
			for (CorefChain cc : annotation.get(CorefCoreAnnotations.CorefChainAnnotation.class).values()) {
				System.out.println("\t" + cc);
			}
			System.out.println();
		} finally {
			writer.close();
		}
	}

	// To preprocess input user story and identify parts of speech.
	public Vector<String> partsOfSpeechTokens(String functionality, String userStory) throws Exception {
		// Display tokens
		String inputFilePath = CommonPage.getInputFilePath(functionality);
		File file = new File(inputFilePath);
		Scanner sc = new Scanner(file);
		sc.useDelimiter(Z_DELIMETER);
		userStory = sc.next();
		sc.close();

		Vector<String> listTokenWord = new Vector<String>();
		String wordToken = EMPTY_STRING;
		try {
			// Set up pipeline properties
			Properties property = new Properties();
			property.setProperty("annotators", "tokenize,ssplit,pos");

			// Create a document object
			StanfordCoreNLP pipeline = new StanfordCoreNLP(property);
			CoreDocument document = new CoreDocument(userStory);
			pipeline.annotate(document);

			for (CoreSentence sent : document.sentences()) {
				for (CoreLabel tok : sent.tokens()) {
					if (tok.word().equals(COMMA)) {
						wordToken += tok.word() + SPACE;
					} else {
						wordToken += tok.word() + SPACE + tok.tag() + SPACE;
					}
				}
				listTokenWord.add(wordToken);
				wordToken = "\0";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listTokenWord;
	}
}