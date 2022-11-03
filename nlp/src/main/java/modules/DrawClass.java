package modules;

import static constants.Constants.*;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import com.opencsv.CSVWriter;

public class DrawClass extends FrameStructure {

	static Vector<String> pathstring = new Vector<String>();
	static Map<String, List<String>> map = new LinkedHashMap<String, List<String>>();

	void draw(Figure root) {
		FunctionCalls obj = new FunctionCalls();
		int n = BLOCK_COUNT.length; // the block count
		int lengthofprev = 40;
		// to check precondition present in previous block or not
		int ifused = 0;
		int cnt1 = 0;
		int reverseindex = 0;
		for (int i = 0; i < n; i++) {
			reverseindex += BLOCK_COUNT[i];
		}
		Figure first = obj.myRound("Start");
		int forwardindex = 0;
		ORIGINAL_STRING.setSize(reverseindex + 1);
		// for storing the string value at correct index for traversing
		System.out.println("Orginal string at start ");
		for (String os1 : ORIGINAL_STRING) {
			System.out.println(os1);
		}
		int linecnt = 1;
		block.put(linecnt, new LinkedList<Integer>());
		block.get(linecnt).add(forwardindex);
		linecnt += 5;
		// For START frame in the graph
		ORIGINAL_STRING.setElementAt("Start", forwardindex++);
		Figure second = obj.myRectangle(EMPTY_STRING);
		int lengthofwords;
		int linesrequired = 3;
		int n11 = 725;
		int n22 = 500;
		int n33 = 900;
		int tmpindex = 0;
		int n44 = 700;
		// for giving previous reference
		Figure[] fig = new Figure[reverseindex + 1];
		fig[forwardindex] = first;
		List<Integer> ifindex = new LinkedList<Integer>();
		Map<Integer, List<Integer>> mappt = new LinkedHashMap<Integer, List<Integer>>();
		// creating graph
		root.add(first, new Rectangle(new Point(700, 40), first.getPreferredSize()));
		for (int i = 0; i < n; i++) {
			// To remove POS tags.
			String sentence_pos_removed = WORD_TOKEN_LIST[cnt1].replaceAll("([A-Z]*)\\b", EMPTY_STRING);
			sentence_pos_removed = sentence_pos_removed.replaceAll("[^a-zA-Z0-9,_/-]", SPACE);
			sentence_pos_removed = sentence_pos_removed.trim();
			// if simple sentence is present
			if (BLOCK_COUNT[i] == 1) {
				int lengthcal = WORD_TOKEN_LIST[cnt1].length();
				lengthcal = sentence_pos_removed.length();
				String[] words = sentence_pos_removed.split("  ");
				second = obj.myRoundedRectangle(sentence_pos_removed.substring(0, lengthcal));
				// for checking any block is present on the same line to rearrange the blocks
				if (!block.containsKey(linecnt)) {
					block.put(linecnt, new LinkedList<Integer>());
					block.get(linecnt).add(forwardindex);
				} else {
					block.get(linecnt).add(forwardindex);
				}
				int tmpcnt = 0;
				for (Integer t : block.get(linecnt)) {
					if (t == forwardindex) {
						break;
					}
					tmpcnt++;
				}
				linecnt += 5;
				System.out.println((forwardindex - 1) + SPACE + (forwardindex));
				obj.addEdge(forwardindex - 1, forwardindex);
				ORIGINAL_STRING.setElementAt(sentence_pos_removed.substring(0, lengthcal), forwardindex++);
				// adding the string to Graph
				root.add(second, new Rectangle(new Point(n44 - ifused * 200 + tmpcnt * 280, lengthofprev + 100),
						second.getPreferredSize()));
				// storing the position values for identifying position for next block
				mappt.put(forwardindex - 1, new LinkedList<Integer>());
				mappt.get(forwardindex - 1).add(n44 - ifused * 200 + tmpcnt * 280);
				mappt.get(forwardindex - 1).add(lengthofprev + 100);
				mappt.get(forwardindex - 1).add(ifused);
				mappt.get(forwardindex - 1).add(lengthofprev);
				mappt.get(forwardindex - 1).add(linesrequired);
				mappt.get(forwardindex - 1).add(n44);
				lengthofprev += 140;
				lengthofwords = words.length - 1;
				linesrequired = lengthofwords / 3;
				linesrequired += 2;
				// adding connection between two blocks
				root.add(myConnection(first, second, false, 0));
				first = second;

			} else {
				// if precondition is present
				System.out.println(sentence_pos_removed);
				int indexwhen = sentence_pos_removed.indexOf("When");
				// if precondition is of when format then we need to check the condition first
				// then execute the later part
				if (indexwhen > 0) {
					if (!block.containsKey(linecnt)) {
						block.put(linecnt, new LinkedList<Integer>());
						block.get(linecnt).add(forwardindex);
					} else {
						block.get(linecnt).add(forwardindex);
					}
					int tmpcnt = 0;
					for (Integer t : block.get(linecnt)) {
						if (t == forwardindex) {
							break;
						}
						tmpcnt++;
					}
					int endind1 = sentence_pos_removed.indexOf("When");
					String secondstring = sentence_pos_removed.substring(indexwhen);
					String[] words = secondstring.split("  ");
					Figure second1 = obj.myRoundedRectangle(secondstring);
					obj.addEdge(forwardindex - 1, forwardindex);
					ORIGINAL_STRING.setElementAt(secondstring, forwardindex++);
					root.add(second1, new Rectangle(new Point(n44 - ifused * 200 + tmpcnt * 280, lengthofprev + 100),
							second1.getPreferredSize()));
					mappt.put(forwardindex - 1, new LinkedList<Integer>());
					mappt.get(forwardindex - 1).add(n44 - ifused * 200 + tmpcnt * 280);
					mappt.get(forwardindex - 1).add(lengthofprev + 100);
					mappt.get(forwardindex - 1).add(ifused);
					mappt.get(forwardindex - 1).add(lengthofprev);
					mappt.get(forwardindex - 1).add(linesrequired);
					mappt.get(forwardindex - 1).add(n44);
					lengthofprev += 140;
					lengthofwords = words.length - 1;
					linesrequired = lengthofwords / 3;
					linesrequired += 2;
					fig[forwardindex - 1] = second1;
					String firststring = sentence_pos_removed.substring(0, endind1 - 2);
					Figure second2 = obj.myRoundedRectangle(firststring);
					obj.addEdge(forwardindex - 1, forwardindex);
					ORIGINAL_STRING.setElementAt(secondstring, forwardindex++);
					root.add(second2, new Rectangle(new Point(n44 - ifused * 200 + tmpcnt * 280, lengthofprev + 100),
							second2.getPreferredSize()));
					mappt.put(forwardindex - 1, new LinkedList<Integer>());
					mappt.get(forwardindex - 1).add(n44 - ifused * 200 + tmpcnt * 280);
					mappt.get(forwardindex - 1).add(lengthofprev + 100);
					mappt.get(forwardindex - 1).add(ifused);
					mappt.get(forwardindex - 1).add(lengthofprev);
					mappt.get(forwardindex - 1).add(linesrequired);
					mappt.get(forwardindex - 1).add(n44);
					root.add(myConnection(second, second1, false, 0));
					root.add(myConnection(second1, second2, false, 0));
					lengthofprev += 140;
					lengthofwords = words.length - 1;
					linesrequired = lengthofwords / 3;
					linesrequired += 2;
					fig[forwardindex - 1] = second2;
					second = second2;
				} else {
					// if if then else present
					int endind = sentence_pos_removed.indexOf("Then");
					String firststring = sentence_pos_removed.substring(4, endind - 2);
					int vectcnt = 0;
					int ind = 0;
					int flg = 0;
					// if the string is in prev position or not
					for (String tmp1 : ORIGINAL_STRING) {
						if (tmp1 != null) {
							if (tmp1.equals(firststring)) {
								tmpindex = ORIGINAL_STRING.indexOf(tmp1);
								List<Integer> ltmp = mappt.get(tmpindex);
								ind = ORIGINAL_STRING.indexOf(tmp1);
								for (Map.Entry<Integer, List<Integer>> mapElement1 : block.entrySet()) {
									List<Integer> val = mapElement1.getValue();
									for (Integer i1 : val) {
										if (i1 == tmpindex) {
											linecnt = mapElement1.getKey();
											break;
										}
									}
								}
								if (!block.containsKey(linecnt)) {
									block.put(linecnt, new LinkedList<Integer>());
									block.get(linecnt).add(forwardindex);
								} else {
									block.get(linecnt).add(forwardindex);
								}
								ifindex.add(linecnt);
								linecnt += 5;
								mappt.put(ORIGINAL_STRING.indexOf(tmp1), new LinkedList<Integer>());
								mappt.get(ind).add(ltmp.get(0));
								mappt.get(ind).add(ltmp.get(1));
								ifused = ltmp.get(2);
								lengthofprev = ltmp.get(3);
								linesrequired = ltmp.get(4);
								n11 = ltmp.get(5);
								n22 = n11 - 225;
								n33 = n11 + 175;
								flg = 1;
								System.out.println("vectcnt" + vectcnt);
								break;
							}
							vectcnt++;
						}
					}
					if (flg == 1) {
						// to map the new if to old then part
						firststring = firststring.concat(" ?");
						Figure third = fig[tmpindex];
						ORIGINAL_STRING.setElementAt(firststring, forwardindex++);
						String[] wordselse = firststring.split("  ");
						int tmplinecnt = linecnt - 10;
						List<Integer> tmplinecntlist = block.get(tmplinecnt);
						second = fig[tmplinecntlist.get(0)];
						root.add(third, new Rectangle(new Point(n11 - ifused * 200, lengthofprev + 100),
								third.getPreferredSize()));
						fig[forwardindex - 1] = third;
						mappt.put(forwardindex - 1, new LinkedList<Integer>());
						mappt.get(forwardindex - 1).add(n11 - ifused * 200);
						mappt.get(forwardindex - 1).add(lengthofprev + 100);
						mappt.get(forwardindex - 1).add(ifused);
						mappt.get(forwardindex - 1).add(lengthofprev);
						mappt.get(forwardindex - 1).add(linesrequired);
						mappt.get(forwardindex - 1).add(n11);
						root.add(myConnection(second, third, false, 0));
						lengthofprev += 140;
						lengthofwords = wordselse.length - 1;
						linesrequired = lengthofwords / 3;
						linesrequired += 2;
						int endind2 = sentence_pos_removed.indexOf("Else");
						String secondstring = sentence_pos_removed.substring(endind + 6, endind2 - 2);
						String[] wordselse2 = secondstring.split("  ");
						Figure third1 = obj.myRoundedRectangle(secondstring);
						if (!block.containsKey(linecnt)) {
							block.put(linecnt, new LinkedList<Integer>());
							block.get(linecnt).add(forwardindex);
						} else {
							block.get(linecnt).add(forwardindex);
						}
						int tmpcnt = 0;
						for (Integer t : block.get(linecnt)) {
							if (t == forwardindex) {
								break;
							}
							tmpcnt++;
						}
						System.out.println((ind) + SPACE + (forwardindex));
						obj.addEdge(ind, forwardindex);
						ORIGINAL_STRING.setElementAt(secondstring, forwardindex++);
						root.add(third1, new Rectangle(new Point(n22 - ifused * 200 + tmpcnt * 280, lengthofprev + 100),
								third1.getPreferredSize()));
						mappt.put(forwardindex - 1, new LinkedList<Integer>());
						mappt.get(forwardindex - 1).add(n22 - ifused * 200 + tmpcnt * 280);
						mappt.get(forwardindex - 1).add(lengthofprev + 100);
						mappt.get(forwardindex - 1).add(ifused);
						mappt.get(forwardindex - 1).add(lengthofprev);
						mappt.get(forwardindex - 1).add(linesrequired);
						mappt.get(forwardindex - 1).add(n22);
						root.add(myConnection(third, third1, true, 1));
						n44 = n22;
						fig[forwardindex - 1] = third1;
						String thirdstring = sentence_pos_removed.substring(endind2 + 6, sentence_pos_removed.length());
						if (!block.containsKey(linecnt)) {
							block.put(linecnt, new LinkedList<Integer>());
							block.get(linecnt).add(reverseindex);
						} else {
							block.get(linecnt).add(reverseindex);
						}
						int tmpcnt1 = 0;
						for (Integer t : block.get(linecnt)) {
							if (t == forwardindex) {
								break;
							}
							tmpcnt1++;
						}
						linecnt += 5;
						System.out.println((ind) + SPACE + reverseindex);
						obj.addEdge(ind, reverseindex);
						ORIGINAL_STRING.setElementAt(thirdstring, reverseindex--);
						Figure third2 = obj.myRoundedRectangle(thirdstring);
						root.add(third2,
								new Rectangle(new Point(n33 - ifused * 200 + tmpcnt1 * 280, lengthofprev + 100),
										third2.getPreferredSize()));
						mappt.put(reverseindex + 1, new LinkedList<Integer>());
						mappt.get(reverseindex + 1).add(n33 - ifused * 200 + tmpcnt1 * 280);
						mappt.get(reverseindex + 1).add(lengthofprev + 100);
						mappt.get(reverseindex + 1).add(ifused);
						mappt.get(reverseindex + 1).add(lengthofprev);
						mappt.get(reverseindex + 1).add(linesrequired);
						mappt.get(reverseindex + 1).add(n33);
						lengthofprev += 140;
						lengthofwords = wordselse2.length - 1;
						linesrequired = lengthofwords / 3;
						linesrequired += 2;
						root.add(myConnection(third, third2, true, 2));
						second = third1;
						fig[reverseindex + 1] = third2;
						ifused++;
						if (ifused > 1) {
							int z1 = ifindex.get(0);
							List<Integer> z2 = mappt.get(z1);
							if (z2 != null) {
								int z3 = z2.get(0);
								z3 += 280;
								mappt.get(z1).set(0, z3);
							}
						}
						first = third1;
					} else {
						// new if then else block
						firststring = firststring.concat(" ?");
						Figure third = obj.myRectangle(firststring);
						if (!block.containsKey(linecnt)) {
							block.put(linecnt, new LinkedList<Integer>());
							block.get(linecnt).add(forwardindex);
						} else {
							block.get(linecnt).add(forwardindex);
						}
						int tmpcnt = 0;
						for (Integer t : block.get(linecnt)) {
							if (t == forwardindex) {
								break;
							}
							tmpcnt++;
						}
						ifindex.add(linecnt);
						linecnt += 5;
						System.out.println((forwardindex - 1) + SPACE + (forwardindex));
						obj.addEdge(forwardindex - 1, forwardindex);
						ORIGINAL_STRING.setElementAt(firststring, forwardindex++);
						String[] wordselse = firststring.split("  ");
						root.add(third, new Rectangle(new Point(n11 - ifused * 200 + tmpcnt * 220, lengthofprev + 100),
								third.getPreferredSize()));
						fig[forwardindex - 1] = third;
						mappt.put(forwardindex - 1, new LinkedList<Integer>());
						mappt.get(forwardindex - 1).add(n11 - ifused * 200 + tmpcnt * 100);
						mappt.get(forwardindex - 1).add(lengthofprev + 100);
						mappt.get(forwardindex - 1).add(ifused);
						mappt.get(forwardindex - 1).add(lengthofprev);
						mappt.get(forwardindex - 1).add(linesrequired);
						mappt.get(forwardindex - 1).add(n11);
						root.add(myConnection(second, third, false, 0));
						lengthofprev += 140;
						lengthofwords = wordselse.length - 1;
						linesrequired = lengthofwords / 3;
						linesrequired += 2;
						int endind2 = sentence_pos_removed.indexOf("Else");
						String secondstring = sentence_pos_removed.substring(endind + 6, endind2 - 2);
						String[] wordselse2 = secondstring.split("  ");
						Figure third1 = obj.myRoundedRectangle(secondstring);
						if (!block.containsKey(linecnt)) {
							block.put(linecnt, new LinkedList<Integer>());
							block.get(linecnt).add(forwardindex);
						} else {
							block.get(linecnt).add(forwardindex);
						}
						int tmpcnt1 = 0;
						for (Integer t : block.get(linecnt)) {
							if (t == forwardindex) {
								break;
							}
							tmpcnt1++;
						}
						System.out.println((forwardindex - 1) + SPACE + (forwardindex));
						obj.addEdge(forwardindex - 1, forwardindex);
						ORIGINAL_STRING.setElementAt(secondstring, forwardindex++);
						root.add(third1,
								new Rectangle(new Point(n22 - ifused * 200 + tmpcnt1 * 220, lengthofprev + 100),
										third1.getPreferredSize()));
						mappt.put(forwardindex - 1, new LinkedList<Integer>());
						mappt.get(forwardindex - 1).add(n22 - ifused * 200 + tmpcnt1 * 100);
						mappt.get(forwardindex - 1).add(lengthofprev + 100);
						mappt.get(forwardindex - 1).add(ifused);
						mappt.get(forwardindex - 1).add(lengthofprev);
						mappt.get(forwardindex - 1).add(linesrequired);
						mappt.get(forwardindex - 1).add(n22);
						root.add(myConnection(third, third1, true, 1));
						fig[forwardindex - 1] = third1;
						String thirdstring = sentence_pos_removed.substring(endind2 + 6, sentence_pos_removed.length());
						if (!block.containsKey(linecnt)) {
							block.put(linecnt, new LinkedList<Integer>());
							block.get(linecnt).add(reverseindex);
						} else {
							block.get(linecnt).add(reverseindex);
						}
						int tmpcnt2 = 0;
						for (Integer t : block.get(linecnt)) {
							if (t == forwardindex) {
								break;
							}
							tmpcnt2++;
						}
						linecnt += 5;
						System.out.println((forwardindex - 2) + SPACE + reverseindex);
						obj.addEdge(forwardindex - 2, reverseindex);
						ORIGINAL_STRING.setElementAt(thirdstring, reverseindex--);
						Figure third2 = obj.myRoundedRectangle(thirdstring);
						root.add(third2,
								new Rectangle(new Point(n33 - ifused * 200 + tmpcnt2 * 280, lengthofprev + 100),
										third2.getPreferredSize()));
						mappt.put(reverseindex + 1, new LinkedList<Integer>());
						mappt.get(reverseindex + 1).add(n33 - ifused * 200 + tmpcnt2 * 100);
						mappt.get(reverseindex + 1).add(lengthofprev + 100);
						mappt.get(reverseindex + 1).add(ifused);
						mappt.get(reverseindex + 1).add(lengthofprev);
						mappt.get(reverseindex + 1).add(linesrequired);
						mappt.get(reverseindex + 1).add(n33);
						lengthofprev += 140;
						lengthofwords = wordselse2.length - 1;
						linesrequired = lengthofwords / 3;
						linesrequired += 2;
						root.add(myConnection(third, third2, true, 2));
						second = third1;
						fig[reverseindex + 1] = third2;
						if (ifused > 1) {
							int z1 = ifindex.get(0);
							List<Integer> z2 = mappt.get(z1);
							if (z2 != null) {
								int z3 = z2.get(0);
								z3 += 280;
								mappt.get(z1).set(0, z3);
							}
						}
						n44 = n22;
						ifused++;
						first = third1;
					}
				}
			}
			cnt1++;
		}
		obj.storeCombinationInMap();
		Map<Integer, Integer> addbranch = new LinkedHashMap<Integer, Integer>();
		obj.addEdgesToGraph(addbranch);
		obj.displayAllPaths();
		Vector<Integer> storegreater = obj.storeGreater(addbranch);
		int flgcnt = 0;
		for (String ps : pathstring) {
			ps = ps.replaceAll("[,]", EMPTY_STRING);
			int psl = ps.length();
			ps = ps.substring(1, psl - 1);
			String[] parts = ps.split(SPACE);
			int[] n1 = new int[parts.length];
			for (int i = 0; i < parts.length; i++) {
				n1[i] = Integer.parseInt(parts[i]);
			}

			System.out.println(ps);
			String[] pssplit = ps.split(SPACE);
			Integer[] psarr = new Integer[pssplit.length];
			for (int i = 0; i < pssplit.length; i++) {
				try {
					psarr[i] = Integer.parseInt(pssplit[i]);
				} catch (Exception e) {
					System.out.println("Unable to parse string to int: " + e.getMessage());
				}
			}
			int tmptotal = 0;
			for (Integer sg : storegreater) {
				for (Integer psi : psarr) {
					if (psi == sg) {
						tmptotal += addbranch.get(sg);
						System.out.println("value return" + addbranch.get(sg));
						break;
					}
				}
			}
			tmptotal += n1.length;
			System.out.println("Tm tot:" + tmptotal);
			String pv[] = new String[tmptotal];
			int indexcnt = 0;
			for (int i = 0; i < n1.length; i++) {
				if (storegreater.contains(n1[i]) == false) {
					pv[indexcnt] = ORIGINAL_STRING.elementAt(n1[i]);
					indexcnt++;
				} else {
					pv[indexcnt] = ORIGINAL_STRING.elementAt(n1[i]);
					indexcnt++;
					indexcnt += addbranch.get(n1[i]);
				}
			}
			System.out.println("temp path");
			String tmppath = EMPTY_STRING;
			for (String path : pv) {
				System.out.println(path);
				if (path != null) {
					tmppath = tmppath.concat(path);
				}
			}
			Map<String, List<String>> tmpmap = new LinkedHashMap<String, List<String>>();
			for (Map.Entry<String, List<String>> mapElement : map.entrySet()) {
				String key = mapElement.getKey();
				List<String> values = mapElement.getValue();
				if (tmppath.contains(key)) {
					if (!tmpmap.containsKey(key)) {
						tmpmap.put(key, new LinkedList<String>());
						for (String tmps : values) {
							tmpmap.get(key).add(tmps);
						}
					}
				}
			}
			List<Map.Entry<String, List<String>>> mapEntryList = new ArrayList<Map.Entry<String, List<String>>>(
					tmpmap.entrySet());
			List<String> combinations = new ArrayList<String>();
			FunctionCalls.doRecursion(mapEntryList, combinations);
			obj.writeCombinationInCSV(tmpmap, pv, flgcnt);
			try {
				int flgcnt1 = flgcnt + 1;
				writer2 = new CSVWriter(new FileWriter(CommonPage.getIntermediateFilePath(FUNCTIONALITY, FLAG) + flgcnt + CSV_EXTENTSION));

				writer3 = new CSVWriter(new FileWriter(CommonPage.getIntermediateFilePath(FUNCTIONALITY, COMBINE_TESTCASES), true));
				flgcnt++;
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

	}
}
