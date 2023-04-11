import os
import sys
import nltk
from nltk.corpus import stopwords
from nltk.corpus import wordnet
from nltk.tokenize import word_tokenize
from nltk import word_tokenize, sent_tokenize
from nltk import sent_tokenize, word_tokenize
from keybert import KeyBERT
import numpy
import csv
import pandas as pd
from textblob import TextBlob
import nltk
from nltk import word_tokenize
import pickle
import stanza




from sklearn.ensemble import RandomForestClassifier, GradientBoostingClassifier
from sklearn.feature_extraction.text import TfidfVectorizer

def runall():
  working_dif = os.getcwd()
  feature_name = sys.argv[1]

  # path = os.path.abspath(os.curdir)
  path = "F:\\BE Project\\Working code\\veridical\\nlp"


  #path+"\\src\\main\\java\\nlp\\dataset_scenario_usable_unusable.csv"
  with open(path+"\\src\\main\\resources\\input\\"+feature_name+"_input.txt") as file:
      text= file.readlines()


  statements = []
  for lines in text:
      statements.append(lines.replace("\n", ""))



  # pos tagging

  stanza.download('en')
  nlp = stanza.Pipeline(lang='en', processors='tokenize,mwt,pos')

  stanza_adj = []
  stanza_verb = []
  verb_tag = ["VERB"]
  adj_tag = ["ADJ"]

  for line in text:
    temp = nlp(line)
    for item in temp.sentences:
      for word in item.words:
        if(word.upos in verb_tag):
          stanza_verb.append(word.text)
        elif(word.upos in adj_tag):
          stanza_adj.append(word.text)



  # keyword extraction using keybert
  kw_model = KeyBERT(model='all-mpnet-base-v2')
  keywords_bert = []
  for statement in statements:
      keywords_bert.append(kw_model.extract_keywords(statement, keyphrase_ngram_range=(2,2),stop_words='english')) 

  words = []
  for wordset in keywords_bert:
    for word in wordset:
      words.append(word)


  keywords_file = path+"\\csv_files\\"+feature_name +"_keywords.csv"
  add_header = True
  if(os.path.exists(keywords_file)):
    add_header = False


  with open(keywords_file, 'a', newline='') as out:
    header = ['keyword', 'weight']
    csv_out = csv.writer(out)
    if(add_header):
      csv_out.writerow(['keyword', 'weight'])
      add_header = False
    for row in words:
      csv_out.writerow(row)


  # classification of keywords
  rf = RandomForestClassifier(n_estimators=40)

  df_train_file = pd.read_csv(path+"\\csv_files\\"+"dataset_scenario_usable_unusable.csv")



  # df_test_file = pd.read_csv(path+"\\src\\main\\resources\\datasets"+"\\dictionary_temp.csv")
  df_test_file = pd.read_csv(path+"\\csv_files\\"+feature_name+"_keywords.csv")


  classifier = "rf" # indicates the name of classifier
  classifier_file = path+"\\python_scripts\\classification_models\\"+classifier+'_model.pkl'  #stored classifier model 

  tfidf_vect = TfidfVectorizer()
  X_train = df_train_file[["keyword","weight"]]
  y_train = df_train_file[["spam/ham"]] 
  tfidf_vect_fit = tfidf_vect.fit(X_train['keyword'])
  tfidf_train = tfidf_vect_fit.transform(X_train['keyword'])
  X_train_vect = pd.concat([X_train[["weight"]].reset_index(drop=True), pd.DataFrame(tfidf_train.toarray())], axis=1)

  X_test = df_test_file[["keyword","weight"]]
  tfidf_test = tfidf_vect_fit.transform(X_test['keyword'])
  X_test_vect = pd.concat([X_test[["weight"]].reset_index(drop=True), pd.DataFrame(tfidf_test.toarray())], axis=1)
  X_test_vect['weight'] = X_test_vect['weight'].astype('float')


  if(os.path.exists(classifier_file) == False):
    print("training classification model for spam ham classifier...")
    rf_model = rf.fit(X_train_vect, y_train.values.ravel())
    pickle.dump(rf_model, open(classifier_file, 'wb'))





  print("loading classification model for spam ham classifier...")
  classifier_model = pickle.load(open(classifier_file, 'rb'))

  # load the model from disk
  print("Classifiying the keywords...")
  y_pred = classifier_model.predict(X_test_vect)



  df_test_file["spam/ham"] = y_pred
  df_final = df_test_file[(df_test_file["spam/ham"]=="ham")]

  df = pd.read_csv(path+"\\csv_files\\"+ feature_name+"_keywords.csv")
  words = []
  temp = df_final[df_final["spam/ham"] == "ham"] 
  keywords = temp['keyword'].tolist()



  dict1 = []
  for i in keywords:
      word = i.split()    
      if word[0] in stanza_verb:
        dict1.append(word[1]+" usable")
        dict1.append(word[1]+ " unusable")

      if word[0] in stanza_adj:
        dict1.append(word[1]+" "+word[0])

      if(word[1] in stanza_adj):
        dict1.append(word[0]+" "+word[1])

  dict1 = set(dict1)

  print(dict1)

  print("Appending New Entries to the Dictionary file")
  with open(path+"\\Dictionaries\\dictionary.csv", "a", newline='') as f:
      writer = csv.writer(f)
      for i in dict1:
          writer.writerow([i])
      
  # read the dictionary file and remove the duplicate entries and write it back to the file 

  print("Removing duplicate Entries from the dictionary file")
  df = pd.read_csv(path+"\\Dictionaries\\dictionary.csv", header=None)
  df.drop_duplicates(inplace=True)
  df.to_csv(path+"\\Dictionaries\\dictionary.csv", index=False)


if __name__ == '__main__':
  runall()