from rest_framework import serializers
from .models import Prediction

class  PredictSerializer(serializers.ModelSerializer):
    class Meta:
        model = Prediction
        fields = ['storage' ,
                'security' ,
                'access' ,
                'support' ,
                'computation', 
                'vulnerability' ,
                'apiCalls',
                'apiCustumize',
                'serverLoc' ,
                'computationIns' ,
                'serverLoc']