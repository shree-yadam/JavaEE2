package servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Item;

@WebServlet("/items")
public class Items extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(false);

		StringBuffer sb = new StringBuffer("<html><body>");

		String contextRoute = request.getContextPath();

		if (session != null && session.getAttribute("email") != null) {
			
			List<Item> items = DatabaseConnection.getInstance().getAllItems();
			
			if(!items.isEmpty()) {
				sb.append("<form method=\"POST\" action=\"/ShoppingProject/order\">");
				sb.append("<table style=\"border: 1px solid gray \"><tbody>");
				sb.append("<tr  style=\"border: 1px solid gray \">");
				sb.append("<td  style=\"border: 1px solid gray \"></td>");
				sb.append("<td  style=\"border: 1px solid gray; color: blue \"><h2>ITEM</h2></td>");
				sb.append("<td  style=\"border: 1px solid gray; color: blue \"><h2>Price</h2></td>");
				sb.append("<td  style=\"border: 1px solid gray; color: blue \"><h2>Quantity</h2></td>");
				sb.append("</tr>");
				
				for(Item item: items) {
					sb.append("<tr style=\"margin: 20px 0;border: 1px solid gray \">");
					sb.append("<td  style=\"border: 1px solid gray \"><img src=\"" + item.getImgURL()
							+ "\" alt=\"item\" width=70px ></td>");
					sb.append("<td  style=\"border: 1px solid gray \"><h3 style=\"margin: 5px 0; color: blue \">"
							+ item.getName() + "</h3><pstyle=\"margin: 5px 0 \">" + item.getDescription() + "</p></td>");
					sb.append("<td  style=\"border: 1px solid gray \"><h5>" + item.getPrice() + "</h5></td>");
					sb.append("<td  style=\"border: 1px solid gray \"><input type=\"number\" min=\"0\" max=\""
							+ item.getQuantity() + "\" id=\"" + item.getId() + "\" name=\"" + item.getId() + "\" value=0></td>");
					sb.append("</tr>");
				}
				sb.append("<tr>");
				sb.append("<td><button type=\"button\" onclick=\"window.location.href='" + contextRoute
						+ "/home';\">Back</button></td>");
				sb.append("<td></td><td></td>");
				sb.append("<td><button type=\"submit\">Purchase</button></td>");
				sb.append("</tr>");
				sb.append("</tbody></table>");

				sb.append("</form>");
			}

		} else {
			sb.append("<h3> Please <a href=\"" + contextRoute + "/login.html\">login</a> to continue</h3>");
		}

		sb.append("</body></html>");

		response.getWriter().append(sb.toString());
	}
}
