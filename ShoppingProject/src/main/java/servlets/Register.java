package servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/register")
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private DatabaseConnection db = null;
       
    public Register() {
        super();
        db = DatabaseConnection.getInstance();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String phoneNumber = request.getParameter("phoneNumber");
		String password = request.getParameter("password");
		
		Connection conn = db.getDbConn();
		
		try {
			PreparedStatement pSt = conn.prepareStatement("INSERT into users (name, password, phone_number, email) VALUES (?, ? , ?, ?)");
			
			pSt.setString(1, name);
			pSt.setString(2, password);
			pSt.setString(3, phoneNumber);
			pSt.setString(4, email);
			
			int numRows = pSt.executeUpdate();
			
			System.out.println(numRows + "users registered.");
			
			HttpSession session = request.getSession(true);
			session.setAttribute("name", name);
			session.setAttribute("email", email);
			
			pSt.close();
			response.sendRedirect("/ShoppingProject/home");
		} catch (SQLException e) {
			System.out.println("SQLException " + e);
			response.sendError(500, "Error registering!");
		}
		
	}

}
