package com.pict.tia.core;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import com.pict.tia.constants.TIAConstants;
import com.pict.tia.models.analyzer.CommitDetails;
import com.pict.tia.models.analyzer.Records;
import com.pict.tia.tokenizer.ComponentTokenizer;
import com.pict.tia.tokenizer.LabelTokenizer;
import com.pict.tia.utils.FileOperations;
import com.pict.tia.utils.JsonOperations;

public class TestImpactAnalyzer
{
	private static final Logger logger = LogManager.getLogger(TestImpactAnalyzer.class);
	public static void main(String[] args)
	{
		ComponentTokenizer component = new ComponentTokenizer();
		LabelTokenizer label = new LabelTokenizer();
		ArrayList<Records> records = new ArrayList<Records>();
		List<String[]> gitHistory = FileOperations.readCSVUsingOpenCSV(TIAConstants.GIT_HISTORY_CSV_PATH);		
		logger.info("Starting Analyzer");
		for (String[] gitRow : gitHistory)
		{
			Records record = new Records();
			CommitDetails commitDetails = new CommitDetails();
			commitDetails.setCommitID(gitRow[0]);
			record.setCommitDetails(commitDetails);
			record.setComponents(component.analyzer(gitRow[3], gitRow[5], gitRow[8], gitRow[7], "PutWhenYouGetMethodName"));
			record.setLabels(label.analyzer(gitRow[3], gitRow[5], gitRow[8], gitRow[7], "PutWhenYouGetMethodName"));
			records.add(record);
		}
		JsonOperations.writeToJSONFile(records, TIAConstants.ANALYZER_JSON_PATH);
		logger.info("Processed successfully, refer json file "+ TIAConstants.ANALYZER_JSON_PATH);
	}
}
