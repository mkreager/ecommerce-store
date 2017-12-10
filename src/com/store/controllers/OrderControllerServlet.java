package com.store.controllers;

import java.io.IOException;
import java.io.StringReader;
import java.math.BigDecimal;
import java.util.List;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
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
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.store.models.*;
import com.store.utils.Handshake;
import com.store.utils.Paths;

/**
 * Controller servlet to handle processing of orders
 * 
 * @author Mike Kreager
 * @version 2017-10-28
 *
 */
@WebServlet("/order")
public class OrderControllerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public OrderControllerServlet() {
        super();
    }

    /**
     * Get the account info and shipping options, and forward the user to the order page
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
        // Get the session
	HttpSession session = request.getSession();
	String username = (String) session.getAttribute("username");
	String encodedPassword = (String) session.getAttribute("password");
	String[] cdListArray = new String[] {};
	String cdList = (String) session.getAttribute("cdList");
	if (cdList != null) {
	    cdList = cdList.trim();
	    cdListArray = cdList.split(" ");
	}
		
	if (cdListArray.length == 0) {
            session.setAttribute("message", "No item in shopping cart.");
            response.sendRedirect(request.getContextPath() + "/store");
            return;
	}
		
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
        Account account = (Account) accountResponse.readEntity(Account.class);
        accountResponse.close();
        
	// Invoke the request for the shipping options
	Response shippingResponse = client.target(Paths.GET_SHIPPING)
            .request()
            .accept(MediaType.APPLICATION_JSON)
            .get();
		
        int shippingStatus = shippingResponse.getStatus();
	List<ShippingInfo> shippingList = shippingResponse.readEntity(new GenericType<List<ShippingInfo>>(){});
	shippingResponse.close();
		
        // Get customer info
        Customer customer = account.getCustomer();
        request.setAttribute("email", customer.getEmail());
        request.setAttribute("firstName", customer.getFirstName());
        request.setAttribute("lastName", customer.getLastName());
        
        // Get shipping options
        request.setAttribute("shippingOptions", shippingList);
        
        // Item list
        JsonArrayBuilder itemsListBuilder = Json.createArrayBuilder();
        for (String cdId : cdListArray) {
            JsonObjectBuilder tmpBuilder = Json.createObjectBuilder();
            tmpBuilder.add("cdid", cdId);
            Response resp = client.target(Paths.CD_INFO + cdId).request(MediaType.APPLICATION_JSON).get();
            Cd cd  = resp.readEntity(Cd.class);
            resp.close();
            tmpBuilder.add("title", cd.getTitle());
            tmpBuilder.add("artist", cd.getArtist());
            Integer quantity = (Integer) session.getAttribute(cdId + ".counter");
            tmpBuilder.add("quantity", quantity);
            BigDecimal totalPrice = cd.getPrice().multiply(new BigDecimal(quantity));
            tmpBuilder.add("price", totalPrice);
            itemsListBuilder.add(tmpBuilder);
        }     
        
        // Get item list
        request.setAttribute("items", itemsListBuilder.build());
		
        // Continue with the request if status codes are 200, otherwise send an error message to the client
        if (accountStatus == 200 && shippingStatus == 200) {
            RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/jsp/order.jsp");  
    	    dispatcher.forward(request, response);
        } else if (accountStatus == 401 || shippingStatus == 401) {
            session.setAttribute("message", "Unauthorized.");
            response.sendRedirect(request.getContextPath() + "/store");
        } else if (accountStatus == 403 || shippingStatus == 403) {
            session.setAttribute("message", "Forbidden.");
            response.sendRedirect(request.getContextPath() + "/store");
        } else if (accountStatus == 404 || shippingStatus == 404) {
            session.setAttribute("message", "Not found.");
            response.sendRedirect(request.getContextPath() + "/store");
        } else if (accountStatus == 500 || shippingStatus == 500) {
            session.setAttribute("message", "Database or server error occurred.");
            response.sendRedirect(request.getContextPath() + "/store");
        } else {
            session.setAttribute("message", "Something went wrong.");
            response.sendRedirect(request.getContextPath() + "/store");
        }
    }

    /**
     * Pass the cart items, account id and shipping info to the order creation service
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
        // Get the session
	HttpSession session = request.getSession();
		
	// Create JSON object for post
	JsonObjectBuilder builder = Json.createObjectBuilder();
	builder.add("customerEmail",  request.getParameter("email"));
		
	// Add PO items to JSON
	JsonArrayBuilder itemsListBuilder = Json.createArrayBuilder();
	int numOfCD = request.getParameterValues("cdid").length;
	for (int i = 0; i < numOfCD; i++) {
	    String cdid = request.getParameterValues("cdid")[i];
	    String quantity = request.getParameterValues("quantity")[i];
	    String price = request.getParameterValues("price")[i];
	    JsonObjectBuilder itemBuilder = Json.createObjectBuilder();
	    itemBuilder.add("cdId", cdid);
	    itemBuilder.add("numOrdered", quantity);
	    itemBuilder.add("poId", 0);
	    itemBuilder.add("unitPrice", price);
	    itemsListBuilder.add(itemBuilder);
	}
	builder.add("poItems", itemsListBuilder);
	builder.add("shippingInfoId", request.getParameter("ship"));

	// Build JSON object for the post
	String input = builder.build().toString();
		
	// Create the API client and invoke the request
	ServletContext sc = this.getServletContext();
	Client client = ClientBuilder.newBuilder().sslContext(Handshake.getSslContext(sc)).build();
	Response resp = client.target(Paths.CREATE_ORDER)
            .request(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .post(Entity.json(input));
		
	// Check the response
        int code = resp.getStatus();
        
        // If the response is 200/201, otherwise send the error message to the client
        if (code == 200 || code == 201) { 
            session.setAttribute("message", "Order created successfully.");
            String poId = resp.readEntity(String.class);
            session.setAttribute("poId", poId);
            // Remove the cart and its items from session after order success
            session.removeAttribute("session.order");
            String cdList = (String) session.getAttribute("cdList");
    	    String[] cdListArray = cdList.split(" ");
    	    for (String cdId : cdListArray) {
    		session.removeAttribute(cdId + ".counter");
    	    }
    	    session.removeAttribute("cdList");
            response.sendRedirect(request.getContextPath() + "/payment");
        } else if (code == 400) {
            String responseStr = resp.readEntity(String.class);
            StringReader stringReader = new StringReader(responseStr);
            JsonReader reader = Json.createReader(stringReader);
            String message = reader.readObject().getString("message");
            session.setAttribute("message", message);
            response.sendRedirect(request.getContextPath() + "/order");
        } else if (code == 401) {
            session.setAttribute("message", "Unauthorized.");
            response.sendRedirect(request.getContextPath() + "/order");
        } else if (code == 403) {
            session.setAttribute("message", "Forbidden.");
            response.sendRedirect(request.getContextPath() + "/order");
        } else if (code == 404) {
            session.setAttribute("message", "Not found.");
            response.sendRedirect(request.getContextPath() + "/order");
        } else if (code == 500) {
            session.setAttribute("message", "Database or server error occurred.");
            response.sendRedirect(request.getContextPath() + "/order");
        } else {
            session.setAttribute("message", "Something went wrong.");
            response.sendRedirect(request.getContextPath() + "/order");
        }
        resp.close();
    }
}
