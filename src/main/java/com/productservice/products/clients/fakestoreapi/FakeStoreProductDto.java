package com.productservice.products.clients.fakestoreapi;

import com.productservice.products.dtos.RatingDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.SecondaryRow;

import java.io.Serializable;

@Setter
@Getter
@ToString
public class FakeStoreProductDto implements Serializable {

    private Long id;
    private String title;
    private String description;
    private Double price;
    private String image;
    private String category;
    private RatingDto rating;
}
