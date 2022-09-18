package com.example.MyEcommerce.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.MyEcommerce.model.Category;
import com.example.MyEcommerce.repository.CategoryRepo;

@Service
public class CategoryService {
	
	@Autowired
	CategoryRepo categoryRepo;
	
	public void createCategory(Category category) {
		categoryRepo.save(category);
	}
	
	public boolean findById(int categoryId) {
		return categoryRepo.findById(categoryId).isPresent();
	}
	
	public List<Category> listCategory(){
		return categoryRepo.findAll();
	}
	
	public void editCategory(int categoryId, Category newCategory) {
		Category category = categoryRepo.getById(categoryId);
		category.setCategoryName(newCategory.getCategoryName());
		category.setDescription(newCategory.getDescription());
		category.setImageUrl(newCategory.getImageUrl());
		categoryRepo.save(category);
	}
}
