#importing all libs

import pandas as pd
import matplotlib.pyplot as plt
from sklearn.model_selection import train_test_split
from nltk.corpus import wordnet
from sklearn import svm
from sklearn.svm import SVC
from sklearn.ensemble import RandomForestClassifier, GradientBoostingClassifier
from sklearn.metrics import precision_recall_fscore_support as score
from sklearn.metrics import accuracy_score as acs
import seaborn as sns
from sklearn.feature_extraction.text import TfidfVectorizer
import csv
import keyword_extraction
#from keyword_extraction import extract_words

def dict_convert():
    #reading training ds
    df_train_file = pd.read_csv("C:\\Users\\pooja\\eclipse-workspace\\nlp\\src\\main\\java\\nlp\\dataset_scenario_usable_unusable.csv")
    df_test_file = pd.read_csv("C:\\Users\\pooja\\eclipse-workspace\\nlp\\dictionary_temp.csv")


    X_train = df_train_file[["keyword", "weight"]]
    y_train = df_train_file[["spam/ham"]]
    X_test = df_test_file[["keyword", "weight"]]
    #X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.30, random_state=1)

    tfidf_vect = TfidfVectorizer()
    tfidf_vect_fit = tfidf_vect.fit(X_train['keyword'])

    tfidf_train = tfidf_vect_fit.transform(X_train['keyword'])
    tfidf_test = tfidf_vect_fit.transform(X_test['keyword'])

    X_train_vect = pd.concat([X_train[["weight"]].reset_index(drop=True), pd.DataFrame(tfidf_train.toarray())], axis=1)

    X_test_vect = pd.concat([X_test[["weight"]].reset_index(drop=True), pd.DataFrame(tfidf_test.toarray())], axis=1)






    #applying support vector machine,
    classifier = SVC(kernel='linear', random_state=0)
    classifier.fit(X_train_vect, y_train)
    y_pred = classifier.predict(X_test_vect)
    df_test_file["spam/ham"] = y_pred
    print("//////////////////////////////////////////////////////")
    #print(y_pred)

    #list if ham words
    df_final = df_test_file[(df_test_file["spam/ham"] == "ham")]
    #print(df_final)
    keywords = df_final['keyword'].tolist()
    print(type(keywords))
    print(keywords[0])
    print(keyword_extraction.pos.keys())
    dict = []
    ant = []
    for i in keywords:
        j = i.split()
        if j[0] in keyword_extraction.pos["VERB"]:
            dict.append(j[1] + " usable")
            dict.append(j[1] + " unusable")
        #if j[0] in keyword_extraction.pos["ADJ"] or j[1] in keyword_extraction.pos["ADJ"]:
        if j[0]in keyword_extraction.pos["ADJ"]:
            dict.append(j[1] + " " + j[0])
            for syn in wordnet.synsets(j[0]):
                for l in syn.lemmas():
                    if l.antonyms():
                        ant.append(l.antonyms()[0].name())
            for i in ant:
                if i.startswith(("in", "un", "dis")):
                    dict.append(j[1] + " " + i)
        if j[1] in keyword_extraction.pos["ADJ"]:
            dict.append(j[0] + " " + j[1])
            for syn in wordnet.synsets(j[1]):
                for l in syn.lemmas():
                    if l.antonyms():
                        ant.append(l.antonyms()[0].name())
            for i in ant:
                if i.startswith(("in", "un", "dis")):
                    dict.append(j[0] + " " + i)
    dict = set(dict)

    with open("FINAL_DICT.csv", "w") as f:
        writer = csv.writer(f)
        for i in dict:
            writer.writerow([i])
