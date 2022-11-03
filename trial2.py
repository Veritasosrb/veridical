import sys
from jira import JIRA
import getpass

username=input('Enter username : ')
password=getpass.getpass(prompt='Enter password : ')

options={'server':'https://jira.community.veritas.com/'}

 

jira_obj = JIRA(options,basic_auth=(username,password))

 

jql_query="project=CORTEX and type=Test"

 

issues_in_proj = jira_obj.search_issues(jql_query)



for issue in issues_in_proj:
    print("Issue Key : "+issue.key+"\nIssue Description : "+issue.fields.description+"\n\n")