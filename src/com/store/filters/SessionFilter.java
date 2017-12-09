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
 * Servlet Filter implementation class SessionFilter
 * Prevents authenticated users from accessing "/account" and "/login" paths
 * 
 * @author Mike Kreager
 * @version 2017-10-28
 * 
 */
@WebFilter(servletNames = { "com.store.controllers.AccountControllerServlet", 
		"com.store.controllers.LoginControllerServlet" })
public class SessionFilter implements Filter {

    FilterConfig filterConfig;
	
    public void init(FilterConfig fConfig) throws ServletException {
	this.filterConfig = fConfig;
    }
	
    public void destroy() {	
    }
	
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {    
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        HttpSession session = req.getSession();
        if (session.getAttribute("username") != null) {
            session.setAttribute("message", "You're already logged in.");
            res.sendRedirect(req.getContextPath() + "/store");
        } else {
            chain.doFilter(request, response);
        }
    }
}
