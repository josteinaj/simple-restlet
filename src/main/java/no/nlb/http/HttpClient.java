package no.nlb.http;

import java.io.File;
import java.util.Map;


import org.w3c.dom.Document;

public interface HttpClient {
	
	/**
	 * Send a GET request.
	 * 
	 * @param url URL for instance "http://localhost:8182/ws".
	 * @param parameters URL query string parameters
	 * @return The return body.
	 * @throws HttpException 
	 */
	public HttpResponse get(String url) throws HttpException;
	
	/**
	 * POST an XML document.
	 * 
	 * @param url URL for instance "http://localhost:8182/ws".
	 * @param xml The XML document to post.
	 * @return The return body.
	 * @throws HttpException 
	 */
	public HttpResponse post(String url, Document xml) throws HttpException;
	
	/**
	 * POST a String.
	 * 
	 * @param url URL for instance "http://localhost:8182/ws".
	 * @param text The string to post.
	 * @return The return body.
	 * @throws HttpException 
	 */
	public HttpResponse post(String url, String text) throws HttpException;
	
	/**
	 * POST a multipart request.
	 * 
	 * @param url URL for instance "http://localhost:8182/ws".
	 * @param parts A map of all the parts.
	 * @return The return body.
	 * @throws HttpException 
	 */
	public HttpResponse post(String url, Map<String,File> parts) throws HttpException;
}