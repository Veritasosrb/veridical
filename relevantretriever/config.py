import torch

config = {
    'BATCH_SIZE': 32,
    'MAX_LENGTH': 512,
    'DEVICE': 'cuda' if torch.cuda.is_available() else 'cpu',
}