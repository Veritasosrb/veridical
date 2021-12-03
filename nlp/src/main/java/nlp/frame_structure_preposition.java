package nlp;

//imported matcher,pattern to use concept of regular expressions in java
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.*;

//public class accessbile everywhere consist code to check whether the sentence consist of preposition or not.
public class frame_structure_preposition {
	// First regular expression consist of compulsory dependancy called as case is
	// used for any case-marking element which is treated as a separate syntactic
	// word (including prepositions, postpositions,
	private static final String REGEX = "(.*)(case)(.*)";

	// Function consist of actual code to check the voice of sentence with
	// parameters consist of enhanced plus plus dependency and pos tags of each
	// sentence
	public Map<String,String> frame_preposition(String sl, String k1) throws Exception {
		// frame structure for preposition
		Scanner s = new Scanner("Preposition       case\n" + "Preposition_object       case\n"
				+ "Preposition_governing_object		 nmod\n" + "Modifiers		 compound\n");
		// map is used to store pattern for preposition frame structure
		Map<String, String> map = new LinkedHashMap<String, String>();
		// Storing the input into map temporary stored in Scanner object
		while (s.hasNext()) {

			String key = s.next();
			if (!map.containsKey(key))
				map.put(key, s.next());

		}
		// Printing out key value pairs from map
		for (Map.Entry<String, String> mapElement : map.entrySet()) {

			String key = mapElement.getKey();

			// Finding the value
			String value = mapElement.getValue();

			System.out.println(key + " : " + value);

		}
		System.out.println();

		Map<String, String> mapoutput = new LinkedHashMap<String, String>();
		// Declared a map for storing output frame
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
			System.out.print("No preposition present in sentence : " + k1);
			System.out.println();
		//	return 0;

		}
		// case is present
		else {
			// As m.find() finds second subsequence so we need to set to first point so we
			// do reset of matcher object
			m.reset();
			// After resetting we again match pattern with string
			m = p.matcher(sl);
			// checks every subsequence present in sentence for match
			if (m.find()) {
				{
					// Finding matches from every pair of map
					for (Map.Entry<String, String> mapElement : map.entrySet()) {
						// extracting key
						String key = mapElement.getKey();

						// Finding the value
						String value = mapElement.getValue();
						// Finding index position of the string value end to store its respective value
						// in new map i.e final final frame for that sentence
						int l = value.length();
						int index = 0;
						if (sl.contains(value)) {
							index = sl.indexOf(value);
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
