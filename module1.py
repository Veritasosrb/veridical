# For extracting the docstring from the user story

import re
import os
import json


def process_data(ip):
    # print(ip)

    if not ip:
        return None
    ip = ip.lower()
    data_entities = []


    subject_extractor = re.compile(r'as (an|a|the) [ a-zA-Z0-9_]+')
    reason_extractor = re.compile(r'so that [ a-zA-Z0-9_]+')
    subject = subject_extractor.search(ip)

    if subject:
        idxrangesub = subject.span()
        subject = subject[0] 
        data_entities.append((idxrangesub[0],idxrangesub[1], "US_SUB"))
        
    reason = reason_extractor.search(ip)
    if reason:
        idxrangereas = reason.span()
        reason = reason[0]
        data_entities.append((idxrangereas[0],idxrangereas[1], "US_REAS"))

    if 'idxrangereas' not in locals():
        idxrangereas = (len(ip),0)
    data_entities.append((idxrangesub[1]+1,idxrangereas[0]-1, "US_FUNC"))
        
    function = ip.replace(subject, "")
    if reason:
        function = function.replace(reason,"")
    function = re.sub(r"^(, )","",function)
    function = re.sub(r"(, \.)","",function)
    function = function.replace("i want to","")

    #Debugging
    # print(subject) 
    # print(function)
    # print(reason)

    if reason:
        reason = reason.replace('so that', "")

    # data_row = (ip,{'entities': data_entities})
    data_row = {'subject': subject, 'function': function, 'reason': reason}

    return(data_row)


def run(user_story_block):
    data_row = process_data(user_story_block['story'])
    return {'docstring': data_row['function']}
