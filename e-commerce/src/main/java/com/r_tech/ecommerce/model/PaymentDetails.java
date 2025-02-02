package com.r_tech.ecommerce.model;

import com.r_tech.ecommerce.domain.PaymentStatus;
import lombok.Data;

@Data
public class PaymentDetails {
	
	private String paymentMethod;
	
	private PaymentStatus status;

	public void setStatus(PaymentStatus status) {
		this.status = status;
	}

	private String paymentid;
	
	private String razorpayPaymentLinkId;
	
	private String razorpayPaymentLinkReferenceId;
	
	private String razorPaymentLinkStatus;
	
	private String razorpayPaymentId;

	public PaymentDetails() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod; 
	}


	public String getPaymentid() {
		return paymentid;
	}

	public void setPaymentid(String paymentid) {
		this.paymentid = paymentid;
	}

	public String getRazorpayPaymentLinkId() {
		return razorpayPaymentLinkId;
	}

	public void setRazorpayPaymentLinkId(String razorpayPaymentLinkId) {
		this.razorpayPaymentLinkId = razorpayPaymentLinkId;
	}

	public String getRazorpayPaymentLinkReferenceId() {
		return razorpayPaymentLinkReferenceId;
	}

	public void setRazorpayPaymentLinkReferenceId(String razorpayPaymentLinkReferenceId) {
		this.razorpayPaymentLinkReferenceId = razorpayPaymentLinkReferenceId;
	}

	public String getRazorPaymentLinkStatus() {
		return razorPaymentLinkStatus;
	}

	public void setRazorPaymentLinkStatus(String razorPaymentLinkStatus) {
		this.razorPaymentLinkStatus = razorPaymentLinkStatus;
	}

	public String getRazorpayPaymentId() {
		return razorpayPaymentId;
	}

	public void setRazorpayPaymentId(String razorpayPaymentId) {
		this.razorpayPaymentId = razorpayPaymentId;
	}

	public PaymentDetails(String paymentMethod, PaymentStatus status, String paymentid, String razorpayPaymentLinkId,
						  String razorpayPaymentLinkReferenceId, String razorPaymentLinkStatus, String razorpayPaymentId) {
		super();
		this.paymentMethod = paymentMethod;
		this.status = status;
		this.paymentid = paymentid;
		this.razorpayPaymentLinkId = razorpayPaymentLinkId;
		this.razorpayPaymentLinkReferenceId = razorpayPaymentLinkReferenceId;
		this.razorPaymentLinkStatus = razorPaymentLinkStatus;
		this.razorpayPaymentId = razorpayPaymentId;
	}
	
	

}
