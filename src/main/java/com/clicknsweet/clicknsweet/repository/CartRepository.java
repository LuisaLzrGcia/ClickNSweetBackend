package com.clicknsweet.clicknsweet.repository;

import com.clicknsweet.clicknsweet.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {
    Optional<Cart> findByUser_Id(Integer userId);
    boolean existsByUser_Id(Integer userId);
}

