package servlets;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Map.Entry;

import model.Item;
import model.ItemDetails;
import model.OrderDetails;
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
	
	/**
	 * Get user by email
	 * @param email
	 * @return
	 */
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
	
	/**
	 * Add user to database
	 * @param user
	 */
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
	
	/**
	 * Get all the items available
	 * @return
	 */
	public List<Item> getAllItems() {
		Statement st;
		
		List<Item> result = new ArrayList<>();
		try {
			st = dbConn.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM items WHERE quantity > 0");
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
	
	/**
	 * Add a new order to order table
	 * @param totalValue
	 * @param userId
	 * @return
	 */
	synchronized public int createOrder(int totalValue, int userId) {
		int orderId = -1;
		try {
			
			String insertOrder = "INSERT INTO orders (user_id, total) VALUES (?, ?)";
			PreparedStatement pSt = dbConn.prepareStatement(insertOrder, Statement.RETURN_GENERATED_KEYS);
			
			pSt.setInt(1, userId);
			pSt.setInt(2, totalValue);
			
			pSt.execute();
			
			ResultSet rs = pSt.getGeneratedKeys();
			
			if (rs.next()) {
				orderId = rs.getInt(1);
			}
			
			rs.close();
			pSt.close();
		} catch (SQLException e) {
			System.out.println("SQLException " + e);
		}
		
		return orderId;
	}
	
	/**
	 * Add entry to order_item table when creating an order
	 * @param itemsMap
	 * @param orderId
	 */
	synchronized public void createOrderItemEntries(Map<String, String[]> itemsMap, int orderId) {
		String orderItems = "INSERT INTO order_items (order_id, item_id, quantity) VALUES (?, ?, ?)";


		for(Entry<String, String[]> itemsEntry: itemsMap.entrySet()) {
			if(Integer.parseInt(itemsEntry.getValue()[0]) > 0) {
		
				try {
					PreparedStatement pSt = dbConn.prepareStatement(orderItems.toString());
					pSt.setInt(1, orderId);
					pSt.setInt(2, Integer.parseInt(itemsEntry.getKey()));
					pSt.setInt(3,Integer.parseInt(itemsEntry.getValue()[0]));
					
					int numRows = pSt.executeUpdate();
					
					
					System.out.println("Inserted " + numRows + " order items.");
					
					pSt.close();
				} catch (SQLException e) {
					System.out.println("SQLException " + e);
				}
			}
		}
	}
	
	/**
	 * Get user ID by email
	 * @param email
	 * @return user ID
	 */
	public int getUserIDForEmail(String email) {
		int userId = -1;
		try {
			PreparedStatement pSt = dbConn.prepareStatement("SELECT id FROM users WHERE email = ?");
			
			pSt.setString(1, email);
			
			ResultSet rs = pSt.executeQuery();
			
			if(rs.next()) {
				userId = rs.getInt("id");
			}
			rs.close();
			pSt.close();
		} catch (SQLException e) {
			System.out.println("SQLException " + e);
		}
		return userId;
	}
	
	/**
	 * Get price of an item by item ID
	 * @param item_id
	 * @return price in cents
	 */
	public Integer getPriceForItem(String item_id) {
		Integer price = 0;
		PreparedStatement pSt;
		try {
			pSt = dbConn.prepareStatement("SELECT price FROM items WHERE id = ?");
			pSt.setString(1, item_id);
			
			ResultSet rs = pSt.executeQuery();
			
			if(rs.next()) {
				 price = rs.getInt("price");
			}
			
			rs.close();
			pSt.close();
		} catch (SQLException e) {
			System.out.println("SQLException " + e);
		}
		return price;
	}
	
	/**
	 * Get all the order for a particular user by user ID
	 * @param userId
	 * @return List of orders
	 */
	public List<OrderDetails> getAllOrdersForUser(int userId) {
		
		Map<Integer, OrderDetails> orderMap = new HashMap<>();
		List<OrderDetails> ordersList = new ArrayList<>();
		try {
			PreparedStatement pSt = dbConn.prepareStatement("SELECT o.id, o.user_id, o.total,o.timestamp, oi.quantity, i.name, i.price FROM orders o JOIN order_items oi ON o.id = oi.order_id JOIN items i ON i.id = oi.item_id WHERE user_id = ? ORDER BY timestamp DESC, id");
			pSt.setInt(1, userId);
			
			ResultSet rs = pSt.executeQuery();
			
			if(rs.next()) {
				
				do {
					
					
					Date orderDateTime = rs.getTimestamp("timestamp");
					double orderTotal = ((double)rs.getInt("total")) / 100;
					
					OrderDetails od = new OrderDetails(rs.getInt("id"), rs.getInt("user_id"), orderTotal, orderDateTime);
					ItemDetails itmDetails = new ItemDetails(rs.getInt("quantity"), rs.getString("name"), ((double)rs.getInt("price")) / 100);
					if(orderMap.containsKey(od.getId())) {
						od = orderMap.get(od.getId());
						od.addItem(itmDetails);
					} else {
						od.addItem(itmDetails);
						orderMap.put(od.getId(), od);
						ordersList.add(od);
						
					}
					
				} while (rs.next());
			}
			
			rs.close();
			pSt.close();
			
			
		} catch (SQLException e) {
			System.out.println("SQLException " + e);
		}
		return ordersList;
	}

}
