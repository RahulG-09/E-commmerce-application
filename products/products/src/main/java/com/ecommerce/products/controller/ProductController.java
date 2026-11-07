package com.ecommerce.products.controller;


import com.ecommerce.products.dto.ProductRequest;
import com.ecommerce.products.dto.ProductResponse;
import com.ecommerce.products.service.ProductServcie;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/products/")
public class ProductController {

   private final ProductServcie productServcie;

    @PostMapping()
    public  ResponseEntity<ProductResponse> createProduct(@RequestBody ProductRequest productRequest){


        return  new ResponseEntity<ProductResponse>(productServcie.createProduct(productRequest), HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity<List<ProductResponse>> getProducts(){


        return  ResponseEntity.ok(productServcie.getProducts());
    }


    @PutMapping("/{id}")
    public  ResponseEntity<ProductResponse> updateProduct(@PathVariable Long id,
            @RequestBody ProductRequest productRequest){


        return productServcie.updateProduct(id,productRequest).
                map(ResponseEntity::ok).
                orElseGet(()->ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public  ResponseEntity<ProductResponse> DeleteProduct(@PathVariable Long id){


        boolean deleted =productServcie.deleteProduct(id);
               return deleted? ResponseEntity.noContent().build():ResponseEntity.notFound().build();
    }

   @GetMapping("/search")
    public ResponseEntity<List<ProductResponse>>searchProduct(@RequestParam String keyword) {

        return ResponseEntity.ok(productServcie.searchProduct(keyword));
    }
    //request param is used to fetch waluve from keywords  @RequestParam
    //From the query parameters (after ?) where path varibale directly from url



}
