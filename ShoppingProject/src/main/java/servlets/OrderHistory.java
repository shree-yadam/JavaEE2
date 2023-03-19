package servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/order-history")
public class OrderHistory extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private DatabaseConnection db = null;
	
    public OrderHistory() {
        super();
		db = DatabaseConnection.getInstance();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);

		StringBuffer sb = new StringBuffer("<html><body>");
		String contextRoute = request.getContextPath();
		
		if(session != null) {
			
			Connection conn = db.getDbConn();
			
			String email = (String) session.getAttribute("email");
			int userId = getUserIdForEmail(conn, email);
			
			sb.append("<table style=\"border: 1px solid gray \"><tbody>");
			sb.append("<tr  style=\"border: 1px solid gray \">");
			sb.append("<td  style=\"border: 1px solid gray; color: blue \"><h2>Order Date<h2></td>");
			sb.append("<td  style=\"border: 1px solid gray; color: blue \"><h2>Order Time</h2></td>");
			sb.append("<td  style=\"border: 1px solid gray; color: blue \"><h2>Total Price</h2></td>");
			sb.append("</tr>");
			
			try {
				PreparedStatement pSt = conn.prepareStatement("SELECT o.id, o.total, o.timestamp FROM orders o JOIN order_items oi ON o.id = oi.order_id WHERE user_id = ? GROUP BY o.id");
				pSt.setInt(1, userId);
				
				ResultSet rs = pSt.executeQuery();
				
				if(rs.next()) {
					
					do {
						Date orderDateTime = rs.getTimestamp("timestamp");
						String orderDateString = new SimpleDateFormat("yyyy-MM-dd").format(orderDateTime);
						String orderTimeString = new SimpleDateFormat("HH:mm:ss").format(orderDateTime);
						double orderTotal = ((double)rs.getInt("total")) / 100;
						
						sb.append("<tr style=\"margin: 20px 0;border: 1px solid gray \">");
						sb.append("<td  style=\"border: 1px solid gray \"><h5>" + orderDateString + "</h5></td>");
						sb.append("<td  style=\"border: 1px solid gray \"><h5>" +  orderTimeString + "</h5></td>");
						sb.append("<td  style=\"border: 1px solid gray \"><h5>" +  orderTotal + "</h5></td>");
						sb.append("</tr>");
						
					} while (rs.next());
				}
				
				rs.close();
				pSt.close();
				sb.append("<tr>");
				sb.append("<td><button type=\"button\" onclick=\"window.location.href='" + contextRoute
						+ "/home';\">Back</button></td>");
				sb.append("<td></td><td></td><td></td>");
				sb.append("</tr>");
				sb.append("</tbody></table>");
			} catch (SQLException e) {
				System.out.println("SQLException " + e);
			}
			
		} else {
			sb.append("<h3> Please <a href=\"" + contextRoute + "/login.html\">login</a> to continue</h3>");
		}

		sb.append("</body></html>");

		response.getWriter().append(sb.toString());
		
	}

	private int getUserIdForEmail(Connection conn, String email) {
		int userId = -1;
		PreparedStatement pSt;
		try {
			pSt = conn.prepareStatement("SELECT id FROM users WHERE email = ?");
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

}
