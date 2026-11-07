package com.ecommerce.orders.service;

import com.ecommerce.orders.Repository.OrderRepository;
import com.ecommerce.orders.dto.OrderItemDTO;
import com.ecommerce.orders.dto.OrderResponse;
//import com.ecommerce.orders.model.*;
import com.ecommerce.orders.model.CartItem;
import com.ecommerce.orders.model.Order;
import com.ecommerce.orders.model.OrderItem;
import com.ecommerce.orders.model.OrderStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {
     private final CartService cartService;
     private final OrderRepository orderRepository;

     public Optional<OrderResponse> createOrder(String userId){

        //Validate for cart item
        List<CartItem> cartItem= cartService.getcart(userId);

        if(cartItem.isEmpty()){
            return Optional.empty();
        }

         //validate user
       /* Optional<User>userOptional =userRepository.findById(Long.valueOf(userId));

        if(userOptional.isEmpty()){
            return Optional.empty();
        }

        User user= userOptional.get();*/

 //calculate total price
         BigDecimal totalPrice=cartItem.stream().
                 map(CartItem::getPrice).reduce(BigDecimal.ZERO,BigDecimal::add);
        //create order

         Order order= new Order();
         order.setUserId(Long.valueOf(userId));
         order.setStatus(OrderStatus.CONFIRMED);
         order.setTotalAmount(totalPrice);
         List<OrderItem>orderItems= cartItem.stream()
                 .map(items-> new OrderItem(
                         null,items.getProductId(),items.getQuantity(),items.getPrice(),order
                 )).toList();
         order.setItem(orderItems);
        Order savedOrder= orderRepository.save(order);
        //clear cart

         cartService.clearCart(userId);

        return Optional.of(mapToOrderResponse(savedOrder));
    }

    private OrderResponse mapToOrderResponse(Order order) {
         return new OrderResponse(
                 order.getId(),
                 order.getTotalAmount(),
                 order.getStatus(),
                 order.getItem().stream().map(
                         orderItem->new OrderItemDTO(
                                 orderItem.getId(),
                                 orderItem.getProductId(),
                                 orderItem.getQuantity(),
                                 orderItem.getPrice(),
                                 orderItem.getPrice().multiply(new BigDecimal(orderItem.getQuantity()))


                         )).toList(), order.getCreatedAt()

         );


    }
}
