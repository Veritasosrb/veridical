import pandas as pd
import os

rules = pd.read_csv('models/rules.csv')
rules['antecedents'] = rules['antecedents'].apply(lambda r: eval(r))
rules['consequents'] = rules['consequents'].apply(lambda r: eval(r))


def find_rule(lookup):
    def inner(fset: frozenset) -> bool:
        return (len(lookup - fset) == 0) and (fset.union(lookup) == lookup)
    return inner


def get_recommendations(cart):
    recommends = rules[rules['antecedents'].apply(find_rule(cart))] \
        .sort_values(by=['confidence'], ascending=False)
    
    results = set()
    for fset in recommends['consequents'].to_list():
        results = results.union(fset)
    return results
