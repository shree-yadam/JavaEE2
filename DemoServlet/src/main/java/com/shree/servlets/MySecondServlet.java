package com.shree.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/MySecondServlet")
public class MySecondServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
     
    public MySecondServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name = request.getParameter("fullName");
		String phoneNumber = request.getParameter("phoneNumber");
		String gender = request.getParameter("gender");
		response.getOutputStream().println("Name: " + name);
		response.getOutputStream().println("Phone Number: " + phoneNumber);
		response.getOutputStream().println("Gender: " + gender);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name = request.getParameter("fullName");
		String phoneNumber = request.getParameter("phoneNumber");
		String gender = request.getParameter("gender");
		response.getOutputStream().println("Name: " + name);
		response.getOutputStream().println("Phone Number: " + phoneNumber);
		response.getOutputStream().println("Gender: " + gender);
	}

}
