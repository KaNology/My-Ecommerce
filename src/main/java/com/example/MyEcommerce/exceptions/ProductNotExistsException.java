package com.example.MyEcommerce.exceptions;

public class ProductNotExistsException extends IllegalArgumentException {
	public ProductNotExistsException(String msg) {
		super(msg);
	}
}
