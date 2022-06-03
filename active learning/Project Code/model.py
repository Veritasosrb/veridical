import os
import pickle
import random
from unicodedata import category

import river
from PyQt5.QtWidgets import QMessageBox
from river.feature_extraction import TFIDF
from river.metrics import ClassificationReport
from river.naive_bayes import MultinomialNB
from river.compose import Pipeline
from PyQt5 import QtCore, QtGui, QtWidgets
from operator import itemgetter

#check the train test split count (count) and labeling of unlabeled and autolabeled documents count (autocnt)
def model(self,client,realdatasetwindow, labelui):
    db = client['AutoReview']  # database object
    collection_name = db['TestDataset']
    count=collection_name.find_one({"_id": "Flag"})["Count"]
    autocnt=collection_name.find_one({"_id": "Flag"})["Count_to_Automated"]
    print("count from model")
    print(count)

    print("Auto cnt: "+str(autocnt))

    unattended_data = list(collection_name.find({"Classification_Mode":"Manual", "Input_to_Model": "False"}))
    len_unattended_data=len(unattended_data)

    if count >= 3:
        update_count = collection_name.update_one({"_id": "Flag"}, {"$set": {"Count": 0}})
        runModel1(client, unattended_data, len_unattended_data, count, realdatasetwindow, labelui)
    if autocnt>=5:
        runModel(client, realdatasetwindow,labelui)

#function for training the model
def runModel1(client, unattended_data, len_unattended_data, count, realdatasetwindow, labelui):
    db = client['AutoReview']  # database object
    collection_name = db['TestDataset']

    total_data = []

    for item in unattended_data:
        if item["Classification_Mode"]=="Manual":
            temp=[]
            temp.append(item["Category"])
            temp.append(item["Transformed_text"])
            total_data.append(tuple(temp))
    random.shuffle(total_data)
    distinct_categories=list(collection_name.distinct("Category"))

    # find count of distinct categories
    count_of_categories={}
    for item in distinct_categories:
        # temp={}
        count_of_categories[item]=collection_name.count_documents({"Category":item})
        # count_of_categories.append(temp)

    if "None" in count_of_categories:
     del count_of_categories["None"]
    # find count of distinct categories over


    # check if the category in unattended_data has count == 1 then put the item in train_data
    df_size=len(total_data)
    train_size = int(df_size*0.8)
    test_size = df_size-train_size

    train_data=[]
    test_data=[]

    temp_total_data=total_data

    for item in temp_total_data:
        if item[0] in count_of_categories.keys() == 1:  
            train_data.append(item)
            temp_total_data.remove(item)
            

    temp_test_size=0
    if temp_total_data:
        for it in temp_total_data:
            temp_test_size+=1
            if temp_test_size<=test_size:
                test_data.append(it)
            else:
                train_data.append(it)
    # check if the category in unattended_data has count == 1 then put the item in train_data over
    
    # update model.txt
    if os.path.exists("model.txt"):
        with open("model.txt", 'rb') as f:
            pipe_nb = pickle.load(f)
    else:
        classifier = MultinomialNB()
        pipe_nb = Pipeline(('vectorizer', TFIDF(lowercase=True)), ('nb', MultinomialNB()))

    for label, text in train_data:
        pipe_nb = pipe_nb.learn_one(text, label)

    y_pred = []
    for label, text in test_data:
        res = pipe_nb.predict_one(text)
        y_pred.append(res)

    report = ClassificationReport()
    y_pred = []
    y_test = []
    for label, text in test_data:
        res = pipe_nb.predict_one(text)
        y_pred.append(res)
        y_test.append(label)

    for yt, yp in zip(y_test, y_pred):
        report = report.update(yt, yp)

    metric = river.metrics.Accuracy()
    for label, text in test_data:
        y_pred_before = pipe_nb.predict_one(text)
        metric = metric.update(label, y_pred_before)
        # Has already learnt the pattern
        pipe_nb = pipe_nb.learn_one(text, label)

    final_accuracy = str(metric)[10:]

    with open("model.txt", 'wb') as f:
        pickle.dump(pipe_nb, f)

    db = client['AutoReview']  # database object
    collection_name = db['TestDataset']  # collection object

    file_names=[]
    for item in total_data:
        info = collection_name.find_one({"Transformed_text": item[1]})
        file_names.append(info["Name"])

    modelPredicted=[]
    actualLabel=[]
    predictedLabel=[]

    for item in train_data:
        modelPredicted.append("False")
        actualLabel.append(item[0])
        predictedLabel.append(item[0])

    for x,y in zip(y_pred, y_test):
        modelPredicted.append("True")
        actualLabel.append(y)
        predictedLabel.append(x)

    for d in unattended_data:
        if d["Classification_Mode"]=="Manual":
          collection_name.update_one({"_id": d["_id"]},{"$set": {"Input_to_Model": "True"}})


    data=[]
    data.append(file_names)
    data.append(modelPredicted)
    data.append(actualLabel)
    data.append(predictedLabel)

    result=[]
    result.append(df_size)
    result.append(final_accuracy)


    all_data=collection_name.count_documents({})

    Count_to_Automated = collection_name.find_one({"_id": "Flag"})["Count_to_Automated"]

    update_count = collection_name.update_one({"_id": "Flag"}, {"$set": {"Count_to_Automated": Count_to_Automated}})
    print("Training and testing over")
    if Count_to_Automated>=5:
        
        runModel(client, realdatasetwindow, labelui)

#function to implement model on unlabeled and autolabeled documents
def runModel(client, realdatasetwindow,labelui):
    realdatasetwindow.close()
    labelui.close()

    db=client['AutoReview']     # database object
    myCol = db['TestDataset']    # collection object
    update_count = myCol.update_one({"_id": "Flag"}, {"$set": {"Count_to_Automated": 0, "setFlag":0}})

    all_data_part1 = list(myCol.find({"Status":"Unlabeled"}))
    all_data_part2 = list(myCol.find({"Classification_Mode":"Automated"}))
    all_data=all_data_part1+all_data_part2

    data = list()
    for x in all_data:
        z = (x["_id"], x["Transformed_text"])
        data.append(z)

    with open("model.txt", 'rb') as f:
        model = pickle.load(f)

    for id,text in data:
        res = model.predict_one(text)
        myCol.update_one({"_id": id}, {"$set": {"Category": res, "Classification_Mode":"Automated", "Status":"Labeled"}})
    
    print("All labeled")
    showdialogrunmodel()


    #dialog on successful document classification
def showdialogrunmodel():
    msg = QMessageBox()
    msg.setIcon(QMessageBox.Information)
    msg.setText("All documents classified successfully!")
    msg.setWindowTitle("Document Classification Status")
    msg.exec()