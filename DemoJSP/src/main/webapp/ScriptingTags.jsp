<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Demo of Scripting Tags</title>
</head>
<body bgcolor="cyan">

	<h1>Scripting Tags Code ... </h1>
	
	<%-- This is a declaration --%>
	
	<%!
		int counter = 10, i = 0;
	
		private Integer sum(Integer val1, Integer val2) {
			return val1 + val2;
		}
		
		private Integer diff(Integer val1, Integer val2) {
			return val1 - val2;
		}
	%>
	
	<%-- This is JSP scriptlet --%>
	
	<%
		for(i = 0; i < counter; i++) {
			out.println("<br> Code has run " + i + " times...");
		}
	%>
	
	<h2> Code is running second time </h2>
	
	<%
		for(i = 0; i < counter; i++) {
			out.println("<br> Code has run " + i + " times...");
		}
	%>
	
	<br>
	
	<h4>Sum goes here :: </h4>
	
	The sum of 2 numbers is 
	
	<%= sum(10,20) %>
	
	<br>
	
	The diff of 2 numbers is 
	
	<%
		out.println(diff(10,20));
	%>
	<br>
	<%
		out.println("The sum of 2 numbers is " + sum(10, 20) + "<br>");
		out.println("The diff of 2 numbers is " + diff(10, 20));
	%>
	
	<br>
	
	This is JSP expression :: <%= sum(100, 200) %>

</body>
</html>