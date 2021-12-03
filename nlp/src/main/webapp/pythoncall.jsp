<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="java.io.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<%



try{
 

String number1="CORTEX-330";
 
ProcessBuilder pb = new ProcessBuilder("python","trial2.py",number1);
Process p = pb.start();
 
BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
String ret = in.readLine();
System.out.println("value is : "+ret);
}catch(Exception e){System.out.println(e);}


%>
</body>
</html>