package com.example.MyEcommerce.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;
import java.util.Optional;

import javax.xml.bind.DatatypeConverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.MyEcommerce.dto.ResponseDto;
import com.example.MyEcommerce.dto.user.SigninDto;
import com.example.MyEcommerce.dto.user.SigninResponseDto;
import com.example.MyEcommerce.dto.user.SignupDto;
import com.example.MyEcommerce.exceptions.AuthenticationFailException;
import com.example.MyEcommerce.exceptions.CustomException;
import com.example.MyEcommerce.model.AuthenticationToken;
import com.example.MyEcommerce.model.User;
import com.example.MyEcommerce.repository.UserRepo;

@Service
public class UserService {
	@Autowired
	UserRepo userRepo;
	@Autowired
	AuthenticationService authService;

	@Transactional
	public ResponseDto signUp(SignupDto signupDto) {
		// Check if user email already exists
		if (Objects.nonNull(userRepo.findByEmail(signupDto.getEmail()))) {
			throw new CustomException("user email already present");
		}

		// hash the password
		String encryptedPassword = signupDto.getPassword();
		try {
			encryptedPassword = hashPassword(signupDto.getPassword());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			throw new CustomException(e.getMessage());
		}

		// save the user
		User user = new User(signupDto.getFirstName(), signupDto.getLastName(), signupDto.getEmail(),
				encryptedPassword);
		
		userRepo.save(user);

		// create the token
		final AuthenticationToken authToken = new AuthenticationToken(user);
		authService.saveConfirmationToken(authToken);
		
		ResponseDto responseDto = new ResponseDto("success", "user created successfully");
		return responseDto;
	}

	private String hashPassword(String password) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(password.getBytes());
		byte[] digest = md.digest();
		String hash = DatatypeConverter.printHexBinary(digest).toUpperCase();
		return hash;
	}

	public SigninResponseDto signIn(SigninDto signInDto) {
		// find user by email
		
		User user = userRepo.findByEmail(signInDto.getEmail());
		
		if (Objects.isNull(user)) {
			throw new AuthenticationFailException("user does not exist");
		}
		
		// hash the password and compare the password
		
		try {
			if (user.getPassword().equals(hashPassword(signInDto.getPassword()))) {
				throw new AuthenticationFailException("wrong password");
			}
		}
		catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		// retrieve the token
		
		AuthenticationToken token = authService.getToken(user);
		
		if (Objects.isNull(token)) {
			throw new CustomException("token is not present");
		}
		
		// return response
		return new SigninResponseDto("success", token.getToken());
	}
}
