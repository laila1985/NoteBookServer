package com.notebookserver.controller;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.notebookserver.exceptions.PytonExecutionException;
import com.notebookserver.exceptions.WrongCodeException;
import com.notebookserver.model.ErrorDetails;

import org.springframework.http.HttpStatus;
@RestControllerAdvice
public class ExceptionHandlerControllerAdvice {
	
	@ExceptionHandler(value =WrongCodeException.class)
	  public final ResponseEntity<ErrorDetails> handleUserNotFoundException(WrongCodeException ex, WebRequest request) {
	    ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(),
	        request.getDescription(false));
	    return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
	  }
	
	
	@ExceptionHandler(value =NullPointerException.class)
	  public final ResponseEntity<ErrorDetails> handleNullPointerException(NullPointerException ex, WebRequest request) {
	    ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(),
	        request.getDescription(false));
	    return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
	  }
	
	
	
	@ExceptionHandler(Exception.class)
	  public final ResponseEntity<ErrorDetails> handleException(Exception ex, WebRequest request) {
	    ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(),
	        request.getDescription(false));
	    return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
	  }
	
	@ExceptionHandler(IOException.class)
	  public final ResponseEntity<ErrorDetails> handleIOException(IOException ex, WebRequest request) {
	    ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(),
	        request.getDescription(false));
	    return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
	  }
	
	@ExceptionHandler(FileNotFoundException.class)
	  public final ResponseEntity<ErrorDetails> handleFileNotFoundException(FileNotFoundException ex, WebRequest request) {
	    ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(),
	        request.getDescription(false));
	    return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
	  }
	
	@ExceptionHandler(UnsupportedEncodingException.class)
	  public final ResponseEntity<ErrorDetails> handleUnsupportedEncodingException(UnsupportedEncodingException ex, WebRequest request) {
	    ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(),
	        request.getDescription(false));
	    return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
	  }
	
	@ExceptionHandler(PytonExecutionException.class)
	  public final ResponseEntity<ErrorDetails> handlePytonExecutionException(PytonExecutionException ex, WebRequest request) {
	    ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(),
	        request.getDescription(false));
	    return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
	  }

}
