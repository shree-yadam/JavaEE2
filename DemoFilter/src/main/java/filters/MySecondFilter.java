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

public class MySecondFilter extends HttpFilter implements Filter {
       
    public MySecondFilter() {
        super();
    }

	public void destroy() {
		System.out.println("MySecondFilter destroy called ....");
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		System.out.println("MySecondFilter entereed ....");
		chain.doFilter(request, response);
		System.out.println("MySecondFilter exit ....");
	}

	public void init(FilterConfig fConfig) throws ServletException {
		System.out.println("MySecondFilter init called ....");
	}

}
