package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.User;

@WebServlet("/register")
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String phoneNumber = request.getParameter("phoneNumber");
		String password = request.getParameter("password");
		
		if(DatabaseConnection.getInstance().getUserForEmail(email) != null) {
			System.out.println("User already exists");
			
			// TODO: User already exists error page
		}
		
		DatabaseConnection.getInstance().registerUser(new User(name, email, phoneNumber, password));
		
		HttpSession session = request.getSession(true);
		session.setAttribute("name", name);
		session.setAttribute("email", email);
		response.sendRedirect("/ShoppingProject/home");
		
	}

}
