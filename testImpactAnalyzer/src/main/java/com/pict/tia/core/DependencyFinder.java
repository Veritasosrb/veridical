package com.pict.tia.core;

import java.io.IOException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.pict.tia.constants.TIAConstants;
import com.pict.tia.utils.ProcessUtils;

public class DependencyFinder {

	private static final Logger logger = LogManager.getLogger(DependencyFinder.class);

	public static void main(String[] arg) throws IOException {
	}

	public int findDependencies() throws IOException {

		try {
			return ProcessUtils.executePYDrillerScriptForFunctionDependency(TIAConstants.DOXYGEN_OUTPUT_REPORT);
		} catch (IOException e) {
			logger.error("Error occurred while executing pydriller");
		}
		return -1;
	}

}
