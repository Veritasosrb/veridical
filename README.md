## Problem Statement:

Using information retrieval to aid support engineers to find relevant documentation available in Veritas intranet (all types, confluence, webpages, PDF, word, excel, text, etc.)

## Motivation:

One of the main motivations is to reduce the response time needed for solving the problem. Another important reason for solving this problem is to help support engineers find the relevant documentation instead of searching through volumes of documents.

## System Architecture

<img src="https://i.imgur.com/gzd58ym.png" alt="arch-diag" height="200"/>

## UI

Extractive QA

<img src="https://i.imgur.com/jxiuAbk.png" alt="SS" height="200"/>

Document similarity

<img src="https://i.imgur.com/jEP1a2k.png" alt="SS" height="200"/>

Knowledge Graph

<img src="https://i.imgur.com/uIuQqKM.png" alt="SS" height="200"/>

Tabular QA

<img src="https://i.imgur.com/eNETpSR.png" alt="SS" height="200"/>


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