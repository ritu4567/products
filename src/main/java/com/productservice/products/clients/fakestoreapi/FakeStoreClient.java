package com.productservice.products.clients.fakestoreapi;

import com.productservice.products.dtos.ProductDto;
import com.productservice.products.models.Product;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class FakeStoreClient {

    private RestTemplateBuilder restTemplateBuilder;

    public FakeStoreClient(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplateBuilder = restTemplateBuilder;
    }

    // get all products method
    public List<FakeStoreProductDto> getAllProducts() {

        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<FakeStoreProductDto[]> l = restTemplate.getForEntity("https://fakestoreapi.com/products", FakeStoreProductDto[].class);

        return Arrays.asList(l.getBody());
    }


    public FakeStoreProductDto getSingleProduct(Long productId) {

        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<FakeStoreProductDto> response = restTemplate.getForEntity("https://fakestoreapi.com/products/{id}", FakeStoreProductDto.class, productId);
        FakeStoreProductDto fakeStoreProductDto = response.getBody();

        return fakeStoreProductDto;
    }

    // add new product
    FakeStoreProductDto addNewProduct(ProductDto product)
    {
      return null;
    }
    /*
    Product object has only those fields filled which need to be updated.
    Everything else is null
     */
    FakeStoreProductDto updateProduct(Long productId, Product product) {
        return null;
    }
    // if (product.getImageUrl() != null) {
    //
    // }
    FakeStoreProductDto replaceProduct(Long productId, Product product) {
        return null;
    }

    FakeStoreProductDto deleteProduct(Long productId) {
        return null;
    }


    /*public FakeStoreProductDto getSingleProduct(Long productId) {

        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<FakeStoreProductDto> response = restTemplate.getForEntity("https://fakestoreapi.com/products/{id}", FakeStoreProductDto.class, productId);
        FakeStoreProductDto fakeStoreProductDto = response.getBody();

        return fakeStoreProductDto;
    }*/
}
