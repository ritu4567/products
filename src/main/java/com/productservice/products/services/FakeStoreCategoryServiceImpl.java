package com.productservice.products.services;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@Getter
@Setter

public class  FakeStoreCategoryServiceImpl implements CategoryService{

    private RestTemplateBuilder restTemplateBuilder;

    @Override
    public String[] getAllCategories() {

        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<String[]> response = restTemplate.getForEntity(
                "https://fakestoreapi.com/products/categories",
                String[].class);

        return response.getBody();
    }

    @Override
    public String getProductsInCategory(Long categoryId) {
        return "null";
    }
}
