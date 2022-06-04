# from tqdm.auto import tqdm

import torch
import transformers
# from accelerate import Accelerator

from relevantretriever.dataset import EncoderDataset
from relevantretriever.config import config

def encode_codes(codes, model_path):
    accelerator = Accelerator()
    
    # model_path = 'codesearch-codebert-latefusion-allpl/'

    tokenizer = transformers.AutoTokenizer.from_pretrained(model_path)
    model = transformers.AutoModel.from_pretrained(model_path)
    
    data = EncoderDataset(codes, tokenizer)
    dataloader = torch.utils.data.DataLoader(
        data, 
        batch_size=config["BATCH_SIZE"]
    )

    model, dataloader = accelerator.prepare(model, dataloader)
    
    summary_embeds = []
    code_embeds = []
    
    progress_bar = tqdm(
        dataloader, 
        total=len(dataloader),
        desc='Generating embeddings',
        disable=not accelerator.is_main_process,
    )
    
    model.eval()
    for step, batch in enumerate(progress_bar):
        code_input_ids = batch['input_ids']
        code_attention_mask = batch['attention_mask']

        with torch.no_grad():
            code_embed = model(
                input_ids=code_input_ids, 
                attention_mask=code_attention_mask
            )[1]
        code_embeds.append(accelerator.gather(code_embed))

    code_embeds = torch.cat(code_embeds)[:len(data)]
    code_embeds = code_embeds.view(-1, 768)
    
    return code_embeds

def encode_docstring(docstring, model_path):
    device = torch.device(config['DEVICE'])

    tokenizer = transformers.AutoTokenizer.from_pretrained(model_path)
    tokenized_docstring = tokenizer(
        docstring,
        max_length=config['MAX_LENGTH'],
        padding='max_length',
        truncation=True,
        return_tensors='pt',
        return_token_type_ids=False
    )
    docstring_input_ids = tokenized_docstring['input_ids'].long()
    docstring_attention_mask = tokenized_docstring['attention_mask'].long()

    model = transformers.AutoModel.from_pretrained(model_path).to(device)
    docstring_embed = model(
        input_ids=docstring_input_ids.to(device), 
        attention_mask=docstring_attention_mask.to(device),
    )[1]

    return docstring_embed

if __name__ == '__main__':
    x = encode_docstring(
        'sort an array', 
        model_path = './codesearch-codebert-latefusion-allpl/'
    )
    print(x.shape)