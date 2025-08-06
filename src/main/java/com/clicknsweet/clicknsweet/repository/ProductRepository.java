package com.clicknsweet.clicknsweet.repository;

import com.clicknsweet.clicknsweet.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {

    // Buscra porducto por nombre
    @Query("SELECT p FROM Product p WHERE p.productName =?1")
    Product  findByName(String name);
}
