import json
from flask import Flask
from flask import request
from flask import jsonify
from haystack import Document
from haystack.document_stores import ElasticsearchDocumentStore
from haystack.nodes import TableReader
from haystack.nodes.retriever import TableTextRetriever


app = Flask(__name__)

document_store = ElasticsearchDocumentStore(
    host="localhost", username="", password="", index="table", embedding_dim=512)

table_retriever = TableTextRetriever(
        document_store=document_store,
        query_embedding_model="deepset/bert-small-mm_retrieval-question_encoder",
        passage_embedding_model="deepset/bert-small-mm_retrieval-passage_encoder",
        table_embedding_model="deepset/bert-small-mm_retrieval-table_encoder",
        embed_meta_fields=["title", "section_title"],
    )

table_reader = TableReader(model_name_or_path="google/tapas-base-finetuned-wtq", max_seq_len=512)


@app.route('/retrieve_table')
def getTableRetrieverResults():
    srchstr = request.json['query']
    top_k = request.json['top_k']
    t = table_retriever.retrieve (
        query=srchstr,
        top_k=top_k+1
    )
    t = [i.to_dict() for i in t]
    return jsonify(t)


@app.route('/read_table', methods=['POST'])
def getTableReaderResults():
    content = request.json
    query = content['query']
    top_k = content['top_k']
    documents = content['documents']
    documents = [Document.from_dict(i) for i in documents]
    results = table_reader.predict(query=query,
                             documents=documents,
                             top_k=top_k+1)
    doc_dict = dict()
    print(documents)
    for i in documents:
        doc_dict[i.id] = i.meta.get('name')
        #print(i)
        print()
        print(i.meta)
    ans = list()
    for i in results['answers']:
        t = dict()
        t['answer'] = i.answer
        t['score'] = i.score
        t['context'] = i.context.to_string()
        try:
            t['df_json'] = i.context.to_json()
        except:
            t['df_json'] = ""
        t['document_id'] = i.document_id
        if doc_dict.get(i.document_id, -1) != -1:
            t['source'] = doc_dict[i.document_id]
        else:
            t['source'] = "Unknown"
        ans.append(t)
    return json.dumps(ans)


if __name__ == "__main__":
    app.run(port=5005)
