# Test Impact Analyzer

TestImpactAnalyzer is a tool that helps project stake-holders or QA in getting effective set of test cases as per commit/code changes. It uses, **PYDriller** to extract information about **commits**, **modified files**, **diffs** then it uses **Doxygen** and **GraphViz** to find out references and then it matches existing list of components/labels with code changes and recommend list of test cases to execute

### Installation or Pre-requisites
```
Python 3.9 and install pip install pydriller
JDK 11
Maven 
Eclipse
Doxygen
GraphViz
```

### Steps to setup Doxygen & GraphViz
```
Download the latest version of Doxygen from [Link to Doxygen download](https://sourceforge.net/projects/doxygen/files/)
Download the latest version of Graphviz from [Link to Graphviz download] (https://graphviz.org/download/)

1.Install Doxygen and Graphviz using their respective installers.
2.Add the bin folder location of Doxygen and Graphviz to System Path. By default Graphviz provides an option in its installation wizard to add its bin folder location to System Path.
3.To add the bin folder of the Doxygen to System Path navigate to the bin folder of Doxygen(By default it is C:\Program Files\doxygen\bin)
4.Copy the full path of the bin folder and now go to Control Panel -> System and Security -> System -> Advanced system settings -> Environment Variables
5.Now under System Variables, double click on Path and select "New" and paste the full path of the bin folder copied in the previous step and click on "OK".

Note :- If the Graphviz has not been added to the System variables in Environment Variables
		go to the bin folder of Graphviz (By default it is C:\Program Files\Graphviz\bin) and follow steps 4 and 5.
 
```

### Runner Classes and description
#####1. DoxygenRunner
* Pre-requisite : `Test GIT repository has been checked out and placed it in src\main\resources\testdata. You can use test repos present in Repodriller https://github.com/mauricioaniche/repodriller.git.`
* Run method : `Run it as java application`
* Configuration : `Update OUTPUT_DIRECTORY and INPUT of doxygen_template.Doxyfile file.` 
* Description : `This class is responsible to run doxygen command using template file and creates output under OUTPUT_DIRECTORY`

#####2. GitRunner
* Pre-requisite : `Test GIT repository has been checked out and placed it in src\main\resources\testdata`
* Run method : `Run it as java application`
* Description : `This class is responsible to create git history file src\main\resources\testdata\git_history.csv. Currently it fetches all commits but ideally it should fetch list of commits provided by the jira api`

#####3. RepositoryAnalyzer
* Pre-requisite : `Test GIT repository has been checked out and placed it in src\main\resources\testdata`
* Run method : `Run it as java application`
* Description : `This class is similar to GitRunner but uses python library. Currently commit_details.py has some issues, once it is resolved GitRunner will be replaced by RepositoryAnalyzer`.

#####4. TestImpactAnalyzer
* Pre-requisite : `labels.csv, component.csv and githistory.csv is placed in src\main\resources\`
* Run method : `Run it as java application`
* Description : `This class is responsible to create analyzer.json file which matches existing list of components and labels in GIT history csv file and creates json file. Refer below sample file.`

```
{
    "components":
    [
        {  
            "name": "reports_activity",
            "match_found": "true",
            "confidence": "HIGH",
            "isMatchInFileName" true,
            "isMatchInCodeChanges" true,
            "isMatchInPath" true,   
            "tokens" : [
                {
                    "name": "reports",
                    "isMatchInFileName" true,
                    "isMatchInCodeChanges" true,
                    "isMatchInPath" true,
                },
                {
                    "name": "activity",
                    "isMatchInFileName" true,
                    "isMatchInCodeChanges" true,
                    "isMatchInPath" true,
                }
            ]
        },
        {  
            "name": "dashboard",
            "match_found": "true",
            "confidence": "MEDIUM",
            "isMatchInFileName" true,
            "isMatchInCodeChanges" true,
            "isMatchInPath" true,   
            "tokens" : []
        }
    ]
}
```

#####5. TestRecommendations
* Pre-requisite : `analyzer.json should be present in src\main\resources\testdata`
* Run method : `Run it as java application`
* Description : `This class is responsible to find out impacted component and labels. Need to perform similar thing for labels too. This is partially done yet.`



##### References

```
https://github.com/mauricioaniche/repodriller/blob/master/README.md
https://github.com/ishepard/pydriller
```
