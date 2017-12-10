package com.store.controllers;

import java.io.IOException;
import javax.json.Json;
import javax.json.JsonObjectBuilder;
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
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.store.models.Account;
import com.store.models.Address;
import com.store.models.Customer;
import com.store.utils.Handshake;
import com.store.utils.Paths;
/**
 * Controller servlet to handle payment for orders
 * 
 * @author Mike Kreager
 * @version 2017-10-28
 *
 */
@WebServlet("/payment")
public class PaymentControllerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PaymentControllerServlet() {
        super();
    }

    /**
     * Get account and address info, and forward the user to the payment page
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	// Get the session and attributes
	HttpSession session = request.getSession();
	String username = (String) session.getAttribute("username");
	String encodedPassword = (String) session.getAttribute("password");
		
	// Create the API client and invoke the request to get the account information
	ServletContext sc = this.getServletContext();
	Client client = ClientBuilder.newBuilder().sslContext(Handshake.getSslContext(sc)).build();
	Response accountResponse = client.target(Paths.GET_ACCOUNT)
            .queryParam("username", username)
            .queryParam("password", encodedPassword)
            .request(MediaType.TEXT_PLAIN_TYPE)
            .accept(MediaType.APPLICATION_JSON)
            .get();
		
	// Read the responses
        int accountStatus = accountResponse.getStatus();
        Account account = accountResponse.readEntity(Account.class);
        accountResponse.close();
        
        // Get customer info
        Customer customer = account.getCustomer();
        request.setAttribute("email", customer.getEmail());
        request.setAttribute("firstName", customer.getFirstName());
        request.setAttribute("lastName", customer.getLastName());
	    
	// Get billing info
        Address billingAddress = account.getDefaultAddressInfo().getBillingAddress();
        request.setAttribute("billingFullName", billingAddress.getFullName());
        request.setAttribute("billingAddressLine1", billingAddress.getAddressLine1());
        request.setAttribute("billingAddressLine2", billingAddress.getAddressLine2());
        request.setAttribute("billingCity", billingAddress.getCity());
        request.setAttribute("billingProvince", billingAddress.getProvince());
        request.setAttribute("billingCountry", billingAddress.getCountry());
        request.setAttribute("billingZip", billingAddress.getZip());
        request.setAttribute("billingPhone", billingAddress.getPhone());
        
        // Get shipping info
        Address shippingAddress = account.getDefaultAddressInfo().getShippingAddress();	
	request.setAttribute("shippingFullName", shippingAddress.getFullName());
        request.setAttribute("shippingAddressLine1", shippingAddress.getAddressLine1());
        request.setAttribute("shippingAddressLine2", shippingAddress.getAddressLine2());
        request.setAttribute("shippingCity", shippingAddress.getCity());
        request.setAttribute("shippingProvince", shippingAddress.getProvince());
        request.setAttribute("shippingCountry", shippingAddress.getCountry());
        request.setAttribute("shippingZip", shippingAddress.getZip());
        request.setAttribute("shippingPhone", shippingAddress.getPhone());
		
        // Continue with the request if status code is 200, otherwise send an error message to the client
        if (accountStatus == 200) {
            // Must have a PO id in the session
            if (session.getAttribute("poId") == null) {
	        session.setAttribute("message", "You must checkout a cart first.");
	        response.sendRedirect(request.getContextPath() + "/store");
	    } else {
	        RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/jsp/payment.jsp");  
	    	dispatcher.forward(request, response);
	    }
        } else if (accountStatus == 401) {
            session.setAttribute("message", "Unauthorized.");
            response.sendRedirect(request.getContextPath() + "/store");
        } else if (accountStatus == 403) {
            session.setAttribute("message", "Forbidden.");
            response.sendRedirect(request.getContextPath() + "/store");
        } else if (accountStatus == 404) {
            session.setAttribute("message", "Not found.");
            response.sendRedirect(request.getContextPath() + "/store");
        } else if (accountStatus == 500) {
            session.setAttribute("message", "Database or server error occurred.");
            response.sendRedirect(request.getContextPath() + "/store");
        } else {
            session.setAttribute("message", "Something went wrong.");
            response.sendRedirect(request.getContextPath() + "/store");
        }
    }

    /**
     * Pass the account id, credit card number and address info to the order confirmation service
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	// Get the session and PO id attribute
	HttpSession session = request.getSession();
	String poId = (String) session.getAttribute("poId");
		
	// Create JSON object for post
	JsonObjectBuilder builder = Json.createObjectBuilder();
		
	// Add billing address to JSON
	JsonObjectBuilder billingAddressBuilder = Json.createObjectBuilder();
	billingAddressBuilder.add("fullName", request.getParameter("billingFullName"));
	billingAddressBuilder.add("addressLine1", request.getParameter("billingAddressLine1"));
	billingAddressBuilder.add("addressLine2", request.getParameter("billingAddressLine2"));
	billingAddressBuilder.add("city", request.getParameter("billingCity"));
	billingAddressBuilder.add("province", request.getParameter("billingProvince"));
	billingAddressBuilder.add("country", request.getParameter("billingCountry"));
	billingAddressBuilder.add("zip", request.getParameter("billingZip"));
	billingAddressBuilder.add("phone", request.getParameter("billingPhone"));
	billingAddressBuilder.add("type", "BILLING");
	billingAddressBuilder.add("id", -1);
	builder.add("billingAddress", billingAddressBuilder);

	// Add shipping address to JSON
	JsonObjectBuilder shippingAddressBuilder = Json.createObjectBuilder();
	shippingAddressBuilder.add("fullName", request.getParameter("shippingFullName"));
	shippingAddressBuilder.add("addressLine1", request.getParameter("shippingAddressLine1"));
	shippingAddressBuilder.add("addressLine2", request.getParameter("shippingAddressLine2"));
	shippingAddressBuilder.add("city", request.getParameter("shippingCity"));
	shippingAddressBuilder.add("province", request.getParameter("shippingProvince"));
	shippingAddressBuilder.add("country", request.getParameter("shippingCountry"));
	shippingAddressBuilder.add("zip", request.getParameter("shippingZip"));
	shippingAddressBuilder.add("phone", request.getParameter("shippingPhone"));
	shippingAddressBuilder.add("type", "SHIPPING");
	shippingAddressBuilder.add("id", -1);
	builder.add("shippingAddress", shippingAddressBuilder);
		
	// Build JSON object for the post
	String input = builder.build().toString();

	// Create the API client and invoke the request
	ServletContext sc = this.getServletContext();
	Client client = ClientBuilder.newBuilder().sslContext(Handshake.getSslContext(sc)).build();
	Response resp = client.target(Paths.CONFIRM_ORDER + poId)
	    .queryParam("card", request.getParameter("creditCard"))
	    .request(MediaType.APPLICATION_JSON)
	    .accept(MediaType.APPLICATION_JSON)
	    .post(Entity.json(input));
		
	// Check the response
        int code = resp.getStatus();

	// Confirm or deny order, otherwise send the error message to the client
	if (code == 200 || code == 201) {
	    String success = resp.readEntity(String.class);
	    if (success.equals("true")) {
	        session.setAttribute("message", "Order successfully completed.");
	        // Remove the PO id session attribute after order confirmation
	        session.removeAttribute("poId");
	        response.sendRedirect(request.getContextPath() + "/store");
	    } else {
	        session.setAttribute("message", "Credit card authorization failed.");
	        response.sendRedirect(request.getContextPath() + "/payment");
	    }
        } else if (code == 400) {
	    session.setAttribute("message", "Bad request.");
	    response.sendRedirect(request.getContextPath() + "/payment");
	} else if (code == 401) {
	    session.setAttribute("message", "Unauthorized.");
	    response.sendRedirect(request.getContextPath() + "/payment");
	} else if (code == 403) {
	    session.setAttribute("message", "Forbidden.");
	    response.sendRedirect(request.getContextPath() + "/payment");
	} else if (code == 404) {
	    session.setAttribute("message", "Not found.");
	    response.sendRedirect(request.getContextPath() + "/payment");
	} else if (code == 500) {
	    session.setAttribute("message", "Database or server error occurred.");
	    response.sendRedirect(request.getContextPath() + "/payment");
	} else {
	    session.setAttribute("message", "Something went wrong.");
	    response.sendRedirect(request.getContextPath() + "/payment");
	}
        resp.close();
    }
}
