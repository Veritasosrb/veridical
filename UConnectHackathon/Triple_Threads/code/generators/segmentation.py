from faker import Faker
from uuid import uuid4
import random as rnd
import csv

columns = ['customer_id', 'name', 'purchase_freq',
           'purchase_value', 'annual_revenue', 'years_associated']

# annual revenue in 1000 $US

fake = Faker(['en_IN', 'en_US'])

def make_record():
    spending = rnd.gauss(71.5, 17)
    profit = spending * rnd.uniform(3, 12)
    return [
        uuid4().hex,
        fake.name(),
        rnd.randrange(0, 15),
        spending,
        profit,
        rnd.gauss(8, 6)
    ]

with open('segmentation-2.csv', 'w', newline='', encoding='utf-8') as csvfile:
    writer = csv.writer(csvfile)
    writer.writerow(columns)
    for i in range(500_000):
        writer.writerow(make_record())
        if (i % 100_000 == 0):
            print(f'Generated {i}')
print('Completed!')

