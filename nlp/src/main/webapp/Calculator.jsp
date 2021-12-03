<%@page import="nlp.Calculator"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
 pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

    <% nlp.Calculator calculator= new nlp.Calculator(); %>

    Addition of 20 + 10 =
    <%= calculator.addition(20, 10) %>

    <br><br> Subtraction of 20 - 10 =
    <%= calculator.substraction(20, 10) %>

    <br><br> Multiplication of 20 * 10 =
    <%= calculator.multiplication(20, 10) %>

    <br><br> Division of 20/10 =
    <%= calculator.division(20, 10) %>

</body>
</html>