package nlp;
//imported general libraries for reading/file and handling basic functionality
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.List;
//libraries for reading regular expressions
import java.util.regex.Matcher;
import java.util.regex.Pattern;
//reading csv inputs
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import edu.stanford.nlp.pipeline.JSONOutputter.Writer;

import java.awt.geom.AffineTransform;
import java.io.BufferedReader;

//import my.draw2d.example.MainView;
//import my.draw2d.example.MyListener;

import java.io.File;
import java.io.FileNotFoundException;
import org.eclipse.draw2d.ConnectionAnchor;

//For activity graph flow
// import org.eclipse.draw2d.PolygonShape;
import org.eclipse.draw2d.Triangle;
import org.eclipse.draw2d.Viewport;
import org.eclipse.draw2d.Shape;
import org.eclipse.draw2d.ArrowButton;
import org.eclipse.draw2d.Ellipse;
import org.eclipse.draw2d.RoundedRectangle;
import org.eclipse.draw2d.Button;
import org.eclipse.draw2d.ChopboxAnchor;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.Label;

import org.eclipse.draw2d.ConnectionEndpointLocator;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.FigureCanvas;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.LightweightSystem;
import org.eclipse.draw2d.PolygonDecoration;
import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.draw2d.RectangleFigure;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.ScrollBar;

import org.eclipse.draw2d.ToolbarLayout;
import org.eclipse.draw2d.XYLayout;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Layout;
import java.text.DateFormat;  
import java.text.SimpleDateFormat; 

public class frame_structure {
  
	
	public static int[] arr1;
	public String[] km;
	//maps for storing various frames
	public Map<String, String> fsamap;
	public Map<String, String> fspmap;
	public Map<String, String> fsprmap;
	public Map<String, String> fspcmap;
	public Map<String, String> fscmap;
	public Map<String, String> fsmmap;
	//map to store the keywords
	Map<String, List<String>> map = new LinkedHashMap<String, List<String>>();
	Vector<String> v;
	//to reduce redundancy
	Vector<String> not_to_form_combinations = new Vector<String>();
	Vector<String> originalstring = new Vector<String>();
	int ver;
	Vector<Integer> tostorea = new Vector<Integer>();
	Vector<Vector<Integer>> g = new Vector<Vector<Integer>>();
	Vector<Integer> path = new Vector<Integer>();
	Map<Integer, List<Integer>> block = new LinkedHashMap<Integer, List<Integer>>();
	Vector<String> pathstring = new Vector<String>();
	ArrayList<Integer>[] adjList;
	

	int len = 0;
	//variable for reading csv file
	static CSVWriter writer;
	static CSVReader reader;
	static CSVWriter writer2;
	static CSVReader reader2;
	static CSVWriter writer3;
	static CSVReader readerfinal;
	static CSVWriter writerfinal;
	static CSVWriter writertrial;
	static CSVReader reader3;
	 static CSVWriter writerex;
	static String functionality="";
	//for storing date and time along with output
	static String timeStamp = new SimpleDateFormat("dd.MM.yyyy.HH.mm.ss").format(new Date());
	public static String userStory="";
	
	public static void main(String[] args) throws Exception
	{
		
		frame_structure frames=new frame_structure();
		int z=frames.call();
	}
	
	public int call()  {

		try {
			frame_structure fs1=new frame_structure();
			//to extract the input file we need to enter the name of functionality
			System.out.println("Enter the name of feature of which test case needs to derived: ");
			Scanner sc=new Scanner(System.in);
			functionality=sc.next();
			//To get dependencies of the input in excel sheet
			CreateNlpPipeline snlp2=new CreateNlpPipeline();
			snlp2.dependencies(functionality,userStory);
			//file created to store the indices of graph created using 
			writer = new CSVWriter(
					new FileWriter("./intermediate/UserStoryStorepath"+functionality+".csv"));
			//intermediate file
			writer2 = new CSVWriter(new FileWriter(
					"./intermediate/UserStoryStorepathtrialhomepage"+functionality+"0.csv"));
			//combined file
			writer3 = new CSVWriter(new FileWriter(
					"./intermediate/UserStoryStorepathtrialhomepage"+functionality+"combine.csv"));
			reader3=new CSVReader(new FileReader("./intermediate/UserStoryStorepathtrialhomepage"+functionality+"combine.csv"));
			//removing duplicates
			writerex= new CSVWriter(new FileWriter("./intermediate/UserStoryStorepathtrialhomepage"+functionality+"combinenodup.csv"));
			
			readerfinal = new CSVReader(new FileReader(
					"./intermediate/UserStoryStorepathtrialhomepage"+functionality+"combinenodup.csv"));
			writerfinal = new CSVWriter(new FileWriter(
					"./output/testcase_"+functionality+" "+timeStamp+"_standard.csv"));
			 


			frame_structure fs = new frame_structure();
			//different function calls
			fs.create_frame_structure();
			fs.open();
			
			fs.nodup();
			fs.standardexcel();
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 1;

	}
	public frame_structure(String userStory)
	{
		frame_structure.userStory=userStory;
	}
	public frame_structure() {
		// TODO Auto-generated constructor stub
	}

    public void setuserStory(String userStory) throws Exception{
  //   System.out.println(userStory);
    	
    	frame_structure.userStory=userStory;
    	//frame_structure.main(null);
    	
    }
    //to remove duplicates from the csv sheet
	void nodup() throws Exception
	{
		
 
        String crunchifyCSVFile = "./intermediate\\UserStoryStorepathtrialhomepage"+functionality+"combine.csv";
 
        // Reads text from a character-input stream, buffering characters so as to provide for the
        // efficient reading of characters, arrays, and lines.
        BufferedReader crunchifyBufferReader = null;
        String crunchifyLine = "";
 
        // This class implements the Set interface, backed by a hash table (actually a HashMap instance).
        // It makes no guarantees as to the iteration order of the set; in particular, it does not guarantee that the order will
        // remain constant over time. This class permits the null element.
        Set<String> crunchifyAllLines = new HashSet<String>();
        crunchifyAllLines.clear();
   
        try {
            crunchifyBufferReader = new BufferedReader(new FileReader(crunchifyCSVFile));
            while ((crunchifyLine = crunchifyBufferReader.readLine()) != null) {
            	crunchifyLine=crunchifyLine.strip();
            	
                if (crunchifyAllLines.add(crunchifyLine)) {
                	System.out.println(crunchifyLine.length());
                    //crunchifyLog("Processed line: " + crunchifyLine);
                    String[] cfl=crunchifyLine.split(",");
                    writerex.writeNext(cfl);
                    
                } 
            }
            writerex.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (crunchifyBufferReader != null) {
                try {
                    crunchifyBufferReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
       
    }
	//to store the output in standard excel sheet
   
	void standardexcel() throws Exception {
		try {
			//column names
			String line1[] = { "Testcase Name", "Testcase Description", "Testcase Steps", "Testcase Excepted Results",
					"Components", "Test Type", "Automation Status", "Fix Version", "Priority", "Affects Version" };

			
			StringBuffer buffer = new StringBuffer();
			String line[];
			String linedup1[];
			String s = "";
			String line2[] = { "", "", s, "", "QA", "Functional", "Not Applicable", "<release_version>", "P1", "<release_version>" };
			Set<String[]> hash_Set = new HashSet<String[]>();
			
			
			writerfinal.writeNext(line1);
			writerfinal.flush();
			int cnter = 1;
			int i;
			//writing test cases fetching from other files
			while ((line = readerfinal.readNext()) != null) {
				for (i = 0; i < line.length; i++) {
					// System.out.print(line[i]);
					// System.out.print("\n");
					s += cnter;
					s += ')';
					s += line[i];
					s += "\n";

					if (i != line.length - 1) {
						if (line[i + 1].equals("")) {
							break;
						}

					}

					cnter++;

				}
				line2[2] = s;
				s = "";

				writerfinal.writeNext(line2);
				writerfinal.flush();
				cnter = 1;
			}
		

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	void create_frame_structure() throws Exception {
		CSVReader reader = new CSVReader(
				new FileReader("./intermediate/UserStoryJiraNewOutputtrial2.csv"));
		// Reading the contents of the csv file
		String line[];
		line = reader.readNext();

		List<String> slist = new ArrayList<String>();
		CreateNlpPipeline snlp=new CreateNlpPipeline();
		//Storing the pos tag output to vector
		Vector<String> k = snlp.pos_data(functionality,userStory);
		
		System.out.println(k);
		//store enhanced plus plus dependencies
		while ((line = reader.readNext()) != null) {
			for (int i = 0; i < line.length; i++) {
				if (i == line.length - 2) {
					slist.add(line[i]);

				}
			}

		}

	
		km = new String[k.size()];
		// iterate vector
		arr1 = new int[k.size()];
		for (int i = 0; i < k.size(); i++) {

			// add current element to the array
			km[i] = k.get(i);
		}

		int cnt = 0; // Accessing each sentence from string list and calling functions for
						// calculating frame structures as stated.
		int finalcnt = 0;
		// Map<String,String> mo =new LinkedHashMap<String, String>();
		//for each sentence in paragraph
		for (String sl : slist) {
			System.out.println(sl);
			//storing frames in map
			String k1 = km[cnt];
			System.out.println(k1);
			frame_structure_active fsa = new frame_structure_active();

			fsamap = fsa.frame_active(sl, k1);
			frame_structure_passive fsp = new frame_structure_passive();
			fspmap = fsp.frame_passive(sl, k1);
			frame_structure_preposition fspr = new frame_structure_preposition();
			fsprmap = fspr.frame_preposition(sl, k1);
			frame_structure_precondition fspc = new frame_structure_precondition();
			fspcmap = fspc.frame_precondition(sl, k1);
			frame_structure_conjunction fsc = new frame_structure_conjunction();
			fscmap = fsc.frame_conjunction(sl, k1);
			frame_structure_marker fsm = new frame_structure_marker();
			fsmmap = fsm.frame_marker(sl, k1);
			//if precondition is there than we require 3 blocks else only 1 block
			if (!fspcmap.isEmpty()) {
				finalcnt += 2;
				arr1[cnt] = 3;

			} else {
				arr1[cnt] = 1;
			}

			cnt++;

		}
		for(int i=0;i<arr1.length;i++)
		{
			System.out.println(arr1[i]);
		}

		finalcnt += cnt;
		//initialize the graph with size
		Graph(finalcnt + 1);
		System.out.println("No of blocks in activity diagram:" + finalcnt);
		int a1 = 1;
		int b1 = 2;
		int c1 = finalcnt;

	
		System.out.println();

	}

	// This method draws figures on the root
	void draw(Figure root) {
		int n = arr1.length; //the block count
		int lengthofprev = 40;   //default size
		//to check predcondition present in previous block or not
		int ifused = 0;
		//for iteration
		int cnt1 = 0;
		int reverseindex = 0;
		for (int i = 0; i < n; i++) {
			reverseindex += arr1[i];
		}
		Figure first = myRound("Start");
		int forwardindex = 0;
		originalstring.setSize(reverseindex + 1);
		//for storing the string value at correct index for traversing
		System.out.println("Orginal string at start ");
		for (String os1 : originalstring) {
			System.out.println(os1);
		}
		int linecnt = 1;
		block.put(linecnt, new LinkedList<Integer>());
		block.get(linecnt).add(forwardindex);
		linecnt += 5;
		//drawn initial
		originalstring.setElementAt("Start", forwardindex++);
		Figure second = myRoundedRectangle("");
		int lengthofwords;

		int linesrequired = 3;
		
		int serialindex = 0;
	
		int n11 = 725;
		int n22 = 500;
		int n33 = 900;
		int tmpindex = 0;
		int n44 = 700;
		//for giving previous ref
		Figure[] fig = new Figure[reverseindex + 1];
		// int figcnt=0;
		fig[forwardindex] = first;

		int cnter = 1;

		List<Integer> ifindex = new LinkedList<Integer>();
		Map<Integer, List<Integer>> mappt = new LinkedHashMap<Integer, List<Integer>>();
		int cnt = 0;
		
		//creating graph
		root.add(first, new Rectangle(new Point(700, 40), first.getPreferredSize()));
		for (int i = 0; i < n; i++) {
			//if simple sentence is present
			if (arr1[i] == 1) {
				int lengthcal = km[cnt1].length();
				//to remove pos tags
				String name1 = km[cnt1].replaceAll("([A-Z]*)\\b", "");
				//name1=name1.replaceAll("(-)\\b", "");
				
				name1 = name1.replaceAll("[^a-zA-Z0-9 ,_/-]", "");
				name1 = name1.trim();
				System.out.println(name1);
				lengthcal = name1.length();
				String[] words = name1.split("  ");
				//calling the function to draw rounded rectangle
				second = myRoundedRectangle(name1.substring(0, lengthcal));

				// forwardindex++;
				//for checking any block is present on the same line to rearrange the blocks
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

				System.out.println((forwardindex - 1) + " " + (forwardindex));
				addEdge(forwardindex - 1, forwardindex);
				originalstring.setElementAt(name1.substring(0, lengthcal), forwardindex++);
				//adding to the grapg
				root.add(second, new Rectangle(new Point(n44 - ifused * 200 + tmpcnt * 280, lengthofprev + 100),
						second.getPreferredSize()));
				//storing the position values for identifying position for next block 
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
				//connnecting two blocks
				root.add(myConnection(first, second, false, 0));

				first = second;

			} else {
				//if precondition is present
				String s = km[cnt1].replaceAll("([A-Z]*)\\b", "");
				s = s.replaceAll("[^a-zA-Z0-9,_/-]", " ");
				s = s.trim();
				System.out.println(s);
				int indexwhen=s.indexOf("When");
				//if precondition is of when format then we need to check the condition first then execute the later part
				if (indexwhen>0) {
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
					int endind1 = s.indexOf("When");
					String secondstring = s.substring(indexwhen);
					int lengthcal = secondstring.length();
					String[] words = secondstring.split("  ");

					Figure second1 = myRoundedRectangle(secondstring);
					addEdge(forwardindex - 1, forwardindex );
					originalstring.setElementAt(secondstring, forwardindex++);
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
					String firststring = s.substring(0, endind1 - 2);
					lengthcal = secondstring.length();
					String[] words1 = secondstring.split("  ");

					Figure second2 = myRoundedRectangle(firststring);
					addEdge(forwardindex -1, forwardindex);
					originalstring.setElementAt(secondstring, forwardindex++);
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
					second=second2;
					

				} else {
					//if if then else present
					int endind = s.indexOf("Then");
					String firststring = s.substring(4, endind - 2);
					// boolean String;
					int vectcnt = 0;
					int ind = 0;
					int flg = 0;
					//if the string is in prev position or not
					for (String tmp1 : originalstring) {
						if (tmp1 != null) {
							if (tmp1.equals(firststring)) {
								tmpindex = originalstring.indexOf(tmp1);
								List<Integer> ltmp = mappt.get(tmpindex);
							
								ind = originalstring.indexOf(tmp1);

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
								mappt.put(originalstring.indexOf(tmp1), new LinkedList<Integer>());
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
						//to map the new if to old then part
						firststring = firststring.concat(" ?");						
						Figure third = fig[tmpindex];

						originalstring.setElementAt(firststring, forwardindex++);

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
						// mappt.put(725 - ifused * 200, lengthofprev + (linesrequired * 20));
						root.add(myConnection(second, third, false, 0));
						lengthofprev += 140;
						lengthofwords = wordselse.length - 1;
						linesrequired = lengthofwords / 3;
						linesrequired += 2;

						int endind2 = s.indexOf("Else");
						String secondstring = s.substring(endind + 6, endind2 - 2);
						String[] wordselse2 = secondstring.split("  ");

						Figure third1 = myRoundedRectangle(secondstring);
						// originalstring.add(forwardindex++,secondstring);
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
						System.out.println((ind) + " " + (forwardindex));
						addEdge(ind, forwardindex);
						originalstring.setElementAt(secondstring, forwardindex++);
						root.add(third1, new Rectangle(new Point(n22 - ifused * 200 + tmpcnt * 280, lengthofprev + 100),
								third1.getPreferredSize()));

						mappt.put(forwardindex - 1, new LinkedList<Integer>());
						mappt.get(forwardindex - 1).add(n22 - ifused * 200 + tmpcnt * 280);
						mappt.get(forwardindex - 1).add(lengthofprev + 100);
						mappt.get(forwardindex - 1).add(ifused);
						mappt.get(forwardindex - 1).add(lengthofprev);
						mappt.get(forwardindex - 1).add(linesrequired);
						mappt.get(forwardindex - 1).add(n22);
						// mappt.put(00 - ifused * 200, lengthofprev + (linesrequired * 20));
						root.add(myConnection(third, third1, true, 1));
						n44 = n22;
						fig[forwardindex - 1] = third1;

						String thirdstring = s.substring(endind2 + 6, s.length());

						// originalstring.add(reverseindex--, thirdstring);
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
						System.out.println((ind) + " " + reverseindex);
						addEdge(ind, reverseindex);
						originalstring.setElementAt(thirdstring, reverseindex--);
						String[] wordselse3 = thirdstring.split("  ");

						Figure third2 = myRoundedRectangle(thirdstring);

						// reverseindex--;

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
						// mappt.put(900 - ifused * 200, lengthofprev + (linesrequired * 20));
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

					}

					else {
						//new if then else block
						firststring = firststring.concat(" ?");
						Figure third = myRectangle(firststring);
						
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
						System.out.println((forwardindex - 1) + " " + (forwardindex));
						addEdge(forwardindex - 1, forwardindex);
						originalstring.setElementAt(firststring, forwardindex++);

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

						int endind2 = s.indexOf("Else");
						String secondstring = s.substring(endind + 6, endind2 - 2);
						String[] wordselse2 = secondstring.split("  ");

						Figure third1 = myRoundedRectangle(secondstring);
						
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
						System.out.println((forwardindex - 1) + " " + (forwardindex));
						addEdge(forwardindex - 1, forwardindex);
						originalstring.setElementAt(secondstring, forwardindex++);
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

						String thirdstring = s.substring(endind2 + 6, s.length());

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
						System.out.println((forwardindex - 2) + " " + reverseindex);
						addEdge(forwardindex - 2, reverseindex);
						originalstring.setElementAt(thirdstring, reverseindex--);
						String[] wordselse3 = thirdstring.split("  ");

						Figure third2 = myRoundedRectangle(thirdstring);


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

							List z11 = block.get(z1);
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
		
		//printing the output
		for (Map.Entry<Integer, List<Integer>> mapElement1 : block.entrySet()) {
			System.out.print("key" + mapElement1.getKey());
			for (Integer i : mapElement1.getValue()) {
				System.out.print("values" + i + " ");
				System.out.print(originalstring.get(i));
			}
			System.out.println();
		}
		for (Integer ind : ifindex) {
			System.out.print(ind);
		}
		System.out.println();
		for (Map.Entry<Integer, List<Integer>> mapElement1 : mappt.entrySet()) {
			System.out.print("key" + mapElement1.getKey());
			List<Integer> lst = mapElement1.getValue();
			System.out.print("p1" + lst.get(0));
			System.out.print("p2" + lst.get(1));
			System.out.println();

		}
	

		System.out.println("Figure ");
		for (int figcnt1 = 0; figcnt1 < reverseindex; figcnt1++) {
			System.out.println(fig[figcnt1]);
		}
		System.out.println("originalstring");
		for (String os : originalstring) {
			System.out.println(os);
		}
		System.out.println("End");
		

		Scanner s = new Scanner( "Appliance_Model		select\n" + "Appliance_Model		deselect\n" +

		
				"Software_Version 	select\n" + "Software_Version 	deselect\n" + "Capacity_min	positive\n"
				+ "Capacity_min	 negative\n" + "Capacity_max	positive\n"
				+ "Capacity_max	negative\n" + "Appliance_type	HA_cluster\n" + "Appliance_type	Single\n"
				+ "Actions	usable\n" + "Actions	unusable\n" + "Manage_Alerts	usable\n"
				+ "Manage_Alerts unusable\n" + "number usable\n" + "number	unusable\n" + "days/hours	usable\n"
				+ "days/hours	unusable\n" + "three-dot usable\n" + "three-dot unusable\n" 
				+"Application	Netinsights\n"+"Application		Cortex\n"+"Application	All-open\n"+"popup	usable\n"
				+ "popup	unusable\n" + "logout	usable\n" + "logout	unusable\n" + "Continue	usable\n"
				+ "Continue	unusable\n"+"email_header	correct\n"+"email_header	incorrect\n"+"email_body	correct\n"
				+"email_body	incorrect\n"+"appliance_details		correct\n"+"appliance_details	incorrect\n"
				);
	
		not_to_form_combinations.add("Capacity_min");
		not_to_form_combinations.add("Capacity_max");


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
		//
		int flg = 0;
		Map<String, Integer> countermap = new LinkedHashMap<String, Integer>();
		for (Map.Entry<String, List<String>> mapElement1 : map.entrySet()) {
			String key = mapElement1.getKey();
			for (String skey : not_to_form_combinations) {
				if (key.equals(skey)) {
					flg = 1;
					break;
				}

			}
			if (flg == 0) {

				List<String> value = mapElement1.getValue();
				int i = value.size();
				countermap.put(key, i);
				// i=0;
			}
		

			else {
				flg = 0;
			}

		}
		System.out.println("Counter map : " + countermap);

		int count = 0;
		int index = 0;
		Map<Integer, Integer> addbranch = new LinkedHashMap<Integer, Integer>();
		//to add edges to the graph
		for (String ostring : originalstring) {
			// System.out.println("String "+ostring);
			for (Map.Entry<String, List<String>> mapElement : map.entrySet()) {
				String key = mapElement.getKey();
				// System.out.println("key"+key);
				if (ostring != null) {
					if (ostring.contains(key) == true) {
						count++;
					}
				}


			}
			// addbranch.put(1, 2);
			addbranch.put(index, count);
			index++;
			count = 0;
		}
		System.out.print("Map of add branch" + addbranch);
		int src = 0;
		int d;
		int counter = 0;
		System.out.println(tostorea);
		for (ArrayList<Integer> arr : adjList) {
			System.out.print("counter:" + counter);
			System.out.print(" ");
			System.out.print(originalstring.get(counter));
			for (Integer i : arr) {
				System.out.print(i);

				System.out.print(" ");
				System.out.print(originalstring.get(i));
				System.out.print(" ");
			}
			System.out.println("Ne");
			System.out.println();
			// counter++;
			if (arr.isEmpty()) {
				d = counter;

				System.out.println("\nFollowing are all different paths from " + src + " to " + d);
				printAllPaths(src, d);
			}
			counter++;
		}
		try {
			reader = new CSVReader(
					new FileReader("./intermediate\\UserStoryStorepath"+functionality+".csv"));
			StringBuffer buffer = new StringBuffer();
			String line[];
			// String s="";
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
		String path1 = "";
		int indexofpath = 0;
		int counter1 = 0;
		int counter2 = 0;
		int flag = 0;
		int flgcnt = 0;
		// Integer sg=0;
		for (String ps : pathstring) {
			ps = ps.replaceAll("[,]", "");
			int psl = ps.length();
			ps = ps.substring(1, psl - 1);
			String[] parts = ps.split(" ");
			int[] n1 = new int[parts.length];
			for (int i = 0; i < parts.length; i++) {
				n1[i] = Integer.parseInt(parts[i]);
			}
			for (int i = 0; i < n1.length; i++) {
				System.out.println(n1[i]);
			}
			System.out.println(ps);

			String[] pssplit = ps.split(" ");
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
			// pathvalue.setSize(tmptotal);
			String pv[] = new String[tmptotal];
			int indexcnt = 0;
			for (int i = 0; i < n1.length; i++) {
				if (storegreater.contains(n1[i]) == false) {
					pv[indexcnt] = originalstring.elementAt(n1[i]);
					indexcnt++;

				} else {
					pv[indexcnt] = originalstring.elementAt(n1[i]);
					indexcnt++;

					
					indexcnt += addbranch.get(n1[i]);
				}

			}
			System.out.println("temp path");
			String tmppath = "";
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

			doRecursion(mapEntryList, combinations);
			if (!tmpmap.isEmpty()) {

				int ind = 0;
				Iterator<String> it = tmpmap.keySet().iterator();
				Vector<String> keyarr=new Vector<String>();
				//String[] keyarr = new String[tmpmap.keySet().size()];
				int keyarrind = 0;
				while (it.hasNext()) {

					keyarr.add(it.next());
					keyarrind++;
				}
				keyarrind = 0;
				Vector<String> keyarr1=new Vector<String>();
				
				int indextmp = 0;
				try {
					reader2 = new CSVReader(new FileReader(
							"./intermediate\\UserStoryStorepathtrialhomepage"+functionality+flgcnt
									+ ".csv"));
					StringBuffer buffer = new StringBuffer();
					String line[];
					// String s="";
					int tmpcnt = 0;
					while ((line = reader2.readNext()) != null) {

						String pv1[] = pv.clone();
						indextmp = 0;
						keyarrind = 0;
						tmpcnt = 0;
						for (int i = 0; i < line.length; i++) {

							if (indextmp < pv1.length) {
								while (pv1[indextmp] != null && indextmp < pv1.length - 1) {

									indextmp++;
								}
								if (keyarrind < keyarr.size() && i < line.length) {
									String has = "";
									for (String ka : keyarr) {
										if (pv1[indextmp - 1].contains(ka)) {
											keyarr1.add(ka);
											
										}
									}

									pv1[indextmp] = keyarr1.get(keyarrind) + " ? " + line[i];
									if (line[i].equals("unusable")||line[i].equals("incorrect")) {
									
										for (int i1 = indextmp + 1; i1 < pv1.length; i1++) {
											pv1[i1]=null;
											
										}
										break;
									}
									tmpcnt++;
									keyarrind++;
									indextmp++;
								}
								
							}

						}
						
						String[] pv11;
						if(indextmp==pv1.length)
						{
						pv11=new String[indextmp];
						}
						else
						{
							pv11=new String[indextmp+2];
						}
						for(int i1=0;i1<pv1.length;i1++)
						{
							if(pv1[i1]!=null)
							{
								pv11[i1]=pv1[i1];
							}
							else
							{
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
			}

			else {
				writer3.writeNext(pv);

				try {
					writer3.flush();
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
			// mapEntryList.clear();writer
			try {
				int flgcnt1 = flgcnt + 1;
				writer2 = new CSVWriter(
						new FileWriter("./intermediate\\UserStoryStorepathtrialhomepage"+functionality+
								+ flgcnt1 + ".csv"));

				writer3 = new CSVWriter(new FileWriter(
						"./intermediate\\UserStoryStorepathtrialhomepage"+functionality+"combine.csv",
						true));
				flgcnt++;
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

	}

	static int cnter = 0;
	//to find out combinations possible of keywords
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

	void Graph(int vertices) {

		// initialise vertex count
		ver = vertices;

		// initialise adjacency list
		initAdjList();
	}

	// utility method to initialise adjacency list
	@SuppressWarnings("unchecked")
	private void initAdjList() {
		adjList = new ArrayList[ver];

		for (int i = 0; i < ver; i++) {
			adjList[i] = new ArrayList<Integer>();
		}
	}

	// add edge from u to v
	public void addEdge(int u, int v) {
		// Add v to u's list.
		adjList[u].add(v);
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
		//to identify paths present in graph
		if (u.equals(d)) {
			System.out.println(localPathList);
			pathlistfinal.add(localPathList);
			String tmp = localPathList.toString();
			String tmp1[] = { tmp };
			if (!tmp.isEmpty()) {
				writer.writeNext(tmp1);
				if (!localPathList.isEmpty()) {
					for (Integer i : localPathList) {
						System.out.print(originalstring.get(i));
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
		for (Integer i : adjList[u]) {

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

	// Open a Shell call a method for drawing
	public void open() {
		Shell shell = new Shell(new Display());
		// shell.setSize(600, 450);
		shell.setText("My Main View");
		shell.setLayout(new GridLayout());

		// build diagram
		Canvas canvas = buildDiagram(shell);
		canvas.setLayoutData(new GridData(GridData.FILL_BOTH));

		Display display = shell.getDisplay();
		// open and wait until closing
		shell.open();
		while (!shell.isDisposed())
			while (!display.readAndDispatch())
				display.sleep();
		// RoundedRectangle rr=new RoundedRectangle();
		ScrollBar sb = new ScrollBar();
		sb.setBackgroundColor(ColorConstants.black);

	}

	//Instantiate the root figure, where we draw figures
	private Canvas buildDiagram(Composite parent) {

		// instantiate root figure

		Figure root = new Figure();
		root.setFont(parent.getFont());
		root.setLayoutManager(new XYLayout());

		// insantiate a canvas on which to draw
		// Canvas canvas = new Canvas(parent, SWT.CURSOR_SIZEALL) ;
		FigureCanvas canvas = new FigureCanvas(parent, SWT.V_SCROLL);
		canvas.setBackground(ColorConstants.white);
		LightweightSystem lws = new LightweightSystem(canvas);
		canvas.setViewport(new Viewport(true));
		canvas.setScrollBarVisibility(FigureCanvas.ALWAYS);
		canvas.setContents(root);
		lws.setContents(canvas.getViewport());
		// lws.setContents(root);

		// this code for drawing
		draw(root);
		//
		return canvas;
	}

	public Figure myRectangle(String name) {
		//rectangle for precondition
		RectangleFigure f = new RectangleFigure();

		f.setBackgroundColor(ColorConstants.lightGreen);
		f.setLayoutManager(new ToolbarLayout());

		String name1 = name.replaceAll("([A-Z]*)\\b", "");
		System.out.println("name1" + name1);

		String[] arrOfStr = name1.split("  ");
		int k = arrOfStr.length;
		System.out.println("k" + k);

		int nooflines = (k + 3 - 1) / 3;
		//set required size
		f.setPreferredSize(250, nooflines*20);
		int cnttmp = 0;
		int cntarr = 0;
		String fsstr = "";
		for (String a : arrOfStr) {

			fsstr += a;
			fsstr += " ";
			cnttmp++;
			cntarr++;

			if (cnttmp > 2 || cntarr == k) {
				Label fl = new Label(fsstr);

				f.add(fl);

				cnttmp = 0;
				fsstr = "";
			}

		}

		new MyListener(f);
		return f;
	}

	// specific method to draw a block figure
	public Figure myRound(String name) {
		//round for start block
		Ellipse f = new Ellipse();
		// private CompartmentFigure attributeFigure = new CompartmentFigure()
		f.setBackgroundColor(ColorConstants.lightGray);
		f.setLayoutManager(new ToolbarLayout());

		String name1 = name.replaceAll("([A-Z]*)\\b", "");

		String[] arrOfStr = name1.split("  ");

		int k = arrOfStr.length;

		int nooflines = (k + 3 - 1) / 3;
		nooflines*=3;
		f.setPreferredSize(200, nooflines*20);
		int cnttmp = 0;
		int cntarr = 0;
		String fsstr = "";
		//to find number of lines
		for (String a : arrOfStr) {

			fsstr += a;
			fsstr += " ";
			cnttmp++;
			cntarr++;

			if (cnttmp > 2 || cntarr == k) {
				Label fl = new Label(fsstr);

				f.add(fl);

				cnttmp = 0;
				fsstr = "";
			}

		}

		new MyListener(f);
		return f;
	}

	public Figure myRoundedRectangle(String name) {
		//to draw roundedrectangle for simple blocks
		RoundedRectangle f = new RoundedRectangle();
		f.setBackgroundColor(ColorConstants.lightGreen);
		f.setLayoutManager(new ToolbarLayout());

		String name1 = name.replaceAll("([A-Z]*)\\b", "");

		String[] arrOfStr = name1.split("  ");
		int k = arrOfStr.length;
		int nooflines = (k + 3 - 1) / 3;
		//for setting size
		f.setPreferredSize(320, nooflines*20);
		int cnttmp = 0;
		int cntarr = 0;
		//to find number of lines
		String fsstr = "";
		for (String a : arrOfStr) {

			fsstr += a;
			fsstr += " ";
			cnttmp++;
			cntarr++;

			if (cnttmp > 2 || cntarr == k) {
				Label fl = new Label(fsstr);

				f.add(fl);

				cnttmp = 0;
				fsstr = "";
			}
		

		}
		new MyListener(f);
		return f;
	}

	// return a connection figure with chopbox between two figures
	public Connection myConnection(IFigure fig1, IFigure fig2, boolean ifcontains, int direction) {
		PolylineConnection conn = new PolylineConnection();
		conn.setSourceAnchor(new ChopboxAnchor(fig1));
		conn.setTargetAnchor(new ChopboxAnchor(fig2));

		if (ifcontains == true) {

			addMultiplicity(conn, direction);
		}

		return conn;
	}

	public void addMultiplicity(PolylineConnection c, int direction) {
		ConnectionEndpointLocator targetEL = new ConnectionEndpointLocator(c, true);
		//to add two different directions
		if (direction == 1) {
			targetEL.setUDistance(4);
			targetEL.setVDistance(6);
			Label multiplicity = new Label("Yes");
			c.add(multiplicity, targetEL);
		} else if (direction == 2) {
			targetEL.setUDistance(4);
			targetEL.setVDistance(6);
			Label multiplicity = new Label("No");
			c.add(multiplicity, targetEL);
		}
	}

	// build an image starting from relative path wrt user directory
	private static Image getImage(String path) {
		File f = new File(System.getProperty("user.dir"), path);
		return new Image(Display.getCurrent(), f.getAbsolutePath());
	}

	// builds a label with a list of strings
	public Label myListLabel(String[] list) {
		String text = "";
		for (String s : list) {
			text += s + "\n";
		}
		Label label = new Label(text) {
			protected void paintBorder(Graphics g) {
				Rectangle r = getBounds();
				// paints horizontal line on top of label
				g.drawLine(r.x, r.y, r.x + r.width, r.y);
			};

			public Insets getInsets() {
				// top, left, bottom, right
				return new Insets(5, 0, 0, 0);
			}
		};
		return label;
	}
}
