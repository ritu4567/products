package com.productservice.products.dtos;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class RatingDto implements Serializable {

    private Double rate;
    private Double count;
}
