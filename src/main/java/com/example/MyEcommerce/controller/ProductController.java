package com.example.MyEcommerce.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.MyEcommerce.common.ApiResponse;
import com.example.MyEcommerce.dto.ProductDto;
import com.example.MyEcommerce.model.Category;
import com.example.MyEcommerce.repository.CategoryRepo;
import com.example.MyEcommerce.service.ProductService;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/product")
public class ProductController {
	@Autowired
	ProductService productService;
	@Autowired
	CategoryRepo categoryRepo;

	@PostMapping("/create")
	public ResponseEntity<ApiResponse> createProduct(@RequestBody ProductDto productDto) {
		Optional<Category> optionalCategory = categoryRepo.findById(productDto.getCategoryId());
		if (!optionalCategory.isPresent()) {
			return new ResponseEntity<>(new ApiResponse(false, "Category does not exist!"), HttpStatus.BAD_REQUEST);
		}

		productService.createProduct(productDto, optionalCategory.get());
		return new ResponseEntity<>(new ApiResponse(true, "Product has been added!"), HttpStatus.CREATED);
	}

	@GetMapping("/")
	public ResponseEntity<List<ProductDto>> getProducts() {
		List<ProductDto> products = productService.getAllProducts();
		return new ResponseEntity<>(products, HttpStatus.OK);
	}

	@PostMapping("/update/{productId}")
	public ResponseEntity<ApiResponse> updateProduct(@PathVariable("productId") Integer productId,
			@RequestBody ProductDto productDto) throws Exception {
		Optional<Category> optionalCategory = categoryRepo.findById(productDto.getCategoryId());
		if (!optionalCategory.isPresent()) {
			return new ResponseEntity<>(new ApiResponse(false, "Category does not exist!"), HttpStatus.BAD_REQUEST);
		}

		productService.updateProduct(productDto, productId);
		return new ResponseEntity<>(new ApiResponse(true, "Product has been updated!"), HttpStatus.CREATED);
	}
}
