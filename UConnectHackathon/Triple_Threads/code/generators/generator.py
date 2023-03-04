from faker import Faker
from uuid import uuid4
import random as rnd
import csv

products = {'netbackup', 'backupexec', 'infoscale', 'merge1',
            'ediscovery', 'vault', 'alta', 'datainsight', 'netbackup_applicance'}
column_titles = ['customer_id', 'name', *[f'buys_{name}' for name in products]]

patterns = [
    [1, 0, 0, 0, 0, 0, 0, 0, 0],
    [0, 1, 0, 0, 0, 0, 0, 0, 0],
    [0, 0, 1, 0, 0, 0, 0, 0, 0],
    [0, 0, 0, 1, 0, 0, 0, 0, 0],
    [0, 0, 0, 0, 1, 0, 0, 0, 0],
    [0, 0, 0, 0, 0, 0, 0, 0, 0],
    [0, 0, 0, 0, 0, 1, 0, 0, 0],
    [0, 0, 0, 0, 0, 0, 1, 0, 0],
    [0, 0, 0, 0, 0, 0, 0, 1, 0],
    [0, 0, 0, 0, 0, 0, 0, 0, 1],
    [0, 1, 0, 1, 0, 1, 0, 0, 0],
    [0, 0, 0, 0, 1, 0, 1, 0, 1],
    [0, 1, 0, 0, 0, 1, 0, 0, 0],
    [0, 0, 1, 0, 1, 0, 0, 1, 0],
    [1, 0, 0, 0, 1, 0, 1, 0, 0],
    [0, 1, 1, 1, 0, 0, 1, 0, 0],
    [1, 1, 0, 0, 1, 0, 0, 1, 0],
    [0, 0, 1, 0, 1, 1, 1, 0, 0],
]

fake = Faker(['en_IN', 'en_US'])


def mutate_pattern(pattern, noise=0.2):
    pattern = pattern[:]
    plen = len(pattern)
    for _ in range(int(plen * noise)):
        idx = rnd.randrange(0, plen)
        value = rnd.choice([0, 1])
        pattern[idx] = value
    return pattern


def make_record():
    return [
        uuid4().hex,
        fake.name(),
        *mutate_pattern(rnd.choice(patterns))
    ]


with open('dataset-2.csv', 'w', newline='', encoding='utf-8') as csvfile:
    writer = csv.writer(csvfile)
    writer.writerow(column_titles)
    for i in range(500_000):
        writer.writerow(make_record())
        if (i % 100_000 == 0):
            print(f'Generated {i}')
print('Completed!')
