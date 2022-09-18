package com.example.MyEcommerce.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.MyEcommerce.dto.ProductDto;
import com.example.MyEcommerce.model.User;
import com.example.MyEcommerce.model.Wishlist;
import com.example.MyEcommerce.repository.WishlistRepo;

@Service
public class WishlistService {
	@Autowired
	WishlistRepo wishlistRepo;
	@Autowired
	ProductService productService;

	public void createWishlist(Wishlist wishlist) {
		wishlistRepo.save(wishlist);
	}

	public List<ProductDto> getWishlistForUser(User user) {
		List<Wishlist> items = wishlistRepo.findAllByUserOrderByCreatedDateDesc(user);
		
		List<ProductDto> products = new ArrayList<>();
		
		for(Wishlist item : items) {
			products.add(
					productService.getProductDto(item.getProduct())
				);
		}
		
		return products;
	}
}
