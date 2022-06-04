# For generating pseudocode
from pyminifier import minification
from Code2Pseudocode.predict import PseudocodeGenUtils

def generate_pseudocode(aug_code):
	aug_code = aug_code.replace("return", ":return").split(":")#.splitlines()
	# aug_code_split = []
	# for a in aug_code:
	# 	if "return" in a:
	# 		tmp = a.split("return")
	# 		tmp2 = ["return"+t for t in tmp][1:]
	# 	aug_code_split
	# # print(aug_code)
	gen_pc = []
	max_len = 0
	new_snips = []
	model = PseudocodeGenUtils.loadmodel()

	for snippet in aug_code:
		snippet = snippet.replace("\n", "").replace("\t", "")
		if len(snippet.strip()) != 0:
			temp_len = len(snippet)
			pc = PseudocodeGenUtils.get_pseudocode(snippet, model)
			gen_pc.append(pc)
			new_snips.append(snippet)
	
			if max_len < temp_len:
				max_len = temp_len
	gen_pc[0] = aug_code[0]
	pc = ""
	for i, c in enumerate(gen_pc):
		if i>0:
			gen_pc[i] = "\t"+gen_pc[i]
		pc+=gen_pc[i]+"\n"
	# pc = PseudocodeGenUtils.get_pseudocode(aug_code, model)
	return pc

def run(aug_code_block):
	pseudocode = "pseudocode"
	pseudocode = generate_pseudocode(aug_code_block['aug_code'])				#change with relevent function/api call
	return {"code": aug_code_block['aug_code'], "pseudo_code": pseudocode}