package com.clicknsweet.clicknsweet.dto;

import lombok.Getter;
import lombok.Setter;

public class ProductFilterRequest {
    @Getter
    @Setter
    private String name;
    @Getter
    @Setter
    private Double minPrice;
    @Getter
    @Setter
    private Double maxPrice;
    @Getter
    @Setter
    private String status;
    @Getter
    @Setter
    private Double averageRating;
    @Getter
    @Setter
    private int page = 0;
    @Getter
    @Setter
    private int size = 10;


    @Getter
    @Setter
    private String category;
    @Getter
    @Setter
    private String country;


    @Getter
    @Setter
    private String orderBy;
    @Getter
    @Setter
    private String sort;
}
