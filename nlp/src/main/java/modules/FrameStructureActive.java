package modules;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//This class is used to identify whether the sentence is active voice or not.
public class FrameStructureActive {
	private static final String REGEX = "(.*)(nsubj)(.*)";
	private static final String REGEX2 = "(NN)(.*)(VB)(.*)(NN)";

	// Function to check voice of sentence
	public Map<String, String> frameActive(String eppdependency, String sentence) throws Exception {
		Map<String, String> finalMapOutput = new LinkedHashMap<String, String>();
		Map<String, List<String>> map = storeFramePatternActiveVoiceAndPrint();

		// Compile and match regex to get Matcher object
		Pattern firstRegexPattern = Pattern.compile(REGEX, Pattern.CASE_INSENSITIVE);
		Matcher firstRegexMatcherObj = firstRegexPattern.matcher(eppdependency);

		// Compiled second regex required for sentence to be in active form
		Pattern secondRegexPattern = Pattern.compile(REGEX2, Pattern.CASE_INSENSITIVE);

		// Here false indicates that there no nsubj present in sentence so need to check
		// further for second regular expression and no active voice present in sentence
		if (firstRegexMatcherObj.find() == false) {
			System.out.print("No active voice present in sentence : " + sentence);
			System.out.println();
		}
		// indicates nsubj is present in sentence
		else {
			// Need to set to first point so we do reset of matcher object
			firstRegexMatcherObj.reset();
			firstRegexMatcherObj = firstRegexPattern.matcher(eppdependency);

			// Checks every subsequence present in sentence for match
			if (firstRegexMatcherObj.find()) {
				{
					// Matches second pattern consisting of nn+vb+nn to pos tags of the sentence
					Matcher secondRegexMatcherObj = secondRegexPattern.matcher(sentence);
					if (secondRegexMatcherObj.find()) {
						finalMapOutput = activeFrameStructure(map, eppdependency);
					}
				}
			}

			// Printing out the frame stored in map format
			System.out.println("Frame Structure : \n" + finalMapOutput);
			System.out.println();
		}
		System.out.println("--------------------------------------------------");
		return finalMapOutput;
	}

	// This function is used to store pattern of frame in a map
	private Map<String, List<String>> storeFramePatternActiveVoiceAndPrint() {
		Map<String, List<String>> map = new LinkedHashMap<String, List<String>>();

		// Pattern of frame for active voice
		Scanner scanner = new Scanner("Actor       nsubj(\n" + "Actor       compound\n" + "Actor       advmod\n"
				+ "Modifier_of_actor       amod\n" + "Action		 root\n" + "Action		 acl:relcl\n"
				+ "Object		 dobj\n" + "Object_modifier	 amod\n" + "Object_modifier	 advmod\n"
				+ "Object_modifier	 compound\n");

		// Storing the input into map which is stored in Scanner object
		while (scanner.hasNext()) {
			String key = scanner.next();
			if (!map.containsKey(key))
				map.put(key, new LinkedList<String>());
			map.get(key).add(scanner.next());
		}

		// Printing key value pairs from map
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
	private Map<String, String> activeFrameStructure(Map<String, List<String>> map, String eppdependency) {
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
