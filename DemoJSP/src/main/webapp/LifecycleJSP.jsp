<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Lifecycle</title>
</head>
<body>
	<header>JSP Lifecycle</header>

	<%!
		int counter = 0;
	
		String userName = "";
		
		public void jspInit() {
			System.out.println("jspInit() is called ....");
		}
		
		public void jspDestroy() {
			System.out.println("jspDestroy() is called ....");
		}
		
		private void modifyCounter() {
			counter += 100;
		}
	
	%>
	
	<p>Hello how are you my friend.. You have visited this page :: </p>
	
	<%
		int localVariable = 0;
	
		counter++;
		
		if(counter%5 == 0) {
			modifyCounter();
		}
	%>
	
	<%= counter %>
	
	<p>times</p>
	
	<%
		counter++;
	%>
	
	<p>Displayed again</p>
	
	<%= counter %>
</body>
</html>