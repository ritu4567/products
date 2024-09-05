package com.productservice.products.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
@Entity
public class Product extends BaseModel {

    private String description;
    private String title;
    private double price;
    private String imageUrl;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private Category category;
}

// Assuming one product will have one category but one category can have many products