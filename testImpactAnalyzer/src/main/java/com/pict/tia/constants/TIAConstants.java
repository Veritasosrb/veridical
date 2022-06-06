package com.pict.tia.constants;

import java.io.File;

public class TIAConstants {
	public static String REPOSITORY_PATH = "https://github.com/mantriaditi10/bookMyMovieTia";
	public static String JIRA_ID = "IMP-123456";
	// public static String JIRA_URL = "https://jira.community.veritas.com";
	public static String JIRA_URL = "http://localhost:1080";
	public static String JIRA_STASH_COMMITS_QUERY = "/rest/dev-status/latest/issue/detail?issueId=%s&applicationType=stash&dataType=repository";
	public static String JIRA_USER_NAME = "shashank.mahajan";
	public static String JIRA_PASSWORD = "XYZ";
	public static String COMPONENT_FILE_PATH = System.getProperty("user.dir") + File.separator + "src" + File.separator
			+ "main" + File.separator + "resources" + File.separator + "component.csv";
	public static String LABELS_FILE_PATH = System.getProperty("user.dir") + File.separator + "src" + File.separator
			+ "main" + File.separator + "resources" + File.separator + "labels.csv";
	public static String GIT_HISTORY_CSV_PATH = System.getProperty("user.dir") + File.separator + "src" + File.separator
			+ "main" + File.separator + "resources" + File.separator + "git_history.csv";
	// Sample Jira Query : project = "Data Insight" And type = Test And labels
	// in (ActivitySummaryForPaths,Dev_Task) And component in (reports)
	public static String CAMELCASE_REGEX = "(?<=[a-z])(?=[A-Z])|(?<=[A-Z])(?=[A-Z][a-z])";
	public static String TOKENIZER_DELIMITER = "-_ &";
	public static String MOCK_JIRA_COMMITS_PATH = System.getProperty("user.dir") + File.separator + "src"
			+ File.separator + "main" + File.separator + "resources" + File.separator + "testdata" + File.separator
			+ "response" + File.separator + "jiraCommits.json";
	public static String ANALYZER_JSON_PATH = System.getProperty("user.dir") + File.separator + "src" + File.separator
			+ "main" + File.separator + "resources" + File.separator + "analyzer.json";
//	public static String PYDRILLER_SCRIPT_PATH = System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator
//			+ "commit_details_csv.py";
	public static String PYDRILLER_SCRIPT_PATH_FOR_GIT = System.getProperty("user.dir") + File.separator
			+ "pythonScripts" + File.separator + "gitHistory.py";
	public static String PYDRILLER_SCRIPT_PATH_FOR_FUNCTION_DEPENDENCY = System.getProperty("user.dir") + File.separator
			+ "pythonScripts" + File.separator + "functionDependencyFinder.py";

	public static String DOXYGEN_TEMPLATE_PATH = System.getProperty("user.dir") + File.separator + "src"
			+ File.separator + "main" + File.separator + "resources" + File.separator + "doxygen_template.Doxyfile";
	public static String DOXYGEN_OUTPUT_REPORT = System.getProperty("user.dir") + File.separator + "op" + File.separator
			+ "html";
	public static String DOXYGEN_OUTPUT_FOLDER = System.getProperty("user.dir") + File.separator + "op";

	public static String FUNCTION_DEPENDENCY_JSON = System.getProperty("user.dir") + File.separator + "pythonScripts"
			+ File.separator + "functionDependency.json";
	public static String GIT_HISTORY_JSON_PATH = System.getProperty("user.dir") + File.separator + "pythonScripts"
			+ File.separator + "gitHistory.json";

}
