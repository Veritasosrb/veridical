import numpy as nm  
import matplotlib.pyplot as plt 
import pandas as pd  
from sklearn.ensemble import RandomForestClassifier
from sklearn.metrics import confusion_matrix 
from sklearn.metrics import precision_score

#importing datasets  
data_set= pd.read_csv('login1.csv')  
  
#Extracting Independent and dependent Variable  
x= data_set.iloc[:, [4]].values  #independent
y= data_set.iloc[:, 5].values   #dependent
  
# Splitting the dataset into training and test set.  
from sklearn.model_selection import train_test_split  
x_train, x_test, y_train, y_test= train_test_split(x, y, test_size= 0.25, random_state=0)  
  
#feature Scaling  
from sklearn.preprocessing import StandardScaler    
st_x= StandardScaler()    
x_train= st_x.fit_transform(x_train)    
x_test= st_x.transform(x_test) 
  
classifier= RandomForestClassifier(n_estimators= 10, criterion="entropy")  
classifier.fit(x_train, y_train)    

#Predicting the test set result  
y_pred= classifier.predict(x_test)  

# #Creating the Confusion matrix  
# cm = confusion_matrix(y_test, y_pred) 
# cm_display=

cm = confusion_matrix(y_test, y_pred)
print(cm)
print("Accuracy:", classifier.score(x_test, y_test))

# print("Precision:", precision_score(x_test,y_test))