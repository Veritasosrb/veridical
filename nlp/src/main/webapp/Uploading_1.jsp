<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Guru Uploading File</title>
</head>
<body>
File: <br />
<form action="nlp.guru_upload" method="post"
                        enctype="multipart/form-data">
<input type="file" name="guru_file" size="50" />
<br />
<input type="submit" value="Upload" />
</form>
</body>
</html>