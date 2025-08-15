package com.clicknsweet.clicknsweet.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data

public class OrderDTO {
    private Integer id;
    private Long userId;
    private Integer shippingAddressId;
    private BigDecimal totalAmount;
    private String status;
    private String shippingCarrier;
    private String trackingNumber;
    private LocalDateTime createdAt;
    private List<OrderLineDTO> orderLines;
    private AddressDTO shippingAddress;

}
