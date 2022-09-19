package com.example.MyEcommerce.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.MyEcommerce.dto.cart.AddToCartDto;
import com.example.MyEcommerce.dto.cart.CartDto;
import com.example.MyEcommerce.dto.cart.CartItemDto;
import com.example.MyEcommerce.exceptions.CustomException;
import com.example.MyEcommerce.model.Cart;
import com.example.MyEcommerce.model.Product;
import com.example.MyEcommerce.model.User;
import com.example.MyEcommerce.repository.CartRepo;

@Service
public class CartService {

	@Autowired
	CartRepo cartRepo;
	@Autowired
	ProductService productService;

	public void addToCart(AddToCartDto addToCartDto, User user) {
		// check if the product id is valid
		Product product = productService.findById(addToCartDto.getProductId());
		
		// save the cart
		Cart cart = new Cart(product, user, addToCartDto.getQuantity());
		cartRepo.save(cart);
	}

	public CartDto listCartItems(User user) {
		List<Cart> cartList = cartRepo.findAllByUserOrderByCreatedDateDesc(user);
		
		List<CartItemDto> cartItems = new ArrayList<>();
		double totalPrice = 0;
		
		for (Cart cart : cartList) {
			CartItemDto cartItemDto = new CartItemDto(cart);
			cartItems.add(cartItemDto);
			
			totalPrice += cartItemDto.getQuantity() * cart.getProduct().getPrice();
		}
		
		CartDto cartDto = new CartDto();
		cartDto.setCartItems(cartItems);
		cartDto.setTotalCost(totalPrice);
		
		return cartDto;
	}

	public void deleteCartItem(Integer cartItemId, User user) throws CustomException {
		// check if the itemId belongs to this user
		// this prevents injection
		Optional<Cart> optionalCart = cartRepo.findById(cartItemId);
		
		if (optionalCart.isEmpty()) {
			throw new CustomException("cart item does not exist");
		}
		
		Cart cart = optionalCart.get();
		
		if (cart.getUser() != user) {
			throw new CustomException("cart item does not belong to user");
		}
		
		cartRepo.delete(cart);
	}
}
