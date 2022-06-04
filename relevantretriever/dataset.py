import torch
from relevantretriever.config import config

class EncoderDataset(torch.utils.data.Dataset):
    def __init__(self, codes, tokenizer):
        super(EncoderDataset, self).__init__()
        self.codes = codes
        self.tokenizer = tokenizer

    def __len__(self):
        return len(self.summaries)

    def __getitem__(self, idx):
        tokenized_code = self.tokenizer(
            self.codes[idx],
            max_length=config['MAX_LENGTH'],
            padding='max_length',
            truncation=True,
            return_tensors='pt',
            return_token_type_ids=False
        )

        return {
            'input_ids': tokenized_code['input_ids'].squeeze().long(),
            'attention_mask': tokenized_code['attention_mask'].squeeze().long(),
        }