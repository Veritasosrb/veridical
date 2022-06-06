import os
import sys
import pydriller
import csv
from pydriller import Repository

n = len(sys.argv)
print("Total arguments passed:", n)

filename= "commit_details_csv.csv"
path_to_repo=sys.argv[1]
path_to_html=sys.argv[2]

csvfile=open(filename,'w')
csvwriter=csv.writer(csvfile)
Fields=["commitID","AuthorName","CommitterName","FileName","OldPath","NewPath","Type","Functions","Lines added","Lines deleted","Dependent Functions","Files containing dependent functions"]
csvwriter.writerow(Fields)
for commit in Repository(path_to_repo).traverse_commits():
    #if commit.hash == "866e997a9e44cb4ddd9e00efe49361420aff2559":
        row=[]
        row.extend([commit.hash,commit.author.name,commit.committer.name])

        for mod in commit.modified_files:
            row.extend([mod.filename,mod.old_path,mod.new_path,mod.change_type.name])

            mod_func=""

            for f in mod.changed_methods:
                mod_func+=f.name+"\n"

            row.append(mod_func)
            lines_added=""
            lines_deleted=""

            for lines in mod.diff_parsed['added']:
                lines_added+=lines[1]+"\n"
            for lines in mod.diff_parsed['deleted']:
                lines_deleted+=lines[1]+"\n"

            row.append(lines_added)
            row.append(lines_deleted)

            file_name=mod.filename

            for f in mod.changed_methods:
                function_name=f.name
            folder_name=path_to_repo +"\\"+ mod.new_path
            print(folder_name)
            js_file_to_open=""
            dot_file_folder=""

            for file in os.listdir(path_to_html):
                if file.endswith(".xhtml") and os.path.isfile(os.path.join(path_to_html,file)):
                    with open(os.path.join(path_to_html,file)) as f:
                        if folder_name in f.readlines()[7]:
                            js_file_to_open=file.rsplit(".",1)[0]+".js"
                        print(js_file_to_open)
                        print(f.readlines())
                else:
                    continue

            with open(os.path.join(path_to_html,js_file_to_open)) as js_file:
                for i,line in enumerate(js_file):
                    if file_name in line:
                        dot_file_folder=line.split(",")[1].split('"')[1].rsplit("/",1)[0]
                        print(dot_file_folder)

            dot_files=[]

            folder_to_look=os.path.join(path_to_html,dot_file_folder)

            for file in os.listdir(folder_to_look):
                if file.endswith(".dot"):
                    dot_files.append(file)
            print("\n\n\n",dot_files)
            
            lines_in_file=[]

            for file in dot_files:
                if os.path.isfile(os.path.join(folder_to_look,file)):
                    file_to_open=os.path.join(folder_to_look,file)
                    f=open(file_to_open)

                    f.seek(8)

                    if f.read(len(function_name)+2) == "\""+function_name+"\"" and file_to_open.endswith("_icgraph.dot"):
                        for pos,line in enumerate(f):
                            if pos > 6:
                                lines_in_file.append(line.rstrip('\n'))

            if lines_in_file:

                print("\n\n\n",lines_in_file)
                lines_in_file.remove('}')

                list_of_functions_in_file=[]

                list_of_dependencies_in_file=[]

                for lines in lines_in_file:
                    if lines.split('"')[1] == 'back' and lines.split('"')[0].endswith("dir="):
                        list_of_dependencies_in_file.append(lines)
                    else:
                        list_of_functions_in_file.append(lines)

                function_map={}

                function_map['Node1']=[function_name,file_name]
                table_form=[]

                for i in range(1,len(list_of_functions_in_file)):
                    temp_list=list_of_functions_in_file[i].split(",")

                    Node_number=temp_list[0].split()[0]
                    func_name=temp_list[0].split('"')[1].replace("\l","")
                    file_belong=temp_list[6].split("$")[1].split(".")[0].rsplit("/",1)[1].replace("_8",".").replace("_2","/")

                    function_map[Node_number]=[func_name,file_belong]
                Dependent_Functions=""
                Files_Containing_Dependent_Functions=""
                for key in function_map:
                    if key != "Node1":
                        Dependent_Functions+=function_map[key][0]+"\n"
                        Files_Containing_Dependent_Functions+=function_map[key][1]+"\n"

                row.extend([Dependent_Functions,Files_Containing_Dependent_Functions])
            csvwriter.writerow(row)
            row=[' ',' ',' ']
            


