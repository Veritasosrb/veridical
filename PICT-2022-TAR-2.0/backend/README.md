
## Steps to setup backend server locally

After Cloning the main Repo get into the `PICT-2022-TAR-2.0/backend` and perform these steps

1) Create and Activate a python3 environment
    ```
    python3 -m venv env
    source env/bin/activate
    ```

2) Install Requirments
    ```
    pip3 install -r requirements.txt
    ```

3) Few Initial Scripts

    ```
    python manage.py makemigrations
    python manage.py migrate
    python inital_config_variables.py
    ```

4) Adding Sample dataset into the system

   Download the dataset [AG_News Zip File](https://drive.google.com/file/d/1-jwvz8qPflFVFCoHR__dQ3EZVrgS38g2/view?usp=sharing)
   
   ```
   The Dataset includes 4 files
   i) blank_model_4classes.h5
   ii) complete_datafile.csv
   iii) init_test.csv
   iv) trained_model.h5
   ```
   After downloading zip file, move that file to `backend/ml/components/` and extract or unzip the file.
   
5) Run the Backend Server
    ```
    python manage.py runserver
    ```
   
   You have successfully made the backend code working, the backend server will be hosted `localhost:8000`
   
   You can test the server by hitting the url `http://localhost:8000/mainapp/getvariables/`
   It should return this response
   
   ```
    HTTP 200 OK
    Allow: OPTIONS, GET
    Content-Type: application/json
    Vary: Accept

    {
        "id": 1,
        "main_project_location": "/home/gauravghati/veridical/PICT-2022-TAR-2.0/",
        "initial_epochs": 20,
        "increment_epochs": 1,
        "inque_maxlen": 24,
        "batch_size": 4,
        "curr_dataset": "AG_News"
    }
   ```
