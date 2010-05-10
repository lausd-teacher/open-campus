package edu.opencampus.lms.excepcion;

public class ActionException extends Exception {

	private static final long serialVersionUID = 8295191320258782081L;
	
	public ActionException() {
		super();
	}
	
	public ActionException(String message) {
		super(message);
	}
	
	public ActionException(Exception exception) {
		super(exception.toString());
	}

}
