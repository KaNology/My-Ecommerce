package com.example.MyEcommerce.service;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.MyEcommerce.exceptions.AuthenticationFailException;
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

	public User getUser(String token) {
		AuthenticationToken userToken = tokenRepo.findByToken(token);
		if (Objects.isNull(userToken)) {
			return null;
		}
		
		return userToken.getUser();
	}
	
	public void authenticate(String token) throws AuthenticationFailException {
		// check if token exist
		if(Objects.isNull(token)) {
			throw new AuthenticationFailException("token not found");
		}
		
		if(Objects.isNull(getUser(token))) {
			throw new AuthenticationFailException("token not valid");
		}
	}
}
