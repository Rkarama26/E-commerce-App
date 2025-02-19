package com.r_tech.ecommerce.controller;

import com.r_tech.ecommerce.exception.OrderNotFoundException;
import com.r_tech.ecommerce.response.ApiResponse;
import com.r_tech.ecommerce.response.PaymentLinkResponse;
import com.r_tech.ecommerce.service.PaymentService;
import com.razorpay.RazorpayException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@Slf4j
public class PaymentController {


    @Autowired
    private PaymentService paymentService;

    @PostMapping("/payments/{orderId}")
    public ResponseEntity<PaymentLinkResponse> createPaymentLink(@PathVariable Long orderId,
                                                                 @RequestHeader("Authorization") String jwt)throws OrderNotFoundException, RazorpayException {
        PaymentLinkResponse response = paymentService.createPaymentLink(orderId);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


    @GetMapping("/payments")
    public ResponseEntity<ApiResponse> redirect(@RequestParam(name = "payment_id") String paymentId,
                                                @RequestParam(name = "order_id") Long orderId)throws OrderNotFoundException, RazorpayException {
        ApiResponse response = paymentService.processPaymentRedirect(paymentId, orderId);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }
}
