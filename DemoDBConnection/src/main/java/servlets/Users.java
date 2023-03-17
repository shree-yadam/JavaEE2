package servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/users")
public class Users extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private Connection dbConnection = null;
  
    public Users() {
        super();
    }

	@Override
	public void init() throws ServletException {
		// Initialize all the information regarding
        // Database Connection
		StringBuffer connectionURL = new StringBuffer(Optional.ofNullable(System.getenv("CONNECTION_URL")).orElse(""));
        String dbDriver = Optional.ofNullable(System.getenv("DB_DRIVER")).orElse("");
        String dbURL = Optional.ofNullable(System.getenv("DB_URL")).orElse("");
        // Database name to access
        String dbName = Optional.ofNullable(System.getenv("DEMO_DB_NAME")).orElse("");;
        String dbUsername = Optional.ofNullable(System.getenv("DB_USER")).orElse("");;
        String dbPassword = Optional.ofNullable(System.getenv("DB_PASSWORD")).orElse("");;
        
        connectionURL.append(dbURL);
        connectionURL.append("/");
        connectionURL.append(dbName);
        
        try {
        	Class.forName(dbDriver);
        	
			dbConnection = DriverManager.getConnection(connectionURL.toString(), dbUsername, dbPassword);
		} catch (SQLException e) {
			System.out.println("Exception getting DB connection");
		} catch (ClassNotFoundException e1) {
			System.out.println("ClassNotFoundException");
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			Statement st = dbConnection.createStatement();

			ResultSet result = st.executeQuery("SELECT * FROM users");
			response.getWriter().append("<html><body><table><tbody>");
			response.getWriter().append("<tr><td>NAME</td><td>EMAIL</td><td>PHONE NUMBER</td></tr>");
			while(result.next()) {
				String name = result.getString("name");
				String phoneNumber = result.getString("phone_number");
				String email = result.getString("email");
				response.getWriter().append("<tr><td>" + name + "</td><td>" + email + "</td><td>" + phoneNumber + "</td></tr>");
			}
			response.getWriter().append("</tbody></table></body></html>");
			result.close();
			st.close();
		} catch (SQLException e) {
			System.out.println("SQLException");
		}
		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			PreparedStatement pSt = dbConnection.prepareStatement("INSERT INTO users (name, password, email, phone_number) VALUES (?, ?, ?, ?)");
			
			String name = request.getParameter("name");
			String password = request.getParameter("password");
			String email = request.getParameter("email");
			String phoneNumber = request.getParameter("phoneNumber");
			
			pSt.setString(1, name);
			pSt.setString(2, password);
			pSt.setString(3, email);
			pSt.setString(4, phoneNumber);
			
			int numRows = pSt.executeUpdate();
			
			System.out.println(numRows + " row added.");
			
			response.sendRedirect(request.getContextPath() + "/users");
		} catch (SQLException e) {
			System.out.println("SQLException");
		}
		
	}

	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
