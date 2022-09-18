package com.example.MyEcommerce.exceptions;

public class AuthenticationFailException extends IllegalArgumentException {
	public AuthenticationFailException(String msg) {
		super(msg);
	}
}
