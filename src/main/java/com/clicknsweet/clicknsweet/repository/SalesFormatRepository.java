package com.clicknsweet.clicknsweet.repository;

import com.clicknsweet.clicknsweet.model.SalesFormat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SalesFormatRepository extends JpaRepository<SalesFormat, Integer> {
}