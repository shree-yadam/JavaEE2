package com.shree.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/SendRedirectServlet")
public class SendRedirectServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public SendRedirectServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String websiteName = request.getParameter("websiteName");
		
		if(websiteName.isEmpty()) {
			websiteName = "http://www.google.com";
		} else {
			//websiteName = "http://www." + websiteName + ".com";
			websiteName = new StringBuffer("http://www.").append(websiteName).append(".com").toString();
		} 
	
		
		response.sendRedirect(websiteName);
	}

}
