package com.store.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet Filter implementation class AuthenticationFilter
 * Prevents un-authenticated visitors from accessing "/order" and "/payment" paths
 * 
 * @author Mike Kreager
 * @version 2017-10-28
 * 
 */
@WebFilter(servletNames = { "com.store.controllers.OrderControllerServlet", 
		"com.store.controllers.PaymentControllerServlet" })
public class AuthenticationFilter implements Filter {

    FilterConfig filterConfig;
	
    public void init(FilterConfig fConfig) throws ServletException {
	this.filterConfig = fConfig;
    }
	
    public void destroy() {	
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {    
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        
        // Get the session
        HttpSession session = req.getSession();
        if (session.getAttribute("username") == null || session.getAttribute("password") == null) {
            // Set a flag to indicate a checkout request so we can direct the user back to their order after login
            session.setAttribute("checkout", "true");
            session.setAttribute("message", "You need to login first.");
            res.sendRedirect(req.getContextPath() + "/login");
        } else {
            chain.doFilter(request, response);
        }
    }
}
