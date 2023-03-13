package com.shree.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/CallValidationServlet")
public class CallValidationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
 
    public CallValidationServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String name = request.getParameter("fullName");
		String phoneNumber = request.getParameter("phoneNumber");
		String gender = request.getParameter("gender");
		
		//Get dispatcher
		RequestDispatcher rd = request.getRequestDispatcher("/ValidationServlet");
		
		//Include the request
		rd.include(request, response);
		
		response.getWriter().print("<h1> My Details </h1>");		
		response.getWriter().print("<h2 style=\"color:blue ; font-family: serif; font-style: italic\"> " + name
				+ " === " + phoneNumber + " ==== " + gender + "</h2>");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
