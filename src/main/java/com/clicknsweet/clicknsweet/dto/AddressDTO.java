package com.clicknsweet.clicknsweet.dto;

import lombok.Data;

@Data
public class AddressDTO {
    private Integer id;
    private String address;
    private String city;
    private String country;
    private String region;
    private String typeAddress;
}