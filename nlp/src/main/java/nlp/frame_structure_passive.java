package nlp;

//imported matcher,pattern to use concept of regular expressions in java
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.*;


//public class accessbile everywhere consist code to check whether the sentence is passive voice or not.
public class frame_structure_passive {
	// First regular expression consist of compulsory dependancy called as
	// nsubjpass-passive nominal subject (is a noun phrase which is the syntactic
	// subject of a passive clause)
	private static final String REGEX = "(.*)(nsubjpass)(.*)";
	// The general format of passive voice is <form of TO BE> <verb in PAST
	// PRINCIPLE>.
	// So created regular expression consisting verb(third person)+verb in past
	// participle to check whether sentence is in passivee voice or not.
	// here .* is used to take into consideration the words that would occur in
	// middle of structure.
	// here //b means we want explicitly vbn and vbz
	private static final String REGEX2 = "(.*)(VBZ)(.*)(VBN)(.*)";

	// Function consist of actual code to check the voice of sentence with
	// parameters consist of enhanced plus plus dependency and pos tags of each
	// sentence
	public Map<String,String> frame_passive(String sl, String k1) throws Exception {
		// Pattern of frame for passive voice
		Scanner s = new Scanner("Actor       agent\n" + "Modifier_of_actor       amod\n" + "Action		 root\n"
				+ "Action		 acl:relcl\n" + "Object		 nsubjpass\n" + "Object_modifier	 dobj\n"
				+ "Object_modifier	 xcomp\n");
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

		// Compiled second regular expression consisting of subject+verb+object required
		// for sentence to be in active form.
		Pattern p1 = Pattern.compile(REGEX2);

		// find() function attempts to find second subsequence of the input sequence
		// that matchesthe pattern.
		// Here false indicates that there no nsubj present in sentence so need to check
		// further for second regular expression and no active voice present in sentence
		if (m.find() == false) {
			System.out.print("No passive voice present in sentence : " + k1);
			System.out.println();
			//return 0;
		}
		// indicates nsubjpass present
		else {
			// As m.find() finds second subsequence so we need to set to first point so we
			// do reset of matcher object
			m.reset();
			// After resetting we again match pattern with string
			m = p.matcher(sl);
			// checks every subsequence present in sentence for match
			if (m.find()) {
				{
					// matches second pattern consisting of vb+vbn to pos tags of the sentence to
					// check the mapping
					Matcher r = p1.matcher(k1);
					//// find() function attempts to find second subsequence of the input sequence
					//// that matches the pattern.
					if (r.find()) {
						// Finding matches from every pair of map
						for (Map.Entry<String, List<String>> mapElement : map.entrySet()) {

							// extracting key
							String key = mapElement.getKey();

							// Finding the values for that key
							List<String> value = mapElement.getValue();
							// Traversing through each value for that key
							// Finding index position of the string value end to store its respective value
							// in new map i.e final final frame for that sentence
							// If the dependency gets match at first value only than we do not consider
							// other values
							for (String v : value) {

								int l = v.length();
								int index = 0;
								if (sl.contains(v)) {
									index = sl.indexOf(v);
									index = index + l;

									String brac = ")";

									int index2 = sl.indexOf(brac, index);
								
									if (!mapoutput.containsKey(key)) {
										mapoutput.put(key, sl.substring(index, index2 + 1));
									}

								}
							}

						}
					}

				}
			}
			// Printing out the frame stored in map format
			System.out.println("Frame Structure : \n" + mapoutput);
			System.out.println();
			// clearly map for storing frame for next sentence
			
			
			//mapoutput.clear();
		}
		System.out.println("--------------------------------------------------");
		return mapoutput;

	}
}
