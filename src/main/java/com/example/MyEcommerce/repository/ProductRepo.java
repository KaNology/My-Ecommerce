package com.example.MyEcommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.MyEcommerce.model.Product;

@Repository
public interface ProductRepo extends JpaRepository<Product, Integer> {

}
