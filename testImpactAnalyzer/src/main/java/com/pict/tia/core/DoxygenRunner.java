package com.pict.tia.core;

import java.io.IOException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.pict.tia.constants.TIAConstants;
import com.pict.tia.utils.ProcessUtils;

public class DoxygenRunner
{
	private static final Logger logger = LogManager.getLogger(DoxygenRunner.class);
	public static void main(String[] arg) throws IOException{
		ProcessUtils.executeDoxygenCommand(TIAConstants.DOXYGEN_TEMPLATE_PATH);
	}
}