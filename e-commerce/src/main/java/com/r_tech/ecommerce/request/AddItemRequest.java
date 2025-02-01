package com.r_tech.ecommerce.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;


public class AddItemRequest {

    @NotNull(message = "Product ID cannot be null")
    private Long productId;

    private Integer quantity;

    public AddItemRequest() {

    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }


    public Integer getQuantity() {
        return (quantity != null) ? quantity : 1;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }


}
