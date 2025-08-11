package com.clicknsweet.clicknsweet.dto;

import lombok.Data;

import java.util.List;

@Data
public class OrderDTO {
    private Integer userId;
    private Integer shippingAddressId;
    private String status;
    private String totalAmount;
    private String trackingNumber;
    private String shippingCarrier;
    private String createdAt;
    private List<OrderLineDTO> orderLines;


}
