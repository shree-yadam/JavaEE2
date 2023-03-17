package servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
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
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
