import os
import csv
from cryptography.fernet import Fernet

import pandas as pd 
import shutil
location=["D:\\NODE1" , "D:\\NODE2" , "D:\\NODE3"]
with open('Table.csv', 'r') as read_obj:
    csv_reader = csv.reader(read_obj)
    list_of_csv = list(csv_reader)
    print(len(list_of_csv))
print(list_of_csv)
for i in range(0, len(list_of_csv)):
    if(i%2 == 0):
        print("i :: {} ".format(i))
        curr_node_num = int(list_of_csv[i][2])
        print(curr_node_num)
        # print(curr_node_num + type(curr_node_num))
        # print(curr_node_num)
        pa = location[curr_node_num]
        file_path = list_of_csv[i][0].split('\\')
        print(file_path)
        # rev_list = file_path.split('\\').reverse()
        file_name = file_path[2] #TODO this needs to be generalised
        
 
        key = list_of_csv[i][1]
        fernet = Fernet(key)
        print(file_name)
        print("{}\\{}".format(pa,file_name))
        with open('{}\{}'.format(pa,file_name), 'rb') as file:
            original = file.read()
        decrypted = fernet.decrypt(original)
        with open('{}\{}'.format(pa,file_name), 'wb') as decrypted_file:
            decrypted_file.write(decrypted)
        shutil.move('{}\{}'.format(pa,file_name),'D:\Files') 
        print("Succesfully decrypted {}".format(file_name))
        print(i)
