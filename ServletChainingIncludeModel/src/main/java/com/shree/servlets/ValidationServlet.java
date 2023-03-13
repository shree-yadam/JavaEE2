package com.shree.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/ValidationServlet")
public class ValidationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public ValidationServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name = request.getParameter("fullName");
		String phoneNumber = request.getParameter("phoneNumber");
		
		if(name.isEmpty() || phoneNumber.length() < 10) {
			request.getRequestDispatcher("Error.html").forward(request, response);
		} else {
			response.getWriter().append("<h1>Data is fine... </br></br></h1>");
			response.getWriter().append("Welcome " + name);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
