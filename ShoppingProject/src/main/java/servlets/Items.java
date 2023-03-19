package servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/items")
public class Items extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private DatabaseConnection db = null;

	public Items() {
		super();
		db = DatabaseConnection.getInstance();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(false);

		StringBuffer sb = new StringBuffer("<html><body>");

		String contextRoute = request.getContextPath();

		if (session != null) {

			Connection conn = db.getDbConn();

			try {
				Statement st = conn.createStatement();

				ResultSet rs = st.executeQuery("SELECT * FROM items");

				if (rs.next()) {
					sb.append("<form method=\"POST\" action=\"/ShoppingProject/order\">");
					sb.append("<table style=\"border: 1px solid gray \"><tbody>");
					sb.append("<tr  style=\"border: 1px solid gray \">");
					sb.append("<td  style=\"border: 1px solid gray \"></td>");
					sb.append("<td  style=\"border: 1px solid gray; color: blue \"><h2>ITEM</h2></td>");
					sb.append("<td  style=\"border: 1px solid gray; color: blue \"><h2>Price</h2></td>");
					sb.append("<td  style=\"border: 1px solid gray; color: blue \"><h2>Quantity</h2></td>");
					sb.append("</tr>");

					do {
						int id = rs.getInt("id");
						String name = rs.getString("name");
						double price = ((double) rs.getInt("price")) / 100;
						String description = rs.getString("description");
						String imgURL = rs.getString("img_url");
						int quantity = rs.getInt("quantity");

						sb.append("<tr style=\"margin: 20px 0;border: 1px solid gray \">");
						sb.append("<td  style=\"border: 1px solid gray \"><img src=\"" + imgURL
								+ "\" alt=\"item\" width=70px ></td>");
						sb.append("<td  style=\"border: 1px solid gray \"><h3 style=\"margin: 5px 0; color: blue \">"
								+ name + "</h3><pstyle=\"margin: 5px 0 \">" + description + "</p></td>");
						sb.append("<td  style=\"border: 1px solid gray \"><h5>" + price + "</h5></td>");
						sb.append("<td  style=\"border: 1px solid gray \"><input type=\"number\" min=\"0\" max=\""
								+ quantity + "\" id=\"" + id + "\" name=\"" + id + "\" value=0></td>");
						sb.append("<tr>");

					} while (rs.next());
					sb.append("<tr>");
					sb.append("<td><button type=\"button\" onclick=\"window.location.href='" + contextRoute
							+ "/home';\">Back</button></td>");
					sb.append("<td></td><td></td>");
					sb.append("<td><button type=\"submit\">Purchase</button></td>");
					sb.append("</tbody></table>");

					sb.append("</form>");
				}

				rs.close();
				st.close();
			} catch (SQLException e) {
				System.out.println("SQLException " + e);
			}
		} else {
			sb.append("<h3> Please <a href=\"" + contextRoute + "/login.html\">login</a> to continue</h3>");
		}

		sb.append("</body></html>");

		response.getWriter().append(sb.toString());
	}
}
