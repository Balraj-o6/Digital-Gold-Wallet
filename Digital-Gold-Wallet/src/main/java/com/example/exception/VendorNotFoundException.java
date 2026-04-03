package com.example.exception;


/**
 * @author Anant
 */

@SuppressWarnings("serial")
public class VendorNotFoundException extends RuntimeException {
	public VendorNotFoundException(String message) {
		super(message);
	}
}
