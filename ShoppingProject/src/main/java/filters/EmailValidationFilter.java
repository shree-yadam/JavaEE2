package filters;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;

@WebFilter({ "/register", "/login" })
public class EmailValidationFilter extends HttpFilter implements Filter {
       
    private static final long serialVersionUID = 1L;

	public EmailValidationFilter() {
        super();
    }

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		String email = request.getParameter("email");
		HttpServletRequest httpReq = (HttpServletRequest) request;
		if(httpReq.getMethod().equalsIgnoreCase("GET") || ((!email.isEmpty()) && (email.matches("^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$")))) {
			chain.doFilter(request, response);
		} else {
			response.getWriter().append("<h1>Email not valid!</h1>");
		}
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}

}
