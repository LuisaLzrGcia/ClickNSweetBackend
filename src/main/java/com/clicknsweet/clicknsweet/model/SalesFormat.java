package com.clicknsweet.clicknsweet.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "sales_format")
@Getter
@Setter
public class SalesFormat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sales_format_id")
    private Integer id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;
}