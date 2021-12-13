package com.pict.tia.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.Gson;
import com.squareup.okhttp.Credentials;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.ResponseBody;
import com.pict.tia.constants.TIAConstants;
import com.pict.tia.models.api.jiracommits.JiraStashPOJO;


public class JiraAPIRequest {
	
	public List<String> getAllCommitsFromJira(String jiraID) throws IOException {
		Gson gson = new Gson(); 
		String jiraURL = TIAConstants.JIRA_URL + String.format(TIAConstants.JIRA_STASH_COMMITS_QUERY, jiraID); 
		OkHttpClient client = new OkHttpClient();
		System.out.println(jiraURL);
		String credential = Credentials.basic(TIAConstants.JIRA_USER_NAME, TIAConstants.JIRA_PASSWORD);
		Request request = new Request.Builder()
		  .url(jiraURL)
		  .get()
		  .addHeader("authorization", credential)
		  .build();
		ResponseBody responseBody = client.newCall(request).execute().body();
		JiraStashPOJO jiraStashEntity = gson.fromJson(responseBody.string(), JiraStashPOJO.class);
		List<String> commitList = new ArrayList<String>();
		jiraStashEntity.getDetail().get(0).getRepositories().get(0).getCommits().stream().forEach(entry -> {commitList.add(entry.getId());});
		return commitList;
	}
}