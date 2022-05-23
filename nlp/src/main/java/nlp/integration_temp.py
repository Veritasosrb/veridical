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
import os
def dict_convert():
    path = os.path.abspath(os.curdir)
    #reading training and testing csv files
    df_train_file = pd.read_csv(path+"\\src\\main\\java\\nlp\\dataset_scenario_usable_unusable.csv")
    df_test_file = pd.read_csv(path+"\\dictionary_temp.csv")

    X_train = df_train_file[["keyword", "weight"]]
    y_train = df_train_file[["spam/ham"]]
    X_test = df_test_file[["keyword", "weight"]]
    #vectorising
    tfidf_vect = TfidfVectorizer()
    tfidf_vect_fit = tfidf_vect.fit(X_train['keyword'])
    tfidf_train = tfidf_vect_fit.transform(X_train['keyword'])
    tfidf_test = tfidf_vect_fit.transform(X_test['keyword'])

    X_train_vect = pd.concat([X_train[["weight"]].reset_index(drop=True), pd.DataFrame(tfidf_train.toarray())], axis=1)
    X_test_vect = pd.concat([X_test[["weight"]].reset_index(drop=True), pd.DataFrame(tfidf_test.toarray())], axis=1)

    #applying support vector machine for filtering out spam words
    classifier = SVC(kernel='linear', random_state=0)
    classifier.fit(X_train_vect, y_train)
    y_pred = classifier.predict(X_test_vect)
    df_test_file["spam/ham"] = y_pred

    #storing list if ham words
    df_final = df_test_file[(df_test_file["spam/ham"] == "ham")]
    keywords = df_final['keyword'].tolist()

    dict = []
    #stores list of antonyms
    ant = []
    for i in keywords:
        word = i.split()
        #if the word is a verb then append 'usable'/'unusable' to it
        if word[0] in keyword_extraction.pos["VERB"]:
            dict.append(word[1] + " usable")
            dict.append(word[1] + " unusable")
        #if the word is an adjective then store it as it is in the final list
        if word[0]in keyword_extraction.pos["ADJ"]:
            dict.append(word[1] + " " + word[0])
            for syn in wordnet.synsets(word[0]):
                for synonym in syn.lemmas():
                    #find antonym of the adjective and add it to the final list
                    if synonym.antonyms():
                        ant.append(l.antonyms()[0].name())
            for antonym in ant:
                if antonym.startswith(("in", "un", "dis")):
                    dict.append(word[1] + " " + antonym)
        if word[1] in keyword_extraction.pos["ADJ"]:
            dict.append(word[0] + " " + word[1])
            for syn in wordnet.synsets(word[1]):
                for synonym in syn.lemmas():
                    if synonym.antonyms():
                        ant.append(l.antonyms()[0].name())
            for antonym in ant:
                if antonym.startswith(("in", "un", "dis")):
                    dict.append(word[0] + " " + antonym)
    #convert to set to remove duplicate entries
    dict = set(dict)
    #store the dictionary to a csv file
    with open("FINAL_DICT.csv", "w") as f:
        writer = csv.writer(f)
        for i in dict:
            writer.writerow([i])
