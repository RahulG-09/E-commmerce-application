package com.ecommerce.orders.service;

import com.ecommerce.orders.Repository.CartItemRepository;

import com.ecommerce.orders.dto.CartItemRequest;
import com.ecommerce.orders.model.CartItem;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CartService {

   // private final ProductRepository productRepository;
   // private final UserRepository userRepository;
    private final CartItemRepository cartItemRepository;

    public boolean addToCart(String userId, CartItemRequest request) {
      /* Optional<Product> productOpt=productRepository.findById(request.getProductId());

       if(productOpt.isEmpty()){
           return false;
       }
       Product product= productOpt.get();
       if(product.getStockQuantity()<request.getQuantity()){
           return false;
       }

       Optional<User> userOpt= userRepository.findById(Long.valueOf(userId));
       User  user= userOpt.get();
*/
        CartItem existingCartItem= cartItemRepository.findByUserIdAndProductId(userId,request.getProductId());

        if (existingCartItem != null) {

            existingCartItem.setQuantity(existingCartItem.getQuantity()+request.getQuantity());
            existingCartItem.setPrice(BigDecimal.valueOf(1000.00));
            cartItemRepository.save(existingCartItem);

        }else {
            CartItem cartItem= new CartItem();
            cartItem.setProductId(Long.valueOf(request.getProductId()));
            cartItem.setUserId(Long.valueOf(userId));
            cartItem.setQuantity(request.getQuantity());
            cartItem.setPrice(BigDecimal.valueOf(100.00));
            cartItemRepository.save(cartItem);

        }
        return true;


    }

    public boolean deleteItemFromCart(Long productId,String userId) {
       CartItem cartItem=cartItemRepository.findByUserIdAndProductId(userId, String.valueOf(productId));

        if(cartItem!=null){
            cartItemRepository.deleteByUserIdAndProductId(userId, String.valueOf(productId));
            return  true;


        }



return false;
    }

    public List<CartItem> getcart(String  userId) {

       return cartItemRepository.findByUserId(userId);


    }

    public void clearCart(String userId) {
       cartItemRepository.deleteByUserId(userId);
    }
}
