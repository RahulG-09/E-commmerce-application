package com.ecommerce.orders.dto;

import lombok.Data;

@Data
public class CartItemRequest {


    private String productId;
    private Integer quantity;
}
