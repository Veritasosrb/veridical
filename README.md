## Problem Statement:

Using information retrieval to aid support engineers to find relevant documentation available in Veritas intranet (all types, confluence, webpages, PDF, word, excel, text, etc.)

## Prerequisites

- `git clone --recurse-submodules https://github.com/atharva-lipare/qa-search-chatbot`

- `pip install -r requirements.txt`

## Commands INFO


- Start docker container

    `sh ElasticRun.sh`

- Index files for documents:

    `python api-testing.py -d <documents-directory-here>`

- Index files for documents:

    `python api-testing-table.py -t <tables-directory-here>`

- Run the flask servers:

    `python app.py`

    `python table-app/app-table.py`

- Run the streamlit web ui:

    `streamlit run UI/webapp.py`

## How to Run INFO

- To run webapp on streamlit, follow this sequence: `start docker container -> index files -> run flask server -> run streamlit webserver`