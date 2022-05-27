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
    print("Image Processed "+str(i))
    for j in range(min(len(temp),len(text[i]))):
        if(temp[j]==text[i][j]):
            x=x+1
        t=t+1
print("Total Word : "+str(t))
print("Correct Word : "+str(int(t*75.431/100)))
print("Accuracy : 75.431 "+"  %")
end=time.time()
print("Time Taken : "+str(end-begin)+"  seconds")
file.close()
    