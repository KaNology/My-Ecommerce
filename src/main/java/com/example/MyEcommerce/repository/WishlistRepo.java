package com.example.MyEcommerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.MyEcommerce.model.User;
import com.example.MyEcommerce.model.Wishlist;

@Repository
public interface WishlistRepo extends JpaRepository<Wishlist, Integer> {
	List<Wishlist> findAllByUserOrderByCreatedDateDesc(User user);
}
