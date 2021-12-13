package com.pict.tia.core;

import java.io.IOException;
import java.util.List;
import com.pict.tia.api.JiraAPIRequest;
import com.pict.tia.constants.TIAConstants;
import com.pict.tia.mocks.jiracommits.JiraServiceMock;
import com.pict.tia.utils.MockServerUtils;
import com.pict.tia.utils.ProcessUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class RepositoryAnalyzer
{
	private static final Logger logger = LogManager.getLogger(RepositoryAnalyzer.class);
	public static void main(String[] arg) throws IOException
	{
		MockServerUtils.startMockServer();
		JiraServiceMock mockService = new JiraServiceMock();
		String inputJira = "1428623";
		mockService.mockGetCommitsAPI(inputJira, 200);
		JiraAPIRequest jira = new JiraAPIRequest();
		List<String> commitList = jira.getAllCommitsFromJira(inputJira);
		logger.info("List Commits: "+commitList);
		MockServerUtils.stopMockServer();
		try
		{
			ProcessUtils.executePYDrillerScript(TIAConstants.REPOSITORY_PATH, TIAConstants.DOXYGEN_OUTPUT_REPORT, commitList.toArray());
		}
		catch (IOException e)
		{
			logger.error("Error occurred while executing pydriller");
		}
	}
}
