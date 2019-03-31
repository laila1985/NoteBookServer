package com.notebookserver.exceptions;

import org.python.core.PySystemState;

public class PytonExecutionException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

		

	public PytonExecutionException(PySystemState systemState) {
		super("Error Ocures when the pyton code was executed. Please check the code before second second submission!!"+ systemState);
	}

}
