package no.nlb.http;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.Map;

import no.nlb.utils.Status;
import no.nlb.utils.XML;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.w3c.dom.Document;

/**
 * Implementation of HttpClient that uses Restlet as the underlying HTTP client.
 * 
 * @author jostein
 */
public class HttpClientImpl implements HttpClient {
	
//	private Client client;
	private Long timeout = 0L;
	
	public HttpClientImpl() {
//		createNewClient();
	}
	
//	private void createNewClient() {
//		Context context = new Context();
//		context.getParameters().add("followRedirects", "true");
////		context.getParameters().add("idleCheckInterval", "1000");
////		context.getParameters().add("stopIdleTimeout", "1000");
////		context.getParameters().add("idleTimeout", timeout+"");
////		context.getParameters().add("socketTimeout", timeout+"");
//		client = new Client(context, Protocol.HTTP); // TODO: add support for HTTPS WS ?
//	}
	
	/**
	 * Implementation of HttpClient.setTimeout(Long)
	 */
	public void setTimeout(Long ms) {
		timeout = ms;
//		createNewClient();
	}
	
	/**
	 * Implementation of HttpClient.get(String)
	 */
	public HttpResponse get(String url) throws HttpException {
		
		DefaultHttpClient httpclient = new DefaultHttpClient(); // Creating an instance here
        HttpGet request = new HttpGet(url);
        HttpContext context = new BasicHttpContext();
        
        org.apache.http.HttpResponse response;
        
        try {
            response = httpclient.execute(request, context);
            String bodyText = null;
            
            if (response != null && response.getStatusLine().getStatusCode() == 200) {
            	HttpEntity resEntity = response.getEntity();
        		try {
        			bodyText = streamToText(resEntity.getContent());
        		} catch (IOException e) {
        			throw new HttpException("Error while reading response body", e); 
        		}

            } else return null;
            
            Status status = Status.valueOf(response.getStatusLine().getStatusCode());
    		
    		return new HttpResponse(status.getCode(), status.getReasonPhrase(), status.getDescription(), response.getFirstHeader("Content-Type").getValue(), bodyText);
            
        } catch (ClientProtocolException e) {
			throw new HttpException("Error while GETing.", e);
        } catch (IOException e) {
        	throw new HttpException("Error while GETing.", e);
        } finally {
            httpclient.getConnectionManager().shutdown(); // Close the instance here
        }
        
		
        
        
        
//		ClientResource resource = new ClientResource(url);
//		resource.setNext(client);
//		Representation representation = null;
//		InputStream in = null;
//		boolean error = false;
//		try {
//			representation = resource.get();
//			if (representation != null) {
//				in = representation.getStream();
//			}
//			
//		} catch (ResourceException e) {
//			// Unauthorized etc.
//			error = true;
//		} catch (IOException e) {
//			e.printStackTrace();
//			error = true;
//		}
//		
//		Status status = resource.getStatus();
//		
//		if (error) {
//			try {
//				if (status != null && status.getCode() >= 1000) {
//					in = new ByteArrayInputStream(("Could not communicate with the remote server: "+url).getBytes("utf-8"));
//				} else {
//					in = new ByteArrayInputStream(("Could not communicate with the remote server: "+url).getBytes("utf-8"));
//				}
//	        } catch(UnsupportedEncodingException e) {
//	            throw new HttpException("Unable to create error body string as stream", e);
//	        }
//		}
//		
//		HttpResponse response = new HttpResponse(status.getCode(), status.getReasonPhrase(), status.getDescription(), representation==null?null:representation.getMediaType()==null?null:representation.getMediaType().toString(), in);
//		if (Http.debug()) {
//			try {
//    			if (representation == null) {
//    				System.err.println("---- Received: null ----\n");
//                } else if (representation.getMediaType() == MediaType.APPLICATION_ALL_XML) {
//                    System.err.println("---- Received: ----\n"+response.asText());
//    			} else {
//    				System.err.println("---- Received: "+representation.getMediaType()+" ("+representation.getSize()+" bytes) ----");
//    			}
//			} catch (Exception e) {
//				if (Http.debug()) System.err.print("---- Received: ["+e.getClass()+": "+e.getMessage()+"] ----");
//				if (Http.debug()) e.printStackTrace();
//			}
//		}
//		
//		return response;
	}
	
	/**
	 * Implementation of HttpClient.post(String,Document)
	 */
	public HttpResponse post(final String url, final Document xml) throws HttpException {
		return post(url, XML.toString(xml));
//		if (Http.debug()) {
//			System.err.println("POST URL: ["+url+"]");
//			System.err.println(XML.toString(xml));
//		}
//		
//		ClientResource resource = new ClientResource(url);
//		resource.setNext(client);
//		Representation representation = null;
//		try {
//			representation = resource.post(XML.toString(xml));
//		} catch (org.restlet.resource.ResourceException e) {
//			throw new HttpException(e.getMessage(), e);
//		}
//		
//		InputStream in = null;
//		if (representation != null) {
//			try {
//				in = representation.getStream();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//		
//		Status status = resource.getStatus();
//		
//		return new HttpResponse(status.getCode(), status.getReasonPhrase(), status.getDescription(), representation==null?null:representation.getMediaType().toString(), in);
	}
	
	/**
	 * Implementation of HttpClient.post(String,String)
	 */
	public HttpResponse post(final String url, final String text) throws HttpException {
		DefaultHttpClient httpclient = new DefaultHttpClient(); // Creating an instance here
        HttpPost request = new HttpPost(url);
        HttpContext context = new BasicHttpContext();
        
        org.apache.http.HttpResponse response;
        
        try {
        	request.setEntity(new StringEntity(text));
            response = httpclient.execute(request, context);
            String bodyText = null;
            
            if (response != null && response.getStatusLine().getStatusCode() == 200) {
            	HttpEntity resEntity = response.getEntity();
            	
        		try {
        			bodyText = streamToText(resEntity.getContent());
        		} catch (IOException e) {
        			throw new HttpException("Error while reading response body", e); 
        		}

            } else return null;
            
            Status status = Status.valueOf(response.getStatusLine().getStatusCode());
    		
    		return new HttpResponse(status.getCode(), status.getReasonPhrase(), status.getDescription(), response.getFirstHeader("Content-Type").getValue(), bodyText);
            
        } catch (ClientProtocolException e) {
			throw new HttpException("Error while POSTing.", e);
        } catch (IOException e) {
        	throw new HttpException("Error while POSTing.", e);
        } finally {
            httpclient.getConnectionManager().shutdown(); // Close the instance here
        }
        
		
		
		
		
		
		
		
		
		
//		ClientResource resource = new ClientResource(url);
//		resource.setNext(client);
//		Representation representation = null;
//		try {
//			representation = resource.post(text);
//		} catch (org.restlet.resource.ResourceException e) {
//			throw new HttpException(e.getMessage(), e);
//		}
//		
//		InputStream in = null;
//		if (representation != null) {
//			try {
//				in = representation.getStream();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//		
//		Status status = resource.getStatus();
//		
//		return new HttpResponse(status.getCode(), status.getReasonPhrase(), status.getDescription(), representation==null?null:representation.getMediaType().toString(), in);
	}
	
	/**
	 * Implementation of HttpClient.post(String,Map<String,File>)
	 */
	public HttpResponse post(final String url, final Map<String,File> parts) throws HttpException {
		org.apache.http.client.HttpClient httpclient = new DefaultHttpClient();
//		httpclient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, timeout);
//	    httpclient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, timeout);
		HttpPost httppost = new HttpPost(url);
		
		MultipartEntity reqEntity = new MultipartEntity();
		for (String partName : parts.keySet()) { 
			reqEntity.addPart(partName, new FileBody(parts.get(partName)));
		}
		httppost.setEntity(reqEntity);
		
		org.apache.http.HttpResponse response = null;
		try {
			response = httpclient.execute(httppost);
			
			HttpEntity resEntity = response.getEntity();
			
			String bodyText = null;
			try {
				bodyText = streamToText(resEntity.getContent());
			} catch (IOException e) {
				throw new HttpException("Error while reading response body", e); 
			}
			
			Status status = Status.valueOf(response.getStatusLine().getStatusCode());
			
			return new HttpResponse(status.getCode(), status.getReasonPhrase(), status.getDescription(), response.getFirstHeader("Content-Type").getValue(), bodyText);
			
		} catch (ClientProtocolException e) {
			throw new HttpException("Error while POSTing multipart.", e);
		} catch (IOException e) {
			throw new HttpException("Error while POSTing multipart.", e);
		} finally {
            httpclient.getConnectionManager().shutdown(); // Close the instance here
        }
	}
	
	
	private String streamToText(InputStream stream) throws HttpException {
		Writer writer = new StringWriter();
		 
        char[] buffer = new char[1024];
        try {
            Reader reader = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
            int n;
            while ((n = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, n);
            }
        } catch (UnsupportedEncodingException e) {
			// unable to open stream
			e.printStackTrace();
		} catch (IOException e) {
			// unable to read buffer
			e.printStackTrace();
		} finally {
        	try {
				stream.close();
				stream = null;
			} catch (IOException e) {
				throw new HttpException("Unable to close stream while reading response body", e);
			}
        }
        
        return writer.toString();
	}
	
}