<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html><head>
<title>Check Boxes Example in JSP</title>
</head>
<body>
<br><br><br>
<%
String[] paramNames = request.getParameterValues("directinput");
if(paramNames!=null)
{
	out.println(paramNames[0]);

if(paramNames[0].equals("String input manually."))
{
	response.sendRedirect("index2.jsp");
}
else
{
	response.sendRedirect("pythoncall.jsp");
}
}
%>
</p>
</body></html>