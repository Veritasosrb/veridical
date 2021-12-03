<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<title>Check Boxes Example in JSP</title>
</head>
<body>
<form action="displayHobbies.jsp" method="get">
<p align="left" style="font-weight: bolder;">Choose You Hobbies :
</p>
<table border="0">
<tr>
<td><input type="checkbox" name="internetSurf"
value="I am fond of Internet Surfing." /></td>
<td>I am fond of Internet Surfing.</td>
</tr>
<tr>
<td><input type="checkbox" name="readBooks"
value="I am fond of reading books." /></td>
<td>I am fond of reading books.</td>
</tr>
<tr>
<td><input type="checkbox" name="playTennis"
value="I am fond of Plaing Tennis." /></td>
<td>I am fond of Plaing Tennis.</td>
</tr>
<tr>
<td><input type="checkbox" name="driving"
value="I am fond of Driving." /></td>
<td>I am fond of Driving.</td>
</tr>
</table>
<input type="submit" value="Show My Hobbies" />
</form>
</body>
</html>