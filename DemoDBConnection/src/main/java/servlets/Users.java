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
			System.out.println("Exception getting DB connection " + e);
		} catch (ClassNotFoundException e1) {
			System.out.println("ClassNotFoundException " + e1);
		}
	}

	@Override
	public void destroy() {
		try {
			dbConnection.close();
		} catch (SQLException e) {
			System.out.println("SQLException " + e);
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			Statement st = dbConnection.createStatement();

			ResultSet result = st.executeQuery("SELECT * FROM users");
			StringBuffer sb = new StringBuffer("<html>\n\t<body>\n\t\t<table>\n\t\t\t<tbody>\n");
			sb.append("\t\t\t\t<tr><td>NAME</td><td>EMAIL</td><td>PHONE NUMBER</td></tr>\n");
			while(result.next()) {
				String name = result.getString("name");
				String phoneNumber = result.getString("phone_number");
				String email = result.getString("email");
				sb.append("\t\t\t\t<tr><td>" + name + "</td><td>" + email + "</td><td>" + phoneNumber + "</td></tr>\n");
			}
			sb.append("\t\t\t</tbody>\n\t\t</table>\n\t</body>\n</html>");
			response.getWriter().append(sb.toString());
			System.out.println(sb.toString());
			result.close();
			st.close();
		} catch (SQLException e) {
			System.out.println("SQLException " + e);
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
			
			pSt.close();
		} catch (SQLException e) {
			System.out.println("SQLException " + e);
		}
		
	}

	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		StringBuffer sb = new StringBuffer("UPDATE users SET ");
		String rName = request.getParameter("name");
		String rEmail = request.getParameter("email");
		String rPassword = request.getParameter("password");
		String rPhoneNumber = request.getParameter("phoneNumber");
		int id = Integer.parseInt(request.getParameter("id"));
		
		if(rName != null) {
			sb.append("name = ? WHERE id = ?");
		} else if(rEmail != null) {
			sb.append("email = ? WHERE id = ?");
		} else if(rPassword != null) {
			sb.append("password = ? WHERE id = ?");
		} else if(rPhoneNumber != null) {
			sb.append("phone_number = ? WHERE id = ?");
		}
		try {
			PreparedStatement pSt = dbConnection.prepareStatement(sb.toString());
			
			if(rName != null) {
				pSt.setString(1, rName);
			} else if(rEmail != null) {
				pSt.setString(1, rEmail);
			} else if(rPassword != null) {
				pSt.setString(1, rPassword);
			} else if(rPhoneNumber != null) {
				pSt.setLong(1, Long.parseLong(rPhoneNumber));
			}
			
			pSt.setInt(2, id);
			
			int numRows = pSt.executeUpdate();
			
			System.out.println(numRows + " row updated.");
			
			response.sendRedirect(request.getContextPath() + "/users");
			pSt.close();
		} catch (SQLException e) {
			System.out.println("SQLException " + e);
		}
	}

	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			PreparedStatement pSt = dbConnection.prepareStatement("DELETE FROM users WHERE id = ?");
			
			int id = Integer.parseInt(request.getParameter("id"));
			pSt.setInt(1, id);
			
			int numRows = pSt.executeUpdate();
			
			System.out.println(numRows + " row deleted.");
			
			response.sendRedirect(request.getContextPath() + "/users");
			pSt.close();
			
		} catch (SQLException e) {
			System.out.println("SQLException " + e);
		}
	}

}
