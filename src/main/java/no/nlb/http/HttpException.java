package no.nlb.http;

/**
 * A generic error thrown by the Http Client Library.
 * 
 * @author jostein
 */
public class HttpException extends Exception {
	private static final long serialVersionUID = 1L;
	
	public HttpException(String message, Throwable cause) {
		super(message, cause);
	}

	public HttpException(String message) {
		super(message);
	}

	public HttpException(Throwable cause) {
		super(cause);
	}
	
}
