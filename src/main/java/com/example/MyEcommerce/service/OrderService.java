package com.example.MyEcommerce.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.MyEcommerce.dto.order.CheckoutItemDto;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;

@Service
public class OrderService {

	@Value("${BASE_URL}")
	private String baseURL;
	@Value("${STRIPE_SECRET_KEY}")
	private String apiKey;

	public Session createSession(List<CheckoutItemDto> checkoutItemDtoList) throws StripeException {

		// success and failure urls
		String successURL = baseURL + "payment/success";
		String failureURL = baseURL + "payment/failed";

		Stripe.apiKey = apiKey;

		List<SessionCreateParams.LineItem> sessionItemList = new ArrayList<>();

		for (CheckoutItemDto item : checkoutItemDtoList) {
			sessionItemList.add(createSessionLineItem(item));
		}

		SessionCreateParams params = SessionCreateParams.builder()
				.addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
				.setMode(SessionCreateParams.Mode.PAYMENT)
				.setCancelUrl(failureURL)
				.addAllLineItem(sessionItemList)
				.setSuccessUrl(successURL)
				.build();
		
		return Session.create(params);
	}

	private SessionCreateParams.LineItem createSessionLineItem(CheckoutItemDto item) {
		// TODO Auto-generated method stub
		return SessionCreateParams.LineItem.builder().setPriceData(createPriceData(item))
				.setQuantity(Long.parseLong(String.valueOf(item.getQuanity()))).build();
	}

	private SessionCreateParams.LineItem.PriceData createPriceData(CheckoutItemDto item) {
		// TODO Auto-generated method stub
		return SessionCreateParams.LineItem.PriceData.builder().setCurrency("usd")
				.setUnitAmount((long) (item.getPrice() * 100))
				.setProductData(SessionCreateParams.LineItem.PriceData.ProductData.builder()
						.setName(item.getProductName()).build())
				.build();
	}

}
