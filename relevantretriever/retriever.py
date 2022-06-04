import torch
# from tqdm.auto import tqdm
import pandas as pd
import relevantretriever.encoder
import time

# from config import config
config = {
    'BATCH_SIZE': 32,
    'MAX_LENGTH': 512,
    'DEVICE': 'cuda' if torch.cuda.is_available() else 'cpu',
}

def rank(docstring_embed, code_embeds, topk=None):
    scores = torch.einsum("ab,cb->ac", docstring_embed, code_embeds)
    scores = torch.softmax(scores, -1)
    _, ranks = torch.sort(scores, descending=True)
    ranks = ranks.squeeze()
    if topk is not None:
        ranks = ranks[:topk]
    return ranks.tolist()

def retrieve_similar(docstring_embed, code_embeds):
    device = torch.device(config['DEVICE'])
    code_embeds = code_embeds.to(device)
    ranks = rank(docstring_embed, code_embeds, topk=20)
    return ranks

if __name__ == '__main__':
    start_time = time.time()
    # docstrings = pd.read_csv('csnet_py_val_ret.csv')['docstring'].values
    codes = pd.read_csv('csnet_py_val_ret.csv')['code'].values
    # docstring_embeds = torch.load('csnet_py_val_docstring_embeds.pt')
    code_embeds = torch.load('csnet_py_val_code_embeds.pt')

    docstring = "donwnload a video from a url"
    docstring_embed = encoder.encode_docstring(
        docstring, 
        model_path = './codesearch-codebert-latefusion-allpl/'
    )

    # ranks = retrieve_similar(
    #     docstring_embeds[0].unsqueeze(dim=0), 
    #     code_embeds
    # )
    ranks = retrieve_similar(
        docstring_embed, 
        code_embeds
    )
    retrieved = [codes[i] for i in ranks]

    
    print(f'docstring: {docstring}')

    # print(f'docstring: {docstrings[0]}')
    print('retrieved top-5:')
    for item in retrieved[:5]:
        print(item)
        print('_' * 100)

    print("--- %s seconds ---" % (time.time() - start_time))



