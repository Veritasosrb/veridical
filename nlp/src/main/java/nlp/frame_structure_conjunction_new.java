package nlp;

//imported matcher,pattern to use concept of regular expressions in java
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.CoreSentence;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.semgraph.SemanticGraphCoreAnnotations;
import edu.stanford.nlp.util.CoreMap;

import java.util.*;
import java.util.Map.Entry;

//public class accessbile everywhere consist code to check whether the sentence is active voice or not.
public class frame_structure_conjunction_new {
	// First regular expression consist of compulsory dependancy called as
	// nsubj-nominal subject(is a noun phrase which is the syntactic subject of a
	// clause)
	
	private static final String REGEX = "(.*)(\\bcc\\b)(.*)";
	// The general format of active voice is subject(Noun)+main verb+object(NN).
	// So created regular expression consisting noun+verb+noun to check whether
	// sentence is in active voice or not.
	// here .* is used to take into consideration the words that would occur in
	// middle of structure.
	private static final String REGEXcombine = "(NN)(.*)(CC)(.*)(NN)";
	private static final String REGEX2 = "(VB)";
	private static final String REGEX3 = "(CC)";
	
	private static final String REGEX4="(NN)";
	

	// Function consist of actual code to check the voice of sentence with
	// parameters consist of enhanced plus plus dependency and pos tags of each
	// sentence
	public void frame_conjunction_new(String sl, String k1) throws Exception {
		// Pattern of frame for active voice
		Scanner s = new Scanner(
				"Conjunction       cc\n" + "Actor_for_verb_1       nsubj(\n" + "Actor_for_verb_1		agent\n"
						+ "Actor_for_verb_1		 compound\n" + "Actor_for_verb_2       nsubj(\n"
						+ "Actor_for_verb_2		agent\n" + "Actor_for_verb_2		 compound\n"
						+ "Object_for_verb_1		 dobj\n" + "Object_for_verb_1	 nsubjpass\n"
						+ "Object_for_verb_2		 dobj\n" + "Object_for_verb_2	 nsubjpass\n");
		// map used to store the pattern of active voice comprising of string for key
		// and List<String> for its values as we can anyone out of multiple.
		Properties props = new Properties();
		props.setProperty("annotators", "tokenize, ssplit, pos, lemma, ner,depparse,kbp,parse,dcoref,sentiment");
		props.setProperty("ner.useSUTime", "false");
		StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
		Map<String, List<String>> map = new LinkedHashMap<String, List<String>>();
		// Storing the input into map temporary stored in Scanner object
		while (s.hasNext()) {
			String key = s.next();
			if (!map.containsKey(key))
				map.put(key, new LinkedList<String>());
			map.get(key).add(s.next());
		}
		// Printing out key value pairs from map
		for (Map.Entry<String, List<String>> mapElement : map.entrySet()) {
			String key = mapElement.getKey();
			List<String> value = mapElement.getValue();
			for (String v : value) {
				System.out.println(key + " : " + v);
			}
		}
		System.out.println();
		// Declared a map for storing output frame
		// Map<String, String> mapoutput = new LinkedHashMap<String, String>();
		// Pattern class is a compiled representation of a regular expression stored in
		// string above
		// Compiled first regular expression comprising of compulsory dependancy tag i.e
		// nsubj
		Pattern p = Pattern.compile(REGEX, Pattern.CASE_INSENSITIVE);
		// Matcher class is kind of an engine that performs match operations on a
		// character sequence by interpreting a Pattern.
		Matcher m = p.matcher(sl); // get a matcher object

		// Compiled second regular expression consisting of subject+verb+object required
		// for sentence to be in active form.
		Pattern p1 = Pattern.compile(REGEX2, Pattern.CASE_INSENSITIVE);
		Pattern p2 = Pattern.compile(REGEX3, Pattern.CASE_INSENSITIVE);
		Pattern p3=Pattern.compile(REGEX4,Pattern.CASE_INSENSITIVE);
		Pattern combine=Pattern.compile(REGEXcombine,Pattern.CASE_INSENSITIVE);
		Matcher mcombine=combine.matcher(sl);
		// find() function attempts to find second subsequence of the input sequence
		// that matchesthe pattern.
		// Here false indicates that there no nsubj present in sentence so need to check
		// further for second regular expression and no active voice present in sentence
		if (m.find() == false) {
			System.out.print("No active voice present in sentence : " + k1);
			System.out.println();
		}
		// indicates nsubj is present in sentence
		else  {
			
			// As m.find() finds second subsequence so we need to set to first point so we
			// do reset of matcher object
			m.reset();
			// After resetting we again match pattern with string
			m = p.matcher(sl);
			SortedMap<Integer, Integer> map1 = new TreeMap<Integer, Integer>();
			SortedMap<Integer, Integer> map2 = new TreeMap<Integer, Integer>();
			SortedMap<Integer, Integer> map3 =new TreeMap<Integer,Integer>();
			// checks every subsequence present in sentence for match
			if (m.find()) {
				Matcher r1 = p1.matcher(k1);
				Matcher r2 = p2.matcher(k1);
				Matcher r3 = p3.matcher(k1);
				// unordered_map<Integer,Integer> mp=new map<Integer.Integer>();
				while (r1.find()) {
					map1.put(r1.start(), r1.end());
				}
				while (r2.find()) {
					map2.put(r2.start(), r2.end());

				}
				while(r3.find())
				{
					map3.put(r3.start(), r3.end());
				}
				// System.out.println(sl);
				// System.out.println(k1);
				System.out.println(map1);
				System.out.println(map2);
				System.out.println(map3);
				int a1, b1;
				String op = "\0";
				for (SortedMap.Entry<Integer, Integer> entry1 : map2.entrySet()) {
					a1 = entry1.getKey();
					b1 = entry1.getValue();
					System.out.println(a1 + " " + b1);
					int cnt = 1;
					Iterator<SortedMap.Entry<Integer, Integer>> iterator = map1.entrySet().iterator();
					Iterator<SortedMap.Entry<Integer, Integer>> iterator1 = map1.entrySet().iterator();

					while (iterator.hasNext()) {
						Map.Entry<Integer, Integer> entry = iterator.next();
						int a = entry.getKey();
						int b = entry.getValue();
						int cnt1 = cnt;
						while (iterator1.hasNext() && cnt1 != 0) {
							Map.Entry<Integer, Integer> entryk = iterator1.next();
							cnt1 = cnt1 - 1;
						}

						while (iterator1.hasNext()) {
							Map.Entry<Integer, Integer> entry2 = iterator1.next();
							System.out.println(a + " " + b + " " + entry2.getKey() + " " + entry2.getValue());
							if ((a1 > a && a1 < entry2.getKey()) && (b1 > b && b1 < entry2.getValue())) {
								String sp = k1.substring(0, a);
								int k = sp.lastIndexOf(' ');
								String sp1 = sp.substring(0, k - 1);
								int k2 = sp1.lastIndexOf(' ');

								if (k2 == -1) {
									if (op == "\0") {
										op = k1.substring(0, entry2.getValue() + 1);
									} else {
										op += k1.substring(0, entry2.getValue() + 1);
									}
									op += ".";
									System.out.println(k1.substring(0, entry2.getValue() + 1));
								} else {
									if (op == "\0") {
										op = k1.substring(k2 + 1, entry2.getValue() + 1);
									} else {
										op += k1.substring(k2 + 1, entry2.getValue() + 1);
									}
									op += ".";
									System.out.println(k1.substring(k2 + 1, entry2.getValue() + 1));
								}

							}

						}
						cnt++;
						iterator1 = map1.entrySet().iterator();

					}

				}
				System.out.println(op);
				for (SortedMap.Entry<Integer, Integer> entry1 : map2.entrySet()) {
					a1 = entry1.getKey();
					b1 = entry1.getValue();
					System.out.println(a1 + " " + b1);
					int cnt = 1;
					Iterator<SortedMap.Entry<Integer, Integer>> iterator1 = map3.entrySet().iterator();
					Iterator<SortedMap.Entry<Integer, Integer>> iterator11 = map3.entrySet().iterator();

					while (iterator1.hasNext()) {
						Map.Entry<Integer, Integer> entry = iterator1.next();
						int a = entry.getKey();
						int b = entry.getValue();
						int cnt1 = cnt;
						while (iterator11.hasNext() && cnt1 != 0) {
							Map.Entry<Integer, Integer> entryk = iterator11.next();
							cnt1 = cnt1 - 1;
						}

						while (iterator11.hasNext()) {
							Map.Entry<Integer, Integer> entry2 = iterator11.next();
							System.out.println(a + " " + b + " " + entry2.getKey() + " " + entry2.getValue());
							if ((a1 > a && a1 < entry2.getKey()) && (b1 > b && b1 < entry2.getValue())) {
								String sp = k1.substring(0, a);
								int k = sp.lastIndexOf(' ');
								String sp1 = sp.substring(0, k - 1);
								int k2 = sp1.lastIndexOf(' ');

								if (k2 == -1) {
									if (op == "\0") {
										op = k1.substring(0, entry2.getValue() + 1);
									} else {
										op += k1.substring(0, entry2.getValue() + 1);
									}
									op += ".";
									System.out.println(k1.substring(0, entry2.getValue() + 1));
								} else {
									if (op == "\0") {
										op = k1.substring(k2 + 1, entry2.getValue() + 1);
									} else {
										op += k1.substring(k2 + 1, entry2.getValue() + 1);
									}
									op += ".";
									System.out.println(k1.substring(k2 + 1, entry2.getValue() + 1));
								}

							}

						}
						cnt++;
						iterator11 = map1.entrySet().iterator();

					}

				}
				System.out.println(op);
				String opf = op.replaceAll("([A-Z]*)\\b", "");
				System.out.println(opf);

				// op="\0";
				// String text =fd.retrieve_data();
				// String text="User opens the item page. If item is out-of-stock,then display
				// out-of-stock message,else show quantity box.";
				// String text="He had to continue his work because he was late.";
				// String text="User opens the item page.";
				// set up pipeline properties

				// Initialize an Annotation with some text to be annotated. The text is the
				// argument to the constructor.
				Annotation annotation;
				annotation = new Annotation(opf);

				// run all the selected Annotators on this text
				pipeline.annotate(annotation);
				Map<String, String> mapoutput = new LinkedHashMap<String, String>();
				List<CoreMap> sentences = annotation.get(CoreAnnotations.SentencesAnnotation.class);
				for (CoreMap sentence : sentences) {
					System.out.println("The sentence is "+sentence);
					// Enhanced ++ dependencies present in sentence
					System.out.println("The sentence Enhanced plus plus dependencies in typed dependancy format are:");
					System.out.println(
							sentence.get(SemanticGraphCoreAnnotations.EnhancedPlusPlusDependenciesAnnotation.class)
									.toString(SemanticGraph.OutputFormat.LIST));
					String epp = sentence.get(SemanticGraphCoreAnnotations.EnhancedPlusPlusDependenciesAnnotation.class)
							.toString(SemanticGraph.OutputFormat.LIST);
					for (Map.Entry<String, List<String>> mapElement : map.entrySet()) {

						// extracting key
						String key = mapElement.getKey();

						// Finding the values for that key
						List<String> value = mapElement.getValue();
						// Traversing through each value for ,that key
						// Finding index position of the string value end to store its respective value
						// in new map i.e final final frame for that sentence
						// If the dependency gets match at first value only than we do not consider
						// other values
						for (String v : value) {

							int l = v.length();
							int index = 0;
							if (epp.contains(v)) {
								index = epp.indexOf(v);
								index = index + l;

								String brac = ")";

								int index2 = epp.indexOf(brac, index);
								// System.out.println(index2);
								// System.out.println( sl.substring(index, index2+1));
								if (!mapoutput.containsKey(key)) {
									mapoutput.put(key, epp.substring(index, index2 + 1));
								}

							}
						}

					}
					// Printing out the frame stored in map format
					System.out.println("Frame Structure : \n" + mapoutput);
					System.out.println();
					// clearly map for storing frame for next sentence
					mapoutput.clear();

				}
			
			}			
			
		}
		System.out.println("--------------------------------------------------");
	}

}

