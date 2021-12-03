package nlp;

//imported matcher,pattern to use concept of regular expressions in java
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.*;

//public class accessbile everywhere consist code to check whether the sentence consist of conjunction or not.

public class frame_structure_conjunction {
	// first regular expression consist of compulsory dependancy called as cc
	// coordination is the relation between an element of a conjunct and the
	// coordinating conjunction word of
	// the conjunct.

	private static final String REGEX = "(.*)(cc)(.*)";
	// The general format to check coordination is <verb clause> + conjunction +
	// <verb clause>
	// or <noun clause> + conjunction + <noun clause>
	private static final String REGEX1 = "(.*)(VB)(.*)(CC)(.*)(VB)(.*)";
	private static final String REGEX2 = "(.*)(NN)(.*)(CC)(.*)(NN)(.*)";

	// Function consist of actual code to check the voice of sentence with
	// parameters consist of enhanced plus plus dependency and pos tags of each
	// sentence
	public Map<String,String> frame_conjunction(String sl, String k1) throws Exception {
		// Pattern of frame for conjunction
		Scanner s = new Scanner(
				"Conjunction       cc\n" + "Actor_for_verb_1       nsubj(\n" + "Actor_for_verb_1		agent\n"
						+ "Actor_for_verb_1		 compound\n" + "Actor_for_verb_2       nsubj(\n"
						+ "Actor_for_verb_2		agent\n" + "Actor_for_verb_2		 compound\n"
						+ "Object_for_verb_1		 dobj\n" + "Object_for_verb_1	 nsubjpass\n"
						+ "Object_for_verb_2		 dobj\n" + "Object_for_verb_2	 nsubjpass\n");
		// map used to store the pattern of passive voice comprising of string for key
		// and List<String> for its values as we can anyone out of multiple.
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

			// Finding the value
			List<String> value = mapElement.getValue();

			for (String v : value) {
				System.out.println(key + " : " + v);
			}

		}
		System.out.println();

		// Declared a map for storing output frame
		Map<String, String> mapoutput = new LinkedHashMap<String, String>();
		// Pattern class is a compiled representation of a regular expression stored in
		// string above
		// Compiled first regular expression comprising of compulsory dependancy tag i.e
		// nsubj
		Pattern p = Pattern.compile(REGEX);
		// Matcher class is kind of an engine that performs match operations on a
		// character sequence by interpreting a Pattern.
		Matcher m = p.matcher(sl); // get a matcher object


		// find() function attempts to find second subsequence of the input sequence
		// that matchesthe pattern.
		// Here false indicates that there no nsubj present in sentence so need to check
		// further for second regular expression and no active voice present in sentence
		if (m.find() == false) {
			System.out.print("No conjunction present in sentence : " + k1);
			System.out.println();
			//return 0;
		}
		//indicates cc present
		else {
			// As m.find() finds second subsequence so we need to set to first point so we
			// do reset of matcher object
			m.reset();
			// After resetting we again match pattern with string
			m = p.matcher(sl);
			// matches pattern first with matcher for identifying the pattern
			Pattern p1 = Pattern.compile(REGEX1);
			Matcher m1 = p1.matcher(k1);
			//if first pattern do not match check for second pattern
			if (m1.find() == false) {
				//creates pattern of second
				Pattern p2 = Pattern.compile(REGEX2);
				//checks for every subsequence
				if (m.find()) {
					{
						
						Matcher m2 = p2.matcher(k1);
						// find() function attempts to find second subsequence of the input sequence
						// that matches the pattern.
						if (m2.find()) {
							// Finding matches from every pair of map
							for (Map.Entry<String, List<String>> mapElement : map.entrySet()) {

								String key = mapElement.getKey();

								// Finding the value
								List<String> value = mapElement.getValue();

								for (String v : value) {
									int l = v.length();
									int index = 0;
									if (sl.contains(v)) {
										index = sl.indexOf(v);
										index = index + l;

										String brac = ")";

										int index2 = sl.indexOf(brac, index);
										// System.out.println(index2);
										// System.out.println( sl.substring(index, index2+1));
										if (!mapoutput.containsKey(key)) {
											mapoutput.put(key, sl.substring(index, index2 + 1));
										}

									}
								}

							}
						}

					}
				}
				System.out.println("Frame Structure : \n" + mapoutput);
				System.out.println();
				//mapoutput.clear();

			} else {
				//similarly tries for first  pattern of verb clause
				m1.reset();
				m1 = p1.matcher(k1);

				if (m.find()) {
					{

						m1 = p1.matcher(k1);

						if (m1.find()) {

							for (Map.Entry<String, List<String>> mapElement : map.entrySet()) {

								String key = mapElement.getKey();

								// Finding the value
								List<String> value = mapElement.getValue();

								for (String v : value) {
									int l = v.length();
									int index = 0;
									if (sl.contains(v)) {
										index = sl.indexOf(v);
										index = index + l;

										String brac = ")";

										int index2 = sl.indexOf(brac, index);
										// System.out.println(index2);
										// System.out.println( sl.substring(index, index2+1));
										if (!mapoutput.containsKey(key)) {
											mapoutput.put(key, sl.substring(index, index2 + 1));
										}

									}
								}

							}
						}

					}
				}
				//prints the frame stored in map
				System.out.println("Frame Structure : \n" + mapoutput);
				System.out.println();
				//clears map for next frame
				//mapoutput.clear();
			}
		}
		System.out.println("--------------------------------------------------");
		return mapoutput;
	}
}
