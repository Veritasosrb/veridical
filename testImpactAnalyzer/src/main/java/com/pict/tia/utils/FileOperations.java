package com.pict.tia.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import com.opencsv.CSVReader;
import java.io.Reader;

public class FileOperations
{
	private static final Logger logger = LogManager.getLogger(FileOperations.class);
	public static ArrayList<String> readCSV(String filePath)
	{
		ArrayList<String> columnsvalue = new ArrayList<String>();
		String splitBy = "\\t";
		String[] colvalue = null;
		try
		{
			FileReader fr = new FileReader(filePath);
			BufferedReader br = new BufferedReader(fr);
			String line = null;
			while ((line = br.readLine()) != null)
			{
				colvalue = line.split(splitBy);
				columnsvalue.addAll((Arrays.asList(colvalue)));
			}
			fr.close();
			br.close();
		}
		catch (IOException e)
		{
			logger.error("Path not found", e);
		}
		return columnsvalue;

	}
	
	public static List<String[]> readCSVUsingOpenCSV(String filePath)
	{
		List<String[]> records = null;
		try {
			Reader reader = new FileReader(filePath);  
			@SuppressWarnings("resource")
			CSVReader csvReader = new CSVReader(reader);
            records = csvReader.readAll();
		}catch(Exception e) 
		{
			logger.error("Exception occurred while reading CSV "+filePath);
		}
		return records;
	}
}
