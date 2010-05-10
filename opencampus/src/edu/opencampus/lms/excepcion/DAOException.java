package edu.opencampus.lms.excepcion;

public class DAOException extends Exception {

	private static final long serialVersionUID = -7062699595250904601L;

	public DAOException() {
		super();
	}
	
	public DAOException(String message) {
		super(message);
	}
	
	public DAOException(Exception exception) {
		super(exception.toString());
	}

}
