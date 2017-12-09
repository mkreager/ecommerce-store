package com.store.controllers;

import java.io.IOException;
import java.io.StringReader;
import java.util.Base64;

import javax.json.Json;
import javax.json.JsonReader;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.store.utils.Handshake;
import com.store.utils.*;
/**
 * Controller servlet to handle authentication to the services
 * 
 * @author Mike Kreager
 * @version 2017-10-28
 *
 */
@WebServlet("/login")
public class LoginControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginControllerServlet() {
        super();
    }

	/**
	 * Respond to get requests with forward to the login page
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/jsp/login.jsp");  
	    dispatcher.forward(request, response);
	}

	/**
	 * Check password/email combination with the Account service, and log the user in (create a session)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Get the form inputs
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String encodedPassword = Base64.getEncoder().encodeToString(password.getBytes());
		
		// Get the session
		HttpSession session = request.getSession();
		
		// Create the API client and invoke the request
		ServletContext sc = this.getServletContext();
		Client client = ClientBuilder.newBuilder().sslContext(Handshake.getSslContext(sc)).build();
		Response resp = client.target(Paths.LOGIN)
        	.queryParam("username", email)
        	.queryParam("password", encodedPassword)
        	.request(MediaType.TEXT_PLAIN_TYPE)
        	.accept(MediaType.APPLICATION_JSON)
            .get();
		
		// Check the response
        int code = resp.getStatus();
        
        // Login if the code is 200, otherwise send the error message to the client
        if (code == 200) {
        	session.setAttribute("message", "Login successful");
        	session.setAttribute("username", email);  
        	session.setAttribute("password", encodedPassword);
        	// Check if the login request came from an order checkout attempt and redirect accordingly
        	String checkout = (String) session.getAttribute("checkout");
        	if (checkout != null && checkout.equals("true")) {
        		session.setAttribute("checkout", null);
        		response.sendRedirect(request.getContextPath() + "/order");
        	} else {
        		response.sendRedirect(request.getContextPath() + "/store");
        	}
        } else if (code == 400 || code == 404) {
        	String responseStr = resp.readEntity(String.class);
        	StringReader stringReader = new StringReader(responseStr);
        	JsonReader reader = Json.createReader(stringReader);
        	String message = reader.readObject().getString("message");
        	session.setAttribute("message", message);
        	response.sendRedirect(request.getContextPath() + "/login");
        } else if (code == 401) {
        	session.setAttribute("message", "Unauthorized.");
        	response.sendRedirect(request.getContextPath() + "/login");
        } else if (code == 403) {
        	session.setAttribute("message", "Forbidden.");
        	response.sendRedirect(request.getContextPath() + "/login");
        } else if (code == 500) {
        	session.setAttribute("message", "Database or server error occurred.");
        	response.sendRedirect(request.getContextPath() + "/login");
        } else {
        	session.setAttribute("message", "Something went wrong.");
        	response.sendRedirect(request.getContextPath() + "/login");
        }
        resp.close();
	}

}
