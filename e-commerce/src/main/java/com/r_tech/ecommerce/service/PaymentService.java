package com.r_tech.ecommerce.service;

import com.r_tech.ecommerce.exception.OrderNotFoundException;
import com.r_tech.ecommerce.response.ApiResponse;
import com.r_tech.ecommerce.response.PaymentLinkResponse;
import com.razorpay.RazorpayException;

public interface PaymentService {

    PaymentLinkResponse createPaymentLink(Long orderId) throws OrderNotFoundException, RazorpayException;

    ApiResponse processPaymentRedirect(String paymentId, Long orderId) throws OrderNotFoundException, RazorpayException;
}
