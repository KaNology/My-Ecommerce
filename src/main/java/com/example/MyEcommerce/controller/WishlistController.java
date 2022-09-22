package com.example.MyEcommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.MyEcommerce.common.ApiResponse;
import com.example.MyEcommerce.dto.ProductDto;
import com.example.MyEcommerce.model.Product;
import com.example.MyEcommerce.model.User;
import com.example.MyEcommerce.model.Wishlist;
import com.example.MyEcommerce.service.AuthenticationService;
import com.example.MyEcommerce.service.WishlistService;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/wishlist")
public class WishlistController {
	@Autowired
	WishlistService wishlistService;

	@Autowired
	AuthenticationService authService;

	@PostMapping("/add")
	public ResponseEntity<ApiResponse> addToWishlist(@RequestBody Product product,
			@RequestParam("token") String token) {

		// authenticate the token
		authService.authenticate(token);

		// find the user
		User user = authService.getUser(token);

		// add the item into the wishlist
		Wishlist wishlist = new Wishlist(user, product);

		wishlistService.createWishlist(wishlist);

		return new ResponseEntity<>(new ApiResponse(true, "added to wishlist"), HttpStatus.CREATED);
	}

	@GetMapping("/{token}")
	public ResponseEntity<List<ProductDto>> getWishlist(@PathVariable("token") String token) {

		// authenticate the token
		authService.authenticate(token);

		// find the user
		User user = authService.getUser(token);
		
		// get wishlist items of the user
		List<ProductDto> items = wishlistService.getWishlistForUser(user); 
		
		return new ResponseEntity<>(items, HttpStatus.OK);
	}
}
