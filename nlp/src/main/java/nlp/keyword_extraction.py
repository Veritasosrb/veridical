import sys
import os
import pandas as pd
import csv
from keybert import KeyBERT
import nltk
from nltk.corpus import stopwords
from nltk.tokenize import word_tokenize
from nltk import word_tokenize, sent_tokenize
from nltk import sent_tokenize, word_tokenize
import numpy
import stanza
import integration_temp

wd=os.getcwd()


filename = sys.argv[1]
with open("C:\\Users\\pooja\\eclipse-workspace\\nlp\\input\\product_"+filename+"_input"+".txt") as f:
    lines = f.readlines()
    #df = pd.read_csv("C:\\Users\\pooja\\eclipse-workspace\\nlp\\input\\product_"+filename+"_input"+".txt")
data = []
for sent in lines:
    data.append(sent.replace("\n", " "))
nlp = stanza.Pipeline(lang='en', processors='tokenize,mwt,pos')
pos = {}
for i in data:
    temp = nlp(i)
    for sent in temp.sentences:
        for word in sent.words:
            if word.upos in pos:
                pos[word.upos].append(word.text)
            else:
                pos[word.upos]= list()
                pos[word.upos].append(word.text)


def extract_words():


    kw_model = KeyBERT(model='all-mpnet-base-v2')
    keywords_bert = []

    for i in data:
        keywords_bert.append(kw_model.extract_keywords(i, keyphrase_ngram_range=(2, 2), stop_words='english'))


    words=[]
    for l in keywords_bert:
        for t in l:
                words.append(t)

    with open('dictionary_temp.csv', 'w') as out:
        csv_out = csv.writer(out)
        csv_out.writerow(['keyword', 'weight'])
        for row in words:
            csv_out.writerow(row)
    print("Python file executed")

if __name__ == '__main__':
    extract_words()
    integration_temp.dict_convert()