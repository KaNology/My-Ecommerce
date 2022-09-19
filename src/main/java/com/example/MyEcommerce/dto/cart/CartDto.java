package com.example.MyEcommerce.dto.cart;

import java.util.List;

public class CartDto {
	List<CartItemDto> cartItems;
	private double totalCost;
	public List<CartItemDto> getCartItems() {
		return cartItems;
	}
	public void setCartItems(List<CartItemDto> cartItems) {
		this.cartItems = cartItems;
	}
	public double getTotalCost() {
		return totalCost;
	}
	public void setTotalCost(double totalCost) {
		this.totalCost = totalCost;
	}
	public CartDto() {
		super();
	}
}
