package com.ecommerce.orders.controller;


import com.ecommerce.orders.dto.CartItemRequest;
import com.ecommerce.orders.model.CartItem;
import com.ecommerce.orders.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;
    @PostMapping()
    public ResponseEntity<String> addToCart(
            @RequestHeader("X-User-Id") String userId,
            @RequestBody CartItemRequest request){
       if(! cartService.addToCart(userId, request)){
           return  ResponseEntity.badRequest().body("product or user not found");
       }
        return ResponseEntity.status(HttpStatus.CREATED).build();

    }
    @DeleteMapping("/items/{productId}")
    public ResponseEntity<Void>removeFromCart(
            @RequestHeader("X-User-id") String userId,
            @PathVariable Long productId){

        Boolean deleted=cartService.deleteItemFromCart(productId,userId);
        return deleted? ResponseEntity.noContent().build() :ResponseEntity.badRequest().build();



    }
    @GetMapping()
    public ResponseEntity<List<CartItem>>getCart(@RequestHeader("X-User-id") String userId){
      return   ResponseEntity.ok(cartService.getcart(userId));
    }




}
