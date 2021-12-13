package com.pict.tia.core;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.pict.tia.constants.TIAConstants;
import com.pict.tia.constants.WeightConstants;
import com.pict.tia.models.analyzer.Component;
import com.pict.tia.models.analyzer.Records;
import com.pict.tia.models.analyzer.ReferencesMatched;
import com.pict.tia.models.analyzer.Token;
import com.pict.tia.tokenizer.ComponentTokenizer;
//package com.pict.tia.constants;

public class TestRecommendations
{
	private static final Logger logger = LogManager.getLogger(TestRecommendations.class);

	static HashMap<String, Integer> componentWeight = new HashMap<String, Integer>();

	// function to sort Hashmap by values and return hashmap with updated
	// component values
	public static HashMap<String, Integer> sortByValue(HashMap<String, Integer> hm)
	{
		// Create a list from elements of HashMap
		List<Map.Entry<String, Integer>> list = new LinkedList<Map.Entry<String, Integer>>(hm.entrySet());

		// Sort the list
		Collections.sort(list, new Comparator<Map.Entry<String, Integer>>()
		{
			public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2)
			{
				return (o1.getValue()).compareTo(o2.getValue());
			}
		});

		// put data from sorted list to hashmap
		HashMap<String, Integer> temp = new LinkedHashMap<String, Integer>();
		for (Map.Entry<String, Integer> aa : list)
		{
			temp.put(aa.getKey(), aa.getValue());
		}

		// Getting an iterator
		Iterator hmIterator = temp.entrySet().iterator();
		Iterator hmIterator2 = temp.entrySet().iterator();

		// Iterate through the hashmap
		int MaxComponentValue = 0;
		while (hmIterator.hasNext())
		{
			@SuppressWarnings("rawtypes")
			Map.Entry mapElement = (Map.Entry) hmIterator.next();
			MaxComponentValue = (int) mapElement.getValue();

		}

		while (hmIterator2.hasNext())
		{
			@SuppressWarnings("rawtypes")
			Map.Entry mapElement2 = (Map.Entry) hmIterator2.next();
			int UpdatedComponentValue = (((int) mapElement2.getValue() * 100) / MaxComponentValue);

			temp.put((String) mapElement2.getKey(), UpdatedComponentValue);

		}
		return temp;
	}

	public static void main(String[] arg)
	{
		ComponentTokenizer component = new ComponentTokenizer();
		for (String componentName : component.getListOfComponents())
		{
			componentWeight.put(componentName, 0);
		}
		Gson gson = new Gson();
		JsonReader reader;
		List<Records> recordsList = new ArrayList<Records>();
		try
		{
			reader = new JsonReader(new FileReader(TIAConstants.ANALYZER_JSON_PATH));
			Records[] records = gson.fromJson(reader, Records[].class);
			recordsList = Arrays.asList(records);
		}
		catch (FileNotFoundException e)
		{
			logger.error("Error occurred while reading file:" + e);
		}
		calculateWeightage(recordsList);
	}

	public static void calculateWeightage(List<Records> recordsList)
	{
		for (Records records : recordsList)
		{
			int componentOrLabelWeight = 0;
			int tokensWeight = 0;
			List<Component> componentList = records.getComponents();
			for (Component component : componentList)
			{
				componentOrLabelWeight = (calculateComponentWeightage(component) * WeightConstants.COMPONENT_OR_LABELS) / 100;
				tokensWeight = (calculateTokenWeightage(component.getTokens()) * WeightConstants.TOKENS) / 100;
				int totalWeight = componentOrLabelWeight + tokensWeight;
				logger.debug("Total Weight of component " + component.getName() + " in Commit ID: " + totalWeight);
				if (componentWeight.containsKey(component.getName()))
				{
					componentWeight.put(component.getName(), componentWeight.get(component.getName()) + totalWeight);
				}
				else
				{
					logger.error("Component not found in hashmap" + component.getName());
				}
			}
		}

		Map<String, Integer> hm1 = sortByValue(componentWeight);
		for (Map.Entry<String, Integer> en : hm1.entrySet())
		{

			if (WeightConstants.LOWEST_COMPONENT_VALUE == en.getValue())
			{
				logger.info("Component = " + en.getKey() + ", component Value = " + en.getValue() + ",  No confidence");
			}
			else if (en.getValue() <= WeightConstants.LOW_COMPONENT_VALUE)
			{
				logger.info("Component = " + en.getKey() + ", component Value = " + en.getValue() + ",  Low confidence");
			}
			else if (en.getValue() <= WeightConstants.MEDIUM_COMPONENT_VALUE)
			{
				logger.info("Component = " + en.getKey() + ", component Value = " + en.getValue() + ",  Medium confidence");
			}
			else if (en.getValue() < WeightConstants.HIGH_COMPONENT_VALUE)
			{
				logger.info("Component = " + en.getKey() + ", component Value = " + en.getValue() + ",  High confidence");
			}
			else if (en.getValue() == WeightConstants.HIGH_COMPONENT_VALUE)
			{
				logger.info("Component = " + en.getKey() + ", component Value = " + en.getValue() + ",  Highest confidence");
			}
			else
			{
				logger.error("Component not present with appropriate confidence value" + en.getKey());

			}

		}

	}

	public static int calculateComponentWeightage(Component component)
	{
		int componentWeight = 0;
		if (component.getMatchInFileName())
		{
			componentWeight = componentWeight + WeightConstants.FILE_NAME;
		}
		if (component.getMatchInMethodName())
		{
			componentWeight = componentWeight + WeightConstants.METHOD_NAME;
		}
		if (component.getMatchInNewChanges())
		{
			componentWeight = componentWeight + WeightConstants.NEW_CHANGED_LINES;
		}
		if (component.getMatchInOldChanges())
		{
			componentWeight = componentWeight + WeightConstants.OLD_CHANGED_LINES;
		}
		if (component.getMatchInPathName())
		{
			componentWeight = componentWeight + WeightConstants.FILE_PATH;
		}
		if (component.getReferencesMatched() != null && component.getReferencesMatched().size() != 0)
		{
			componentWeight = componentWeight + calculateReferenceFileNameWeightage(component.getReferencesMatched());
			componentWeight = componentWeight + calculateReferenceFilePathWeightage(component.getReferencesMatched());
		}
		return componentWeight;
	}

	public static int calculateTokenWeightage(List<Token> tokens)
	{
		int tokenWeight = 0;
		for (Token token : tokens)
		{
			tokenWeight = tokenWeight + calculateTokenWeightage(token);
			if (tokenWeight >= 100)
			{
				return tokenWeight;
			}
		}
		return tokenWeight;
	}

	public static int calculateTokenWeightage(Token token)
	{
		int tokenWeight = 0;
		if (token.getMatchInFileName())
		{
			tokenWeight = tokenWeight + WeightConstants.FILE_NAME;
		}
		if (token.getMatchInMethodName())
		{
			tokenWeight = tokenWeight + WeightConstants.METHOD_NAME;
		}
		if (token.getMatchInNewChanges())
		{
			tokenWeight = tokenWeight + WeightConstants.NEW_CHANGED_LINES;
		}
		if (token.getMatchInOldChanges())
		{
			tokenWeight = tokenWeight + WeightConstants.OLD_CHANGED_LINES;
		}
		if (token.getMatchInPathName())
		{
			tokenWeight = tokenWeight + WeightConstants.FILE_PATH;
		}
		if (token.getReferencesMatched() != null && token.getReferencesMatched().size() != 0)
		{
			tokenWeight = tokenWeight + calculateReferenceFileNameWeightage(token.getReferencesMatched());
			tokenWeight = tokenWeight + calculateReferenceFilePathWeightage(token.getReferencesMatched());
		}
		return tokenWeight;
	}

	public static int calculateReferenceFileNameWeightage(List<ReferencesMatched> references)
	{
		int referenceWeight = 0;
		for (ReferencesMatched reference : references)
		{
			if (reference.getMatchInFileName())
			{
				referenceWeight = referenceWeight + 1;
			}
			if (referenceWeight >= WeightConstants.REFERENCE_FILENAME)
			{
				return WeightConstants.REFERENCE_FILENAME;
			}
		}
		return referenceWeight;
	};

	public static int calculateReferenceFilePathWeightage(List<ReferencesMatched> references)
	{
		int referenceWeight = 0;
		for (ReferencesMatched reference : references)
		{
			if (reference.getMatchInPathName())
			{
				referenceWeight = referenceWeight + 1;
			}
			if (referenceWeight >= WeightConstants.REFERENCE_FILEPATH)
			{
				return WeightConstants.REFERENCE_FILEPATH;
			}
		}
		return referenceWeight;
	}
}