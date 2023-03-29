<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Include Directive Demo</title>
</head>
<body>

	Hello World!
	
	<%@ include file="IncludeDirectivePart.jsp" %>
	
	<h3>Something in between</h3>
	
	<%@ include file="IncludeDirectiveFooter.jsp" %>
</body>
</html>