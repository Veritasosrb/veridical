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
import os

wd=os.getcwd()
filename = sys.argv[1]
path  = os.path.abspath(os.curdir)
with open(path+"\\input\\product_"+filename+"_input"+".txt") as f:
    lines = f.readlines()

data = []
#for removing newline character
for sent in lines:
    data.append(sent.replace("\n", " "))
#POS tagging for checking if verb/adjective/noun
nlp = stanza.Pipeline(lang='en', processors='tokenize,mwt,pos')
pos = {}
#traverse through every word in data and give a pos tag to it
for i in data:
    temp = nlp(i)
    for sent in temp.sentences:
        for word in sent.words:
            if word.upos in pos:
                pos[word.upos].append(word.text)
            else:
                pos[word.upos]= list()
                pos[word.upos].append(word.text)

#function for extracting keywords to add them to dictionary
def extract_words():
    kw_model = KeyBERT(model='all-mpnet-base-v2')
    keywords_bert = []
    for sentence in data:
        keywords_bert.append(kw_model.extract_keywords(sentence, keyphrase_ngram_range=(2, 2), stop_words='english'))

    #keybert extracts tuples and stores it in a list of lists of tuples
    words=[]
    for list_data in keywords_bert:
        for tuple_data in list_data:
                words.append(tuple_data)
    #write words to a temporary dictionary->This might contain spam words
    with open('dictionary_temp.csv', 'w') as out:
        csv_out = csv.writer(out)
        csv_out.writerow(['keyword', 'weight'])
        for row in words:
            csv_out.writerow(row)
    print("Python file executed")

if __name__ == '__main__':
    #for extracting keywords from scenarios
    extract_words()

    #for filtering out the spam words extracted by keyword extraction model
    integration_temp.dict_convert()
