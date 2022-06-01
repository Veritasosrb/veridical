# from haystack.utils import launch_es
import traceback
from haystack.utils import clean_wiki_text, convert_files_to_dicts, fetch_archive_from_http, print_answers
from haystack.document_stores import ElasticsearchDocumentStore
import time
from argparse import ArgumentParser
import pandas as pd
from haystack import Document
from haystack.nodes.retriever import TableTextRetriever
import json


def index_docs(document_store, doc_dir):
    #document_store = ElasticsearchDocumentStore(host="localhost", username="", password="", index="document")
    #s3_url = "https://s3.eu-central-1.amazonaws.com/deepset.ai-farm-qa/datasets/documents/wiki_gameofthrones_txt.zip"
    #fetch_archive_from_http(url=s3_url, output_dir=doc_dir)

    # Convert files to dicts
    # You can optionally supply a cleaning function that is applied to each doc (e.g. to remove footers)
    # It must take a str as input, and return a str.
    t1 = time.perf_counter()
    dicts = convert_files_to_dicts(dir_path=doc_dir, clean_func=clean_wiki_text, split_paragraphs=True)
    t2 = time.perf_counter()

    print(f'Preprocessing documents took {t2-t1:0.4f}s')

    # We now have a list of dictionaries that we can write to our document store.
    # If your texts come from a different source (e.g. a DB), you can of course skip convert_files_to_dicts() and create the dictionaries yourself.
    # The default format here is:
    # {
    #    'text': "<DOCUMENT_TEXT_HERE>",
    #    'meta': {'name': "<DOCUMENT_NAME_HERE>", ...}
    #}
    # (Optionally: you can also add more key-value-pairs here, that will be indexed as fields in Elasticsearch and
    # can be accessed later for filtering or shown in the responses of the Pipeline)

    # Let's have a look at the first 3 entries:
    #print(dicts[:3])

    # Now, let's write the dicts containing documents to our DB.
    t1 = time.perf_counter()
    document_store.write_documents(dicts, index="document")
    t2 = time.perf_counter()

    print(f'Indexing documents took {t2-t1:0.4f}s')

# def index_tables(document_store, table_dir):
#     t1 = time.perf_counter()
#     def read_tables(filename):
#         processed_tables = []
#         with open(filename) as tables:
#             tables = json.load(tables)
#             for key, table in tables.items():
#                 current_columns = table["header"]
#                 current_rows = table["data"]
#                 current_df = pd.DataFrame(columns=current_columns, data=current_rows)
#                 document = Document(content=current_df, content_type="table", id=key)
#                 processed_tables.append(document)
#         return processed_tables
#     tables = read_tables(f"{table_dir}/tables.json")
    
#     document_store.write_documents(tables, index="document")
#     retriever = TableTextRetriever(
#         document_store=document_store,
#         query_embedding_model="deepset/bert-small-mm_retrieval-question_encoder",
#         passage_embedding_model="deepset/bert-small-mm_retrieval-passage_encoder",
#         table_embedding_model="deepset/bert-small-mm_retrieval-table_encoder",
#         embed_meta_fields=["title", "section_title"],
#     )
#     document_store.update_embeddings(retriever=retriever)
#     t2 = time.perf_counter()
#     print(f'Indexing tables took {t2-t1:0.4f}s')


if __name__ == "__main__":
    # USAGE:
    # python api-testing.py -d <docs_directory> -t <table_directory>
    parser = ArgumentParser()
    parser.add_argument('-d', '--docs', type=str, required=True, help='docs directory')
    # parser.add_argument('-t', '--tables', type=str, required=True, help='table directory')
    args = parser.parse_args()

    try:
        document_store = ElasticsearchDocumentStore(host="localhost", username="", password="", index="document", embedding_dim = 512)
        index_docs(document_store, args.docs)
        # index_tables(document_store, args.tables)
    except Exception as e:
        traceback.print_exc()
        print("Cannot connect to elasticsearch")
