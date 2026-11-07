package com.ecommerce.products.repository;

import com.ecommerce.products.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product  ,Long> {

    List<Product> findByActiveTrue();

    @Query(" SELECT p from products p where p.active=true And p.stockQuantity>0 and LOWER(p.name) LIKE  LOWER(CONCAT('%',:keyword, '%'))")
    List<Product> searchProduct( @Param("keyword") String keyword);
}
