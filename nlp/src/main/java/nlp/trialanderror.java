package nlp;
import java.util.*;
public class trialanderror {
	public static void main(String[] args) {

	    // your data map
	    Map<String, List<String>> map = new LinkedHashMap<String, List<String>>();
		Scanner s = new Scanner("Username	Correct\n" + "Username	Incorrect\n" + "Password	Correct\n"
				+ "Password	Incorrect" + "\n" + "Appliance_Model		3340\n" + "Appliance_Model		3600\n"
				+ "Appliance_Model		5000\n" + "Appliance_Model		5020\n" +
				// "Appliance_Model 5030\n"+"Appliance_Model 5150\n"+"Appliance_Model
				// 5200\n"+"Appliance_Model 5220\n");
				// "Appliance_Model 5230\n"+"Appliance_Model 5240\n"+"Appliance_Model
				// 5250\n"+"Appliance_Model 5330\n"+
				// "Appliance_Model 5340\n"+"Appliance_Model 5400\n"+"Appliance_Model
				// 7330\n"+"Appliance_Model 8100\n"+
				// "Appliance_Model 8200\n"+"Appliance_Model 9750\n"+"Appliance_Model
				// 9751\n"+"Appliance_Model 9752\n");
				"Software_Version 	1.3\n" + "Software_Version 	1.3.1\n" + "Software_Version 	1.4\n"
				+ "Software_Version 	1.5\n" + "Software_Version 	2.0\n" + "Capacity_min	positive\n"
				+ "Capacity_min	negative\n" + "Capacity_min	zero\n" + "Capacity_max	positive\n"
				+ "Capacity_max	negative\n" + "Capacity_max	zero\n" + "SRS_score_min	positive\n"
				+ "SRS_score_min	negative\n" + "SRS_score_min	zero\n" + "SRS_score_max	positive\n"
				+ "SRS_score_max	negative\n" + "SRS_score_max	zero\n");
	

	
		// Storing the input into map temporary stored in Scanner object
		while (s.hasNext()) {
			String key = s.next();
			if (!map.containsKey(key))
				map.put(key, new LinkedList<String>());
			map.get(key).add(s.next());
		}
		// Printing out key value pairs from map
		// List<List<String>> powerSet = new LinkedList<List<String>>();
		for (Map.Entry<String, List<String>> mapElement : map.entrySet()) {
			String key = mapElement.getKey();
			List<String> value = mapElement.getValue();
			for (String v : value) {
				System.out.println(key + " : " + v);
			}
		}

	    // the map entry set as list, which will help
	    // combining the elements
	    //
	    // note this is a modifiable list

	    List<Map.Entry<String, List<String>>> mapEntryList =
	            new ArrayList<Map.Entry<String, List<String>>>(map.entrySet());

	    // the combinations list, which will store
	    // the desired results

	    List<String> combinations = new ArrayList<String>();

	    doRecursion(mapEntryList, combinations);
	}
static int cnt=0;
	 static void doRecursion(
	        List<Map.Entry<String, List<String>>> mapEntryList,
	        List<String> combinations) {

	    // end of recursion

	    if (mapEntryList.isEmpty()) {

	        // do what you wish
	        //
	        // here i print each element of the combination

	        for (String rr : combinations) {

	            System.out.println(rr);
	        }

	        System.out.println();
	        System.out.println(cnt++);
	        

	        return;
	    }


	    Map.Entry<String, List<String>> entry = mapEntryList.remove(0);

	    List<String> entryValue = new ArrayList<String>(entry.getValue());

	    while (!entryValue.isEmpty()) {

	        String rr = entryValue.remove(0);

	        combinations.add(rr);

	        doRecursion(mapEntryList, combinations);

	        combinations.remove(combinations.size() - 1);
	    }

	    mapEntryList.add(0, entry);
	}

}
