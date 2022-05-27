import views
import time
file = open('.\Testing\output.txt','r')
text=file.read()
text = text.split("\n@#@\n")
x=0
t=0
begin=time.time()
for i in range(70):
    temp = views.ocrs('./Testing/'+str(i+1)+'.jpg')
    print(i)
    for j in range(min(len(temp),len(text[i]))):
        if(temp[j]==text[i][j]):
            x=x+1
        t=t+1
print(x)
print(t)
print(str(x/t*100)+"  %")
end=time.time()
print(str(end-begin)+"  seconds")
file.close()
    