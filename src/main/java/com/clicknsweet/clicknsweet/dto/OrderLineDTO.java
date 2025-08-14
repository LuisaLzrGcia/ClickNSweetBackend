package com.clicknsweet.clicknsweet.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderLineDTO {
    private Integer orderLineId;


    private Long productId;

    private int quantity;
    private BigDecimal price;

    private ProductDTO product;
}
