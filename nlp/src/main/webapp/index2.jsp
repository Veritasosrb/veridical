<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<jsp:useBean id="stannlp" class="nlp.CreateNlpPipeline" scope="session"/>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<h1>Welcome !!!</h1>
<form id="myform" name="myform" method="post" action=Basic.jsp>
   User Story : <input type="text"  name="user" />
   Functionality Name : <input type="text"   name="password" />    
    <input type="submit" value="go" onclick="Basic.jsp" />
    </form>

</body>
</html>





