package com.clicknsweet.clicknsweet.repository;

import com.clicknsweet.clicknsweet.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CountryRepository extends JpaRepository<Country, Integer> {

    // Trae todos los países con sus estados
    @Query("SELECT DISTINCT c FROM Country c LEFT JOIN FETCH c.states s ORDER BY c.id, s.name ASC")
    List<Country> findAllWithStates();

    // Trae un país por id con sus estados
    @Query("SELECT c FROM Country c LEFT JOIN FETCH c.states s WHERE c.id = :id ORDER BY s.name ASC")
    Optional<Country> findByIdWithStates(Integer id);

    // Devuelve todos los países ordenados por id ascendente
    List<Country> findAllByOrderByIdAsc();
}
