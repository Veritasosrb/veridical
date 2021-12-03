<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<jsp:useBean id="country" class="nlp.sample" scope="request" />
<jsp:setProperty name="country" property="*" />

<html>
<head><title>add2</title></head>
<body>



<jsp:setProperty name="country" property="countryName" />
<b>Country Name: </b><%= request.getParameter("countryName") %><br>

<jsp:setProperty name="country" property="countryPopulation" />
<b>Country Population: </b><%= request.getParameter("countryPopulation") %>





</body>
</html>
