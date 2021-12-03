package nlp;

import java.util.regex.Matcher;
//imported matcher,pattern to use concept of regular expressions in java
import java.util.regex.Pattern;

import com.opencsv.CSVReader;

import java.io.FileReader;
import java.util.*;

////public class accessbile everywhere consist code to check whether the sentence consist of precondition or not.
public class frame_structure_precondition {
	// First regular expression consist of compulsory dependancy called as mark
	private static final String REGEX = "(.*)(mark)([(])(.*)";
	// regular expression for each format that checks precondition
	private static final String REGEX1 = "(.*)(If)(.*)(Then)(.*)";
	private static final String REGEX2 = "(.*)(When)(.*)";
	//private static final String REGEX3 = "(.*)(After)*(on)*(once)*(having)*(.*)";
	
	// Function consist of actual code to check the voice of sentence with
	// parameters consist of enhanced plus plus dependency and pos tags of each
	// sentence
	public Map<String,String> frame_precondition(String sl, String k1) throws Exception {
		// frame for precondition
		Scanner s = new Scanner("Precondition       mark\n" + "Precondition_action       mark\n"
				+ "Object_of_precondition		 dobj\n" + "Precondition_on_action		 acl:relcl\n");
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
			//System.out.print("No precondition present in sentence : " + k1);
			//System.out.println();
			m.reset();
			// After resetting we again match pattern with string
			m = p.matcher(sl);
			//creates pattern for first and third format
			Pattern p1 = Pattern.compile(REGEX1);
			Matcher m1 = p1.matcher(k1);
			Pattern p2 = Pattern.compile(REGEX2);
			Matcher m2 = p2.matcher(k1);
			//Pattern p3 = Pattern.compile(REGEX3);
		//	Matcher m3 = p3.matcher(k1);
			//if both the format do not match checks for second one
			if (m2.find() == true ) {
				//creates pattern for second one
				//
				//checks for every subsequence
				if(m.find()==false) {
					{

						//Matcher m2 = p2.matcher(k1);

						if (m2.find()) {
							System.out.println("1 tue");

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
				}
					//prints the frame stored in map
				mapoutput.put("When", "Present");
				System.out.println("Frame Structure : \n" + mapoutput);
				System.out.println();
				// mapoutput.clear();

			//return 0;
		}
			else
			{
				System.out.print("No precondition present in sentence : " + k1);
				System.out.println();
			}
		}
		// mark is present
		else {
			// As m.find() finds second subsequence so we need to set to first point so we
			// do reset of matcher object
			m.reset();
			// After resetting we again match pattern with string
			m = p.matcher(sl);
			//creates pattern for first and third format
			Pattern p1 = Pattern.compile(REGEX1);
			Matcher m1 = p1.matcher(k1);
			// Pattern p3 = Pattern.compile(REGEX3);
		    // Matcher m3 = p3.matcher(k1);
			// if both the format do not match checks for second one
			if (m1.find() == true ) {
				//creates pattern for second one
				//Pattern p2 = Pattern.compile(REGEX2);
				//checks for every subsequence
				

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
					
				
					// prints the frame stored in map
				System.out.println("Frame Structure : \n" + mapoutput);
				System.out.println();
				// mapoutput.clear();

			} else {
				// Please check this code why else is added here
			}
		}
		System.out.println("--------------------------------------------------");
		return mapoutput;

		}
}
			
		
			
