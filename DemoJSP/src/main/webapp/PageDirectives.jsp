<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Page Directives</title>
</head>
<body>

	<%@ page import="java.time.LocalDateTime" %>
	<%@ page errorPage="ErrorPage.jsp" %>
	
	<h2>Example of import directive</h2>
	<p> The current Date and Time is </p>
	<%= LocalDateTime.now() %>

	<p>Example of isErrorPageDirective </p>
	
	<%
		String number = "40";
		out.println("Number parsed is :: " + Integer.parseInt(number));
	%>
</body>
</html>