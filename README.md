# Predictive Coding and Technology Assisted Review (TAR)

## Problem Statement
Use of Predictive Coding and Technology Assisted Review (TAR) for document review since manual reviews of millions of documents are extremely costly, time consuming, error prone, full of biases/prejudices.

## Overview
* For the study purpose, we compared various classification algorithms such as logistic regression, tree classifier, multinomial naive bayes on the basis of accuracy and precision score. We found that the multinomial naive bayes algorithm worked really well and provided an accuracy of 80.87% for a very large dataset of around 22543 documents.  We built this huge dataset by combining various multi categorised document datasets from KDD-UCI website.
* Using River python package for incremental learning purpose, the accuracy of pretrained model using multinomial naive bayes was increased to the figure of 91.09 %.
* The Combined_Dataset.zip is the dataset used for pretraining the model.
* We have created a pretrained model and saved it as model.txt in the project folder.This pretrained model is used in the software for the classification purpose. Using active learning this pretrained model gets updated on every new input. 
* In the software, we have not included authentication and authorization of the user. Instead we take the input of information through a config file. The config file information to be provided by the user.
* The config file includes information like the name of categories according to which the user would like to classify the documents. It also includes information about the company such as org name, client name, project title etc.
* To run the software, run the homepage1.py page.The input to the software is a zip file containing the unlabeled documents. The test_mini.zip is the dataset we provide as input on clicking on the upload dataset button in the desktop application. Once the zip file is uploaded, the text from the documents is fetched and preprocessed using the NLTK  library. The preprocessing steps include removing stopwords, removing punctuation and applying stemming.
* Further, this preprocessed raw input is converted to feature vectors using the TFIDF vectorizer which is provided as input to the pretrained model.
* The user can view the list of names, classification mode, and category of documents uploaded by them.
* They can also read the documents using PDF Viewer integrated in the software.
* After reading a document, they should label a particular document. The categories extracted from the config file are shown in the form of a dropdown. The user also has provision to add a new category. 
* A single label given by the user is fed as an input to the pretrained model. This will help the model update at every input in an incremental fashion. The model will perform training in TAR 2.0 manner.
* After every certain interval the model will predict the label for unlabeled and auto labelled documents. We have two counters named count and autocnt in the model.py folder. The counter named count is for retraining or upgradation of the model and the autocnt counter is for the purpose of labelling of unlabelled and relabeling of autolabelled documents. For the demo purpose, we have set those counters as: count=3 and autocnt=5. The ideal counters are as follows: count=20 and autocnt=70.
* At times when the user finds the label assigned by the model is incorrect, he/she can update the label which will be again provided as an input to the model to improve over the previous mistake. Hence, the model keep on dynamically upgrading.

## Prerequisites
* Pyqt5 designer
* River package
* Nltk library
* Pickle
* Xlsxwriter
* configparser

## Modules
* Formation of dataset
* Preprocessing of text
* Build pretrained model
* Take user documents
* View and add label to file
* Provide user input to pretrained model( iterative learning)
* Feedback Mechanism
* Real time display of output and provision to download excel sheet

## Flow Diagram
![FlowDiag](https://user-images.githubusercontent.com/52365520/171978423-18765735-e0eb-485f-a7a2-c6c03b44d526.jpeg)



