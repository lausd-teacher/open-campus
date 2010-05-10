package edu.opencampus.lms.excepcion;

public class ServiceException extends Exception {

	private static final long serialVersionUID = 8295191320258782081L;

	public ServiceException() {
		super();
	}
	
	public ServiceException(String message) {
		super(message);
	}
	
	public ServiceException(Exception exception) {
		super(exception.toString());
	}

}
