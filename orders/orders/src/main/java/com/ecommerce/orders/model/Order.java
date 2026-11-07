package com.ecommerce.orders.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import  com.ecommerce.orders.model.OrderStatus;

@Entity(name="orders")
@Data
@NoArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

   private Long userId;
    private BigDecimal totalAmount;
   @Enumerated(EnumType.STRING)
    private OrderStatus status= OrderStatus.PENDING;

    @OneToMany(mappedBy = "order", cascade= CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> item= new ArrayList<>();
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
