package com.example.MyEcommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.MyEcommerce.model.AuthenticationToken;
import com.example.MyEcommerce.model.User;

@Repository
public interface TokenRepo extends JpaRepository<AuthenticationToken, Integer> {

	AuthenticationToken findByUser(User user);
	AuthenticationToken findByToken(String token);
}
