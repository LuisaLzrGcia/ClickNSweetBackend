package com.clicknsweet.clicknsweet.repository;

import com.clicknsweet.clicknsweet.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Integer> {
    List<CartItem> findByCart_Id(Integer cartId);
    Optional<CartItem> findByCart_IdAndProduct_Id(Long cartId, Long productId);
}

