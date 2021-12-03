import sys
#from jira import JIRA
#import getpass
print(sys.argv[1])

"""#username=input('Enter username : ')
#password=getpass.getpass(prompt='Enter password : ')
username="Aditi.Inamdar"
password="Veritas@1000"
options={'server':'https://jira.community.veritas.com/'}

 

jira_obj = JIRA(options,basic_auth=(username,password))

 

jql_query="project=CORTEX and type=Test"

 

issues_in_proj = jira_obj.search_issues(jql_query)



for issue in issues_in_proj:
    if issue.key==sys.argv[1]:
        
        print("Issue Key : "+sys.argv[1]+"\nIssue Description : "+issue.fields.description+"\n\n")"""