import streamlit as st
from PIL import Image
import pandas as pd
from apyori import apriori

def main():
    res=[]
    st.title('Welcome')
    menu = ["Recommend a service", "Get expert advice"]
    choice = st.sidebar.selectbox("Menu", menu)

    if choice == "Recommend a service":
        option = st.selectbox('Select service you are interested in!',
                              ('Vertex AI', 'API Gateway', 'Cloud Run', 'BigQuery', 'Edge TPU', 'Cloud CDN',
                               'Cloud Storage'))

        st.write('You selected:', option)
        st.sidebar.markdown("Recommending user services")

        if option == 'Vertex AI':
            cid = 1
        if option == 'API Gateway':
            cid = 2
        if option == 'Cloud Run':
            cid = 3
        if option == 'BigQuery':
            cid = 4
        if option == 'Edge TPU':
            cid = 5
        if option == 'Cloud CDN':
            cid = 6
        if option == 'Cloud Storage':
            cid = 7


        Data = pd.read_csv(r'C:\Users\Saniya\PycharmProjects\CloudGamified\Final.csv', header=None)
        transacts = []
        # populating a list of transactions
        for i in range(len(Data)):
            transacts.append([str(Data.values[i, j]) for j in range(2, 4)])
        rule = apriori(transactions=transacts, min_support=0.001, min_confidence=0.02, min_lift=3, min_length=2,
                       max_length=2)
        output = list(rule)  # returns a non-tabular output
        print(output)

        # putting output into a pandas dataframe
        def inspect(output):
            lhs = [tuple(result[2][0][0])[0] for result in output]
            rhs = [tuple(result[2][0][1])[0] for result in output]
            support = [result[1] for result in output]
            confidence = [result[2][0][2] for result in output]
            lift = [result[2][0][3] for result in output]
            return list(zip(lhs, rhs, support, confidence, lift))

        output_DataFrame = pd.DataFrame(inspect(output),
                                        columns=['Left_Hand_Side', 'Right_Hand_Side', 'Support', 'Confidence',
                                                 'Lift'])
        res = output_DataFrame
        print(res)
        st.table(output_DataFrame)
        st.write('How the services are related to each other')
        st.write('Recommendation will be based on confidence level')




    elif choice =="Get expert advice" :
         print('hi')

if __name__ == "__main__":
    main()