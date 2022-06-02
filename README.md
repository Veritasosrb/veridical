# Duplicate Bug Report Detection

## INTRODUCTION
Using bug tracking tools, organizations can manage test reports more efficiently and deliver high quality products with reduced development costs and higher customer satisfaction. It is common for all testers to encounter the same bug, resulting in duplicate bugs being reported in the system. These duplicate bugs can be solved if common code files are handled properly.In light of this, duplicate bug report detection is a critical process that can significantly improve development quality and
efficiency. It also helps organizations provide their customers with better services.

## PROBLEM STATEMENT
Developers rely on bug reports to fix bugs. Typically, bug reports are stored and managed via bug tracking software. There is a high possibility that many testers
may encounter same bug as a result of which bug tracking systems may contains many duplicate bug reports. Thus implement a tool for effective detection of duplicate bug reports so as to reduce time and cost of operation and human resources.

# Getting Started

## Prerequisites
* <a href="https://nodejs.org/en/">Node js</a>
* <a href="https://www.python.org/downloads/">Python</a>
* <a href="https://www.mongodb.com/try/download/community">MongoDB</a>

# Installation
1. Clone the repo
```
git clone https://github.com/Veritasosrb/veridical.git
```
And checkout to `PICT-2022-DBRD` branch.

2. Install Python Libraries
```
pip install -r requirement.txt
```
<b>Note:</b> Microsoft Visual C++ 14.0 or greater is required for the <a href="https://radimrehurek.com/gensim/">genism</a> library to work.

3. Install NPM packages
```
cd client
npm install
```
4. Download the Eclipse Dataset and follow the instructions to restore the database.
```
http://alazar.people.ysu.edu/msr14data/
```
After restoring the database, we will using the `initial` collection from the `eclipse` database for the project.


5. Download the trained skip-gram model
```
https://drive.google.com/drive/folders/1yBtjVgJnmVe-_UZoqapXiJ8kSsfQcypI?usp=sharing
```
After downloading the model, save it under `./dbrd/trained_model` directory.

<br/>

# Repository Structure
The `/client` directory contains the source code and components for the UI elements of the website.

The `/dbrd` directory contains the skip-gram model and other pre-processing files.

The `/server` directory contains the Flask App and database files.

<br/>

# Development
* Change the directory to `/client` and run the following command in the terminal<br/>
```
npm start
```

* Change the directory to `/server` and run the `app.py` file with the following arguements to start the Flask server
    *  Using MongoDB database
    ```
    python ./server/app.py -db_url mongodb://127.0.0.1:27017/ -db_name eclipse -col_name initial
    ```

    * Using sqlite3 database
    ```
    python ./server/app.py -db_url ./server/databases -db_type 2 -db_name reports -col_name initial
    ```


## ENTITY RELATIONSHIP DIAGRAM
<p align="center">
<img src="https://github.com/DuplicateBugReportDetection/FinalCode/blob/main/Images/ER%20Diagram.png" height="450" width="800">
</p>

## ARCHITECTURE DIAGRAM
<p align="center">
<img src="https://github.com/DuplicateBugReportDetection/FinalCode/blob/main/Images/Architecture%20Diagram.jpeg" height="950" width="900">
</p>

## SCREENSHOT
<p align="center">
<img src="https://github.com/DuplicateBugReportDetection/FinalCode/blob/main/Images/UI.png" height="500" width="900">
</p>

## MADE BY TEAM
Omkar Amilkanthwar  &nbsp; -  [lnx2000](https://github.com/lnx2000)\
Aniruddha Deshmukh   - [Aniruddha004](https://github.com/Aniruddha004) \
Atharva Satpute     &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; - [atharva-satpute](https://github.com/atharva-satpute) \
Pranav Deshmukh      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- [pranav918](https://github.com/pranav918)

