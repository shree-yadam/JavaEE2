package servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/order")
public class Order extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private DatabaseConnection db = null;
	
    public Order() {
        super();

		db = DatabaseConnection.getInstance();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);

		String contextRoute = request.getContextPath();
		
		if(session != null  && session.getAttribute("email") != null){
			Map<String, String[]> itemsMap = request.getParameterMap();
			int numItems = 0;
	
			Connection conn = db.getDbConn();
			
			int totalValue = computeTotalValueOfOrder(itemsMap, numItems, conn);
			
			String email = (String) session.getAttribute("email");
			
			int userId = getUserIDForUser(conn, email);
			
			int orderId = createOrder(conn, totalValue, userId);

			createOrderItemEntries(response, contextRoute, itemsMap, conn, orderId);
		} else {
			response.getWriter().append("<h3> Please <a href=\"" + contextRoute + "/login.html\">login</a></h3>");
		}
	}

	private int computeTotalValueOfOrder(Map<String, String[]> itemsMap, int numItems, Connection conn) {
		int totalValue = 0;
		for(Entry<String, String[]> entry: itemsMap.entrySet()) {
			System.out.println("item: " + entry.getKey() + " quantity: " + entry.getValue()[0]);
			String item_id = entry.getKey();
			int quantity = Integer.parseInt(entry.getValue()[0]);
			double price = 0;
			if(quantity > 0) {
			
				numItems++;
				price = getPriceForItem(conn, item_id, price);
			}
			
			totalValue += price * quantity;
			
		}
		return totalValue;
	}

	private void createOrderItemEntries(HttpServletResponse response, String contextRoute,
			Map<String, String[]> itemsMap, Connection conn, int orderId) throws IOException {
		String orderItems = "INSERT INTO order_items (order_id, item_id, quantity) VALUES (?, ?, ?)";


		for(Entry<String, String[]> itemsEntry: itemsMap.entrySet()) {
			if(Integer.parseInt(itemsEntry.getValue()[0]) > 0) {
		
				try {
					PreparedStatement pSt = conn.prepareStatement(orderItems.toString());
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
		
		response.sendRedirect(contextRoute + "/home");
	}

	private int createOrder(Connection conn, int totalValue, int userId) {
		int orderId = -1;
		try {
			
			String insertOrder = "INSERT INTO orders (user_id, total) VALUES (?, ?)";
			PreparedStatement pSt = conn.prepareStatement(insertOrder, Statement.RETURN_GENERATED_KEYS);
			
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

	private int getUserIDForUser(Connection conn, String email) {
		int userId = -1;
		try {
			PreparedStatement pSt = conn.prepareStatement("SELECT id FROM users WHERE email = ?");
			
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

	private double getPriceForItem(Connection conn, String item_id, double price) {
		PreparedStatement pSt;
		try {
			pSt = conn.prepareStatement("SELECT price FROM items WHERE id = ?");
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

}
