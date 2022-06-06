package com.pict.tia.core;

import java.io.IOException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.repodriller.RepoDriller;
import org.repodriller.RepositoryMining;
import org.repodriller.Study;
import org.repodriller.filter.range.Commits;
import org.repodriller.persistence.csv.CSVFile;
import org.repodriller.scm.CollectConfiguration;
import org.repodriller.scm.GitRepository;
import com.pict.tia.api.JiraAPIRequest;
import com.pict.tia.constants.TIAConstants;
import com.pict.tia.mocks.jiracommits.JiraServiceMock;
import com.pict.tia.utils.GitCrawlerAndWriter;
import com.pict.tia.utils.MockServerUtils;

public class GitRunner implements Study
{
	private static final Logger logger = LogManager.getLogger(GitRunner.class);

	public static void main(String[] arg) throws IOException
	{
		MockServerUtils.startMockServer();
		JiraServiceMock mockService = new JiraServiceMock();
		String inputJira = "1428623";
		mockService.mockGetCommitsAPI(inputJira, 200);
		JiraAPIRequest jira = new JiraAPIRequest();
		System.out.println(jira.getAllCommitsFromJira(inputJira));
		MockServerUtils.stopMockServer();
		new RepoDriller().start(new GitRunner());
	}
	
	@Override
	public void execute() {
		new RepositoryMining()
		.in(GitRepository.singleProject(TIAConstants.REPOSITORY_PATH))
		.through(Commits.all())
		//.through(Commits.list())
		.collect(new CollectConfiguration().sourceCode().diffs())
		.process(new GitCrawlerAndWriter(), new CSVFile(TIAConstants.GIT_HISTORY_CSV_PATH))
		.mine();
	}
}

