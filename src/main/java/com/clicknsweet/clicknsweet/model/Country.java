package com.clicknsweet.clicknsweet.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "countries")
public class Country {
    @Setter
    @Getter
    @Id
    @Column(name = "Country_ID")
    private Integer id;

    @Setter
    @Getter
    @Column(name = "Name", length = 100, nullable = false)
    private String name;

    @Setter
    @Getter
    @Column(name = "Latitude", precision = 10, scale = 8)
    private BigDecimal latitude;

    @Setter
    @Getter
    @Column(name = "Longitude", precision = 11, scale = 8)
    private BigDecimal longitude;

    @OneToMany(mappedBy = "country")
    @JsonManagedReference
    @OrderBy("name ASC")
    private List<State> states;



}
