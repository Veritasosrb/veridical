package com.pict.tia.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import com.pict.tia.constants.TIAConstants;

public class ProcessUtils
{
	private static final Logger logger = LogManager.getLogger(FileOperations.class);

	public static int executeDoxygenCommand(String doxygenTemplatePath) throws IOException
	{
		int exiCode = -1;
		String output = "";
		BufferedReader stdInput = null;
		BufferedReader stdError = null;
		try {
			String remoteCmd = "doxygen" + " " + doxygenTemplatePath ;
			logger.info("Command: " + remoteCmd);
			Process process = Runtime.getRuntime().exec("cmd /C "+remoteCmd);
			//process.waitFor();
			stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
			stdError = new BufferedReader(new InputStreamReader(process.getErrorStream()));
			String s = null;
			while ((s = stdInput.readLine()) != null) {
				output = output + s + "\n";
			}
			while ((s = stdError.readLine()) != null) {
				output = output + s + "\n";
			}
			logger.info("Here is the standard output + error of the command:\n" + output);
			Thread.sleep(10000);
			exiCode = process.exitValue();
			process.destroyForcibly();
		}catch (Exception e) {
			logger.error("Exception occurred while executing command :", e);
		}
		finally {
			if(stdError != null)
				stdError.close();
			if(stdInput != null)
				stdInput.close();
		}
		logger.info("Exit code :" + exiCode);
		return exiCode;
	}

	public static int executePYDrillerScript(String repoPathToDrill, String doxygenOutputReportPath, Object[] commits) throws IOException
	{
		int exiCode = -1;
		String output = "";
		BufferedReader stdInput = null;
		BufferedReader stdError = null;
		try {
			String remoteCmd = "python" + " " + TIAConstants.PYDRILLER_SCRIPT_PATH + " " + repoPathToDrill + " " + doxygenOutputReportPath;
			logger.info("Command: " + remoteCmd);
			Process process = Runtime.getRuntime().exec("cmd /C "+remoteCmd);
			process.waitFor();
			stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
			stdError = new BufferedReader(new InputStreamReader(process.getErrorStream()));
			String s = null;
			while ((s = stdInput.readLine()) != null) {
				output = output + s + "\n";
			}
			while ((s = stdError.readLine()) != null) {
				output = output + s + "\n";
			}
			logger.info("Here is the standard output + error of the command:\n" + output);
			Thread.sleep(1000);
			exiCode = process.exitValue();
			process.destroyForcibly();
		}catch (Exception e) {
			logger.error("Exception occurred while executing command :", e);
		}
		finally {
			if(stdError != null)
				stdError.close();
			if(stdInput != null)
				stdInput.close();
		}
		logger.info("Exit code :" + exiCode);
		return exiCode;
	}
}