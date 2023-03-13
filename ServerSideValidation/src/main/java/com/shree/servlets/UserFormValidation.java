package com.shree.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/UserFormValidation")
public class UserFormValidation extends HttpServlet {
	private static final long serialVersionUID = 1L;
     
    public UserFormValidation() {
        super();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name = request.getParameter("fullName");
		String phoneNumber = request.getParameter("phoneNumber");
		String gender = request.getParameter("gender");
		
		if(name.isEmpty() || phoneNumber.length() < 10) {
			response.getWriter().append("<h1>Data you entered is not correct... </br></h1>");
		} else {
			response.getWriter().append("<h1>Data is fine... </br></br></h1>");
			response.getWriter().append("Welcome " + name);
		}
	}

}
