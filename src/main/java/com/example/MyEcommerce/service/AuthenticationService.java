package com.example.MyEcommerce.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.MyEcommerce.model.AuthenticationToken;
import com.example.MyEcommerce.model.User;
import com.example.MyEcommerce.repository.TokenRepo;

@Service
public class AuthenticationService {

	@Autowired
	TokenRepo tokenRepo;
	
	public void saveConfirmationToken(AuthenticationToken authToken) {
		tokenRepo.save(authToken);
	}

	public AuthenticationToken getToken(User user) {
	 	return tokenRepo.findByUser(user);
	}

}
