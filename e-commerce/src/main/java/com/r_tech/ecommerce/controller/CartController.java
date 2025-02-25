package com.r_tech.ecommerce.controller;

import com.r_tech.ecommerce.exception.ProductException;
import com.r_tech.ecommerce.exception.UserException;
import com.r_tech.ecommerce.model.Cart;
import com.r_tech.ecommerce.model.User;
import com.r_tech.ecommerce.request.AddItemRequest;
import com.r_tech.ecommerce.response.ApiResponse;
import com.r_tech.ecommerce.service.CartItemService;
import com.r_tech.ecommerce.service.CartService;
import com.r_tech.ecommerce.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@RequestMapping("/api/cart")
@Tag(name = "Cart Controller", description = "find user cart and , add items to the cart")
public class CartController {

    private static final Logger logger = LoggerFactory.getLogger(CartController.class);



    private CartService cartService;

    private UserService userService;

    private CartItemService cartItemService;

    public CartController(CartItemService cartItemService, CartService cartService, UserService userService) {
        this.cartItemService = cartItemService;
        this.cartService = cartService;
        this.userService = userService;
    }

    @GetMapping("/")
    @Operation(description = "find cart by user id")
    public ResponseEntity<Cart> findUserCart(@RequestHeader("Authorization") String jwt) throws UserException {

        User user = userService.findUserProfileByJwt(jwt);

        Cart cart = cartService.findUserCart(user.getId());

        return new ResponseEntity<Cart>(cart, HttpStatus.OK);
    }


    @PutMapping("/add")
    @Operation(description = "add item to cart")
    public ResponseEntity<ApiResponse> addItemToCart(@RequestBody AddItemRequest req,
                                                     @RequestHeader("Authorization") String jwt) throws UserException, ProductException {


        logger.info("the product id {}", req.getProductId());
        logger.info("the product quantity {}", req.getQuantity());


        try {
            User user = userService.findUserProfileByJwt(jwt);
          //  logger.info("the user is {} " + user.getId());
            cartService.addItemToCart(user.getId(), req);


            ApiResponse res = new ApiResponse("Item added to cart", true);
            return ResponseEntity.ok(res);

        } catch (UserException | ProductException e) {
            ApiResponse errorResponse = new ApiResponse(e.getMessage(), false);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        } catch (Exception e) {
            logger.error("Unexpected error: {}", e.getMessage(), e);
            ApiResponse errorResponse = new ApiResponse("An unexpected error occurred", false);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }


    }


}
