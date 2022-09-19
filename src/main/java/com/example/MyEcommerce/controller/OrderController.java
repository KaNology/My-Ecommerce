package com.example.MyEcommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.MyEcommerce.dto.order.CheckoutItemDto;
import com.example.MyEcommerce.dto.order.StripeResponse;
import com.example.MyEcommerce.service.AuthenticationService;
import com.example.MyEcommerce.service.OrderService;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;

@RestController
@RequestMapping("/order")
public class OrderController {
	@Autowired
	private AuthenticationService authService;
	@Autowired
	private OrderService orderService;

	@PostMapping("/create")
	public ResponseEntity<StripeResponse> checkoutList(@RequestBody List<CheckoutItemDto> checkoutItemDtoList)
			throws StripeException {
		Session session = orderService.createSession(checkoutItemDtoList);
		StripeResponse stripeResponse = new StripeResponse(session.getId());
		return new ResponseEntity<>(stripeResponse, HttpStatus.OK);
	}
}
