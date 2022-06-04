import Code2Pseudocode.config as config
import os
import re
import transformers
import torch
from transformers import T5Tokenizer, T5Model, T5ForConditionalGeneration

# Printing the working directory
print("Current Working Directory " , os.getcwd())

# Setting the device to either cpu or gpu based on the system configuration
device = torch.device(config.DEVICE)

# T5 model class
class T5Model(torch.nn.Module):
    def __init__(self, path):
        super(T5Model, self).__init__()

        self.t5_model = T5ForConditionalGeneration.from_pretrained(path)

    def forward(
        self,
        input_ids, 
        attention_mask=None, 
        decoder_input_ids=None, 
        decoder_attention_mask=None, 
        lm_labels=None
        ):

        return self.t5_model(
            input_ids,
            attention_mask=attention_mask,
            decoder_input_ids=decoder_input_ids,
            decoder_attention_mask=decoder_attention_mask,
            labels=lm_labels,
        )


# Utility class for Pseudocode Generation
class PseudocodeGenUtils:
    @staticmethod
    def loadmodel():
        model = T5Model(config.MODELS_DIR + 't5-base')
        # push the model to GPU
        model = model.to(device)
        # load weights of best model
        model.load_state_dict(torch.load(
            f"{config.MODELS_DIR}t5_pseudocode_gen_tgt_512.pt", map_location=device))
        return model

    @staticmethod
    def get_pseudocode(code, model):
        src_text = code
        src_tokenized = config.TOKENIZER.encode_plus(
            src_text, 
            padding="max_length",
            truncation=True,
            max_length=config.SRC_MAX_LENGTH,
            return_attention_mask=True,
            return_tensors='pt'
        )
        b_src_input_ids = src_tokenized['input_ids'].long().to(device)
        b_src_attention_mask = src_tokenized['attention_mask'].long().to(device)

        model.eval()
        with torch.no_grad():
            # get pred
            pred_ids = model.t5_model.generate(
                input_ids=b_src_input_ids, 
                attention_mask=b_src_attention_mask,
                max_length=300
            )
            pred_id = pred_ids[0].cpu().numpy()
            pred_decoded = config.TOKENIZER.decode(pred_id)
            pred_cleaned = pred_decoded.replace("<pad>", '').replace("</s>", '').strip()
            
        return pred_cleaned

    @staticmethod
    def get_pseudocode_from_file(filename, model):
        with open(filename, 'r', encoding='utf-8') as f:
            code_lines = f.readlines()
            gen_pc = []
            max_len = 0
            new_snips = []
            
            for snippet in code_lines:
                snippet = snippet.replace("\n", "").replace("\t", "")
                if len(snippet.strip()) != 0:
                    temp_len = len(snippet)
                    pc = PseudocodeGenUtils.get_pseudocode(snippet, model)
                    gen_pc.append(pc)
                    new_snips.append(snippet)
                
                    if max_len < temp_len:
                        max_len = temp_len

        return new_snips, gen_pc, max_len

# Test function
def test_run():
    model = PseudocodeGenUtils.loadmodel()
    snippets, pseudocodes, max_len = PseudocodeGenUtils.get_pseudocode_from_file(config.TEST_FILE_PATH, model)
    assert len(snippets) == len(pseudocodes)
    for code_snippet, pseudocode in zip(snippets, pseudocodes):
        print(f"{code_snippet.strip().ljust(max_len)}| {pseudocode}")

if __name__ == "__main__":
    test_run()