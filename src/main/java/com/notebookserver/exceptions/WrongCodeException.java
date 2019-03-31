package com.notebookserver.exceptions;

public class WrongCodeException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public WrongCodeException(String message) {
		super(message);
	}

}
