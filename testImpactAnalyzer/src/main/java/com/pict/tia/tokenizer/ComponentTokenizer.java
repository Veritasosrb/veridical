package com.pict.tia.tokenizer;

import java.util.ArrayList;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import com.pict.tia.constants.TIAConstants;
import com.pict.tia.models.analyzer.Component;
import com.pict.tia.utils.FileOperations;

public class ComponentTokenizer extends Tokenizer
{
	private static final Logger logger = LogManager.getLogger(ComponentTokenizer.class);
	private ArrayList<String> rawComponentList = new ArrayList<String>();
	
	public ComponentTokenizer()
	{
		this.rawComponentList = FileOperations.readCSV(TIAConstants.COMPONENT_FILE_PATH);
	}
	
	public ArrayList<String> getListOfComponents()
	{
		return rawComponentList;
	}
	
	public ArrayList<Component> analyzer(String fileName, String pathName, String gitNewChanges, String gitoldChanges, String methodName) {
		ArrayList<Component> componentList = new ArrayList<Component>();
		for (String componentName : getListOfComponents())
		{
			boolean isAnyStringMatched = false;
			Component component = new Component();
			if(fileName.toLowerCase().contains(componentName.toLowerCase())) {
				component.setMatchInFileName(true);
				isAnyStringMatched = true;
			}
			if(pathName.toLowerCase().contains(componentName.toLowerCase())) {
				component.setMatchInPathName(true);
				isAnyStringMatched = true;
			}
			if(gitNewChanges.toLowerCase().contains(componentName.toLowerCase())) {
				component.setMatchInNewChanges(true);
				isAnyStringMatched = true;
			}
			if(gitoldChanges.toLowerCase().contains(componentName.toLowerCase())) {
				component.setMatchInOldChanges(true);
				isAnyStringMatched = true;
			}
			if(methodName.toLowerCase().contains(componentName.toLowerCase())) {
				component.setMatchInMethodName(true);
				isAnyStringMatched = true;
			}
			component.setTokens(analyzer(componentName, fileName, pathName, gitNewChanges, gitoldChanges, methodName));
			if(component.getTokens().size() != 0) {
				isAnyStringMatched = true;
			}
			if(isAnyStringMatched)
			{
				component.setName(componentName);
				componentList.add(component);
				logger.debug("Added component "+componentName+" in list");
			} else
				logger.debug("No match found for, hence not added "+componentName+" in list");
		}
		return componentList;
	}
}
