import json
import copy
from flask import Flask
from flask import request
from scipy import spatial
from flask import jsonify
from haystack import Document
from haystack.nodes import ElasticsearchRetriever
from haystack.document_stores import ElasticsearchDocumentStore
from haystack.nodes import TransformersReader
import tensorflow_hub as hub
from torch import embedding
from DocumentEmbeddings import loadEmbeddings, getDocData, clean, CleanData, PATH, WriteEmbeddings
module_url = "https://tfhub.dev/google/universal-sentence-encoder/4"
modelembed = hub.load(module_url)
print("module %s loaded" % module_url)


def embedf(input):
    return modelembed(input)

app = Flask(__name__)
model = "deepset/roberta-base-squad2"
document_store = ElasticsearchDocumentStore(
    host="localhost", username="", password="", index="document", embedding_dim=512)
retriever = ElasticsearchRetriever(document_store)

reader = TransformersReader(model, use_gpu=1)

def Rembedd():
    docData = getDocData(PATH)
    docData = CleanData(docData)
    embeddings = {}
    for i in docData:
        embeddings[i] = list(embedf([docData[i]]).numpy())
    WriteEmbeddings(embeddings)
    return embeddings

embeddingsGlobal = None
try:
    embeddingsGlobal = loadEmbeddings()
except:
    embeddingsGlobal = Rembedd()
    

@app.route('/retrieve')
def returnRetrieverResults():
    srchstr = request.json['query']
    top_k = request.json['top_k']
    t = retriever.retrieve(
        query=srchstr,
        top_k=top_k+1)
    t = [i.to_dict() for i in t]
    return jsonify(t)


@app.route('/read', methods=['POST'])
def getReaderResults():
    content = request.json
    query = content['query']
    top_k = content['top_k']
    documents = content['documents']
    documents = [Document.from_dict(i) for i in documents]
    results = reader.predict(query=query,
                             documents=documents,
                             top_k=top_k+1)
    doc_dict = dict()
    for i in documents:
        doc_dict[i.id] = i.meta['name']
    ans = list()
    for i in results['answers']:
        t = dict()
        t['answer'] = i.answer
        t['score'] = i.score
        t['context'] = i.context
        t['document_id'] = i.document_id
        if doc_dict.get(i.document_id, -1) != -1:
            t['source'] = doc_dict[i.document_id]
        else:
            t['source'] = "Unknown"
        ans.append(t)
    return json.dumps(ans)


@app.route('/docDiff', methods=['POST'])
def getDocumentSimilarity():
    content = request.json
    file1 = content['file1']
    file2 = content['file2']
    return 1 - spatial.distance.cosine(embeddingsGlobal[file1].tolist(), embeddingsGlobal[file2].tolist())

@app.route('/getDocEmbedding', methods=['GET'])
def getDocEmbedding():
    content = request.json
    filetxt = content['text']
    embeddings = embedf([clean(filetxt)])
    embeddings = list(embeddings.numpy().tolist())
    return jsonify(embeddings)

@app.route('/getAllEmbeddings', methods=['POST'])
def getAllEmbeddings():
    embeddings = copy.deepcopy(embeddingsGlobal)
    for i in embeddings:
        embeddings[i] = embeddings[i][0].tolist()
    return jsonify(embeddings) 


@app.route('/ReEmbedd', methods=['GET'])
def ReEmbedd():
    Rembedd()
    return "Success"
    
    

if __name__ == "__main__":
    app.run(port=5000)
