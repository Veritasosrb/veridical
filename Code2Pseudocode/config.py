import transformers
import torch
import os

SEED = 42
MODEL_PATH = "t5-base"
MODELS_DIR = os.getcwd() + "/Code2Pseudocode/" + "/models/"

# data
TOKENIZER = transformers.T5Tokenizer.from_pretrained(MODEL_PATH)
SRC_MAX_LENGTH = 160
TGT_MAX_LENGTH = 300
BATCH_SIZE = 4

# model
DEVICE = torch.device('cuda' if torch.cuda.is_available() else 'cpu')

TRAINED_MODEL_PATH = os.getcwd() + "/Code2Pseudocode/" + "/models/t5_pseudocode_gen_tgt_512.pt"
TEST_FILE_PATH = os.getcwd() + "/Code2Pseudocode/" + "/test.py"
