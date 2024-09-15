package com.r_tech.ecommerce.TestController;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.r_tech.ecommerce.request.AddItemRequest;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/api/test")
public class TestController {

    @PutMapping("/add")
    public ResponseEntity<String> testAddItemToCart(@RequestBody AddItemRequest req) {
    	
    	System.out.print("additemrequest" + req);
    	System.out.print("productid" + req.getProductId());
    	System.out.print("quantity" + req.getQuantity());


    	
    	
        return ResponseEntity.ok("Product ID: " + req.getProductId() + ", Quantity: " + req.getQuantity());
    }
}
