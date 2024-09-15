package com.r_tech.ecommerce.request;
import org.slf4j.Logger;

import org.slf4j.LoggerFactory;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AddItemRequest {
	
	private Long productId;
    
    private Long quantity;
    
    
    
    

	public AddItemRequest() {
		
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	@Override
	public String toString() {
		return "AddItemRequest [productId=" + productId + ", quantity=" + quantity + "]";
	}
	
    private static final Logger logger = LoggerFactory.getLogger(AddItemRequest.class);
	
	 @JsonCreator
	public AddItemRequest(@JsonProperty("productId") Long productId, @JsonProperty("quantity") Long quantity) {
	        logger.debug("Deserializing AddItemRequest:--------- productId={}, quantity={}", productId, quantity);

	        this.productId = productId;

	        this.quantity = quantity;

	    }
	
	
    
   

}
