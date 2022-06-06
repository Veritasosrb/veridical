package com.pict.tia.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

import org.apache.http.util.TextUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import com.pict.tia.constants.TIAConstants;

public class ProcessUtils {
	private static final Logger logger = LogManager.getLogger(FileOperations.class);

	public static void deleteFolder(File folder) {
		File[] files = folder.listFiles();
		if (files != null) { // some JVMs return null for empty dirs
			for (File f : files) {
				if (f.isDirectory()) {
					deleteFolder(f);
				} else {
					f.delete();
				}
			}
		}
		folder.delete();
	}

	public static int executeDoxygenCommand(String doxygenTemplatePath) throws IOException {
		int exiCode = -1;
		String output = "";
		BufferedReader stdInput = null;
		BufferedReader stdError = null;
		try {
			deleteFolder(new File(TIAConstants.DOXYGEN_OUTPUT_FOLDER));
			String remoteCmd = "doxygen" + " " + doxygenTemplatePath;
			logger.info("Command: " + remoteCmd);
			Process process = Runtime.getRuntime().exec(remoteCmd);
			// process.waitFor();
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
		} catch (Exception e) {
			logger.error("Exception occurred while executing command :", e);
		} finally {
			if (stdError != null)
				stdError.close();
			if (stdInput != null)
				stdInput.close();
		}
		logger.info("Exit code :" + exiCode);
		return exiCode;
	}

	public static int executePYDrillerScriptForGitHistory(String repoPathToDrill, String doxygenOutputReportPath,
			List<String> commits) throws IOException {
		int exiCode = -1;
		String output = "";
		BufferedReader stdInput = null;
		BufferedReader stdError = null;
		try {
			String joinedCommits = "";
			for (String s : commits) {
				joinedCommits += (s + ",");
			}
			joinedCommits = joinedCommits.substring(0, joinedCommits.length() - 1);
			String remoteCmd = "python3" + " " + TIAConstants.PYDRILLER_SCRIPT_PATH_FOR_GIT + " " + repoPathToDrill
					+ " " + TIAConstants.FUNCTION_DEPENDENCY_JSON + " " + TIAConstants.GIT_HISTORY_JSON_PATH + " "
					+ joinedCommits;
			logger.info("Command: " + remoteCmd);
			Process process = Runtime.getRuntime().exec(remoteCmd);
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
		} catch (Exception e) {
			logger.error("Exception occurred while executing command :", e);
		} finally {
			if (stdError != null)
				stdError.close();
			if (stdInput != null)
				stdInput.close();
		}
		logger.info("Exit code :" + exiCode);
		return exiCode;
	}

	public static int executePYDrillerScriptForFunctionDependency(String doxygenPath) throws IOException {
		int exiCode = -1;
		String output = "";
		BufferedReader stdInput = null;
		BufferedReader stdError = null;
		try {
			String remoteCmd = "python3" + " " + TIAConstants.PYDRILLER_SCRIPT_PATH_FOR_FUNCTION_DEPENDENCY + " "
					+ doxygenPath + " " + TIAConstants.FUNCTION_DEPENDENCY_JSON;
			logger.info("Command: " + remoteCmd);
			Process process = Runtime.getRuntime().exec(remoteCmd);
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
		} catch (Exception e) {
			logger.error("Exception occurred while executing command :", e);
		} finally {
			if (stdError != null)
				stdError.close();
			if (stdInput != null)
				stdInput.close();
		}
		logger.info("Exit code :" + exiCode);
		return exiCode;
	}
}