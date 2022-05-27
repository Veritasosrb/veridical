from django.urls import include, re_path
from django.conf import settings
from django.conf.urls.static import static
from ocr.views import *

urlpatterns = [
    re_path('upload-doc/', upload , name='ocr-document'),
]

if settings.DEBUG:
    urlpatterns += static(settings.MEDIA_URL, document_root=settings.MEDIA_ROOT)
