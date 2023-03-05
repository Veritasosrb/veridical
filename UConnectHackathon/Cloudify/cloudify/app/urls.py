
from django.contrib import admin
from django.urls import path, include
from . import views


urlpatterns = [
    path("prediction/", views.prediction, name = 'prediction'),
    path('register/', views.register, name="register"),
    path('login/', views.login, name="login"),
    path('', views.home, name="home"),
    path('profile/', views.profile, name="profile"),
    path('logout/', views.logout, name="logout"),
]
