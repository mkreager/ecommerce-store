package com.store.utils;

import javax.net.ssl.SSLContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;

import org.glassfish.jersey.SslConfigurator;

/**
 * Load server key to perform SSL handshake. 
 * For ease of testing; consider adding key to local env variables in production.
 * 
 * @author Mike Kreager
 * @version 2017-10-28
 *
 */
public final class Handshake extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	/**
     * Load the server (web service) key from local (web tier) storage into trust store to create SSL context.
     *   
     * @param sc The servlet context
     * @return sslContext
     */
	public static SSLContext getSslContext(ServletContext sc) {
		// Load SSL key to perform handshake with the server
		String kpath = sc.getRealPath("/keystore.p12");
		SslConfigurator sslConfig = SslConfigurator.newInstance()
				.trustStoreFile(kpath)
				.trustStorePassword("password");
		SSLContext sslContext = sslConfig.createSSLContext();
		return sslContext;
	}
}