package com.r_tech.ecommerce.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.r_tech.ecommerce.request.AddItemRequest;

public class JsonDeserializationTest {

    public static void main(String[] args) {
        // ObjectMapper instance
        ObjectMapper objectMapper = new ObjectMapper();

        // JSON string to be tested
        String json = "{\"productId\": 452, \"quantity\": 2}";

        try {
            // Deserialize JSON to AddItemRequest
            AddItemRequest request = objectMapper.readValue(json, AddItemRequest.class);

            // Print the deserialized object
            System.out.println("Deserialized AddItemRequest:");
            System.out.println("Product ID: " + request.getProductId());
            System.out.println("Quantity: " + request.getQuantity());

        } catch (Exception e) {
            // Print any exceptions that occur during deserialization
            System.err.println("Error during deserialization:");
            e.printStackTrace();
        }
    }
}
