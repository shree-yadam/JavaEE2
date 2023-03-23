package servlets;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import model.Item;
import model.User;

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
	
	public User getUserForEmail(String email) {
		PreparedStatement pSt;
		User user = null;
		try {
			pSt = dbConn.prepareStatement("SELECT * FROM users WHERE email = ?");
			pSt.setString(1, email);
			
			ResultSet rs = pSt.executeQuery();
			
			while(rs.next()) {
				user = new User(rs.getString("name"), email, rs.getString("phone_number"), rs.getString("password"));
				
			}
			rs.close();
			pSt.close();
		} catch (SQLException e) {
			System.out.println("getUserForEmail : SQLException " + e);
		}

		
		return user;
	}
	
	synchronized public void registerUser(User user) {
		 
		try {
			PreparedStatement pSt = dbConn.prepareStatement("INSERT into users (name, password, phone_number, email) VALUES (?, ? , ?, ?)");
			pSt.setString(1, user.getName());
			pSt.setString(2, user.getPassword());
			pSt.setString(3, user.getPhoneNumber());
			pSt.setString(4, user.getEmail());
			
			int numRows = pSt.executeUpdate();
			
			System.out.println(numRows + "users registered.");
			pSt.close();
		} catch (SQLException e) {
			System.out.println("registerUser : SQLException " + e);
			// TODO how to respond with error
		}
		
		
	}
	
	public List<Item> getAllItems() {
		Statement st;
		
		List<Item> result = new ArrayList<>();
		try {
			st = dbConn.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM items");
			while (rs.next()) {
				Integer id = rs.getInt("id");
				String name = rs.getString("name");
				Double price = ((double) rs.getInt("price")) / 100;
				String description = rs.getString("description");
				String imgURL = rs.getString("img_url");
				Integer quantity = rs.getInt("quantity");
				
				Item item = new Item();
				item.setId(id);
				item.setName(name);
				item.setPrice(price);
				item.setQuantity(quantity);
				item.setDescription(description);
				item.setImgURL(imgURL);
				
				result.add(item);
			}

			rs.close();
			st.close();
		} catch (SQLException e) {
			System.out.println("getAllItems : SQLException " + e);
			// TODO how to respond with error
		}
		
		return result;

	}

}
