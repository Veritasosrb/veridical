package com.pict.tia.utils;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;

public class JsonOperations {
	
	private static final Logger logger = LogManager.getLogger(JsonOperations.class);
	
	public static JsonObject getJSON(String absoluteInputFilePath) {
		Gson gson = new Gson();
		JsonReader reader = null;
		JsonObject outputjsonObject = null;
		try {
			reader = new JsonReader(new FileReader(absoluteInputFilePath));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		outputjsonObject = gson.fromJson(reader, JsonObject.class);
		return outputjsonObject;
	}
	
	public static void writeToJSONFile(Object object, String filePath)
	{
		try
		{
			Writer writer = new FileWriter(filePath);
			Gson gson = new GsonBuilder().create();
			gson.toJson(object, writer);
			writer.flush();
		}
		catch (JsonIOException e)
		{
			logger.error("JSON Exception occurred in writing json file:"+ filePath);
		}
		catch (IOException e)
		{
			logger.error("Exception occurred in writing json file:"+ filePath);			
		}
	}
}