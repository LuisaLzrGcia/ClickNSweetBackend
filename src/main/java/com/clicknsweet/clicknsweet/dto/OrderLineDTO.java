package com.clicknsweet.clicknsweet.dto;

import lombok.Data;

@Data
public class OrderLineDTO {
    private Integer productId;
    private String price;
    private Integer quantity;
}
