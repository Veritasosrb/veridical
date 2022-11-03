package modules;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.*;

//This class is used to identify whether the sentence consist of preposition or not.
public class FrameStructurePreposition {
	private static final String REGEX = "(.*)(case)(.*)";

	// To identify if preposition is present and create frames
	public Map<String, String> framePreposition(String eppdependency, String sentence) throws Exception {
		Map<String, String> finalMapOutput = new LinkedHashMap<String, String>();
		Map<String, String> map = storeFramePatternPrepositionAndPrint();

		// Compile and match regex to get Matcher object
		Pattern regexPattern = Pattern.compile(REGEX);
		Matcher regexMatcherObj = regexPattern.matcher(eppdependency);

		// Here false indicates that there no case present in sentence
		if (regexMatcherObj.find() == false) {
			System.out.print("No preposition present in sentence : " + sentence);
			System.out.println();
		} else {
			// Need to set to first point so we do reset of matcher object
			regexMatcherObj.reset();
			regexMatcherObj = regexPattern.matcher(eppdependency);

			// Checks every subsequence present in sentence for match
			if (regexMatcherObj.find()) {
				finalMapOutput = prepositionFrameStructure(map, eppdependency);
			}

			// Printing out the frame stored in map format
			System.out.println("Frame Structure : \n" + finalMapOutput);
			System.out.println();
		}

		System.out.println("--------------------------------------------------");
		return finalMapOutput;
	}

	// This function is used to store pattern of frame in a map
	private Map<String, String> storeFramePatternPrepositionAndPrint() {
		Map<String, String> map = new LinkedHashMap<String, String>();

		// Frame structure for preposition
		Scanner scanner = new Scanner("Preposition       case\n" + "Preposition_object       case\n"
				+ "Preposition_governing_object		 nmod\n" + "Modifiers		 compound\n");

		// Storing the input into map temporary stored in Scanner object
		while (scanner.hasNext()) {
			String key = scanner.next();
			if (!map.containsKey(key))
				map.put(key, scanner.next());
		}

		// Printing out key value pairs from map
		for (Map.Entry<String, String> mapElement : map.entrySet()) {
			String key = mapElement.getKey();
			String value = mapElement.getValue();
			System.out.println(key + " : " + value);
		}

		System.out.println();
		scanner.close();
		return map;
	}

	// To check eppdependency in intermediate csv and add in final map output
	private Map<String, String> prepositionFrameStructure(Map<String, String> map, String eppdependency) {
		Map<String, String> mapoutput = new LinkedHashMap<String, String>();

		// Finding matches from every pair of map
		for (Map.Entry<String, String> mapElement : map.entrySet()) {
			String key = mapElement.getKey();
			String value = mapElement.getValue();

			// Traversing thru pattern to check if present in eppdependency
			int l = value.length();
			int index = 0;
			if (eppdependency.contains(value)) {
				index = eppdependency.indexOf(value);
				index = index + l;
				String brac = ")";
				int index2 = eppdependency.indexOf(brac, index);

				// Checking and adding dependency for max 1 value per key
				if (!mapoutput.containsKey(key)) {
					mapoutput.put(key, eppdependency.substring(index, index2 + 1));
				}
			}
		}
		return mapoutput;
	}
}
