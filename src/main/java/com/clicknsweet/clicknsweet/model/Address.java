package com.clicknsweet.clicknsweet.model;


import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "address")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private Integer id;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "city", nullable = false)
    private String city;

    @Column(name = "region")
    private String region;

    @Column(name = "country", nullable = false)
    private String country;

    @Column(name = "type_address")
    private String typeAddress;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // Constructor vacío
    public Address() {

    }

    // Constructor
    public Address(Integer id, String address, String city, String region, String country, String typeAddress) {
        this.id = id;
        this.address = address;
        this.city = city;
        this.region = region;
        this.country = country;
        this.typeAddress = typeAddress;
    }

    // Getters y Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getTypeAddress() {
        return typeAddress;
    }

    public void setTypeAddress(String typeAddress) {
        this.typeAddress = typeAddress;
    }


    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", region='" + region + '\'' +
                ", country='" + country + '\'' +
                ", typeAddress='" + typeAddress + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Address address1)) return false;
        return Objects.equals(id, address1.id) && Objects.equals(address, address1.address) && Objects.equals(city, address1.city) && Objects.equals(region, address1.region) && Objects.equals(country, address1.country) && Objects.equals(typeAddress, address1.typeAddress);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, address, city, region, country, typeAddress);
    }
}


