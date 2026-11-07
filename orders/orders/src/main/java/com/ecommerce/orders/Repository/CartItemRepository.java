package com.ecommerce.orders.Repository;

import com.ecommerce.orders.model.CartItem;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem,Long> {


    void deleteByUserIdAndProductId(String userId, String productId) ;


    CartItem findByUserIdAndProductId(String userId, String productId);

    List<CartItem> findByUserId(String  userId);

    Object deleteByUserId(String userId);
}
