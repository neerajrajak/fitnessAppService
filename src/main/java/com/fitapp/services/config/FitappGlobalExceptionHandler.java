package com.fitapp.services.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.fitapp.services.exception.NumberNotFoundException;

@ControllerAdvice
public class FitappGlobalExceptionHandler {
	
	@ExceptionHandler(NumberNotFoundException.class)
	public ResponseEntity<Object> handleNumberNotFoundException(NumberNotFoundException e) {
		return new ResponseEntity<>("An unexpected error occurred: " + e.getMessage(),
				HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handleException(Exception e) {
		return new ResponseEntity<>("An unexpected error occurred: " + e.getMessage(),
				HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
