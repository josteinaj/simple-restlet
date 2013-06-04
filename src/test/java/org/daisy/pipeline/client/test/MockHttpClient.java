package org.daisy.pipeline.client.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Scanner;

import no.nlb.http.HttpClient;
import no.nlb.http.Http;
import no.nlb.http.HttpException;
import no.nlb.http.HttpResponse;

import org.w3c.dom.Document;

public class MockHttpClient implements HttpClient {

	public HttpResponse get(String url) throws HttpException {
		File responseFile = new File("src/test/resources/responses/"+url.replaceAll("^\\w+:\\/+", ""));
		if (Http.debug()) System.out.println("Reading mock response: "+responseFile.getAbsolutePath());
		try {
			return new HttpResponse(200, "OK", "Mock object retrieved successfully", "application/xml", readFile(responseFile));
		} catch (FileNotFoundException e) {
			if (Http.debug()) System.out.println("Unable to read mock response for: "+url);
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			if (Http.debug()) System.out.println("Unable to read mock response for: "+url);
			e.printStackTrace();
			return null;
		}
	}
	
	public HttpResponse post(String url, Document xml) throws HttpException {
		return null;
	}
	
	public HttpResponse post(String url, String text) throws HttpException {
		return null;
	}
	
	public HttpResponse post(String url, Map<String,File> parts) throws HttpException {
		return null;
	}

	public void setTimeout(Long ms) throws HttpException {}
	
	
	private String readFile(File file) throws IOException {
	    StringBuilder fileContents = new StringBuilder((int)file.length());
	    Scanner scanner = new Scanner(file);
	    String lineSeparator = System.getProperty("line.separator");

	    try {
	        while(scanner.hasNextLine()) {        
	            fileContents.append(scanner.nextLine() + lineSeparator);
	        }
	        return fileContents.toString();
	    } finally {
	        scanner.close();
	    }
	}
}
