'''
Importing the necessary modules
'''
from flask import Flask, request, render_template, redirect, url_for
import json
from model import run_model
import nlpConstants
app = Flask(__name__)


@app.route("/runModel", methods=['GET', 'POST'])
def runModel():
    if request.method == 'POST':
        return json.dumps({'success': True}), 200, {'ContentType': 'application/json'}

    return redirect(url_for('results'))


@app.route("/results", methods=['GET', 'POST'])
def results():
    f = open(nlpConstants.GIT_HISTORY_PATH)
    gitData = json.load(f)
    f = open(nlpConstants.TEST_DATA_JSON_PATH)
    tcLabelComponentsData = json.load(f)

    data = {}
    data["gitHistory"] = gitData
    data["tcLabelsComponents"] = tcLabelComponentsData
    
    model_result = run_model(data)
    '''
    Return the json if required
    '''
    # return model_result

    return render_template('results.html', data=model_result)


@app.route("/", methods=['GET', 'POST'])
def main():
    return render_template('index.html')


if __name__ == "__main__":
    app.run(debug=True)
