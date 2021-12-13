package com.pict.tia.mocks.jiracommits;

import static io.netty.handler.codec.http.HttpHeaderNames.CONTENT_TYPE;
import static org.mockserver.model.Header.header;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;
import static org.mockserver.model.JsonBody.json;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.mockserver.client.MockServerClient;
import com.google.gson.JsonObject;
import com.pict.tia.constants.TIAConstants;
import com.pict.tia.utils.JsonOperations;

public class JiraServiceMock {
	private static final Logger logger = LogManager.getLogger(JiraServiceMock.class);
	private String mockServerName = "localhost";
	private int mockServerPort = 1080;
	
	@SuppressWarnings("resource")
	public void mockGetCommitsAPI(String key, int statusCode) {
		JsonObject commitList = null;
		String jiraURLPathMatcher = "/rest/dev-status/latest/issue/detail";		
		commitList = JsonOperations.getJSON(TIAConstants.MOCK_JIRA_COMMITS_PATH);
		logger.info("Mocking Get Commits API with response key : " + key + " and status code : " + statusCode);
		new MockServerClient(mockServerName, mockServerPort)
				.when(request().withMethod("GET").withPath(jiraURLPathMatcher))
				.respond(response().withHeaders(header(CONTENT_TYPE.toString(), "application/json"))
						.withBody(json(commitList.toString())).withStatusCode(statusCode));
	}

	@SuppressWarnings("resource")
	public void mockGetCredentialType(String responseKey, int statusCode) {
		JsonObject commitList = null;
		String jiraURL = String.format(TIAConstants.JIRA_STASH_COMMITS_QUERY, responseKey);		
		commitList = JsonOperations.getJSON(TIAConstants.COMPONENT_FILE_PATH);
		logger.info("Mocking Get Commits API with response key : " + responseKey + " and status code : " + statusCode);
		new MockServerClient(mockServerName, mockServerPort)
				.when(request().withMethod("GET").withPath(jiraURL))
				.respond(response().withHeaders(header(CONTENT_TYPE.toString(), "application/json"))
						.withBody(json(commitList.toString())).withStatusCode(statusCode));
	}
}