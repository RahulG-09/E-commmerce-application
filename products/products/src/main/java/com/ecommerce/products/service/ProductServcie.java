package com.ecommerce.products.service;

import com.ecommerce.products.repository.ProductRepository;
import com.ecommerce.products.dto.ProductRequest;
import com.ecommerce.products.dto.ProductResponse;
import com.ecommerce.products.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServcie {

    private final ProductRepository productRepository;

    public ProductResponse createProduct(ProductRequest productRequest){

        Product product= new Product();
        UpdateProductFromRequest(product, productRequest);
        Product savedProduct= productRepository.save(product);
        return mapToProductResponse(savedProduct);




    }

    private ProductResponse mapToProductResponse(Product savedProduct) {

        ProductResponse productResponse= new ProductResponse();
        productResponse.setName(savedProduct.getName());
        productResponse.setId(savedProduct.getId());
        productResponse.setActivity(savedProduct.getActive());
        productResponse.setCategory(savedProduct.getCategory());
        productResponse.setDescription(savedProduct.getDescription());
        productResponse.setPrice(savedProduct.getPrice());
        productResponse.setImageUrl(savedProduct.getImageUrl());
        productResponse.setStockQuantity(savedProduct.getStockQuantity());
        return  productResponse;


    }

    private void UpdateProductFromRequest(Product product, ProductRequest productRequest) {

        product.setName(productRequest.getName());
        product.setCategory(productRequest.getCategory());
        product.setDescription(productRequest.getDescription());
        product.setPrice(productRequest.getPrice());
        product.setImageUrl(productRequest.getImageUrl());
        product.setStockQuantity(productRequest.getStockQuantity());

    }


    public Optional<ProductResponse> updateProduct(Long id, ProductRequest productRequest) {

                      return productRepository.findById(id).
                              map( existingProduct->{
                                  UpdateProductFromRequest(existingProduct,productRequest);
                                  Product savedProduct=productRepository.save(existingProduct);
                                  return mapToProductResponse(savedProduct);

                              });




    }

    public List<ProductResponse> getProducts() {

      return productRepository.findByActiveTrue().stream()
              .map(this::mapToProductResponse).collect(Collectors.toList());


    }

    public boolean deleteProduct(Long id) {
      return  productRepository.findById(id)
              .map(product->{
              product.setActive(false);
              productRepository.save(product);
              return true;

      }).orElse(false);
    }


    public List<ProductResponse> searchProduct(String keyword) {
       return productRepository.searchProduct(keyword).stream().
                map(this::mapToProductResponse).collect(Collectors.toList());
    }

}
