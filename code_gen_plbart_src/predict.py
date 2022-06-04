import torch
import transformers
from code_gen_plbart_src.model import *
from code_gen_plbart_src.config import *

def load_model(model_path):
    model = PLBARTModel()
    model.load_state_dict(torch.load(
        model_path,
        map_location=torch.device(DEVICE),
    ))
    return model


def prepare_input(tokenizer, docstring, ret_code):
    inp = '<s> '
    inp += docstring + ' </s></s> '
    inp += ret_code + ' </s>'
    inp = inp.replace("\n", "").replace("\t", "")
    print("Input aug ip: "+inp)
    inp = tokenizer(inp, return_tensors='pt', max_length=512, truncation=True, padding="max_length")
    return inp


def predict(docstring, ret_code):
    model = load_model(TRAINED_MODEL_PATH)
    tokenizer = transformers.AutoTokenizer.from_pretrained(BASE_MODEL_PATH)
    inp = prepare_input(tokenizer, docstring, ret_code)

    model.eval()
    with torch.no_grad():
        pred_ids = model.tf.generate(
            **inp, 
            max_length=PRED_MAX_LENGTH
        )

    pred_ids = pred_ids.squeeze().cpu().numpy()
    pred = tokenizer.decode(pred_ids)
    pred_cleaned = pred.replace('<s>', '').replace('</s>', '').strip()
    return pred_cleaned


if __name__ == '__main__':
    
    
    docstring = 'Set a url parameter.'
    ret_code = """def _add_query_parameter ( url , name , value ) : if value is None : return url else : return update_query_params ( url , { name : value } )"""
    
    generated_code = predict(docstring, ret_code)
    print(generated_code)