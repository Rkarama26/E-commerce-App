package com.r_tech.ecommerce.response;

import lombok.Data;

@Data
public class PaymentLinkResponse {

    private String payment_link_id;
    private String payment_link_url;
}
