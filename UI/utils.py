import requests
from types import SimpleNamespace
import json
from io import StringIO
from scipy import spatial
from torch import embedding
from nltk.tokenize import sent_tokenize
import spacy
nlp = spacy.load('en_core_web_sm')
from spacy.matcher import Matcher 
from spacy.tokens import Span 
from streamlit_agraph import TripleStore, agraph


def getAnswer(query: str, top_k_reader=5, top_k_retriever=5):
    red = requests.get('http://localhost:5000/retrieve',
                       json={"query": query, "top_k": top_k_retriever})
    if not red.ok:
        return
    results = requests.post('http://localhost:5000/read',
                            json={"query": query, "top_k": top_k_reader, "documents": red.json()})
    return results.json()


def getAnswerTable(query: str, top_k_reader=5, top_k_retriever=5):
    red = requests.get('http://localhost:5005/retrieve_table', json={"query":query, "top_k": top_k_retriever})
    if not red.ok:
        return 
    results = requests.post('http://localhost:5005/read_table', json={"query":query, "top_k": top_k_reader, "documents":red.json()})
    return results.json()


def ProcessDocumentSimilarity(file, max_num):
    stringio = StringIO(file.getvalue().decode("utf-8"))
    text = stringio.read()
    red = requests.get(
        'http://localhost:5000/getDocEmbedding', json={"text": text})
    currentFileEmbeddding = list(red.json()[0])
    red = requests.post('http://localhost:5000/getAllEmbeddings')
    embeddingsAll = red.json()
    # print(embeddingsAll.keys())
    diffDict = {}
    Ans = {}
    for i in embeddingsAll.keys():
        diffDict[i] = 1 - \
            spatial.distance.cosine(embeddingsAll[i], currentFileEmbeddding)
    sorted_keys = sorted(diffDict, key=diffDict.get, reverse=True)
    for w in sorted_keys[:max_num]:
        Ans[w] = diffDict[w]
    return Ans


def get_entities(sent):
    ent1 = ""
    ent2 = ""
    prv_tok_dep = ""    # dependency tag of previous token in the sentence
    prv_tok_text = ""   # previous token in the sentence
    prefix = ""
    modifier = ""
    for tok in nlp(sent):
        if tok.dep_ != "punct":
            if tok.dep_ == "compound":
                prefix = tok.text
                if prv_tok_dep == "compound":
                    prefix = prv_tok_text + " " + tok.text
            if tok.dep_.endswith("mod") == True:
                modifier = tok.text
                if prv_tok_dep == "compound":
                    modifier = prv_tok_text + " " + tok.text

            if tok.dep_.find("subj") == True:
                ent1 = modifier + " " + prefix + " " + tok.text
                prefix = ""
                modifier = ""
                prv_tok_dep = ""
                prv_tok_text = ""

            if tok.dep_.find("obj") == True:
                ent2 = modifier + " " + prefix + " " + tok.text

            prv_tok_dep = tok.dep_
            prv_tok_text = tok.text

    return [ent1.strip(), ent2.strip()]


def get_relation(sent):
    doc = nlp(sent)
    matcher = Matcher(nlp.vocab)
    pattern = [{'DEP': 'ROOT'},
               {'DEP': 'prep', 'OP': "?"},
               {'DEP': 'agent', 'OP': "?"},
               {'POS': 'ADJ', 'OP': "?"}]
    # print(type(pattern))
    matcher.add("matching_1", patterns = [pattern])
    matches = matcher(doc)
    k = len(matches) - 1
    span = doc[matches[k][1]:matches[k][2]]
    return(span.text)


def KnowledgeGraphs(file):
    stringio = StringIO(file.getvalue().decode("utf-8"))
    text = stringio.read()
    sentList = sent_tokenize(text)
    entity_pairs = []
    for i in sentList:
        entity_pairs.append(get_entities(i))
    relations = [get_relation(i) for i in sentList]
    source = [i[0] for i in entity_pairs]
    target = [i[1] for i in entity_pairs]
    store = TripleStore()
    for i in range(len(source)):
        store.add_triple(source[i], relations[i], target[i])
    return store
    
