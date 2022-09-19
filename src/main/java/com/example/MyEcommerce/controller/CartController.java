package com.example.MyEcommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.MyEcommerce.common.ApiResponse;
import com.example.MyEcommerce.dto.cart.AddToCartDto;
import com.example.MyEcommerce.dto.cart.CartDto;
import com.example.MyEcommerce.model.User;
import com.example.MyEcommerce.service.AuthenticationService;
import com.example.MyEcommerce.service.CartService;

@RestController
@RequestMapping("/cart")
public class CartController {

	@Autowired
	CartService cartService;
	@Autowired
	AuthenticationService authService;

	@PostMapping("/add")
	public ResponseEntity<ApiResponse> addToCart(@RequestBody AddToCartDto addToCartDto,
			@RequestParam("token") String token) {

		// authenticate the token
		authService.authenticate(token);

		// find the user
		User user = authService.getUser(token);

		cartService.addToCart(addToCartDto, user);

		return new ResponseEntity<>(new ApiResponse(true, "product added to cart"), HttpStatus.CREATED);
	}

	@GetMapping("/")
	public ResponseEntity<CartDto> getCartItems(@RequestParam("token") String token) {

		// authenticate the token
		authService.authenticate(token);

		// find the user
		User user = authService.getUser(token);

		// get items
		CartDto cartDto = cartService.listCartItems(user);

		return new ResponseEntity<>(cartDto, HttpStatus.OK);
	}

	@DeleteMapping("/delete/{cartItemId}")
	public ResponseEntity<ApiResponse> deleteCartItem(@PathVariable("cartItemId") Integer itemId,
			@RequestParam("token") String token) {

		// authenticate the token
		authService.authenticate(token);

		// find the user
		User user = authService.getUser(token);

		cartService.deleteCartItem(itemId, user);
		
		return new ResponseEntity<>(new ApiResponse(true, "item removed from cart"), HttpStatus.OK);
	}
}
