package com.clicknsweet.clicknsweet.model;

import jakarta.persistence.*;
import jdk.dynalink.linker.LinkerServices;
import org.hibernate.engine.internal.Cascade;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Integer id;


    @Column(name = "category_name", nullable = false, unique = true)
    private String name;

    public Category(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // To String
    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    // Equals & HashCode
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Category category)) return false;
        return Objects.equals(id, category.id) && Objects.equals(name, category.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    // Relacion de Category con product 1:N
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "category")
    private List<Product> products; // mejor plural


    public List<Product> getProducts() {
        return products;
    }
}
