# **Motivation**
As we usually see that most the times the doctor use to write the observation of the patients diseases on the paper which is sometimes lost by the patient
due to this the data of the observation is lost.So Due to this reason we decided to create a Digital Record System which stores the record digitally and due to this there is zero chance of data lost.

**System Implementation**

**1. Page Detection**

Page Detection Module can be also be called noise removal module. In this module we process the image to remove unwanted background segments. We are using Grey scale converstion and Canny edge Detection method to find image border between prescription and unwanted background. Initially we
Median blur the image to minimize the effect of textual content on edge detection, creating a image with highlighted different edge. The threshold value for blur is applied to remove unwanted details during edge detection. Then we add black border to create contour for the image edge. After the process module find main edge border for the image, We add 5px offset to contour to prevent any corner data loss. The image cropped after proccess according to final border after offset.

**2. Word Detection**

Word Detection module is basic model to segment large image from page detection model with multiple textual paragraph into small word segments to increase accuracy of OCR model. Here we 2 different technique firstly sober operator and Watershed algorithm to segment image in image. In Sober Operator we initially blur image a little to find change is continuity of word and creating segments based on the finding in contours.In watershed algorithm we initially remove basic noise to reduce interference to the algorithm. Then we convert image by labeling each pixel to 0 or 1 based on pixel value in comparison to threshold value. After the process we create contour based on values. The image segments are created based on the contour. After combining the segments from both the process the segment are restructured and regrouped based on size and contour. The final result gives a group of segment to send for OCR.

**3. Word Preprocessing**

Data preprocessing is a process of preparing and improving the raw data and making it suitable for further OCR Models.Here we used Word detection output segment individually as input to process the image. Initially we imported preprocessing library and used them to preprocess and normalize the image. Normalization can be explained as process of changing the values of numeric columns in the dataset to use a common scale, without distorting differences in the ranges of values or losing information. We applied Thresholding of image, Histogram Equalization, Using filtering on normalized image, Data augmentation using elastic transform to image for preprocessing.

**4. Optical Character Recognition**

Optical character recognition is Main module for our project. We train the CRNN model which is combination of 2 major machine learning models CNN and RNN. The CRNN model is trained upto 140000 iteration to reach a model accuracy of 0.941. We also added tesseract model for printed text which has accuracy of more than 0.95 for recognizing printed text. Both model are combined based on Precision for each word segment from previous word detection module.
  
**HOW TO RUN THE CODES**

**a) Cloning the repository**
Following steps are there:-
• git clone –recursive https://github.com/Veritasosrb/veridical.git
• Go to Branch ”AIT-2022-DigiHealthOCR”

**b) Installing Environments**
Install the following softwares:-
• Python
• Anaconda
• Jupyter notebook
After this run the following:-
• Run Anaconda Powershell
• Run commands:-
– ”conda create –name ocr-env –file environment.yml”
– ”conda activate ocr-env”

**c) Running Modules**
Start Jupyter Notebook with ocr-env environment.
Open Notebook Folder , It contain all executable python notebook modules
1. ”page detection.ipynb” conatins Page Detection Module
2. ”word detection.ipynb” conatins Word Detection Module
3. ”word preprocessing.ipynb” conatins Word Preprocessing Module
4. ”ocr evaluator.ipynb” conatins Model Accuracy calculator Module
5. ”OCR CRNN.ipynb” conatins OCR Module using CRNN Model
6. ”OCR CTC.ipynb” conatins OCR Module using CTC Model
7. ”Tess.ipynb” conatins OCR Module using Tesseract Model

**d) Running Web Server**
10. Install Django using Command - ”py -m pip install Django”
11. Go to Web folder and run Command prompt
12. Run Command ”python manage.py runserver”
13. Open Browser and Open URL ”http://localhost:8000/ocr/upload-doc/”
14. Upload Image to the page for OCR to receive the output.
