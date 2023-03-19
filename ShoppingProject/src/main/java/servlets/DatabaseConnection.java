package servlets;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Optional;

public class DatabaseConnection {
	
	private static DatabaseConnection db = null;
	
	private Connection dbConn = null;
	
	private DatabaseConnection() {
		// Initialize all the information regarding
        // Database Connection
		StringBuffer connectionURL = new StringBuffer(Optional.ofNullable(System.getenv("CONNECTION_URL")).orElse(""));
        String dbDriver = Optional.ofNullable(System.getenv("DB_DRIVER")).orElse("");
        String dbURL = Optional.ofNullable(System.getenv("DB_URL")).orElse("");
        // Database name to access
        String dbName = Optional.ofNullable(System.getenv("SHOPPING_DB_NAME")).orElse("");;
        String dbUsername = Optional.ofNullable(System.getenv("DB_USER")).orElse("");;
        String dbPassword = Optional.ofNullable(System.getenv("DB_PASSWORD")).orElse("");;
        
        connectionURL.append(dbURL);
        connectionURL.append("/");
        connectionURL.append(dbName);
        
        try {
        	Class.forName(dbDriver);
        	
			dbConn = DriverManager.getConnection(connectionURL.toString(), dbUsername, dbPassword);
		} catch (SQLException e) {
			System.out.println("Exception getting DB connection " + e);
		} catch (ClassNotFoundException e1) {
			System.out.println("ClassNotFoundException " + e1);
		}
		
        System.out.println("DB Connection created successfully");
	}
	
	@Override
	public void finalize() throws Throwable {
		dbConn.close();
		System.out.println("DB Connection closed successfully");
	}

	public static DatabaseConnection getInstance() {
		
		if(null == db) {
			synchronized(DatabaseConnection.class) {
				if(null == db) {
					db = new DatabaseConnection();
				}
			}
		}
		
		return db;
	}

	public final Connection getDbConn() {
		return dbConn;
	}

}