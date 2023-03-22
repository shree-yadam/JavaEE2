package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.User;

@WebServlet("/login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		
		User user = DatabaseConnection.getInstance().getUserForEmail(email);
		
		if(user != null && password.equals(user.getPassword())) {
			HttpSession session = request.getSession(true);
			session.setAttribute("name", user.getName());
			session.setAttribute("email", email);
			
			response.sendRedirect("/ShoppingProject/home");
		} else {
			String contextRoute = request.getContextPath();
			
			StringBuffer sb = new StringBuffer("<html><body>");
			sb.append("<h2> Invalid email or password</h2>");
			sb.append("<h3> Please <a href=\""+ contextRoute + "/login.html\">login</a> to continue</h3>");
			sb.append("</body></html>");
			
			response.getWriter().append(sb.toString());
		}
	}

}
