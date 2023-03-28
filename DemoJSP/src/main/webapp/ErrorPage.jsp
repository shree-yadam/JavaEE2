
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isErrorPage="true"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Error Page</title>
</head>
<body>
<h2>This is error page</h2>

<%
	exception.printStackTrace(new java.io.PrintWriter(out));
%>
</body>
</html>