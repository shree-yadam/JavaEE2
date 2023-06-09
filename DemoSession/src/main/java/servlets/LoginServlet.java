package servlets;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/Login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String COOKIE_NAME = "user-id";
	private static final HashMap<String, String> userMap =new HashMap<>();
     
    public LoginServlet() {
        super();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		HttpSession session = request.getSession(true);
		session.setAttribute(COOKIE_NAME, email);
		userMap.put(email, password);
		response.getWriter().println(email + " logged in !!!");
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession(false);
		if(session == null ) {
			resp.getWriter().println("No user logged in");
			return;
		}
		String email = (String) session.getAttribute(COOKIE_NAME);
		resp.getWriter().println("Login details received...");
		resp.getWriter().println("email: " + email);
		resp.getWriter().println("password: " + userMap.get(email));
		resp.getWriter().println("Logging out ...");
		session.invalidate();
	}

}
