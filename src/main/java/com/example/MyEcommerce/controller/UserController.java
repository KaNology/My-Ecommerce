package com.example.MyEcommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.MyEcommerce.dto.ResponseDto;
import com.example.MyEcommerce.dto.user.SigninDto;
import com.example.MyEcommerce.dto.user.SigninResponseDto;
import com.example.MyEcommerce.dto.user.SignupDto;
import com.example.MyEcommerce.service.UserService;

@RequestMapping("/user")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class UserController {
	@Autowired
	UserService userService;
	
	@PostMapping("/signup")
	public ResponseDto signup(@RequestBody SignupDto signupDto) {
		return userService.signUp(signupDto);
	}
	
	@PostMapping("/signin")
	public SigninResponseDto signin(@RequestBody SigninDto signInDto) {
		return userService.signIn(signInDto);
	}
}
