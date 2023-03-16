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

@WebFilter("/*")
public class MyFirstFilter extends HttpFilter implements Filter {
       
    public MyFirstFilter() {
        super();
    }

	public void destroy() {
		System.out.println("MyFristFilter destroy called ....");
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		System.out.println("MyFristFilter entereed ....");
		chain.doFilter(request, response);
		System.out.println("MyFristFilter exit ....");
	}

	public void init(FilterConfig fConfig) throws ServletException {
		System.out.println("MyFristFilter init called ....");
	}

}
