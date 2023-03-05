
import mysql.connector
import pandas as pd
from sklearn.model_selection import train_test_split
from sklearn.pipeline import Pipeline
from joblib import dump
import numpy as np
from sklearn.metrics import classification_report, confusion_matrix
from sklearn.metrics import accuracy_score
from sklearn.metrics import f1_score
from sklearn.ensemble import RandomForestRegressor
from sklearn import linear_model


# establish database connection
mydb = mysql.connector.connect(
  host="localhost",
  user="root",
  password="root123",
  database="veritas"
)

# create cursor object to execute SQL queries
mycursor = mydb.cursor()

# get user input for username and password
username = input("Enter username: ")
password = input("Enter password: ")

# execute SQL query to check if user exists
mycursor.execute("SELECT * FROM login WHERE username = %s AND password = %s", (username, password))

# fetch result of query
result = mycursor.fetchone()

# check if user exists in database
if result:
  print("User verified.")
  print("***********************************")
  print("***********************************")

  dept = mycursor.execute("SELECT Department FROM hrdataset WHERE Username = %s", (username,))
  comments_df = pd.read_csv("HRDataset.csv")
  accessGiven = [comments_df['Access']==1]
  accessNotGiven = [comments_df['Access']==0]


  designation_given = comments_df['Department'].values.astype('U')
  access = comments_df['Access'].values.astype('U')
  reviews_train, reviews_test, y_train, y_test = train_test_split(designation_given, access, test_size=0.2)

  train=np.array(reviews_train, dtype=int)
  y_tr=np.array(y_train, dtype=int)

  testing=np.array(reviews_test, dtype=int)
  test_res=np.array(y_test, dtype=int)

  logr = linear_model.LogisticRegression()
  y_pred = logr.fit(train.reshape(-1, 1),np.ravel(y_tr)).predict(testing.reshape(-1, 1))

  accuracy = accuracy_score(test_res, y_pred)
  print("f-score=",f1_score(test_res, y_pred))
  print("Accuracy:", accuracy)
  inp=np.array([1])
  ans=logr.predict(inp.reshape(-1,1))
  print()
  print("***********************************")
  print("***********************************")
  if(ans==1):
    print("Department: Production")
    print("ACCESS GRANTED")
  else:
    print("ACCESS DENIED")

else:
  print("User not found.")

