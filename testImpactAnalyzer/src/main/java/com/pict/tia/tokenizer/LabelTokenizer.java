package com.pict.tia.tokenizer;

import java.util.ArrayList;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import com.pict.tia.constants.TIAConstants;
import com.pict.tia.models.analyzer.Label;
import com.pict.tia.utils.FileOperations;

public class LabelTokenizer extends Tokenizer
{
	private static final Logger logger = LogManager.getLogger(LabelTokenizer.class);
	private ArrayList<String> rawLabelsList = new ArrayList<String>();
	
	public LabelTokenizer()
	{
		this.rawLabelsList = FileOperations.readCSV(TIAConstants.LABELS_FILE_PATH);
	}
	
	public ArrayList<String> getListOflabels()
	{
		return rawLabelsList;
	}
	
	public ArrayList<Label> analyzer(String fileName, String pathName, String gitNewChanges, String gitoldChanges, String methodName) {
		ArrayList<Label> labelList = new ArrayList<Label>();
		for (String labelName : getListOflabels())
		{
			boolean isAnyStringMatched = false;
			Label label = new Label();
			label.setName(labelName);
			if(fileName.toLowerCase().contains(labelName.toLowerCase())) {
				label.setMatchInFileName(true);
				isAnyStringMatched = true;
			}
			if(pathName.toLowerCase().contains(labelName.toLowerCase())) {
				label.setMatchInPathName(true);
				isAnyStringMatched = true;
			}
			if(gitNewChanges.toLowerCase().contains(labelName.toLowerCase())) {
				label.setMatchInNewChanges(true);
				isAnyStringMatched = true;
			}
			if(gitoldChanges.toLowerCase().contains(labelName.toLowerCase())) {
				label.setMatchInOldChanges(true);
				isAnyStringMatched = true;
			}
			if(methodName.toLowerCase().contains(labelName.toLowerCase())) {
				label.setMatchInMethodName(true);
				isAnyStringMatched = true;
			}
			label.setTokens(analyzer(labelName, fileName, pathName, gitNewChanges, gitoldChanges, methodName));
			if(label.getTokens().size() != 0) {
				isAnyStringMatched = true;
			}
			if(isAnyStringMatched)
			{
				label.setName(labelName);
				labelList.add(label);
				logger.debug("Added label "+labelName+" in list");
			} else
				logger.debug("No match found, hence not added "+labelName+" in list");
		}
		return labelList;
	}
}
