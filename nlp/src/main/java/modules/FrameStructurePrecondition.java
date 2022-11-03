package modules;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//This class is used to identify whether the sentence consist of precondition or not.
public class FrameStructurePrecondition {
	// First regular expression consist of compulsory dependency called as mark
	private static final String REGEX = "(.*)(mark)([(])(.*)";

	// regular expression for each format that checks precondition
	private static final String REGEX1 = "(.*)(If)(.*)(Then)(.*)";
	private static final String REGEX2 = "(.*)(When)(.*)";

	// To check if precondition is present in the sentence
	public Map<String, String> framePrecondition(String eppdependency, String sentence) throws Exception {
		Map<String, String> finalMapOutput = new LinkedHashMap<String, String>();
		Map<String, String> map = storeFramePatternPreconditionAndPrint();

		// Compile and match regex to get Matcher object
		Pattern RegexPattern = Pattern.compile(REGEX);
		Matcher RegexMatcherObj = RegexPattern.matcher(eppdependency);

		// Here false indicates that there no mark present in sentence
		if (RegexMatcherObj.find() == false) {
			RegexMatcherObj.reset();
			// After resetting we again match pattern with string
			RegexMatcherObj = RegexPattern.matcher(eppdependency);

			// Creates pattern for second format
			Pattern secondRegexPattern = Pattern.compile(REGEX2);
			Matcher secondRegexMatcherObj = secondRegexPattern.matcher(sentence);

			// If both the format do not match checks for second one
			if (secondRegexMatcherObj.find() == true) {

				// Checks for every subsequence
				if (RegexMatcherObj.find() == false) {
					if (secondRegexMatcherObj.find()) {
						System.out.println("1 true");
						finalMapOutput = preconditionFrameStructure(map, eppdependency);
					}
				}

				// Prints the frame stored in map
				finalMapOutput.put("When", "Present");
				System.out.println("Frame Structure : \n" + finalMapOutput);
				System.out.println();
			} else {
				System.out.print("No precondition present in sentence : " + sentence);
				System.out.println();
			}
		} else {
			// To reset to first subsequence
			RegexMatcherObj.reset();
			RegexMatcherObj = RegexPattern.matcher(sentence);
			// creates pattern for first format
			Pattern firstRegexPattern = Pattern.compile(REGEX1);
			Matcher firstRegexMatcherObj = firstRegexPattern.matcher(sentence);

			// if the format do not match checks for second one
			if (firstRegexMatcherObj.find() == true) {
				// creates pattern for second one
				// checks for every subsequence
				finalMapOutput = preconditionFrameStructure(map, eppdependency);

				// prints the frame stored in map
				System.out.println("Frame Structure : \n" + finalMapOutput);
				System.out.println();
			}
		}
		System.out.println("--------------------------------------------------");
		return finalMapOutput;
	}

	// This function is used to store pattern of frame in a map
	private Map<String, String> storeFramePatternPreconditionAndPrint() {
		Scanner scanner = new Scanner("Precondition       mark\n" + "Precondition_action       mark\n"
				+ "Object_of_precondition		 dobj\n" + "Precondition_on_action		 acl:relcl\n");
		Map<String, String> map = new LinkedHashMap<String, String>();

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

	// This function is used to check eppdependency in intermediate csv and add in
	// mapout
	private Map<String, String> preconditionFrameStructure(Map<String, String> map, String eppdependency) {
		Map<String, String> mapoutput = new LinkedHashMap<String, String>();
		// Finding matches from every pair of map
		for (Map.Entry<String, String> mapElement : map.entrySet()) {
			// extracting key
			String key = mapElement.getKey();
			// Finding the value
			String value = mapElement.getValue();
			// Finding index position of the string value end to store its respective value
			// in new map i.e final frame for that sentence
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
