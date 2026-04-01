package com.example.exception;

@SuppressWarnings("serial")
public class VendorNotFoundException extends RuntimeException {
	public VendorNotFoundException(String message) {
		super(message);
	}
}
