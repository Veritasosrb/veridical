import random
import pathlib
import pandas as pd
import shutil
import time
import sys
import inotify
from inotify import pyinotify
from pyinotify import IN_MODIFY
import os
class MyEventHandler(pyinotify.ProcessEvent):
    def process_IN_MODIFY(self, event):
        # pathname of all the files in the directory NODE1
        # print("event.pathname: ",event.pathname)
        # print("os.getcwd(): ",os.getcwd())
        # get all the files in the directory NODE1
        files = os.listdir(os.getcwd() + "/Files")
        print("files: ",files)
        for x in files:

            if event.pathname == os.getcwd() + "/Files/" + x:
                print("Encryption activity detected on file: %s" % event.pathname)

wm = pyinotify.WatchManager()
# mask = 

handler = MyEventHandler()
notifier = pyinotify.Notifier(wm, handler)

files = os.listdir(os.getcwd() + "/Files")
print("files: ",files)
for x in files:
    wm.add_watch(os.getcwd() + "/Files/"+ x, 0x00000002, rec=False)

notifier.loop()
# token = map(str, sys.argv[1:])
# token = list(token)
# print(token)


