package com.clicknsweet.clicknsweet.repository;

import com.clicknsweet.clicknsweet.model.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardRepository extends JpaRepository<Card, Integer> {
    List<Card> findByUser_Id(Integer userId);
}

