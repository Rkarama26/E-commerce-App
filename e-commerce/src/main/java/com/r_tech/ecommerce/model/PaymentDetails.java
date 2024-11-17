package com.r_tech.ecommerce.model;

import com.r_tech.ecommerce.enums.PaymentStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDetails {
	
	private String paymentMethod;
	
	private String status;
	
	private String paymentid;
	
	private String razorpayPaymentLinkId;
	
	private String razorpayPaymentLinkReferenceId;
	
	private String razorPaymentLinkStatus;
	
	private String razorpayPaymentId;

}