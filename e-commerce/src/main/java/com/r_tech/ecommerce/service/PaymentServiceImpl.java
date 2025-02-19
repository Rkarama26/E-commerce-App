package com.r_tech.ecommerce.service;

import com.r_tech.ecommerce.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.r_tech.ecommerce.exception.OrderNotFoundException;
import com.r_tech.ecommerce.model.Order;
import com.r_tech.ecommerce.response.PaymentLinkResponse;
import com.razorpay.PaymentLink;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.json.JSONObject;
import com.r_tech.ecommerce.domain.OrderStatus;
import com.r_tech.ecommerce.domain.PaymentStatus;
import com.r_tech.ecommerce.response.ApiResponse;
import com.razorpay.Payment;

@Service
@Slf4j
public class PaymentServiceImpl implements PaymentService {

    @Value("${razorpay.api.key}")
    String apiKey;

    @Value("${razorpay.api.secret}")
    String apiSecret;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;

    public PaymentLinkResponse createPaymentLink(Long orderId) throws OrderNotFoundException, RazorpayException {
        Order order = orderService.findOrderById(orderId);

        try {
            RazorpayClient razorpayClient = new RazorpayClient(apiKey, apiSecret);

            JSONObject paymentLinkRequest = new JSONObject();
            paymentLinkRequest.put("amount", order.getTotalItem() * 100);
            paymentLinkRequest.put("currency", "INR");

            JSONObject customer = new JSONObject();
            customer.put("name", order.getUser().getFirstName());
            customer.put("email", order.getUser().getEmail());

            paymentLinkRequest.put("customer", customer);

            JSONObject notify = new JSONObject();
            notify.put("sms", true);
            notify.put("email", true);
            paymentLinkRequest.put("notify", notify);

            paymentLinkRequest.put("callback_url", "http://localhost:3000/payment/" + orderId);
            paymentLinkRequest.put("callback_method", "get");

            PaymentLink payment = razorpayClient.paymentLink.create(paymentLinkRequest);

            PaymentLinkResponse res = new PaymentLinkResponse();
            res.setPayment_link_id(payment.get("id"));
            res.setPayment_link_url(payment.get("short_url"));

            return res;
        } catch (RazorpayException e) {
            throw new RazorpayException(e.getMessage());
        }
    }

    public ApiResponse processPaymentRedirect(String paymentId, Long orderId) throws OrderNotFoundException, RazorpayException {
        Order order = orderService.findOrderById(orderId);
        RazorpayClient razorpayClient = new RazorpayClient(apiKey, apiSecret);

        log.info("payment id is {}: - "+paymentId+ " and order id {}:"+ orderId);
        try {
            Payment payment = razorpayClient.payments.fetch(paymentId);

            if ("captured".equals(payment.get("status"))) {
                order.getPaymentDetails().setPaymentid(paymentId);
                order.getPaymentDetails().setStatus(PaymentStatus.COMPLETED);
                order.setOrderStatus(OrderStatus.PLACED);
                orderRepository.save(order);
            }

            ApiResponse res = new ApiResponse();
            res.setMessage("Your Order Has Been Placed");
            res.setStatus(true);

            return res;
        } catch (Exception e) {
            throw new RazorpayException(e.getMessage());
        }
    }

}
