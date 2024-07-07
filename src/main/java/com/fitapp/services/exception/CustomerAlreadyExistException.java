package com.fitapp.services.exception;

public class CustomerAlreadyExistException extends RuntimeException {
	public CustomerAlreadyExistException(String message) {
		super(message);
	}
}
