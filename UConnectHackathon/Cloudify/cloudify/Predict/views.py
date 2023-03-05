from django.shortcuts import render
# import joblib
import joblib
from django.http import JsonResponse
from .models import Prediction
from .serializers import PredictSerializer
from rest_framework.decorators import api_view, permission_classes
from rest_framework.response import Response
from rest_framework import status, permissions
from django.conf import settings
import pickle
import os

# fid = open(os.path.join(('Hack.pkl')),'rb+')
# print(fid)
# model = pickle.load(fid) 
# model = joblib.load(fid)
model = joblib.load(r'C:\Users\ASUS\Desktop\cloudify\cloudify\Hack.pkl')

@api_view(['GET','POST'])
@permission_classes((permissions.AllowAny,))
def prediction(request, format=None):
    if request.method == 'GET':
        Predict = Prediction.objects.all()
        # print(model.predict([[206,3,1,1,64,1,80988,1,1,7974]]))
        serializer = PredictSerializer(Predict, many = True)
        return Response(serializer.data)
    if request.method == 'POST':
        serializer = PredictSerializer(data=request.data)
        if serializer.is_valid():
            serializer.save()
            return Response(serializer.data, status.HTTP_201_CREATED)
        