package com.clicknsweet.clicknsweet.repository;

import com.clicknsweet.clicknsweet.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Integer> {
}
