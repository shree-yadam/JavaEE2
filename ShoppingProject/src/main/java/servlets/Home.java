package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Home
 */
@WebServlet("/home")
public class Home extends HttpServlet {
	private static final long serialVersionUID = 1L;
 
    public Home() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		

		String contextRoute = request.getContextPath();
		

		StringBuffer sb = new StringBuffer("<html><body>");
		
		if(session != null) {
			String name = (String) session.getAttribute("name");
			sb.append("<h3>Welcome " + name +"!</h3>");
			sb.append("<p><a href=\""+ contextRoute + "/items\">Shop</a></p>");
			sb.append("<p><a href=\""+ contextRoute + "/orders\">Order History</a></p>");
			sb.append("</br></br><form action=\"" + contextRoute + "/logout\" method=\"post\"><button type=\"submit\">Logout</button></form>");
			
		} else {
			sb.append("<h3> Please <a href=\""+ contextRoute + "/login.html\">login</a> to continue</h3>");
		}
		
		sb.append("</body></html>");

		response.getWriter().append(sb.toString());
	}

}
