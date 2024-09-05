package com.productservice.products.services;

public interface CategoryService {

    String[] getAllCategories();

    String getProductsInCategory(Long categoryId);
}
