
## Steps to setup backend server locally

python3 -m venv env
source env/bin/activate
pip3 install -r requirements.txt
python manage.py makemigrations
python manage.py migrate
python inital_config_variables.py

unzip the file detailed zip file AG_News from https://drive.google.com/file/d/1-jwvz8qPflFVFCoHR__dQ3EZVrgS38g2/view?usp=sharing
