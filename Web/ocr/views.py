# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.shortcuts import render
from django.core.files.storage import FileSystemStorage
import pytesseract as tess
from PIL import Image

def ocrs(myfile):
    tess.pytesseract.tesseract_cmd = r'C:\Users\Gupta\AppData\Local\Programs\Tesseract-OCR\tesseract.exe'
    img = Image.open(myfile)
    text = tess.image_to_string(img)
    return text
# Create your views here.
def upload(request):
    if request.method == 'POST' and request.FILES['myfile']:
        myfile = request.FILES['myfile']
        text=ocrs(myfile)
        text = text.split("\n")
        fs = FileSystemStorage()
        filename = fs.save(myfile.name, myfile)
        uploaded_file_url = fs.url(filename)
        return render(request, 'upload-doc.html', {
            'uploaded_file_url': uploaded_file_url,
            'uploaded_file_text': text
        })
    return render(request, 'upload-doc.html')

