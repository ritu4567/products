package com.productservice.products.controllers;

import com.productservice.products.authenticationclient.AuthenticationClient;
import com.productservice.products.exceptions.NotFoundException;
import com.productservice.products.models.Product;
import com.productservice.products.respositories.ProductRepository;
import com.productservice.products.services.ProductService;
import jdk.jfr.Registered;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.Authenticator;
import java.util.Optional;


@RestController
@RequestMapping("/products")
public class ProductController {

    private ProductService productService;

    private ProductRepository productRepository;

    public ProductController(ProductService productService, ProductRepository productRepository)
    {
        this.productService = productService;
        this.productRepository = productRepository;
    }


    //private AuthenticationClient authenticationClient;

    @GetMapping("/{productId}")
    public ResponseEntity<Product> getSingleProduct(@PathVariable("productId") Long productId) throws NotFoundException{
        {
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        /* if you’re trying to pass a Map object as a header for an HTTP request,
           you would typically need to convert it into a HttpHeaders object or use a MultiValueMap<String, String> for headers.
        */
            headers.add("auth-token", "noaccessforyou");

            Product productOptional = productService.getSingleProduct(productId);

//            if(productOptional.isEmpty())
//            {
//                throw new NotFoundException("Id not found");
//            }

            ResponseEntity<Product> response = new ResponseEntity(productService.getSingleProduct(productId), headers, HttpStatus.OK);
            return response;
        }
    }

}
