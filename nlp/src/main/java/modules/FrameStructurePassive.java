package modules;

import static constants.Constants.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.*;

//public class accessbile everywhere consist code to check whether the sentence is passive voice or not.
public class FrameStructurePassive {
	private static final String REGEX = "(.*)(nsubjpass)(.*)";
	private static final String REGEX2 = "(.*)(VBZ)(.*)(VBN)(.*)";

	// Function to check voice of sentence
	public Map<String, String> framePassive(String eppdependency, String sentence) throws Exception {
		Map<String, String> finalMapOutput = new LinkedHashMap<String, String>();
		Map<String, List<String>> map = storeFramePatternPassiveVoiceAndPrint();

		// Compile and match regex to get Matcher object
		Pattern firstRegexPattern = Pattern.compile(REGEX);
		Matcher firstRegexMatcherObj = firstRegexPattern.matcher(eppdependency);

		// Compiled second regex required for sentence to be in active form
		Pattern secondRegexPattern = Pattern.compile(REGEX2);

		// Here false indicates that there no nsubjpass present in sentence so need to
		// check
		// further for second regular expression and no passive voice present in
		// sentence
		if (firstRegexMatcherObj.find() == false) {
			System.out.print("No passive voice present in sentence : " + sentence);
			System.out.println();
		} else {
			// Need to set to first point so we do reset of matcher object
			firstRegexMatcherObj.reset();
			firstRegexMatcherObj = firstRegexPattern.matcher(eppdependency);
			// Checks every subsequence present in sentence for match
			if (firstRegexMatcherObj.find()) {
				{
					// Matches second pattern to pos tags of the sentence to check the mapping
					Matcher secondRegexMatcherObj = secondRegexPattern.matcher(sentence);
					if (secondRegexMatcherObj.find()) {
						finalMapOutput = passiveFrameStructure(map, eppdependency);
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
	private Map<String, List<String>> storeFramePatternPassiveVoiceAndPrint() {
		Map<String, List<String>> map = new LinkedHashMap<String, List<String>>();
		Scanner scanner = new Scanner("Actor       agent\n" + "Modifier_of_actor       amod\n" + "Action		 root\n"
				+ "Action		 acl:relcl\n" + "Object		 nsubjpass\n" + "Object_modifier	 dobj\n"
				+ "Object_modifier	 xcomp\n");

		// Storing the input into map which is stored in Scanner object
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
				System.out.println(key + SPACE + COLON + SPACE + eachValue);
			}
		}
		System.out.println();
		scanner.close();
		return map;
	}

	// This function is used to check eppdependency in intermediate csv and add in
	private Map<String, String> passiveFrameStructure(Map<String, List<String>> map, String eppdependency) {
		// Declared a map for storing output frame
		Map<String, String> mapoutput = new LinkedHashMap<String, String>();

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
					index = index + l;
					String brac = ")";
					int index2 = eppdependency.indexOf(brac, index);

					// Checking and adding dependency for max 1 value per key
					if (!mapoutput.containsKey(key)) {
						mapoutput.put(key, eppdependency.substring(index, index2 + 1));
					}
				}
			}
		}
		return mapoutput;
	}
}
