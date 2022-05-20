'''
Importing the necessary libraries
'''
import xml.etree.ElementTree as ET
import glob
import json
import sys

answer = {}
dependencyGraph = {}
dependentMethods = []

'''
Function to run a DFS and find all the dependent methods for a method
'''
def dfs(method, answer):
	global dependentMethods
	dependentMethods.append(method)
	if method not in answer:
		return
	for m in answer[method]:
		dfs(m, answer)
		
'''
Function to parse a single call graph to extract the graph structure
'''
def parser(filename):
    tree = ET.parse(filename)

    root = tree.getroot()
    childGraph = root[0]
    nodeMap = {}
    edgeMap = {}

    for child in childGraph:
        if "class" in child.attrib.keys():
            if(child.attrib["class"] == "node"):
                length = len(child[1][0])
                t = ""
                for i in range(1, length):
                    t = t+child[1][0][i].text
                nodeMap[child[0].text] = t
            if(child.attrib["class"] == "edge"):
                temp = child[0].text.split("->")
                if temp[1] in edgeMap.keys():
                    if temp[0] not in edgeMap[temp[1]]:
                        edgeMap[temp[1]].append(temp[0])
                else:
                    edgeMap.setdefault(temp[1], []).append(temp[0])

    for edge in edgeMap:
        for x in edgeMap[edge]:
            if nodeMap[edge] in answer.keys():
                if nodeMap[x] not in answer[nodeMap[edge]]:
                    answer[nodeMap[edge]].append(nodeMap[x])
            else:
                answer.setdefault(nodeMap[edge], []).append(nodeMap[x])


for filename in glob.iglob(sys.argv[1] + '**/**', recursive=True):
    if filename.endswith('_cgraph.svg') or filename.endswith('_cgraph_org.svg'):
        parser(filename)


for methods in answer:
	dependentMethods = []
	for method in answer[methods]:
		dfs(method, answer)
	dependencyGraph[methods] = list(set(dependentMethods))
	
print(dependencyGraph)
print("Function dependency: ")
print(json.dumps(dependencyGraph, indent=4))

with open(sys.argv[2], 'w', encoding='utf-8') as f:
    json.dump(dependencyGraph, f, ensure_ascii=False, indent=4)
