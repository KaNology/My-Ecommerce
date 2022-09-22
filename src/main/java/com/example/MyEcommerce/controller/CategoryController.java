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
import org.springframework.web.bind.annotation.RestController;

import com.example.MyEcommerce.common.ApiResponse;
import com.example.MyEcommerce.model.Category;
import com.example.MyEcommerce.service.CategoryService;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*") // https://stackoverflow.com/questions/60610175/
@RequestMapping("/category")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	@PostMapping("/create")
	public ResponseEntity<ApiResponse> createCategory(@RequestBody Category category) {
		categoryService.createCategory(category);
		return new ResponseEntity<>(new ApiResponse(true, "successfully created a category"), HttpStatus.CREATED);
	}

	@GetMapping("/list")
	public List<Category> listCategory() {
		return categoryService.listCategory();
	}

	@PostMapping("/update/{categoryId}")
	public ResponseEntity<ApiResponse> updateCategory(@PathVariable("categoryId") int categoryId,
			@RequestBody Category newCategory) {

		if (!categoryService.findById(categoryId)) {
			return new ResponseEntity<>(new ApiResponse(false, "category does not exist"), HttpStatus.NOT_FOUND);
		}

		categoryService.editCategory(categoryId, newCategory);
		return new ResponseEntity<>(new ApiResponse(true, "successfully updated the category"), HttpStatus.OK);
	}
}
