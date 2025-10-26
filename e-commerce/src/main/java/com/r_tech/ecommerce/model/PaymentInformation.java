package com.r_tech.ecommerce.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class PaymentInformation {

    private String cardholderName;

    private String cardNumber;

    private LocalDate expirationDate;

    private String cvv;

}
