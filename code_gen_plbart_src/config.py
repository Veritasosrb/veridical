import torch
import os

DEVICE = 'cuda' if torch.cuda.is_available() else 'cpu'
BASE_MODEL_PATH = os.getcwd() + "/code_gen_plbart_src/models/plbart-base/"
TRAINED_MODEL_PATH = os.getcwd() + "/code_gen_plbart_src/models/code_gen_plbart.pt"
PRED_MAX_LENGTH = 200