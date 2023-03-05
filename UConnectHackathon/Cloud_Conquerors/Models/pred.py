import pickle
import streamlit as st
from streamlit_option_menu import option_menu
from PIL import Image
import matplotlib.pyplot as plt
import numpy as np
import requests
from streamlit_lottie import st_lottie
from matplotlib import pyplot as plt
import base64
import streamlit as st
import plotly.express as px
from pi import *

st.set_page_config(page_title="VAM", page_icon="https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.citypng.com%2Fphoto%2F17224%2Fhd-3d-cloud-storage-web-hosting-icon-png&psig=AOvVaw2DvPc1QgHMWvbiSsi5OMQn&ust=1678009409035000&source=images&cd=vfe&ved=0CBAQjRxqFwoTCIih76_-wf0CFQAAAAAdAAAAABAD", layout="wide")
# plt.pie(y)
# plt.show()
# loading the saved models
df = px.data.iris()


# @st.experimental_memo
# def get_img_as_base64(file):
#     with open(file, "rb") as f:
#         data = f.read()
#     return base64.b64encode(data).decode()


# img = get_img_as_base64("ik.jpeg")

# page_bg_img = f"""
# <style>
# [data-testid="stAppViewContainer"] > .main {{
# background-image: url("https://img.freepik.com/free-photo/abstract-luxury-blur-dark-grey-black-gradient-used-as-background-studio-wall-display-your-pr_1258-66739.jpg?w=360");
# background-size: 180%;
# background-position: top left;
# background-repeat: no-repeat;
# background-attachment: local;
# }}
# [data-testid="stSidebar"] > div:first-child {{
# background-image: url("data:image/png;base64,{img}");
# background-position: center;
# background-repeat: no-repeat;
# background-attachment: fixed;
# }}
# [data-testid="stHeader"] {{
# background: rgba(0,0,0,0);
# }}
# [data-testid="stToolbar"] {{
# right: 2rem;
# }}
# </style>
# """

# st.markdown(page_bg_img, unsafe_allow_html=True)


def load(url):
    r = requests.get(url)
    if r.status_code != 200:
        return None
    return r.json()


lottie_coding = load(
    "https://assets1.lottiefiles.com/packages/lf20_wuxlxvkh.json")

diabetes_model = pickle.load(open('trained_model.sav', 'rb'))
st.title('Attack Prediction using ML')
# getting the input data from the user
col1, col2, col3 = st.columns(3)
with col1:
    Pregnancies = st.text_input('AWS Shield')
with col2:
    Glucose = st.text_input('Security Group')

with col3:
    st_lottie(lottie_coding, height=400, width=400)
with col1:
    BloodPressure = st.text_input('AWS KMS')
with col2:
    SkinThickness = st.text_input('VPC')
with col1:
    Insulin = st.text_input('EC2')
with col2:
    BMI = st.text_input('S3')

with col1:
    DiabetesPedigreeFunction = st.text_input('Route 53')
    # code for Prediction
diab_diagnosis = ''
# creating a button for Prediction
# st.radio('', options=['AWS Shield', 'Security Group', 'AWS KMS', 'VPC', 'EC2',],
#       horizontal=True)
if st.button('Check for Attacks'):
    diab_prediction = diabetes_model.predict(
        [[Pregnancies, Glucose, BloodPressure, SkinThickness, Insulin, BMI, DiabetesPedigreeFunction]])

    if (diab_prediction[0] == 1):
        diab_diagnosis = 'Yay, Your Architecture is fully Secured.'
        labels = ['Fully Secured']
        sizes = [100.0]
        fig1, ax1 = plt.subplots()
        # colors = ("#fadadd", "#FFBA00", "#AFDCEB")
        colors = ("#FFB6C1", "#FFBA00", "#0aeaef")
        patches, texts, autotexts = ax1.pie(sizes, colors=colors, radius=5.0, labels=labels, autopct='%1.1f%%',
                                            startangle=40, textprops={'fontsize': 13})
        fig1.patch.set_facecolor('#111212')
        ax1.axis('equal')
        circle = plt.Circle(xy=(0,0), radius = 4.0, facecolor='black')
        plt.gca().add_artist(circle)
        st.success(diab_diagnosis)
        plt.figure(figsize=(8,10))
        colors = ("#FFB6C1", "#FFBA00", "#0aeaef")
        for text in texts:
            text.set_color('white')
        for autotext in autotexts:
            autotext.set_color('white')
        fig1.tight_layout()
        with col2: 
          st.pyplot(fig1)
    elif (diab_prediction[0] == 2):
        diab_diagnosis = 'Oops! Your Architecture has a chance of getting "Man in the Middle Attack". Try adding Virtual Private Cloud or Security Groups.'
        labels = ['Man in the Middle Attack']
        sizes = [100.0]
        fig1, ax1 = plt.subplots()
        # colors = ("#fadadd", "#FFBA00", "#AFDCEB")
        colors = ("#FFB6C1", "#FFBA00", "#0aeaef")
        patches, texts, autotexts = ax1.pie(sizes, colors=colors, radius=5.0, labels=labels, autopct='%1.1f%%',
                                            startangle=40, textprops={'fontsize': 13})
        fig1.patch.set_facecolor('#111212')
        ax1.axis('equal')
        circle = plt.Circle(xy=(0,0), radius = 4.0, facecolor='black')
        plt.gca().add_artist(circle)
        st.success(diab_diagnosis)
        plt.figure(figsize=(8,10))
        # colors = ("#FFB6C1", "#FFBA00", "#0aeaef")
        for text in texts:
            text.set_color('white')
        for autotext in autotexts:
            autotext.set_color('white')
        fig1.tight_layout()
        with col2: 
          st.pyplot(fig1)
    elif (diab_prediction[0] == 3):
        diab_diagnosis = 'Oops! Your Architecture has a chance of getting "Brute Force Attack". Try adding Key Management Service.'
        labels = ['Brute Force Attack']
        sizes = [100.0]
        fig1, ax1 = plt.subplots()
        # colors = ("#fadadd", "#FFBA00", "#AFDCEB")
        colors = ("#FFB6C1", "#FFBA00", "#0aeaef")
        patches, texts, autotexts = ax1.pie(sizes, colors=colors, radius=5.0, labels=labels, autopct='%1.1f%%',
                                            startangle=40, textprops={'fontsize': 13})
        fig1.patch.set_facecolor('#111212')
        ax1.axis('equal')
        circle = plt.Circle(xy=(0,0), radius = 4.0, facecolor='black')
        plt.gca().add_artist(circle)
        st.success(diab_diagnosis)
        plt.figure(figsize=(8,10))
        # colors = ("#FFB6C1", "#FFBA00", "#0aeaef")
        for text in texts:
            text.set_color('white')
        for autotext in autotexts:
            autotext.set_color('white')
        fig1.tight_layout()
        with col2: 
          st.pyplot(fig1)
    elif (diab_prediction[0] == 4):
        diab_diagnosis = 'Oops! Your Architecture has a chance of getting "Distributed Denial of Service Attack". Try adding Shield service.'
        labels = ['Distributed Denial of Service Attack']
        sizes = [100.0]
        fig1, ax1 = plt.subplots()
        # colors = ("#fadadd", "#FFBA00", "#AFDCEB")
        
        colors = ("#FFB6C1", "#FFBA00", "#0aeaef")
        patches, texts, autotexts = ax1.pie(sizes, colors=colors, radius=5.0, labels=labels, autopct='%1.1f%%',
                                            startangle=40, textprops={'fontsize': 13})
        fig1.patch.set_facecolor('#111212')
        ax1.axis('equal')
        circle = plt.Circle(xy=(0,0), radius = 4.0, facecolor='black')
        plt.gca().add_artist(circle)
        st.success(diab_diagnosis)
        plt.figure(figsize=(8,10))
        # colors = ("#FFB6C1", "#FFBA00", "#0aeaef")
        for text in texts:
            text.set_color('white')
        for autotext in autotexts:
            autotext.set_color('white')
        fig1.tight_layout()
        with col2: 
          st.pyplot(fig1)
    elif (diab_prediction[0] == 5):
        diab_diagnosis = 'Oops! Your Architecture has a chance of getting "Brute Force and Man in the Middle Attack". Try adding Key Management Service, Virtual Private Cloud or Security Groups.'
        labels = ['Brute Force', 'Man in the Middle Attack']
        sizes = [100.0/2.0, 100.0/2.0]
        fig1, ax1 = plt.subplots()
        colors = ("#FFB6C1", "#FFBA00", "#0aeaef")
        patches, texts, autotexts = ax1.pie(sizes, colors=colors, radius=5.0, labels=labels, autopct='%1.1f%%',
                                            startangle=40, textprops={'fontsize': 13})
        fig1.patch.set_facecolor('#111212')
        ax1.axis('equal')
        circle = plt.Circle(xy=(0,0), radius = 4.0, facecolor='black')
        plt.gca().add_artist(circle)
        st.success(diab_diagnosis)
        plt.figure(figsize=(8,10))
        # colors = ("#FFB6C1", "#FFBA00", "#0aeaef")
        for text in texts:
            text.set_color('white')
        for autotext in autotexts:
            autotext.set_color('white')
        fig1.tight_layout()
        with col2: 
          st.pyplot(fig1)
    elif (diab_prediction[0] == 6):
        diab_diagnosis = 'Oops! Your Architecture has a chance of getting "Brute Force and DDOS Attack". Try adding Key Management Service and Shield service.'
        labels = 'Brute Force', 'DDOS'
        sizes = [100.0/2.0, 100.0/2.0]
        fig1, ax1 = plt.subplots()
        colors = ("#FFB6C1", "#FFBA00", "#0aeaef")
        patches, texts, autotexts = ax1.pie(sizes, colors=colors, radius=5.0, labels=labels, autopct='%1.1f%%',
                                            startangle=40, textprops={'fontsize': 13})
        fig1.patch.set_facecolor('#111212')
        ax1.axis('equal')
        circle = plt.Circle(xy=(0,0), radius = 4.0, facecolor='black')
        plt.gca().add_artist(circle)
        st.success(diab_diagnosis)
        plt.figure(figsize=(8,10))
        # colors = ("#FFB6C1", "#FFBA00", "#0aeaef")
        for text in texts:
            text.set_color('white')
        for autotext in autotexts:
            autotext.set_color('white')
        fig1.tight_layout()
        with col2: 
          st.pyplot(fig1)
    elif (diab_prediction[0] == 7):
        diab_diagnosis = 'Oops! Your Architecture has a chance of getting "DDOS and Man in the Middle Attack". Try adding Shield service, Virtual Private Cloud or Security Groups.'
        labels = 'DDOS', 'Man in the Middle'
        sizes = [100.0/2.0, 100.0/2.0]
        fig1, ax1 = plt.subplots()
        colors = ("#FFB6C1", "#FFBA00", "#0aeaef")
        patches, texts, autotexts = ax1.pie(sizes, colors=colors, radius=5.0, labels=labels, autopct='%1.1f%%',
                                            startangle=40, textprops={'fontsize': 13})
        fig1.patch.set_facecolor('#111212')
        ax1.axis('equal')
        circle = plt.Circle(xy=(0,0), radius = 4.0, facecolor='black')
        plt.gca().add_artist(circle)
        st.success(diab_diagnosis)
        plt.figure(figsize=(8,10))
        # colors = ("#FFB6C1", "#FFBA00", "#0aeaef")
        for text in texts:
            text.set_color('white')
        for autotext in autotexts:
            autotext.set_color('white')
        fig1.tight_layout()
        with col2: 
          st.pyplot(fig1)
    elif (diab_prediction[0] == 8):
        diab_diagnosis = 'Oops! Your Architecture has a chance of getting "Distributed Denial of Service Attack", "Brute Force" and "Man in the Middle Attack". Try adding Shield service, Key Management Service, Virtual Private Cloud or Security Groups.'
    # diab_diagnosis ='Distributed Denial of Service Attack, Brute Force and Man in the Middle Attack'
        labels = 'Brute Force', 'DDOS', 'Man in the Middle'
        sizes = [100.0/3.0, 100.0/3.0, 100.0/3.0]
        fig1, ax1 = plt.subplots()
        colors = ("#FFB6C1", "#FFBA00", "#0aeaef")
        patches, texts, autotexts = ax1.pie(sizes, colors=colors, radius=5.0, labels=labels, autopct='%1.1f%%',
                                            startangle=40, textprops={'fontsize': 13})
        fig1.patch.set_facecolor('#111212')
        ax1.axis('equal')
        circle = plt.Circle(xy=(0,0), radius = 4.0, facecolor='black')
        plt.gca().add_artist(circle)
        st.success(diab_diagnosis)
        plt.figure(figsize=(8,10))
        # colors = ("#FFB6C1", "#FFBA00", "#0aeaef")
        for text in texts:
            text.set_color('white')
        for autotext in autotexts:
            autotext.set_color('white')
        fig1.tight_layout()
        with col2: 
          st.pyplot(fig1)


# sidebar for navigation
# with st.sidebar:
#     selected = option_menu('Attack Prediction',
#                           ['Predict',],
#                           icons=['activity'],
#                           default_index=0)
#     st_lottie(lottie_coding, height=300)
# # Diabetes Prediction Page

# if (selected == 'Predict'):
#     # page title
