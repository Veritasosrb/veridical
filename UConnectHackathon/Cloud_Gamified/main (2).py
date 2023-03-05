import streamlit as st
from PIL import Image
import pandas as pd
from apyori import apriori


def main():

    st.title('Welcome')
    menu = ["Welcome","Recommend a service", "Get expert advice"]
    choice = st.sidebar.selectbox("Menu", menu)
    if choice =="Welcome":

        img = Image.open(r'C:\Users\Saniya\PycharmProjects\CloudGamified\Img.png')
        st.image(img, caption='Powered by Cloud : Gamified',width=1000)


    if choice == "Recommend a service":
        st.title('Recommending service')
        option = st.selectbox('Select service you are interested in!',
                              ('Vertex AI', 'API Gateway', 'Cloud Run', 'BigQuery', 'Edge TPU', 'Cloud CDN',
                               'Cloud Storage'))

        st.write('You selected:', option)
        st.sidebar.markdown("Recommending user services")

        if option == 'Vertex AI':
            cid = "Vertex AI"
        if option == 'API Gateway':
            cid = "API Gateway"
        if option == 'Cloud Run':
            cid = "Cloud Run"
        if option == 'BigQuery':
            cid = "BigQuery"
        if option == 'Edge TPU':
            cid = "Edge TPU"
        if option == 'Cloud CDN':
            cid = "Cloud CDN"
        if option == 'Cloud Storage':
            cid = "Cloud Storage"


        Data = pd.read_csv(r'C:\Users\Saniya\PycharmProjects\CloudGamified\Final.csv', header=None)
        transacts = []

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

            output = list(zip(lhs, rhs))
            return output


        output_DataFrame = pd.DataFrame(inspect(output), columns=['Currently Using:', 'Can Use:'])

        df = output_DataFrame.loc[output_DataFrame['Currently Using:'] == cid, :]
        print(df)
        st.table(df)
        st.write('How the services are related to each other')
        st.write('Recommendation will be based on confidence level')

    elif choice =="Get expert advice" :

        st.title('User Leaderboard')
        display = pd.read_csv(r'C:\Users\Saniya\PycharmProjects\CloudGamified\Final1.csv',
                              usecols=['WorkHours','CustomerName'])

        st.bar_chart(display, x='CustomerName', y='WorkHours',width=1000,height=1000)

if __name__ == "__main__":
    main()