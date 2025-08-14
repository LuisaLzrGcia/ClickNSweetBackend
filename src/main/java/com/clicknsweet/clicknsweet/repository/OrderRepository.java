package com.clicknsweet.clicknsweet.repository;

import com.clicknsweet.clicknsweet.model.Country;
import com.clicknsweet.clicknsweet.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    @Query("SELECT c FROM Country c LEFT JOIN FETCH c.states")
    List<Country> findAllWithStates();

}
