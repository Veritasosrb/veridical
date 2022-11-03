package modules;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.*;

//public class accessible everywhere consist code to check whether the sentence consist of marker or not.
public class FrameStructureMarker {

	private static final String REGEX = "(.*)(advmod)(.*)";

	// Function to check voice of sentence
	public Map<String, String> frameMarker(String eppdependency, String sentence) throws Exception {
		Map<String, String> finalMapOutput = new LinkedHashMap<String, String>();
		Map<String, String> map = storeFramePatternMarkerAndPrint();

		// Compile and match regex to get Matcher object
		Pattern firstRegexPattern = Pattern.compile(REGEX);
		Matcher firstRegexMatcherObj = firstRegexPattern.matcher(eppdependency);

		// Here false indicates that there no advmod present in sentence
		if (firstRegexMatcherObj.find() == false) {
			System.out.print("No preposition present in sentence : " + sentence);
			System.out.println();
		} else {
			// Need to set to first point so we do reset of matcher object
			firstRegexMatcherObj.reset();
			firstRegexMatcherObj = firstRegexPattern.matcher(eppdependency);
			// Checks every subsequence present in sentence for match
			if (firstRegexMatcherObj.find()) {
				{
					// Finding matches from every pair of map
					finalMapOutput = markerFrameStructure(map, eppdependency);
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
	private Map<String, String> storeFramePatternMarkerAndPrint() {

		Scanner scanner = new Scanner(
				"Marker       advmod\n" + "Marker_action       advmod\n" + "Marker_object	 nsubjpass");
		// map is used to store pattern for marker frame structure
		Map<String, String> map = new LinkedHashMap<String, String>();
		while (scanner.hasNext()) {
			String key = scanner.next();
			if (!map.containsKey(key))
				map.put(key, scanner.next());
		}
		// Printing out key value pairs from map
		for (Map.Entry<String, String> mapElement : map.entrySet()) {
			String key = mapElement.getKey();
			// Finding the value
			String value = mapElement.getValue();
			System.out.println(key + " : " + value);
		}
		System.out.println();
		scanner.close();
		return map;
	}

	// To check eppdependency in intermediate csv and add in final map output
	private Map<String, String> markerFrameStructure(Map<String, String> map, String eppdependency) {
		// Declared a map for storing output frame
		Map<String, String> mapoutput = new LinkedHashMap<String, String>();

		// Finding matches from every pair of map
		for (Map.Entry<String, String> mapElement : map.entrySet()) {
			String key = mapElement.getKey();

			// Finding the value
			String value = mapElement.getValue();
			// Traversing thru pattern to check if present in eppdependency
			int l = value.length();
			int index = 0;
			if (eppdependency.contains(value)) {
				index = eppdependency.indexOf(value);
				index = index + l;
				String brac = ")";
				int index2 = eppdependency.indexOf(brac, index);

				if (!mapoutput.containsKey(key)) {
					mapoutput.put(key, eppdependency.substring(index, index2 + 1));
				}
			}
		}
		return mapoutput;
	}
}
