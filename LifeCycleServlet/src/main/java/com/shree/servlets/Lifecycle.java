package com.shree.servlets;

import java.io.IOException;
import java.time.LocalDateTime;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Lifecycle")
public class Lifecycle extends HttpServlet {
	private static final long serialVersionUID = 1L;
      
    public Lifecycle() {
        super();
		System.out.println("Lifecycle constructor called .. " + LocalDateTime.now());
    }

	public void init(ServletConfig config) throws ServletException {
		System.out.println("Lifecycle init called .. " + LocalDateTime.now());
	}

	public void destroy() {

		System.out.println("Lifecycle destroy called .. " + LocalDateTime.now());
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		System.out.println("Lifecycle GET called .. " + LocalDateTime.now());
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Lifecycle POST called .. " + LocalDateTime.now());
		doGet(request, response);
	}

}
