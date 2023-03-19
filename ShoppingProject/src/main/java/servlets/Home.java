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
		

		StringBuffer sb = new StringBuffer("<html>\n\t<body>\n\t\t\n");
		
		if(session != null) {
			String name = (String) session.getAttribute("name");
			sb.append("\t\t\t<h3>Welcome " + name +"!</h3>\n");
			sb.append("<p><a href=\""+ contextRoute + "/items\">Shop</a></p>\n");
			sb.append("<p><a href=\""+ contextRoute + "/orders\">Order History</a></p>\n");
			sb.append("</br></br><p><a href=\""+ contextRoute + "/logout\">Logout</a></p>\n");
			sb.append("\t</body>\n</html>\n");
		} else {
			sb.append("\t\t\t<h3> Please <a href=\""+ contextRoute + "/login.html\">login</a> to continue</h3>\n");
		}

		response.getWriter().append(sb.toString());
	}

}
