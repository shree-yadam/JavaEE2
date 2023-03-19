package servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private DatabaseConnection db = null;
       
    public Login() {
        super();
        db = DatabaseConnection.getInstance();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Connection conn = db.getDbConn();
		
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		
		PreparedStatement pSt;
		try {
			pSt = conn.prepareStatement("SELECT * FROM users WHERE email = ?");

			pSt.setString(1, email);
			
			ResultSet rs = pSt.executeQuery();
			
			while(rs.next()) {
				String name = rs.getString("name");
				String storedPassword = rs.getString("password");
				
				if(password.equals(storedPassword)) {
					HttpSession session = request.getSession(true);
					session.setAttribute("name", name);
					session.setAttribute("email", email);
					
					response.sendRedirect("/ShoppingProject/home");
				} else {
					String contextRoute = request.getContextPath();
					
					StringBuffer sb = new StringBuffer("<html><body>");
					sb.append("<h2> Invalid password</h2>");
					sb.append("<h3> Please <a href=\""+ contextRoute + "/login.html\">login</a> to continue</h3>");
					sb.append("</body></html>");
					
					response.getWriter().append(sb.toString());
				}
			}
			rs.close();
			pSt.close();
		} catch (SQLException e) {
			System.out.println("SQLException " + e);
		}
		
		
	}

}
