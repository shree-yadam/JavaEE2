package servlets;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/register")
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String COOKIE_NAME = "user-id";
	private static final HashMap<String, UserInfo> userMap =new HashMap<>();

    public Register() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if(session == null ) {
			response.getWriter().println("No user logged in");
			return;
		}
		String email = (String) session.getAttribute(COOKIE_NAME);
		response.getWriter().println("Login details received...");
		response.getWriter().println(userMap.get(email));
		response.getWriter().println("Logging out ...");
		session.invalidate();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String fullName = request.getParameter("fullName");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String phoneNumber = request.getParameter("phoneNumber");
		UserInfo userInfo = new UserInfo(fullName, phoneNumber, email, password);
		HttpSession session = request.getSession(true);
		session.setAttribute(COOKIE_NAME, email);
		userMap.put(email, userInfo);
		response.getWriter().println(fullName + " logged in !!!");
	}

}
