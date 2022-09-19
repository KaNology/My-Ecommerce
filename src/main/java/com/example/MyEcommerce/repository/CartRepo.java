package com.example.MyEcommerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.MyEcommerce.model.Cart;
import com.example.MyEcommerce.model.User;

@Repository
public interface CartRepo extends JpaRepository<Cart, Integer> {
	List<Cart> findAllByUserOrderByCreatedDateDesc(User user);
}
