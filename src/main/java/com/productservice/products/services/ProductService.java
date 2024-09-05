package com.productservice.products.services;

import com.productservice.products.dtos.ProductDto;
import com.productservice.products.exceptions.NotFoundException;
import com.productservice.products.models.Product;
import org.springframework.stereotype.Service;

import java.util.List;


public interface ProductService {

    List<Product> getAllProducts();

     public Product getSingleProduct(Long productId) throws NotFoundException;

    Product addNewProduct(ProductDto product);

    /*
    Product object has only those fields filled which need to be updated.
    Everything else is null
     */

    Product updateProduct(Long productId, Product product);

    // update product with id 123
    // {
    //   name: iPhone 15
    // }

    Product replaceProduct(Long productId, Product product);

    boolean deleteProduct(Long productId);
}
