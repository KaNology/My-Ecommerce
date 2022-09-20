package com.example.MyEcommerce.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.MyEcommerce.dto.ProductDto;
import com.example.MyEcommerce.exceptions.ProductNotExistsException;
import com.example.MyEcommerce.model.Category;
import com.example.MyEcommerce.model.Product;
import com.example.MyEcommerce.repository.ProductRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
	@Autowired
	ProductRepo productRepo;
	
	public void createProduct(ProductDto productDto, Category category) {
		Product product = new Product();
		product.setDescription(productDto.getDescription());
		product.setImageURL(productDto.getImageURL());
		product.setName(productDto.getName());
		product.setCategory(category);
		product.setPrice(productDto.getPrice());
		
		productRepo.save(product);
	}
	
	public ProductDto getProductDto(Product product) {
		ProductDto productDto = new ProductDto();
		productDto.setId(product.getId());
		productDto.setDescription(product.getDescription());
		productDto.setImageURL(product.getImageURL());
		productDto.setName(product.getName());
		productDto.setCategoryId(product.getCategory().getId());
		productDto.setPrice(product.getPrice());
		return productDto;
	}
	
	public List<ProductDto> getAllProducts(){
		List<Product> allProducts = productRepo.findAll();
		
		List<ProductDto> productDtos = new ArrayList<>();
		
		for(Product product : allProducts) {
			productDtos.add(getProductDto(product));
		}
		
		return productDtos;
	}

	public void updateProduct(ProductDto productDto, Integer productId) throws Exception {
		Optional<Product> optionalProduct = productRepo.findById(productId);
		
		if(optionalProduct.isEmpty()) {
			throw new Exception("product is not present");
		}
		
		Product product = optionalProduct.get();
		
		product.setDescription(productDto.getDescription());
		product.setImageURL(productDto.getImageURL());
		product.setName(productDto.getName());
		product.setPrice(productDto.getPrice());
		
		productRepo.save(product);
	}

	public Product findById(Integer productId) throws ProductNotExistsException {
		Optional<Product> product = productRepo.findById(productId);
		
		if(product.isEmpty()) {
			throw new ProductNotExistsException("product id is invalid" + productId);
		}
		
		return product.get();
	}
}
