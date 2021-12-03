package nlp;

//imported matcher,pattern to use concept of regular expressions in java
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.util.CoreMap;

import java.util.*;

//public class accessbile everywhere consist code to check whether the sentence is active voice or not.
public class frame_structure_active<mapoutput> {
	// First regular expression consist of compulsory dependancy called as nsubj-nominal subject(is a noun phrase which is the syntactic subject of a clause)
	private static final String REGEX = "(.*)(nsubj)(.*)";
	// The general format of active voice is subject(Noun)+main verb+object(NN).
	// So created regular expression consisting noun+verb+noun to check whether sentence is in active voice or not.
	// here .* is used to take into consideration the words that would occur in middle of structure.
	private static final String REGEX2 = "(NN)(.*)(VB)(.*)(NN)";
	//String[] fs=new String[6];
	
	//Map<String, String> mapoutputfinal = new LinkedHashMap<String, String>();
	//List<Map> list1 = new ArrayList();
	
	// Function consist of actual code to check the voice of sentence with parameters consist of enhanced plus plus dependency and pos tags of each sentence
	public Map<String,String> frame_active(String sl, String k1) throws Exception {
		// Pattern of frame for active voice
		Scanner s = new Scanner("Actor       nsubj(\n" + "Actor       compound\n" + "Actor       advmod\n"
				+ "Modifier_of_actor       amod\n" + "Action		 root\n" + "Action		 acl:relcl\n"
				+ "Object		 dobj\n" + "Object_modifier	 amod\n" + "Object_modifier	 advmod\n"
				+ "Object_modifier	 compound\n");
		// map used to store the pattern of active voice comprising of string for key and List<String> for its values as we can anyone out of multiple.
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
			List<String> value = mapElement.getValue();
			for (String v : value) {
				System.out.println(key + " : " + v);
			}
		}
		System.out.println();
		Map<String, String> mapoutput = new LinkedHashMap<String, String>();
		// Declared a map for storing output frame
		
		// Pattern class is a compiled representation of a regular expression stored in string above
		// Compiled first regular expression comprising of compulsory dependancy tag i.e nsubj
		Pattern p = Pattern.compile(REGEX,Pattern.CASE_INSENSITIVE);
		// Matcher class is kind of an engine that performs match operations on a character sequence by interpreting a Pattern.
		Matcher m = p.matcher(sl); // get a matcher object

		// Compiled second regular expression consisting of subject+verb+object required for sentence to be in active form.
		Pattern p1 = Pattern.compile(REGEX2,Pattern.CASE_INSENSITIVE);
		int cnt=0;
		//find() function attempts to find second subsequence of the input sequence that matchesthe pattern. 
		//Here false indicates that there no nsubj present in sentence so need to check further for second regular expression and no active voice present in sentence
		if (m.find() == false) {
			//fs[cnt]="No active voice present in sentence : "+k1;
			//cnt++;
			System.out.print("No active voice present in sentence : " + k1);
			System.out.println();
			//return 0;
		}
		//indicates nsubj is present in sentence
		else {
			//As m.find() finds second subsequence so we need to set to first point so we do reset of matcher object
			m.reset();
			//After resetting we again match pattern with string
			m = p.matcher(sl);
			//checks every subsequence present in sentence for match
			if(m.find()) {
				{
					//matches second pattern consisting of nn+vb+nn to pos tags of the sentence to check the mapping	
					Matcher r = p1.matcher(k1);
					////find() function attempts to find second subsequence of the input sequence that matches the pattern. 
					if(r.find()) {
						System.out.println(r.start());
						System.out.println(r.end());
						//Finding matches from every pair of map
						for (Map.Entry<String, List<String>> mapElement : map.entrySet()) {
							
							//extracting key
							String key = mapElement.getKey();

							// Finding the values for that key
							List<String> value = mapElement.getValue();
							//Traversing through each value for ,that key
							//Finding index position of the string value end to store its respective value in new map i.e final final frame for that sentence
							//If the dependency gets match at first value only than we do not consider other values
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
			
			//Printing out the frame stored in map format
			System.out.println("Frame Structure : \n" + mapoutput);
			System.out.println();
			

		}
		System.out.println("--------------------------------------------------");
	
		return mapoutput;
	}
	

}
