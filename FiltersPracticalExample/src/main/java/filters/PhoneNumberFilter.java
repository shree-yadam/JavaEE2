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

@WebFilter("/register")
public class PhoneNumberFilter extends HttpFilter implements Filter {
       
    private static final long serialVersionUID = 1L;

	public PhoneNumberFilter() {
        super();
    }

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		String phoneNumber = request.getParameter("phoneNumber");
		HttpServletRequest httpReq = (HttpServletRequest) request;
		if(httpReq.getMethod().equalsIgnoreCase("GET") || ((!phoneNumber.isEmpty()) && (phoneNumber.length() == 10) && (phoneNumber.matches("\\d+")))) {
			chain.doFilter(request, response);
		} else {
			response.getWriter().append("<h1>Phone number not valid!</h1>");
		}
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}

}
