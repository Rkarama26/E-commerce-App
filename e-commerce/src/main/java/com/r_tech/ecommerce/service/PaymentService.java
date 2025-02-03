package com.r_tech.ecommerce.service;

import com.r_tech.ecommerce.exception.OrderException;
import com.r_tech.ecommerce.response.ApiResponse;
import com.r_tech.ecommerce.response.PaymentLinkResponse;
import com.razorpay.RazorpayException;

public interface PaymentService {

    PaymentLinkResponse createPaymentLink(Long orderId) throws OrderException, RazorpayException;

    ApiResponse processPaymentRedirect(String paymentId, Long orderId) throws OrderException, RazorpayException;
}
