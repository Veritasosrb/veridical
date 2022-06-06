from sklearn.metrics.pairwise import cosine_similarity
import pandas as pd
from sentence_transformers import SentenceTransformer


model = SentenceTransformer('all-mpnet-base-v2')

def run_model(data):

    tcLabCom=data["tcLabelsComponents"]

    gitHist=data["gitHistory"]

    tcLabComEmb = model.encode(tcLabCom)

    gitHistEmb = model.encode(gitHist)

    finalDict={}

    for i in range(gitHistEmb.shape[0]):
        tempList=[]
        simList = []
        traceBackList = []
    
        tempVar1=gitHistEmb[i].reshape((1,-1))
    
        for j in range(tcLabComEmb.shape[0]):
        
            tempVar2=tcLabComEmb[j].reshape((1,-1))

            simList.append((cosine_similarity(tempVar1,tempVar2)))
            traceBackList.append(j)

        tempDf = (pd.DataFrame(list(zip(simList, traceBackList))).sort_values(by=[0], ascending=False))

        tempDf=tempDf.iloc[:3,:]
        
        tempList.append((tempDf.iloc[0,0], tempDf.iloc[0,1]))
        tempList.append((tempDf.iloc[1,0], tempDf.iloc[1,1]))
        tempList.append((tempDf.iloc[2,0], tempDf.iloc[2,1]))

        finalDict[i]=tempList

    print(finalDict)
    result={}

    for i in finalDict:
        recomTc=[]
        recomTc.append(tcLabCom[finalDict[i][0][1]].get('name'))
        recomTc.append(tcLabCom[finalDict[i][1][1]].get('name'))
        recomTc.append(tcLabCom[finalDict[i][2][1]].get('name'))
        result[gitHist[i].get('commitHash')]=recomTc

    finalResult=[]
    finalResult.append(result)
    print(result)
    print(finalResult)
    
    return result





