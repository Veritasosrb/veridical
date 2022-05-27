# **Motivation**
As we usually see that most the times the doctor use to write the observation of the patients diseases on the paper which is sometimes lost by the patient
due to this the data of the observation is lost.So Due to this reason we decided to create a Digital Record System which stores the record digitally and due to this there is zero chance of data lost.

#**System Implementation**

**1. Page Detection**

Page Detection Module can be also be called noise removal module. In this module we process the image to remove unwanted background segments. We are using Grey scale converstion and Canny edge Detection method to find image border between prescription and unwanted background. Initially we
Median blur the image to minimize the effect of textual content on edge detection, creating a image with highlighted different edge. The threshold value for blur is applied to remove unwanted details during edge detection. Then we add black border to create contour for the image edge. After the process module find main edge border for the image, We add 5px offset to contour to prevent any corner data loss. The image cropped after proccess according to final border after offset.

**2. Word Detection**

Word Detection module is basic model to segment large image from page detection model with multiple textual paragraph into small word segments to increase accuracy of OCR model. Here we 2 different technique firstly sober operator and Watershed algorithm to segment image in image. In Sober Operator we initially blur image a little to find change is continuity of word and creating segments based on the finding in contours.In watershed algorithm we initially remove basic noise to reduce interference to the algorithm. Then we convert image by labeling each pixel to 0 or 1 based on pixel value in comparison to threshold value. After the process we create contour based on values. The image segments are created based on the contour. After combining the segments from both the process the segment are restructured and regrouped based on size and contour. The final result gives a group of segment to send for OCR.

**3. Word Preprocessing**

Data preprocessing is a process of preparing and improving the raw data and making it suitable for further OCR Models.Here we used Word detection output segment individually as input to process the image. Initially we imported preprocessing library and used them to preprocess and normalize the image. Normalization can be explained as process of changing the values of numeric columns in the dataset to use a common scale, without distorting differences in the ranges of values or losing information. We applied Thresholding of image, Histogram Equalization, Using filtering on normalized image, Data augmentation using elastic transform to image for preprocessing.

**4. Optical Character Recognition**

Optical character recognition is Main module for our project. We train the CRNN model which is combination of 2 major machine learning models CNN and RNN. The CRNN model is trained upto 140000 iteration to reach a model accuracy of 0.941. We also added tesseract model for printed text which has accuracy of more than 0.95 for recognizing printed text. Both model are combined based on Precision for each word segment from previous word detection module.

![Identification of dependencies and converting them into frames](./ReadMe_Images/step_frames.png)
  
# **Software build**
1. Step 1: Get sources from GitHub$ git clone https://github.com/scm/crtx/qa-intern-project-test.git
2. Download external jar dependencies in the project buildpath.
2. mvn clean install.

### Resolve dependencies for MacOS:
1. Download WordNet-3.0\
2. cd Downloads/WordNet-3.0\
3. Locate 'Tcl.framework' and 'Tk.framework/' modify paths if needed and run the command below to compile WordNet with appropriate paths for 'Tcl.framework' and 'Tk.framework'.

WordNet-3.0 % sudo ./configure --with-tclconfig=/Library/Developer/CommandLineTools/SDKs/MacOSX11.1.sdk/System/Library/Frameworks/Tcl.framework --with-tkconfig=/Library/Developer/CommandLineTools/SDKs/MacOSX10.15.sdk/System/Library/Frameworks/Tk.framework/

**Expected Output:**

WordNet is now configured

  Installation directory:               /usr/local/WordNet-3.0

  To build and install WordNet:

    make
    make install

  To run, environment variables should be set as follows:

    PATH - include ${exec_prefix}/bin
    WNHOME - if not using default installation location, set to /usr/local/WordNet-3.0

    See INSTALL file for details and additional environment variables
    which may need to be set on your system.

4. Next run:-
WordNet-3.0 % make

5. Next run:-
WordNet-3.0 % make install

### **Pre-requisites:**
1. Install Java and add environment variable. export JAVA_HOME="/usr/libexec/java_home">> ~/.bash_profile
 
2. Install maven and add environment variable. export PATH="$PATH:/Users/$username/Documents/apache-maven-3.8.3/bin"

# **Use cases and benefits**
1. Clients were interested in the possibility of capturing design requirements in a behavioral model that could potentially reduce rework due to miscommunication between the product owner and developer/tester.
2. The test cases must be able to provide maximum coverage of paths and data.
3. Not all the testers have prior knowledge of the working of the system which leads to difficulties in designing the test cases to match the product owner’s needs.
4. Autogeneration of test cases will help save time and money, improve the quality of testing and ensure better test coverage.
5. Autogenerated tests will also facilitate easier maintenance and reuse of test cases if the customer changes the requirements.

![Design](./ReadMe_Images/design.png)
