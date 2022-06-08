import os
import sys

import logging
import pandas as pd
from json import JSONDecodeError
from pathlib import Path
import streamlit as st
import streamlit.components.v1 as components
from annotated_text import annotation
from markdown import markdown
from streamlit_agraph import agraph, TripleStore, Node, Edge, Config
from utils import getAnswer, ProcessDocumentSimilarity, KnowledgeGraphs, getAnswerTable

# Adjust to a question that you would like users to see in the search bar when they load the UI:
DEFAULT_QUESTION_AT_STARTUP = os.getenv(
    "DEFAULT_QUESTION_AT_STARTUP", "what are the applications of machine learning?")
DEFAULT_ANSWER_AT_STARTUP = os.getenv("DEFAULT_ANSWER_AT_STARTUP", "")

st.session_state.update(st.session_state)


def set_state_if_absent(key, value):
    if key not in st.session_state:
        st.session_state[key] = value


def main():

    st.set_page_config(page_title='Extractive Question Answering',
                       page_icon="https://haystack.deepset.ai/img/HaystackIcon.png")

    st.markdown(
        f'''
            <style>
                .reportview-container .sidebar-content {{
                    padding-top: 0rem;
                }}
                .css-12oz5g7{{
                    max-width: 64rem
                }}
            </style>
            ''',
        unsafe_allow_html=True,
    )

    # Persistent state
    st.session_state.question = DEFAULT_QUESTION_AT_STARTUP
    st.session_state.answer = DEFAULT_ANSWER_AT_STARTUP
    st.session_state.results = None

    # Small callback to reset the interface in case the text of the question changes

    def reset_results(*args):
        st.session_state.answer = None
        st.session_state.results = None
        st.session_state.raw_json = None


    

    # Extractive QA Pipeline

    with st.container():

        st.write("# Extractive Question Answering")

        question = st.text_input("",
                                 value=st.session_state.question,
                                 max_chars=100,
                                 on_change=reset_results
                                 )
        top_k_retriever = st.slider("Number of documents", 1, 10, 3, 1)
        top_k_reader = st.slider("Number of results", 1, 10, 3, 1)
        btn1 = st.button("Run")
        st.markdown(
            "<style>.stButton button {width:100%;}</style>", unsafe_allow_html=True)

        run_pressed = btn1
        run_query = (run_pressed or question != st.session_state.question)
        if run_query and question:
            reset_results()
            st.session_state.question = question

            with st.spinner(
                    "🧠 &nbsp;&nbsp; Performing neural search on documents... \n "):
                try:
                    st.session_state.results = getAnswer(question, top_k_reader=top_k_reader,
                                                         top_k_retriever=top_k_retriever)
                except JSONDecodeError as je:
                    st.error(
                        "👓 &nbsp;&nbsp; An error occurred reading the results. Is the document store working?")
                    return
                except Exception as e:
                    logging.exception(e)
                    if "The server is busy processing requests" in str(e) or "503" in str(e):
                        st.error(
                            "🧑‍🌾 &nbsp;&nbsp; All our workers are busy! Try again later.")
                    else:
                        st.error(
                            "🐞 &nbsp;&nbsp; An error occurred during the request.")
                    return

            if st.session_state.results:
                st.write("## Results:")

                for ans in st.session_state.results:

                    answer, context = ans["answer"], ans["context"]
                    # print(answer)
                    if answer is None or context is None:
                        continue
                    start_idx = context.find(answer)
                    end_idx = start_idx + len(answer)
                    # Hack due to this bug: https://github.com/streamlit/streamlit/issues/3190
                    st.write(markdown(context[:start_idx] + str(annotation(
                        answer, "ANSWER", "#8ef")) + context[end_idx:]), unsafe_allow_html=True)
                    source = ""
                    # url, title = get_backlink(result)
                    # if url and title:
                    source = ans['source']
                    # else:
                    #     source = f"{result['source']}"
                    st.markdown(
                        f"**Relevance:** {ans['score']}")
                    st.markdown(
                        f"**Source:** {source}")
                    st.markdown(
                        """<hr style="height:5px;border:none;color:#333;background-color:#333;" /> """, unsafe_allow_html=True)

    # Document Similarity using universal sentence encoder
    with st.container():
        st.write("# Document Similarity and clustering based on document embeddings")
        file = st.file_uploader("Upload document", type='txt', accept_multiple_files=False,
                                key=None, help=None, on_change=None, args=None, kwargs=None,)
        num = st.slider("Number of results", 1, 10, 3, 1, key="2")
        btn3 = st.button(label="Find")
        st.markdown(
            "<style>.stButton button {width:100%;}</style>", unsafe_allow_html=True)
        if file is not None and btn3:
            ans = ProcessDocumentSimilarity(file, num)
            for i in ans:
                st.markdown(f'### {i}')
                st.markdown(f'Match: {ans[i]*100}%')
                st.markdown(
                    """<hr style="height:5px;border:none;color:#333;background-color:#333;" /> """, unsafe_allow_html=True)

    # Knowledge Graphs
    with st.container():
        config = Config(height=600, width=700, nodeHighlightBehavior=True, highlightColor="#F7A7A6", directed=True,
                        collapsible=True)
        st.write("# Knowledge Graph Extraction from text")
        file = st.file_uploader("Upload document for knowledge graph extraction", type='txt',
                                accept_multiple_files=False, key=None, help=None, on_change=None, args=None, kwargs=None,)
        btn4 = st.button(label="Extract")
        st.markdown(
            "<style>.stButton button {width:100%;}</style>", unsafe_allow_html=True)
        if file is not None and btn4:
            TS = KnowledgeGraphs(file)
            agraph(list(TS.getNodes()), (TS.getEdges()), config)

    #Table QA
    with st.container():

        st.write("# Table Question Answering")

        question = st.text_input("Enter query on table: ",
                                 value=st.session_state.question,
                                 max_chars=100,
                                 on_change=reset_results
                                 )
        top_k_retriever = st.slider("Number of tables", 1, 10, 3, 1)
        top_k_reader = st.slider("Number of results to retrieve", 1, 10, 3, 1)
        btn1 = st.button("Run ")
        st.markdown(
            "<style>.stButton button {width:100%;}</style>", unsafe_allow_html=True)

        run_pressed = btn1
        run_query = (run_pressed or question != st.session_state.question)
        if run_query and question:
            reset_results()
            st.session_state.question = question

            with st.spinner(
                    "🧠 &nbsp;&nbsp; Performing neural search on tables... \n "):
                try:
                    st.session_state.results = getAnswerTable(question, top_k_reader=top_k_reader,
                                                         top_k_retriever=top_k_retriever)
                except JSONDecodeError as je:
                    st.error(
                        "👓 &nbsp;&nbsp; An error occurred reading the results. Is the document store working?")
                    return
                except Exception as e:
                    logging.exception(e)
                    if "The server is busy processing requests" in str(e) or "503" in str(e):
                        st.error(
                            "🧑‍🌾 &nbsp;&nbsp; All our workers are busy! Try again later.")
                    else:
                        st.error(
                            "🐞 &nbsp;&nbsp; An error occurred during the request.")
                    return

            if st.session_state.results:
                st.write("## Results:")

                for ans in st.session_state.results:

                    answer, context = ans["answer"], ans["context"]
                    # print(answer)
                    if answer is None or context is None:
                        continue
                    start_idx = context.find(answer)
                    end_idx = start_idx + len(answer)
                    # Hack due to this bug: https://github.com/streamlit/streamlit/issues/3190
                    st.write(markdown(context[:start_idx] + str(annotation(
                        answer, "ANSWER", "#8ef")) + context[end_idx:]), unsafe_allow_html=True)
                    source = ""
                    # url, title = get_backlink(result)
                    # if url and title:
                    source = ans['source']
                    # else:
                    #     source = f"{result['source']}"
                    st.markdown(
                        f"**Relevance:** {ans['score']}")
                    st.markdown(
                        f"**Source:** {source}")
                    if ans['df_json'] != "":
                        df = pd.read_json(ans['df_json'])
                        st.dataframe(df)
                    st.markdown(
                        """<hr style="height:5px;border:none;color:#333;background-color:#333;" /> """, unsafe_allow_html=True)

    # Chatbot
    components.html("""
        <html>
        <body>
            <script>!(function () {
                let e = document.createElement("script"),
                t = document.head || document.getElementsByTagName("head")[0];
                (e.src =
                "https://cdn.jsdelivr.net/npm/rasa-webchat@1.x.x/lib/index.js"),
                // Replace 1.x.x with the version that you want
                (e.async = !0),
                (e.onload = () => {
                    window.WebChat.default(
                    {
                        initPayload: '/greet',
                        customData: { language: "en" },
                        socketUrl: "http://localhost:5005",
                        title: 'Chatbot for Support Engineers',
                        subtitle: 'Retrieve relevant docs faster'
                        // add other props here
                    },
                    null
                    );
                }),
                t.insertBefore(e, t.firstChild);
            })();
            </script>
        </body>
        </html>
        """, height = 600, width=600)

main()
