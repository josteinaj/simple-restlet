package no.nlb.http;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import no.nlb.utils.XML;

import org.w3c.dom.Document;

public class HttpResponse {
	
	public int status;
	public String statusName;
	public String statusDescription;
	public String contentType;
	private InputStream bodyStream;
	private String bodyText;
	private Document bodyXml;
	
	/**
	 * Creates a new HttpResponse with the given HTTP status code, status name, status description and content body.
	 * 
	 * @param status
	 * @param statusName
	 * @param statusDescription
	 * @param bodyStream
	 */
	public HttpResponse(int status, String statusName, String statusDescription, String contentType, String bodyText) {
		this.status = status;
		this.statusName = statusName;
		this.statusDescription = statusDescription;
		this.contentType = contentType;
		this.bodyStream = null;
		this.bodyXml = null;
		this.bodyText = bodyText;
	}
	
	/**
	 * Returns the response body as a String.
	 * @return
	 * @throws HttpException 
	 */
	public String asText() throws HttpException {
		if (bodyText != null)
			return bodyText;
		
//		if (bodyStream != null) {
//            Writer writer = new StringWriter();
// 
//            char[] buffer = new char[1024];
//            try {
//                Reader reader = new BufferedReader(new InputStreamReader(bodyStream, "UTF-8"));
//                int n;
//                while ((n = reader.read(buffer)) != -1) {
//                    writer.write(buffer, 0, n);
//                }
//            } catch (UnsupportedEncodingException e) {
//				// unable to open stream
//				e.printStackTrace();
//			} catch (IOException e) {
//				// unable to read buffer
//				e.printStackTrace();
//			} finally {
//            	try {
//					bodyStream.close();
//					bodyStream = null;
//				} catch (IOException e) {
//					throw new HttpException("Unable to close stream while reading response body", e);
//				}
//            }
//            bodyText = writer.toString();
//        }
		
		else if (bodyXml != null) {
	    	try {
				Transformer transformer = TransformerFactory.newInstance().newTransformer();
				transformer.setOutputProperty(OutputKeys.INDENT, "yes");
				StreamResult result = new StreamResult(new StringWriter());
				DOMSource source = new DOMSource(bodyXml);
				transformer.transform(source, result);
				bodyText = result.getWriter().toString();
				
			} catch (TransformerException e) {
				throw new HttpException("Unable to serialize body XML Document as string", e);
			}
		}
		
		return bodyText;
	}
	
	/**
	 * Returns the response body as a InputStream.
	 * @return
	 * @throws HttpException 
	 */
	public InputStream asStream() throws HttpException {
		if (bodyStream != null)
			return bodyStream;
		
		if (bodyText == null)
			asText();
		
		if (bodyText != null) {
			try {
				return new ByteArrayInputStream(bodyText.getBytes("utf-8"));
	        } catch(UnsupportedEncodingException e) {
	            throw new HttpException("Unable to open body string as stream", e);
	        }
		}
		
		return null;
	}
	
	/**
	 * Returns the response body as an XML Document.
	 * @return
	 * @throws HttpException 
	 */
	public Document asXml() throws HttpException {
		if (bodyXml != null)
			return bodyXml;
		
		if (bodyText == null)
			asText();
		
		bodyXml = XML.getXml(bodyText);
		
		return bodyXml;
	}
	
}
