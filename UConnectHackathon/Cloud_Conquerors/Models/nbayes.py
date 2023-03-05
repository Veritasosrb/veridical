
import numpy as np
import pandas as pd
from sklearn.model_selection import train_test_split
from sklearn import svm
from sklearn.metrics import accuracy_score

# loading the services dataset to a pandas DataFrame
services_dataset = pd.read_csv("bayes.csv", sep=",") 

# printing the first 5 rows of the dataset
services_dataset.head()

# number of rows and Columns in this dataset
services_dataset.shape

# getting the statistical measures of the data
services_dataset.describe()

services_dataset['Class'].value_counts()

services_dataset.groupby('Class').mean()

# separating the data and labels
X = services_dataset.drop(columns = 'Class', axis=1)
Y = services_dataset['Class']

print(X)

print(Y)

X_train, X_test, Y_train, Y_test = train_test_split(X,Y, test_size = 0.2, stratify=Y, random_state=2)

print(X.shape, X_train.shape, X_test.shape)

classifier = svm.SVC(kernel='linear')

#training the support vector Machine Classifier
classifier.fit(X_train, Y_train)

# accuracy score on the training data
X_train_prediction = classifier.predict(X_train)
training_data_accuracy = accuracy_score(X_train_prediction, Y_train)

print('Accuracy score of the training data : ', training_data_accuracy)

# accuracy score on the test data
X_test_prediction = classifier.predict(X_test)
test_data_accuracy = accuracy_score(X_test_prediction, Y_test)

print('Accuracy score of the test data : ', test_data_accuracy)



input_data = (0,1,0,1,1,1,0)
input_data_as_numpy_array = np.asarray(input_data)
input_data_reshaped = input_data_as_numpy_array.reshape(1,-1)

prediction = classifier.predict(input_data_reshaped)
print(prediction)

if (prediction[0] == 1):
  print('Secure')
elif (prediction[0] == 2):
  print('Man in the Middle Attack')
elif (prediction[0] == 3):
  print('Brute Force Attack')
elif (prediction[0] == 4):
  print('Distributed Denial of Service Attack')
elif (prediction[0] == 5):
  print('Brute Force and Man in the Middle Attack')
elif (prediction[0] == 6):
  print('Brute Force and DDOS Attack')
elif (prediction[0] == 7):
  print('DDOS and Man in the Middle Attack')
elif (prediction[0] == 8):
  print('Distributed Denial of Service Attack, Brute Force and Man in the Middle Attack')


import pickle

filename = 'trained_model.sav'
pickle.dump(classifier, open(filename, 'wb'))

# loading the saved model
loaded_model = pickle.load(open('trained_model.sav', 'rb'))

input_data = (0,1,0,1,1,1,0)

# changing the input_data to numpy array
input_data_as_numpy_array = np.asarray(input_data)

# reshape the array as we are predicting for one instance
input_data_reshaped = input_data_as_numpy_array.reshape(1,-1)

prediction = loaded_model.predict(input_data_reshaped)
print(prediction)

if (prediction[0] == 1):
  print('Secure')
elif (prediction[0] == 2):
  print('Man in the Middle Attack')
elif (prediction[0] == 3):
  print('Brute Force Attack')
elif (prediction[0] == 4):
  print('Distributed Denial of Service Attack')
elif (prediction[0] == 5):
  print('Brute Force and Man in the Middle Attack')
elif (prediction[0] == 6):
  print('Brute Force and DDOS Attack')
elif (prediction[0] == 7):
  print('DDOS and Man in the Middle Attack')
elif (prediction[0] == 8):
  print('Distributed Denial of Service Attack, Brute Force and Man in the Middle Attack')

