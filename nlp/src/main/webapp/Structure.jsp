
<%@page import="java.io.*" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
 pageEncoding="ISO-8859-1"%>


  
   
<!DOCTYPE html>
<jsp:useBean id="stannlp" class="nlp.CreateNlpPipeline" scope="session"/> 

<html>
<head>
<meta charset="ISO-8859-1">

<title>Insert title here</title>
</head>
<body>

   
<jsp:setProperty name="stannlp" property="user" />
<b>User Story: </b><%= request.getParameter("user") %><br>
<jsp:setProperty name="stannlp" property="password" />
<b>Functionality Name: </b><%= request.getParameter("password") %><br>



<%

String str = request.getParameter("user");

String str1=request.getParameter("password");

//always give the path from root. This way it almost always works.
String nameOfTextFile = "C:\\Users\\aditi.inamdar\\eclipse-workspace\\nlp\\input\\cortex_"+str1+"_input.txt";
try { 
	
	
    PrintWriter pw = new PrintWriter(new FileOutputStream(nameOfTextFile));
    pw.println(str);
    //clean up
    pw.close();
   	
   
    
} catch(IOException e) {
   out.println(e.getMessage());
}






%>
<%
com.file.Structure structure=new com.file.Structure();
   	int k=structure.struct();%>





    



   
</body>
</html>

    