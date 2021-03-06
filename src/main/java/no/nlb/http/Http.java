package no.nlb.http;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;
import org.w3c.dom.Document;


/**
 * Methods for communicating directly with the Pipeline 2 Web Service.
 * 
 * @author jostein
 */
public class Http {
	
	// Singleton
	private Http(){
		httpClient = new HttpClientImpl();
	}
	private static Http http = null;
	private static Http instance() {
		if (http == null)
			http = new Http();
		return http;
	}
	
	/** Set to true to enable debugging */
	private boolean debug = false;
	
	private HttpClient httpClient = null;
	
	/* -------------- static below -------------------- */
	
	/**
	 * If you'd like to initialize the HttpClient separately, you can use this method.
	 */
	public static void init() {
		instance();
	}
	
	/**
	 * Change the Http Client implementation.
	 * 
	 * @param httpClientImpl
	 */
	public static void setHttpClientImplementation(HttpClient httpClientImpl) {
		instance().httpClient = httpClientImpl;
	}
	
	public static void debug(boolean debug) {
		instance().debug = debug;
	}
	
	public static boolean debug() {
		return instance().debug;
	}

	/**
	 * Send a GET request.
	 * 
	 * @param url URL for instance "http://localhost:8182/ws".
	 * @param parameters URL query string parameters
	 * @return The return body.
	 * @throws HttpException 
	 */
	public static HttpResponse get(String url) throws HttpException {
		if (instance().debug) System.out.println("getting: "+url);
		return instance().httpClient.get(url);
	}

	/**
	 * POST an XML document.
	 * 
	 * @param url URL for instance "http://localhost:8182/ws".
	 * @param xml The XML document to post.
	 * @return The return body.
	 * @throws HttpException 
	 */
	public static HttpResponse post(String url, Document xml) throws HttpException {
		if (instance().debug) System.out.println("posting XML: "+url);
		return instance().httpClient.post(url, xml);
	}

	/**
	 * POST a string.
	 * 
	 * @param url URL for instance "http://localhost:8182/ws".
	 * @param text The string to post.
	 * @return The return body.
	 * @throws HttpException 
	 */
	public static HttpResponse post(String url, String text) throws HttpException {
		if (instance().debug) System.out.println("posting text: "+url);
		return instance().httpClient.post(url, text);
	}

	/**
	 * POST a multipart request.
	 * 
	 * @param url URL for instance "http://localhost:8182/ws".
	 * @param parts A map of all the parts.
	 * @return The return body.
	 * @throws HttpException 
	 */
	public static HttpResponse post(String url, Map<String,File> parts) throws HttpException {
		if (instance().debug) System.out.println("posting multipart: "+url);
		return instance().httpClient.post(url, parts);
	}

	/**
	 * Add parameters to a URL.
	 * 
	 * @param url
	 * @param parameters
	 * @return
	 * @throws HttpException
	 */
	public static String url(String url, Map<String,String> parameters) throws HttpException {
		if (parameters == null || parameters.size() == 0)
			return url;

		url += "?";
		for (String name : parameters.keySet()) {
			try { url += URLEncoder.encode(name, "UTF-8") + "=" + URLEncoder.encode(parameters.get(name), "UTF-8") + "&"; }
			catch (UnsupportedEncodingException e) { throw new HttpException("Unsupported encoding: UTF-8", e); }
		}
		
		return url;
	}
	
	/**
	 * Set the timeout for HTTP requests.
	 * 
	 * @param ms The timeout in milliseconds.
	 * @throws HttpException 
	 */
	public static void setTimeout(Long ms) throws HttpException {
		if (instance().debug) System.out.println("set timeout to "+ms+" milliseconds");
		instance().httpClient.setTimeout(ms);
	}

}
