package modules;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.*;

//This class is used to identify whether the sentence consist of conjunction or not
public class FrameStructureConjunction {
	private static final String REGEX = "(.*)(cc)(.*)";
	private static final String REGEX1 = "(.*)(VB)(.*)(CC)(.*)(VB)(.*)";
	private static final String REGEX2 = "(.*)(NN)(.*)(CC)(.*)(NN)(.*)";

	// To identify if conjunction is present and create frames.
	public Map<String, String> frameConjunction(String eppdependency, String sentence) throws Exception {
		Map<String, String> finalMapOutput = new LinkedHashMap<String, String>();
		Map<String, List<String>> map = storeFramePatternConjunctionAndPrint();

		// Compile and match regex to get Matcher object
		Pattern regexPattern = Pattern.compile(REGEX);
		Matcher regexMatcherObj = regexPattern.matcher(eppdependency);

		// Here false indicates that there no cc present in sentence
		if (regexMatcherObj.find() == false) {
			System.out.print("No conjunction present in sentence : " + sentence);
			System.out.println();
		} else {
			// Need to set to first point so we do reset of matcher object
			regexMatcherObj.reset();
			regexMatcherObj = regexPattern.matcher(eppdependency);

			// Compile and match the first regex to get matcher object
			Pattern firstRegexPattern = Pattern.compile(REGEX1);
			Matcher firstRegexMatchObj = firstRegexPattern.matcher(sentence);

			// If first pattern do not match check for second pattern
			if (firstRegexMatchObj.find() == false) {
				Pattern secondRegexPattern = Pattern.compile(REGEX2);
				if (regexMatcherObj.find()) {
					{
						Matcher secondRegexMatcherObj = secondRegexPattern.matcher(sentence);
						if (secondRegexMatcherObj.find()) {
							finalMapOutput = conjunctionFrameStructure(map, eppdependency);
						}
					}
				}

				// Printing out the frame stored in map format
				System.out.println("Frame Structure : \n" + finalMapOutput);
				System.out.println();
			} else {
				// Similarly checks for the first pattern of verb clause
				firstRegexMatchObj.reset();
				firstRegexMatchObj = firstRegexPattern.matcher(sentence);

				if (regexMatcherObj.find()) {
					{
						firstRegexMatchObj = firstRegexPattern.matcher(sentence);
						if (firstRegexMatchObj.find()) {
							finalMapOutput = conjunctionFrameStructure(map, eppdependency);
						}
					}
				}

				// Printing out the frame stored in map format
				System.out.println("Frame Structure : \n" + finalMapOutput);
				System.out.println();
			}
		}
		System.out.println("--------------------------------------------------");
		return finalMapOutput;
	}

	// This function is used to store pattern of frame in a map
	private Map<String, List<String>> storeFramePatternConjunctionAndPrint() {
		Map<String, List<String>> map = new LinkedHashMap<String, List<String>>();

		// Pattern of frame for conjunction
		Scanner scanner = new Scanner(
				"Conjunction       cc\n" + "Actor_for_verb_1       nsubj(\n" + "Actor_for_verb_1		agent\n"
						+ "Actor_for_verb_1		 compound\n" + "Actor_for_verb_2       nsubj(\n"
						+ "Actor_for_verb_2		agent\n" + "Actor_for_verb_2		 compound\n"
						+ "Object_for_verb_1		 dobj\n" + "Object_for_verb_1	 nsubjpass\n"
						+ "Object_for_verb_2		 dobj\n" + "Object_for_verb_2	 nsubjpass\n");

		// Storing the input into map temporary stored in Scanner object
		while (scanner.hasNext()) {
			String key = scanner.next();
			if (!map.containsKey(key))
				map.put(key, new LinkedList<String>());
			map.get(key).add(scanner.next());
		}

		// Printing out key value pairs from map
		for (Map.Entry<String, List<String>> mapElement : map.entrySet()) {
			String key = mapElement.getKey();
			List<String> value = mapElement.getValue();
			for (String eachValue : value) {
				System.out.println(key + " : " + eachValue);
			}
		}
		System.out.println();
		scanner.close();
		return map;
	}

	// To check eppdependency in intermediate csv and add in final map output
	private Map<String, String> conjunctionFrameStructure(Map<String, List<String>> map, String eppdependency) {
		// Declared a map for storing output frame
		Map<String, String> mapoutput = new LinkedHashMap<String, String>();

		// Finding matches from every pair of map
		for (Map.Entry<String, List<String>> mapElement : map.entrySet()) {
			String key = mapElement.getKey();

			// Finding the values for that key
			List<String> value = mapElement.getValue();

			// Traversing thru pattern to check if present in eppdependency
			for (String eachValue : value) {
				int l = eachValue.length();
				int index = 0;
				if (eppdependency.contains(eachValue)) {
					index = eppdependency.indexOf(eachValue);
					index += l;
					String brac = ")";
					int bracketIndex = eppdependency.indexOf(brac, index);

					// Checking and adding dependency for max 1 value per key
					if (!mapoutput.containsKey(key)) {
						mapoutput.put(key, eppdependency.substring(index, bracketIndex + 1));
					}
				}
			}
		}
		return mapoutput;
	}
}
