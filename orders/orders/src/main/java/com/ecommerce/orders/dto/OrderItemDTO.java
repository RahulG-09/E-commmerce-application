package com.ecommerce.orders.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class OrderItemDTO {
    private Long id;
    private Long productid;
    private Integer quantity;
    private BigDecimal price;
    private BigDecimal subTotal;
}
