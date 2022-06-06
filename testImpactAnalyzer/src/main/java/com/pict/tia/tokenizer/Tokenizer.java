package com.pict.tia.tokenizer;

import java.util.ArrayList;
import java.util.StringTokenizer;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import com.pict.tia.constants.TIAConstants;
import com.pict.tia.models.analyzer.Token;

public class Tokenizer
{
	private static final Logger logger = LogManager.getLogger(LabelTokenizer.class);
	private int Minimum_Token_Size = 3;

	protected ArrayList<String> tokenizer(String entityName)
	{
		ArrayList<String> componentTokenList = new ArrayList<String>();
		StringTokenizer tokenizer = new StringTokenizer(entityName, TIAConstants.TOKENIZER_DELIMITER);
		while (tokenizer.hasMoreTokens())
		{
			String token = tokenizer.nextToken();
			String[] splitByCamalCase = token.split(TIAConstants.CAMELCASE_REGEX);
			for (String splitString : splitByCamalCase)
			{
				if (!splitString.equalsIgnoreCase(entityName))
					componentTokenList.add(splitString.toLowerCase());
			}
		}
		if (componentTokenList.isEmpty())
			logger.debug("No sub level token found for component " + entityName);
		else
			logger.debug("List of tokens found for component" + entityName + " : " + componentTokenList);
		return componentTokenList;
	}

	@SuppressWarnings({ "unused" })
	public ArrayList<Token> analyzer(String componentName, String fileName, String pathName, String gitNewChanges, String gitoldChanges, String methodName)
	{
		ArrayList<Token> tokenList = new ArrayList<Token>();
		for (String tokenName : tokenizer(componentName))
		{
			boolean isAnyTokenMatched = false;
			if (tokenName.length() < Minimum_Token_Size)
			{
				continue;
			}
			Token token = new Token();
			if (fileName.toLowerCase().contains(tokenName.toLowerCase()))
			{
				token.setMatchInFileName(true);
				isAnyTokenMatched = true;
			}
			if (pathName.toLowerCase().contains(tokenName.toLowerCase()))
			{
				token.setMatchInPathName(true);
				isAnyTokenMatched = true;
			}
			if (gitNewChanges.toLowerCase().contains(tokenName.toLowerCase()))
			{
				token.setMatchInNewChanges(true);
				isAnyTokenMatched = true;
			}
			if (gitoldChanges.toLowerCase().contains(tokenName.toLowerCase()))
			{
				token.setMatchInOldChanges(true);
				isAnyTokenMatched = true;
			}
			if (methodName.toLowerCase().contains(tokenName.toLowerCase()))
			{
				token.setMatchInMethodName(true);
				isAnyTokenMatched = true;
			}
			if (isAnyTokenMatched)
			{	
				token.setName(tokenName);
				tokenList.add(token);
			}	
		}
		return tokenList;
	}

}
