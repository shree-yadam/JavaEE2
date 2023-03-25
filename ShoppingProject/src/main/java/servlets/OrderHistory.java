package servlets;

import java.io.IOException;
import model.ItemDetails;
import model.OrderDetails;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/order-history")
public class OrderHistory extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);

		StringBuffer sb = new StringBuffer("<html><body>");
		String contextRoute = request.getContextPath();
		
		if(session != null  && session.getAttribute("email") != null) {
			
			String email = (String) session.getAttribute("email");
			int userId = DatabaseConnection.getInstance().getUserIDForEmail(email);
			
			Map<Integer, OrderDetails> orderMap = DatabaseConnection.getInstance().getAllOrdersForUser(userId);
			
			createHTMLOrderDisplay(sb, contextRoute, orderMap);
			
		} else {
			sb.append("<h3> Please <a href=\"" + contextRoute + "/login.html\">login</a> to continue</h3>");
		}

		sb.append("</body></html>");

		response.getWriter().append(sb.toString());
		
	}

	private void createHTMLOrderDisplay(StringBuffer sb, String contextRoute, Map<Integer, OrderDetails> orderMap) {
		sb.append("<table style=\"border: 1px solid gray; border-collapse: collapse; \"><tbody>");
		sb.append("<tr  style=\"border: 1px solid gray; border-collapse: collapse; \">");
		sb.append("<td  style=\"border: 1px solid gray; color: blue; border-collapse: collapse; \"><h2>Order #<h2></td>");
		sb.append("<td  style=\"border: 1px solid gray; color: blue; border-collapse: collapse; \"><h2>Order Date<h2></td>");
		sb.append("<td  style=\"border: 1px solid gray; color: blue; border-collapse: collapse; \"><h2>Order Time</h2></td>");
		sb.append("<td  style=\"border: 1px solid gray; color: blue; border-collapse: collapse; \"><h2>Items</h2></td>");
		sb.append("<td  style=\"border: 1px solid gray; color: blue; border-collapse: collapse; \"><h2>Total Price</h2></td>");
		sb.append("</tr>");
		
		for(Map.Entry<Integer, OrderDetails> entry: orderMap.entrySet()) {
			OrderDetails order = entry.getValue(); 
			List<ItemDetails> itemList = order.getItems();
			String orderDateString = new SimpleDateFormat("yyyy-MM-dd").format(order.getOrderDateTime());
			String orderTimeString = new SimpleDateFormat("HH:mm:ss").format(order.getOrderDateTime());
			
			sb.append("<tr style=\"margin: 20px 0;border: 1px solid gray; border-collapse: collapse; \">");
			sb.append("<td  style=\"border: 1px solid gray; border-collapse: collapse; \"><h5>" + order.getId() + "</h5></td>");
			sb.append("<td  style=\"border: 1px solid gray; border-collapse: collapse; \"><h5>" + orderDateString + "</h5></td>");
			sb.append("<td  style=\"border: 1px solid gray; border-collapse: collapse; \"><h5>" +  orderTimeString + "</h5></td>");
			sb.append("<td  style=\"border: 1px solid gray; border-collapse: collapse; \">");
			createHTMLItemsDisplay(sb, itemList);
			sb.append("</td>");
			sb.append("<td  style=\"border: 1px solid gray ; border-collapse: collapse;\"><h5>" +  order.getTotal() + "</h5></td>");
			sb.append("</tr>");
		}
		sb.append("<tr>");
		sb.append("<td><button type=\"button\" onclick=\"window.location.href='" + contextRoute
				+ "/home';\">Back</button></td>");
		sb.append("<td></td><td></td><td></td><td></td>");
		sb.append("</tr>");
		sb.append("</tbody></table>");
	}

	private void createHTMLItemsDisplay(StringBuffer sb, List<ItemDetails> itemList) {
		sb.append("<table style=\"border: 1px solid gray; border-collapse: collapse; width:100%\"><tbody>");
		
		for(ItemDetails item: itemList) {
			sb.append("<tr style=\"margin: 20px 0;border: 1px solid gray; border-collapse: collapse; \">");
			sb.append("<td  style=\"border: 1px solid gray; border-collapse: collapse; width:70%\"><h5>" + item.getItemName() + "</h5></td>");
			sb.append("<td  style=\"border: 1px solid gray; border-collapse: collapse; width:10%\"><h5>" + item.getQuantity() + "</h5></td>");
			sb.append("<td  style=\"border: 1px solid gray; border-collapse: collapse; width:20%\"><h5>" +  item.getItemPrice() + "</h5></td>");
			sb.append("</tr>");
		}
		sb.append("</tbody></table>");
	}

}
