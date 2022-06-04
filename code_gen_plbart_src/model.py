import torch
import transformers
from code_gen_plbart_src.config import *

class PLBARTModel(torch.nn.Module):
    def __init__(self):
        super(PLBARTModel, self).__init__()
        self.tf = transformers.PLBartForConditionalGeneration.from_pretrained(BASE_MODEL_PATH)

    def forward(
        self,
        input_ids, 
        attention_mask=None,
        labels=None
        ):
        return self.tf(
            input_ids,
            attention_mask=attention_mask,
            labels=labels,
        )