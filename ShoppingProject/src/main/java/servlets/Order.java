package servlets;

import java.io.IOException;
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

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(false);

		String contextRoute = request.getContextPath();

		if (session != null && session.getAttribute("email") != null) {
			Map<String, String[]> itemsMap = request.getParameterMap();
			int numItems = 0;

			int totalValue = computeTotalValueOfOrder(itemsMap, numItems);

			String email = (String) session.getAttribute("email");

			int userId = DatabaseConnection.getInstance().getUserIDForEmail(email);

			int orderId = DatabaseConnection.getInstance().createOrder(totalValue, userId);

			DatabaseConnection.getInstance().createOrderItemEntries(itemsMap, orderId);

			response.sendRedirect(contextRoute + "/home");

		} else {
			response.getWriter().append("<h3> Please <a href=\"" + contextRoute + "/login.html\">login</a></h3>");
		}
	}

	private int computeTotalValueOfOrder(Map<String, String[]> itemsMap, int numItems) {
		int totalValue = 0;
		for (Entry<String, String[]> entry : itemsMap.entrySet()) {
			System.out.println("item: " + entry.getKey() + " quantity: " + entry.getValue()[0]);
			String itemId = entry.getKey();
			int quantity = Integer.parseInt(entry.getValue()[0]);
			double price = 0;
			if (quantity > 0) {

				numItems++;
				price = DatabaseConnection.getInstance().getPriceForItem(itemId);
				DatabaseConnection.getInstance().updateItemQuantity(Integer.parseInt(itemId), quantity);
			}

			totalValue += price * quantity;

		}
		return totalValue;
	}

}
