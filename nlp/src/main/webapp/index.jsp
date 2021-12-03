<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<title>Testcases generation system</title>
</head>
<body>
<h2>Pick the option to select your input format</h2>

<form action="displayHobbies.jsp" method="get">
<p align="left" style="font-weight: bolder;">Choose the input format:
</p>
<table border="0">
<tr>
<td><input type="checkbox" name="directinput"
value="String input manually." /></td>
<td>String input manually.</td>
</tr>
<tr>
<td><input type="checkbox" name="directinput"
value="Fetch story from Jira." /></td>
<td>Fetch story from Jira.</td>
</tr>
</table>
<input type="submit" value="Select my option" />


</form>
</p>
</body>
</html>