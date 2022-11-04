package modules;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Vector;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Ellipse;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.RectangleFigure;
import org.eclipse.draw2d.RoundedRectangle;
import org.eclipse.draw2d.ToolbarLayout;

import com.opencsv.CSVReader;
import static constants.Constants.*;

public class FunctionCalls extends DrawClass {
//specific methods to draw a block figure
	// For a Round Block.
	public Figure myRound(String name) {
		// round for start block
		Ellipse f = new Ellipse();
		f.setBackgroundColor(ColorConstants.lightGray);
		f.setLayoutManager(new ToolbarLayout());
		String name1 = name.replaceAll("([A-Z]*)\\b", EMPTY_STRING);
		String[] arrOfStr = name1.split("  ");
		int k = arrOfStr.length;
		int nooflines = (k + 3 - 1) / 3;
		nooflines *= 3;
		f.setPreferredSize(200, nooflines * 20);
		int cnttmp = 0;
		int cntarr = 0;
		String fsstr = EMPTY_STRING;
		// to find number of lines
		for (String a : arrOfStr) {
			fsstr += a;
			fsstr += SPACE;
			cnttmp++;
			cntarr++;
			if (cnttmp > 2 || cntarr == k) {
				Label fl = new Label(fsstr);
				f.add(fl);
				cnttmp = 0;
				fsstr = EMPTY_STRING;
			}

		}

		new MyListener(f);
		return f;
	}

	// For a Rounded Rectangle Block.
	public Figure myRoundedRectangle(String name) {
		// to draw rounded rectangle for simple blocks
		RoundedRectangle f = new RoundedRectangle();
		f.setBackgroundColor(ColorConstants.lightGreen);
		f.setLayoutManager(new ToolbarLayout());
		String name1 = name.replaceAll("([A-Z]*)\\b", EMPTY_STRING);
		String[] arrOfStr = name1.split("  ");
		int k = arrOfStr.length;
		int nooflines = (k + 3 - 1) / 3;
		// for setting size
		f.setPreferredSize(320, nooflines * 20);
		int cnttmp = 0;
		int cntarr = 0;
		// to find number of lines
		String fsstr = EMPTY_STRING;
		for (String a : arrOfStr) {
			fsstr += a;
			fsstr += SPACE;
			cnttmp++;
			cntarr++;
			if (cnttmp > 2 || cntarr == k) {
				Label fl = new Label(fsstr);
				f.add(fl);
				cnttmp = 0;
				fsstr = EMPTY_STRING;
			}
		}
		new MyListener(f);
		return f;
	}

	// to write all the combinations present in our string to a CSV file.
	void writeCombinationInCSV(Map<String, List<String>> tmpmap, String pv[], int flgcnt) {
		if (!tmpmap.isEmpty()) {
			Iterator<String> it = tmpmap.keySet().iterator();
			Vector<String> keyarr = new Vector<String>();
			int keyarrind = 0;
			while (it.hasNext()) {
				keyarr.add(it.next());
				keyarrind++;
			}
			keyarrind = 0;
			Vector<String> keyarr1 = new Vector<String>();
			int indextmp = 0;
			try {
				reader2 = new CSVReader(new FileReader(CommonPage.getIntermediateFilePath(FUNCTIONALITY, FLAG) + flgcnt + CSV_EXTENTSION));
				String line[];
				while ((line = reader2.readNext()) != null) {
					String pv1[] = pv.clone();
					indextmp = 0;
					keyarrind = 0;
					for (int i = 0; i < line.length; i++) {
						if (indextmp < pv1.length) {
							while (pv1[indextmp] != null && indextmp < pv1.length - 1) {
								indextmp++;
							}
							if (keyarrind < keyarr.size() && i < line.length) {
								for (String ka : keyarr) {
									if (pv1[indextmp - 1].contains(ka)) {
										keyarr1.add(ka);
									}
								}
								pv1[indextmp] = keyarr1.get(keyarrind) + " ? " + line[i];
								if (line[i].equals("unusable") || line[i].equals("incorrect")) {
									for (int i1 = indextmp + 1; i1 < pv1.length; i1++) {
										pv1[i1] = null;
									}
									break;
								}
								keyarrind++;
								indextmp++;
							}
						}
					}
					String[] pv11;
					if (indextmp == pv1.length) {
						pv11 = new String[indextmp];
					} else {
						pv11 = new String[indextmp + 2];
					}
					for (int i1 = 0; i1 < pv1.length; i1++) {
						if (pv1[i1] != null) {
							pv11[i1] = pv1[i1];
						} else {
							break;
						}
					}
					writer3.writeNext(pv11);
					try {
						writer3.flush();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
			String[] tmp1 = new String[1];
			tmp1[0] = "\n";
			writer.writeNext(tmp1);
			try {
				writer3.flush();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			writer3.writeNext(pv);
			try {
				writer3.flush();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	static int cnter = 0;

	void displayAllPaths() {
		int src = 0;
		int d;
		int counter = 0;
		for (ArrayList<Integer> arr : ADJACENCY_LIST) {
			System.out.print("counter:" + counter);
			System.out.print(SPACE);
			System.out.print(ORIGINAL_STRING.get(counter));
			for (Integer i : arr) {
				System.out.print(i);
				System.out.print(SPACE);
				System.out.print(ORIGINAL_STRING.get(i));
				System.out.print(SPACE);
			}
			System.out.println("Ne");
			System.out.println();
			if (arr.isEmpty()) {
				d = counter;

				System.out.println("\nFollowing are all different paths from " + src + " to " + d);
				printAllPaths(src, d);
			}
			counter++;
		}
	}

	public Figure myRectangle(String name) {
		// rectangle for precondition
		RectangleFigure f = new RectangleFigure();
		f.setBackgroundColor(ColorConstants.lightGreen);
		f.setLayoutManager(new ToolbarLayout());
		String name1 = name.replaceAll("([A-Z]*)\\b", EMPTY_STRING);
		String[] arrOfStr = name1.split("  ");
		int k = arrOfStr.length;
		System.out.println("k" + k);
		int nooflines = (k + 3 - 1) / 3;
		// set required size
		f.setPreferredSize(250, nooflines * 20);
		int cnttmp = 0;
		int cntarr = 0;
		String fsstr = EMPTY_STRING;
		for (String a : arrOfStr) {
			fsstr += a;
			fsstr += SPACE;
			cnttmp++;
			cntarr++;
			if (cnttmp > 2 || cntarr == k) {
				Label fl = new Label(fsstr);
				f.add(fl);
				cnttmp = 0;
				fsstr = EMPTY_STRING;
			}
		}
		new MyListener(f);
		return f;
	}

	public Vector<Integer> storeGreater(Map<Integer, Integer> addbranch) {
		try {
			reader = new CSVReader(new FileReader(CommonPage.getIntermediateFilePath(FUNCTIONALITY)));
			String line[];
			while ((line = reader.readNext()) != null) {
				for (String block : line) {
					pathstring.add(block);
					System.out.println(block);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Path string");
		for (String ps : pathstring) {
			System.out.println(ps);
		}
		Vector<Integer> storegreater = new Vector<Integer>();
		for (Map.Entry<Integer, Integer> mapElement : addbranch.entrySet()) {
			if (mapElement.getValue() > 0) {
				storegreater.add(mapElement.getKey());
			}
		}
		System.out.println("Store greater");
		for (Integer sg : storegreater) {
			System.out.println(sg);
		}
		return storegreater;
	}

	// to find out combinations possible of keywords
	static void doRecursion(List<Map.Entry<String, List<String>>> mapEntryList, List<String> combinations) {
		// end of recursion
		if (mapEntryList.isEmpty()) {
			// here i print each element of the combination
			for (String rr : combinations) {
				System.out.println(rr);
			}
			String[] tmp = new String[combinations.size()];
			for (int i = 0; i < combinations.size(); i++) {
				tmp[i] = combinations.get(i);
			}
			writer2.writeNext(tmp);
			System.out.println();
			System.out.println(cnter++);
			try {
				writer2.flush();
			} catch (Exception e) {
				e.printStackTrace();
			}
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

	void storeCombinationInMap() {
		// TODO: Hard code map directly
		Scanner s = new Scanner("Appliance_Model		select\n" + "Appliance_Model		deselect\n"
				+ "Software_Version 	select\n" + "Software_Version 	deselect\n" + "Capacity_min	positive\n"
				+ "Capacity_min	 negative\n" + "Capacity_max	positive\n" + "Capacity_max	negative\n"
				+ "Appliance_type	HA_cluster\n" + "Appliance_type	Single\n" + "Actions	usable\n"
				+ "Actions	unusable\n" + "Manage_Alerts	usable\n" + "Manage_Alerts unusable\n" + "number usable\n"
				+ "number	unusable\n" + "days/hours	usable\n" + "days/hours	unusable\n" + "three-dot usable\n"
				+ "three-dot unusable\n" + "Application	Netinsights\n" + "Application		Cortex\n"
				+ "Application	All-open\n" + "popup	usable\n" + "popup	unusable\n" + "logout	usable\n"
				+ "logout	unusable\n" + "Continue	usable\n" + "Continue	unusable\n" + "email_header	correct\n"
				+ "email_header	incorrect\n" + "email_body	correct\n" + "email_body	incorrect\n"
				+ "appliance_details		correct\n" + "appliance_details	incorrect\n" + "actions unusable\n "
				+ "email correct\n" + "email incorrect\n" + "actions usable\n " + "advanced_query unusable\n"
				+ "advanced_query usable\n " + "advanced_search unusable\n " + "advanced_search usable\n"
				+ "appliance_uid unusable\n " +" appliance_uid usable\n " +"checkbox unusable\n" 
				+ "checkbox usable\n " + "create_dashboard unusable\n " + "create_dashboard usable\n" 
				+ "dca_tab unusable\n " + "dca_tab usable\n " + "ellipses unusable\n " + "ellipses usable\n" 
				+ "history_tab unusable\n " + "history_tab usable\n " + "manage_alerts unusable\n" + "manage_alerts usable\n"
				+ "mark_as_favourite unusable\n" + "mark_as_favourite usable\n" + "overview_tab unusable\n" + "overview_tab usable\n"
				+ "performance_tab unusable\n" + "performance_tab usable\n" + "recommendation_tab unusable\n"
				+ "recommendation_tab usable\n" + "registration_tab unusable\n" + "registration_tab usable\n"
				+ "save unusable\n" + "save usable\n" + "save_query unusable\n" + "save_query usable\n"
				+ "storage_tab unusable\n" + "storage_tab usable\n" + "support_tab unusable\n" + "support_tab usable\n"
				+ "telemetery_tab unusable\n" + "telemetery_tab usable\n" + "telemetry_tab unusable\n"
				+ "telemetry_tab usable\n" + "turn_off unusable\n" + "turn_off usable\n" + "turn_on unusable\n"
				+  "turn_on usable\n" + "uid unusable\n" + "uid usable\n" + "vertical_ellipsis_menu unusable\n "
				+"vertical_ellipsis_menu usable");

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
			for (String eachValue : value) {
				System.out.println(key + " : " + eachValue);
			}
		}
		s.close();
	}

	void addEdgesToGraph(Map<Integer, Integer> addbranch) {
		int count = 0;
		int index = 0;
		// to add edges to the graph
		for (String ostring : ORIGINAL_STRING) {
			for (Map.Entry<String, List<String>> mapElement : map.entrySet()) {
				String key = mapElement.getKey();
				if (ostring != null) {
					if (ostring.contains(key) == true) {
						count++;
					}
				}
			}
			addbranch.put(index, count);
			index++;
			count = 0;
		}
		System.out.print("Map of add branch" + addbranch);
	}

	// add edge from u to v
	public void addEdge(int u, int v) {
		// Add v to u's list.
		ADJACENCY_LIST[u].add(v);
	}

	public void printAllPaths(int s, int d) {
		boolean[] isVisited = new boolean[ver];
		ArrayList<Integer> pathList = new ArrayList<Integer>();
		List<List<Integer>> pathlistfinal = new ArrayList<List<Integer>>();
		// add source to path[]
		pathList.add(s);
		// Call recursive utility
		printAllPathsUtil(s, d, isVisited, pathList, pathlistfinal);
		System.out.println(pathlistfinal);
	}

	static int cnt = 0;

	void printAllPathsUtil(Integer u, Integer d, boolean[] isVisited, ArrayList<Integer> localPathList,
			List<List<Integer>> pathlistfinal) {
		// to identify paths present in graph
		if (u.equals(d)) {
			System.out.println(localPathList);
			pathlistfinal.add(localPathList);
			String tmp = localPathList.toString();
			String tmp1[] = { tmp };
			if (!tmp.isEmpty()) {
				writer.writeNext(tmp1);
				if (!localPathList.isEmpty()) {
					for (Integer i : localPathList) {
						System.out.print(ORIGINAL_STRING.get(i));
						System.out.print("->");
					}
					System.out.print("end");
				}
				try {
					writer.flush();
				} catch (Exception e) {
					e.printStackTrace();
				}
				// if match found then no need to traverse more till depth
				return;
			}
		}
		// Mark the current node
		isVisited[u] = true;
		// Recur for all the vertices
		// adjacent to current vertex
		for (Integer i : ADJACENCY_LIST[u]) {
			if (!isVisited[i]) {
				// store current node in path[]
				localPathList.add(i);
				printAllPathsUtil(i, d, isVisited, localPathList, pathlistfinal);
				// remove current node in path[]
				localPathList.remove(i);
			}
		}
		// Mark the current node
		isVisited[u] = false;

	}

//to find out combination of words possible for filter values
	public <T> List<List<T>> combination(List<T> values, int size) {
		if (0 == size) {
			return Collections.singletonList(Collections.<T>emptyList());
		}
		if (values.isEmpty()) {
			return Collections.emptyList();
		}
		List<List<T>> combination = new LinkedList<List<T>>();
		T actual = values.iterator().next();
		List<T> subSet = new LinkedList<T>(values);
		subSet.remove(actual);
		List<List<T>> subSetCombination = combination(subSet, size - 1);
		for (List<T> set : subSetCombination) {
			List<T> newSet = new LinkedList<T>(set);
			newSet.add(0, actual);
			combination.add(newSet);
		}
		combination.addAll(combination(subSet, size));
		return combination;
	}
}
