package modules;

import static constants.Constants.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.Vector;

import org.eclipse.draw2d.ChopboxAnchor;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.ConnectionEndpointLocator;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.FigureCanvas;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.LightweightSystem;
import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.draw2d.ScrollBar;
import org.eclipse.draw2d.Viewport;
import org.eclipse.draw2d.XYLayout;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

public class FrameStructure {
	// Maps for storing various frames
	private Map<String, String> frameStructureActiveMap, frameStructurePassiveMap, frameStructurePrepositionMap,
			frameStructurePreconditionMap, frameStructureConjunctionMap, frameStructureMarkerMap;

	private static FrameStructure FRAMES = new FrameStructure();
	public static CSVWriter writer, writer2, writer3, outputWriter, writertrial, writerex;
	public static CSVReader reader, reader2, reader3, readerfinal;
	public static int[] BLOCK_COUNT;
	public static String[] WORD_TOKEN_LIST;
	public static String USER_STORY;
	public static String FUNCTIONALITY;
	public static int ver;
	public static ArrayList<Integer>[] ADJACENCY_LIST;
	public static Vector<String> ORIGINAL_STRING = new Vector<String>();
	public Map<Integer, List<Integer>> block = new LinkedHashMap<Integer, List<Integer>>();

	public static void main(String[] args) throws Exception {
		FRAMES.call();
	}

	public void call() throws Exception {
		// To extract the input file we need to enter the name of FUNCTIONALITY
		System.out.println("Enter the name of feature of which test case needs to derived: ");
		Scanner scanner = new Scanner(System.in);
		FUNCTIONALITY = scanner.next();
		scanner.close();

		// To get dependencies of the input in excel sheet
		CreateNlpPipeline pipeline = new CreateNlpPipeline();
		pipeline.dependencies(FUNCTIONALITY, USER_STORY);
		createFileReaderAndWriter();

		// Different function calls
		FRAMES.fetchEnhancedPlusPlusDependencies();
		FRAMES.openShell();
		FRAMES.removeDuplicatesFromCombineCSV();
		FRAMES.storeOutputInStandardExcel();
	}

	private void createFileReaderAndWriter() throws Exception {
		// File created to store the indices of graph
		writer = new CSVWriter(new FileWriter(CommonPage.getIntermediateFilePath(FUNCTIONALITY)));

		// Intermediate file
		writer2 = new CSVWriter(new FileWriter(CommonPage.getIntermediateFilePath(FUNCTIONALITY, ZERO)));

		// Combined file
		writer3 = new CSVWriter(new FileWriter(CommonPage.getIntermediateFilePath(FUNCTIONALITY, COMBINE_TESTCASES)));
		reader3 = new CSVReader(new FileReader(CommonPage.getIntermediateFilePath(FUNCTIONALITY, COMBINE_TESTCASES)));

		// Removing duplicates
		writerex = new CSVWriter(new FileWriter(CommonPage.getIntermediateFilePath(FUNCTIONALITY, DUPLICATES_REMOVED)));
		readerfinal = new CSVReader(
				new FileReader(CommonPage.getIntermediateFilePath(FUNCTIONALITY, DUPLICATES_REMOVED)));

		// Output file
		outputWriter = new CSVWriter(new FileWriter(CommonPage.getOutputFilePath(FUNCTIONALITY)));
	}

	// To remove duplicates from the csv sheet
	void removeDuplicatesFromCombineCSV() throws Exception {
		String crunchifyCSVFile = CommonPage.getIntermediateFilePath(FUNCTIONALITY, COMBINE_TESTCASES);
		BufferedReader crunchifyBufferReader = null;
		String crunchifyLine = EMPTY_STRING;
		Set<String> crunchifyAllLines = new HashSet<String>();
		crunchifyAllLines.clear();

		try {
			crunchifyBufferReader = new BufferedReader(new FileReader(crunchifyCSVFile));
			while ((crunchifyLine = crunchifyBufferReader.readLine()) != null) {
				crunchifyLine = crunchifyLine.strip();

				if (crunchifyAllLines.add(crunchifyLine)) {
					System.out.println(crunchifyLine.length());
					String[] cfl = crunchifyLine.split(",");
					writerex.writeNext(cfl);
				}
			}
			writerex.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (crunchifyBufferReader != null) {
				crunchifyBufferReader.close();
			}
		}
	}

	// To store the output in standard excel sheet
	void storeOutputInStandardExcel() throws Exception {
		String line[];
		String testStep = EMPTY_STRING;
		int stepNumber = 1;
		try {
			String columnNames[] = { "Testcase Name", "Testcase Description", "Testcase Steps",
					"Testcase Excepted Results", "Components", "Test Type", "Automation Status", "Fix Version",
					"Priority", "Affects Version" };
			String columnContents[] = { EMPTY_STRING,EMPTY_STRING, testStep, EMPTY_STRING, "QA", "Functional", "Not Applicable", "<release_version>",
					"P1", "<release_version>" };

			outputWriter.writeNext(columnNames);
			outputWriter.flush();

			// Writing test cases fetching from other files
			while ((line = readerfinal.readNext()) != null) {
				for (int i = 0; i < line.length; i++) {
					testStep += stepNumber;
					testStep += ')';
					testStep += line[i];
					testStep += "\n";

					if (i != line.length - 1) {
						if (line[i + 1].equals(EMPTY_STRING)) {
							break;
						}
					}
					stepNumber++;
				}
				columnContents[2] = testStep;
				outputWriter.writeNext(columnContents);
				outputWriter.flush();
				testStep = EMPTY_STRING;
				stepNumber = 1;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// To fetch enhanced plus plus dependencies from intermediate csv files
	void fetchEnhancedPlusPlusDependencies() throws Exception {
		List<String> enhancedPlusPlus = new ArrayList<String>();
		CreateNlpPipeline snlp = new CreateNlpPipeline();
		String line[];

		// Reading the contents of the csv file
		CSVReader csvReader = new CSVReader(new FileReader(
				CommonPage.getIntermediateFilePath(FUNCTIONALITY,DEPENDENCIES)));
		line = csvReader.readNext();

		// Storing the pos tag output to vector
		Vector<String> wordToken = snlp.partsOfSpeechTokens(FUNCTIONALITY, USER_STORY);
		WORD_TOKEN_LIST = new String[wordToken.size()];
		BLOCK_COUNT = new int[wordToken.size()];

		// Store enhanced plus plus dependencies
		while ((line = csvReader.readNext()) != null) {
			for (int i = 0; i < line.length; i++) {
				if (i == line.length - 2) {
					enhancedPlusPlus.add(line[i]);
				}
			}
		}

		// Add current sentence containing the word and token to the list
		for (int i = 0; i < wordToken.size(); i++) {
			WORD_TOKEN_LIST[i] = wordToken.get(i);
		}
		createFrameStructure(enhancedPlusPlus);
	}

	// to create frame structures for each sentence based on its structure
	public void createFrameStructure(List<String> enhancedPlusPlus) throws Exception {
		int totalBlockCount = 0;
		int count = 0;
		// Creating frame structures for each sentence in paragraph
		for (String dependency : enhancedPlusPlus) {
			String sentence = WORD_TOKEN_LIST[count];
			FrameStructureActive fsa = new FrameStructureActive();
			frameStructureActiveMap = fsa.frameActive(dependency, sentence);
			FrameStructurePassive fsp = new FrameStructurePassive();
			frameStructurePassiveMap = fsp.framePassive(dependency, sentence);
			FrameStructurePreposition fspr = new FrameStructurePreposition();
			frameStructurePrepositionMap = fspr.framePreposition(dependency, sentence);
			FrameStructurePrecondition fspc = new FrameStructurePrecondition();
			frameStructurePreconditionMap = fspc.framePrecondition(dependency, sentence);
			FrameStructureConjunction fsc = new FrameStructureConjunction();
			frameStructureConjunctionMap = fsc.frameConjunction(dependency, sentence);
			FrameStructureMarker fsm = new FrameStructureMarker();
			frameStructureMarkerMap = fsm.frameMarker(dependency, sentence);

			// If precondition is there than we require 3 blocks else only 1 block
			if (!frameStructurePreconditionMap.isEmpty()) {
				totalBlockCount += 2;
				BLOCK_COUNT[count] = 3;
			} else {
				BLOCK_COUNT[count] = 1;
			}
			count++;
		}
		totalBlockCount += count;

		// Initialize the graph with size
		Graph(totalBlockCount + 1);
		System.out.println("Number of blocks in activity diagram: " + totalBlockCount);
		System.out.println();

	}

	// Initialize vertex count and adjacency list
	public void Graph(int vertices) {
		ver = vertices;
		initAdjList();
	}

	// Utility method to initialize adjacency list
	@SuppressWarnings("unchecked")
	private void initAdjList() {
		ADJACENCY_LIST = new ArrayList[ver];
		for (int i = 0; i < ver; i++) {
			ADJACENCY_LIST[i] = new ArrayList<Integer>();
		}
	}

	// Open a Shell call a method for drawing
	public void openShell() {
		Shell shell = new Shell(new Display());
		shell.getMaximized();
		shell.setText("My Main View");
		shell.setLayout(new GridLayout());

		Canvas canvas = buildDiagram(shell);
		canvas.setLayoutData(new GridData(GridData.FILL_BOTH));

		Display display = shell.getDisplay();
		shell.open();
		while (!shell.isDisposed())
			while (!display.readAndDispatch())
				display.sleep();

		ScrollBar scrollBar = new ScrollBar();
		scrollBar.setBackgroundColor(ColorConstants.black);
	}

	// Instantiate the root figure, where we draw figures
	private Canvas buildDiagram(Composite parent) {
		Figure root = new Figure();
		root.setFont(parent.getFont());
		root.setLayoutManager(new XYLayout());

		// Instantiate a canvas on which to draw
		FigureCanvas canvas = new FigureCanvas(parent, SWT.V_SCROLL);
		canvas.setBackground(ColorConstants.white);
		LightweightSystem lws = new LightweightSystem(canvas);
		canvas.setViewport(new Viewport(true));
		canvas.setScrollBarVisibility(FigureCanvas.ALWAYS);
		canvas.setContents(root);
		lws.setContents(canvas.getViewport());

		DrawClass drawObj = new DrawClass();
		drawObj.draw(root);
		return canvas;
	}

	// Returns a connection figure with chopbox between two figures
	public Connection myConnection(IFigure fig1, IFigure fig2, boolean ifContains, int direction) {
		PolylineConnection connection = new PolylineConnection();
		connection.setSourceAnchor(new ChopboxAnchor(fig1));
		connection.setTargetAnchor(new ChopboxAnchor(fig2));
		if (ifContains == true) {
			addMultiplicity(connection, direction);
		}
		return connection;
	}

	// To add two different directions
	public void addMultiplicity(PolylineConnection c, int direction) {
		ConnectionEndpointLocator targetEL = new ConnectionEndpointLocator(c, true);
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
}
