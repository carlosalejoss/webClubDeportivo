package net.codejava.spring.exceptions;

public class dataAccessExceptions extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public dataAccessExceptions(String message) {
			super(message);
	}
	
	public dataAccessExceptions(String message, Throwable cause) {
		super(message, cause);
	}
}