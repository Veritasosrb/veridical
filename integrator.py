# 1. Story to docstring
# 	Input:  {"story": "user story"}
# 	Output: {"docstring": "docstring"}
# 2. Docstring to relevent codes
# 	Input:  {"docstring": "docstring"}
# 	Output: {"docstring": "docstring", "rel_codes": ['''code1''', '''code2''']}
# 3. Relevent codes to augmented codes
# 	Input:  {"docstring": "docstring", "rel_codes": ['''code1''', '''code2''']}
# 	Output: {"aug_code": '''augmented code'''}
# 4. Augmented code to pseudocode
# 	Input:  {"aug_code": '''augmented code'''}
# 	Output: {"pseudo_code": '''Pseudocode'''}


import module1, module2, module3, module4
from pprint import pprint

from flask import Flask, request, jsonify
from flask_ngrok import run_with_ngrok
from flask_cors import CORS, cross_origin



def integrator(user_story_block):
	docstring_block = module1.run(user_story_block)
	rel_code_block = module2.run(docstring_block)
	# return rel_code_block
	aug_code_block = module3.run(rel_code_block)
	pseudo_code_block = module4.run(aug_code_block)
	print("PseudoCode:\n"+pseudo_code_block['pseudo_code'])
	return pseudo_code_block

# print(integrator({"story": "As a researcher, I want to download reports, so that I can use them in immediate and future in talks and articles."}))	
# pprint(integrator({"story": "As a user, I want to find the sum of a list of vectors"}))	
# pprint(integrator({"story": "As a user, I want to find the k nearest neighbours"}))

app = Flask(__name__)
CORS(app, resources={r"/hello_world": {"origins": "*"}})
app.config['CORS_HEADERS'] = 'Content-Type'
run_with_ngrok(app)

@app.route("/")
def hello_world2():
	return {"Hello, World!"}

@app.route("/beproj" , methods=['POST', 'OPTIONS'])
@cross_origin(origin='',headers=['Content-Type',''])
def hello_world():
	return jsonify(integrator(request.get_json()))

# @app.after_request
# def after_request(response):
#   response.headers.add('Access-Control-Allow-Origin', '*')
#   response.headers.add('Access-Control-Allow-Headers', 'Content-Type,Authorization')
#   response.headers.add('Access-Control-Allow-Methods', 'GET,PUT,POST,DELETE,OPTIONS')
#   response.headers.add('Access-Control-Allow-Credentials', 'true')
#   return response

if __name__ == '__main__':
	app.run()


# pprint(integrator({"story": "As a user, I want to create database"}))