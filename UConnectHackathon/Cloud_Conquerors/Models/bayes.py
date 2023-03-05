import sklearn
from sklearn.metrics import confusion_matrix
from sklearn.model_selection import train_test_split
import pandas as pd
import matplotlib.pyplot as plt
import numpy as np
from sklearn.metrics import confusion_matrix, ConfusionMatrixDisplay

df = pd.read_csv("bayes.csv", sep=",")
allColumns = df.columns
targetClass  = allColumns[len(allColumns)-1]
X = df
y = df[targetClass].values
X_train, X_test, y_train, y_test = train_test_split(X, y, random_state = 0)
from sklearn.naive_bayes import GaussianNB
gnb = GaussianNB().fit(X_train, y_train)
gnb_predictions = gnb.predict(X_test)
test_prediction = gnb.predict(X_test)
print(X_test)
print(test_prediction)
accuracy = gnb.score(X_test, y_test)
cm = confusion_matrix(y_test, gnb_predictions)
disp = ConfusionMatrixDisplay(confusion_matrix=cm,display_labels=gnb.classes_)
#disp.plot()
print(accuracy)

