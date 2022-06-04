# For finding top-k relevent codes
import torch
import pandas as pd
from relevantretriever import encoder
from relevantretriever import retriever
import os 

def retrieve_codes(docstring):
	codes = pd.read_csv('./relevantretriever/csnet_py_val_ret.csv')['code'].values
	code_embeds = torch.load('./relevantretriever/csnet_py_val_code_embeds.pt')

	docstring_embed = encoder.encode_docstring(
		docstring, 
		model_path = './relevantretriever/codesearch-codebert-latefusion-allpl/'
	)

	ranks = retriever.retrieve_similar(
		docstring_embed, 
		code_embeds
	)
	retrieved = [codes[i] for i in ranks][:1]
	return retrieved



def run(docstring_block):
	docstring_block['docstring']
	rel_codes = retrieve_codes(docstring_block['docstring'])	#change with relevent function/api call
	docstring_block['rel_codes'] = rel_codes
	print(f"Rel Code: {rel_codes[0]}")
	return docstring_block