# Test Case Identification Utility Based on Code Check-ins

In terms of quality control, software testing is a crucial activity in software development. For system testing, software testing necessitates the use of testcases. **Based on the code changes on version control systems, there might be a possibility of a negative impact on the existing functionalities.** Regression testing is an essential activity to assure that software code changes do not adversely affect existing functionalities. Running each test case and determining its validity becomes extremely time-intensive in large-scale systems.
***As a solution to this problem, we present a pattern analysis and AI/ML based method for identifying appropriate test cases based on the JIRA ID of the code check-in to provide early and most relevant feedback to all the stakeholders.***


## Problem Definition
Design an automated pattern analysis and AI/ML-based relevant test case identifier based on code check-ins for a JIRA ID.


## Overview Of the Project Model

#### Client
The users can directly interact with the model using the Command Line Interface(CLI) and the GUI(made only for demonstration), which is designed using front-end technologies such as HTML, CSS, etc. The recommended test cases are displayed to the user on the GUI, once the user provides the JIRA ID as an input from the CLI.

#### Server 
Flask server is used to host the recommendation module of the project. It comprises of the BERT model and a similarity function. In addition, mock JIRA servers are implemented to enable extraction of essential data from the JIRA ID provided.

#### Doxygen Runner
The Doxygen Runner uses the Doxygen library to produce documentation for the whole project and makes use of the Graphviz library to display the functional dependency call-graphs in SVG format.

#### Function Dependency Finder
The SVG generated is given as an input to the Functional Dependency Finder, which parses the SVG and generates the functional dependencies’ inverted callgraphs. These inverted call-graphs are stored as a json file.

#### Git Analyzer 
The project’s Git Analyzer Module pulls the necessary data from the mock JIRA servers storing the history of the commits using the Pydriller library. It then integrates the functional dependency json into the extracted information to generate a final git history json having all the required essential data.

#### Machine Learning Model
Sentence-BERT is used to generate commit wise embeddings of the git history json obtained from the Git Analyzer module and the test case json. The test case json contains the name of the test case, and the labels and components associated with them. Each test case embedding is then matched with all the embeddings of the git history json using a similarity function to identify the most relevant test cases.

## Pre-requisites
```
Python >= 3.8.10
JDK 11
Maven 
Eclipse
Doxygen >= 1.8.17
GraphViz (dot - graphviz version 2.43.0 (0))
```

## Java Runner Classes and configurations
#### 1. DoxygenRunner
* Pre-requisite : Test GIT repository has been checked out and placed at [src/main/resources/testdata](src/main/resources/testdata). 
* Configuration : Update OUTPUT_DIRECTORY and INPUT of [doxygen_template.Doxyfile](src/main/resources/doxygen_template.Doxyfile) file. 


#### 2. DependencyFinder
* Pre-requisite : The generated doxygen report and it's location
* Configuration : Update DOXYGEN_OUTPUT_REPORT at [src/main/java/com/pict/tia/constants/TIAConstants.java](src/main/java/com/pict/tia/constants/TIAConstants.java)


#### 3. RepositoryAnalyzer
* Pre-requisite : The function dependency json
* Configuration: Update REPOSITORY_PATH at [src/main/java/com/pict/tia/constants/TIAConstants.java](src/main/java/com/pict/tia/constants/TIAConstants.java)

#### 4. Main
This is the main class to run all the above-listed classes.

## Python scripts and configurations

All the required python scripts are located at: [location](pythonScripts)
* Change the direcctory
```
> cd pythonScripts
```
* Setting Up Virtual Environment(Prerequisites: Your System should have Python3 installed)
  
```
> pip3 install virtualenv    #Installing the virtual environment module
> virtualenv venv           #Create virtual environment venv
```

* Activating Virtual Environment

*On macOS and Linux (Tested on Ubuntu)*
```
> source venv/bin/activate   #Activate Virtual Environment
```
* Install requirements: 
```
> pip3 install -r requirements.txt`
```

## Running Instructions

### Main
* Run method : `Run it as java application`

### For NLP Module

* Change the directory
```
> cd nlp
```
* Setting Up Virtual Environment(Prerequisites: Your System should have Python3 installed)
```
> pip3 install virtualenv    #Installing the virtual environment module
> virtualenv venv           #Create virtual environment venv
```

* Activating Virtual Environment

*On macOS and Linux (Tested on Ubuntu)*
```
> source venv/bin/activate   #Activate Virtual Environment
```

* Install requirements: 
```
> pip3 install -r requirements.txt`
```
* Update Constant Paths: 
  *Update GIT_HISTORY_PATH and TEST_DATA_JSON_PATH in [nlp/nlpConstants.py](nlp/nlpConstants.py)*
* Once all the requirements are installed, it's time to run the Flask App
```
> python3 app.py    #Starts the Server

```

Once the server starts, go to http://127.0.0.1:5000/ to generate recommended test-cases


## Future Scope

<ul>
<li>Current test cases have labels and components written manually, they could
be automatically generated to reduce more work and increase efficiency.</li>
<li> More parameters can be recognized and used for better test case identification.
</li>
<li>
We intend to widen the scope of our research by focusing on the use of AI and machine learning in automated regression testing.
</li>
<li>
We also plan to expand our project by automating additional sorts of test suites, such as unit testing, integration testing, keyword-driven testing, and so on
</li>
<li>
In addition, we will be working on additional advanced ML techniques which will enable the program to identify the relevant test cases with an improved accuracy and efficiency
</li>
</ul>

##References


https://github.com/mauricioaniche/repodriller/blob/master/README.md
https://github.com/ishepard/pydriller
https://docs.python.org/3/library/xml.etree.elementtree.html
https://www.doxygen.nl/
https://flask.palletsprojects.com/en/2.1.x/
https://www.sbert.net/

<!-- Previous README.md -->
<!-- # Test Impact Analyzer

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
``` -->
