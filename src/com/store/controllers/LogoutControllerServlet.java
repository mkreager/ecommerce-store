package com.store.controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Controller servlet to handle ending sessions and logging users out
 * 
 * @author Mike Kreager
 * @version 2017-10-28
 *
 */
@WebServlet("/logout")
public class LogoutControllerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LogoutControllerServlet() {
        super();
    }

    /**
     * Log the user out and invalidate the session
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	// Get the session and invalidate it
	HttpSession session = request.getSession(false);
	if (session != null) {
	    session.invalidate();
	}
	response.sendRedirect(request.getContextPath() + "/store");
    }
}
