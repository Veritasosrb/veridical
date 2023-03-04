from django.shortcuts import render
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

from django.shortcuts import render, redirect
from .models import Prediction
from django.contrib.auth.forms import UserCreationForm
from django.contrib import messages
from django.contrib.auth.models import User
from django.contrib.auth.hashers import make_password
from django.urls import reverse
# import requests
from django.contrib.auth import authenticate, login as login_User, logout as logout_User

@api_view(['GET','POST'])
@permission_classes((permissions.AllowAny,))
def prediction(request, format=None):
    if request.method == 'GET':
        Predict = Prediction.objects.all()
        model = joblib.load(r'C:\Users\ASUS\Desktop\Veritas\cloudify\app\model\Hack.pkl')
        print(model.predict([[206,3,1,1,64,1,80988,1,1,7974]]))
        serializer = PredictSerializer(Predict, many = True)
        return Response(serializer.data)
    if request.method == 'POST':
        serializer = PredictSerializer(data=request.data)
        if serializer.is_valid():
            serializer.save()
            return Response(serializer.data, status.HTTP_201_CREATED)
        
def home(request):
    return render(request, 'app/home.html')


def login(request):
    if request.method == 'POST':
        username = request.POST.get('username')    
        password = request.POST.get('password')
        users = User.objects.filter(username = username).first()
        if users is not None:
            username = users.username
            user =  authenticate(request, username=username, password=password)
            if user is not None:
                print("user is not none true")
                login_User(request, user)
                return redirect('home')
            else:
                print("error message wala")
                messages.success(request, f'Invalid Credentials!')
                return render(request, 'app/login.html')
        else:
            messages.success(request, f'Invalid Credentials!')
            return render(request, 'app/login.html')
    else:
        return render(request, 'app/login.html')

def register(request):
    if request.method == 'POST':

        err_lst = []
        username = request.POST.get('username')
        email = request.POST.get('email')
        password1 = request.POST.get('password1')
        password2 = request.POST.get('password2')
        hashed_password = make_password(password1)
        if len(password1)<8:
            messages.error(request, f'Password must be atleast 8 characters long!')
            err_lst.append("Check Password length")
        if(password1 != password2):
            messages.error(request, f'Passwords not Matching!')
            err_lst.append("Passwords not Matching!")
            print("Got data1")
        if User.objects.filter(username=username).exists():
            messages.error(request, f'Account with this Username already exist.')
            err_lst.append("Account with this Username already exist.")
            print("Got data2")
        if User.objects.filter(email=email).exists():
            messages.error(request, f'This Email is Already linked to an existing Account.')
            err_lst.append("This Email is Already linked to an existing Account.")
            print("Got data3")

        if len(err_lst) == 0:
            print("Got data4")
            u1 = User(username = username, email= email, password = hashed_password)
            u1.save()
            print("User created successfully")
            messages.success(request, f'Accound created!')
            return redirect ('home')
        else:
            return render(request, 'app/register.html', {'err_lst': err_lst})

    else:
        return render (request, 'app/register.html')

def profile(request):
    return render(request, 'app/profile.html')

def logout(request):
    logout_User(request)
    messages.success(request, f'Logged out successfully!')
    return redirect(reverse("home"))