from django.db import models
from django.contrib.auth.models import User
from django.utils import timezone

class Prediction(models.Model):
    storage = models.IntegerField(default=0)
    security = models.IntegerField(default=0)
    access = models.IntegerField(default=0)
    support = models.IntegerField(default=0)
    computation = models.IntegerField(default=0)
    vulnerability = models.IntegerField(default=0)
    apiCalls = models.IntegerField(default=0)
    apiCustumize = models.IntegerField(default=0)
    serverLoc = models.IntegerField(default=0)
    computationIns = models.IntegerField(default=0)
    serverLoc = models.IntegerField(default=0)

