# For augmenting the code 
from code_gen_plbart_src.predict import predict


def run(rel_code_block):
	print(type(rel_code_block['docstring']))
	print(type(rel_code_block['rel_codes'][0]))
	gen_code = predict(rel_code_block['docstring'], rel_code_block['rel_codes'][0])
	print("Gen Code: "+gen_code)
	return {"aug_code": gen_code}