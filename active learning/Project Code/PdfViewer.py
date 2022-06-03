from tkinter import *
from tkinter import filedialog
from tkPDFViewer import tkPDFViewer
import os
from fpdf import FPDF

class PdfViewer:
    def __init__(self, filepath, filename):
        self.filePath=filepath
        root=Tk()
        root.geometry("630x700+400+100")
        root.title(filename)
        root.configure(bg="white")
        Button(root, text="open", command=self.convertFile(filename, root), width=40, font="arial 20", bd=4).pack()
        root.mainloop()
        self.removeFile()

    # convert file to pdf
    def convertFile(self, fileName, root):

        pdf = FPDF()
        pdf.add_page()

        pdf.set_font("Arial", size=15)
        f = open(self.filePath, "r")

        for x in f:
            pdf.cell(200, 10, txt=x, ln=1, align='L')

        fileName+=".pdf"
        self.filePath = os.path.split(self.filePath)
        self.filePath=self.filePath[0]
        self.filePath+="/"+fileName
        pdf.output(self.filePath)

        self.browseFiles(root)

    # open file in viewer
    def browseFiles(self, root):
        # filename="C:\Users\BAVISKAR\Desktop\41413_Mini_Project_AIR_Report.pdf"

        v1=tkPDFViewer.ShowPdf()
        ### clear the image list ###
        v1.img_object_li.clear()
        v2=v1.pdf_view(root, pdf_location=open(str(self.filePath), "r"), width=77, height=100)
        v2.pack(pady=(0,0))

    # delete file after closing
    def removeFile(self):
        if os.path.exists(self.filePath):
            os.remove(self.filePath)