package com.example.exception;

@SuppressWarnings("serial")
public class TransactionNotFoundException extends RuntimeException {
	public TransactionNotFoundException(String msg) {
		super(msg);
	}

}
