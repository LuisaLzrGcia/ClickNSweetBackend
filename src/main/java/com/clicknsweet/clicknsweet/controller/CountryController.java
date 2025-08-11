package com.clicknsweet.clicknsweet.controller;

import com.clicknsweet.clicknsweet.model.Country;
import com.clicknsweet.clicknsweet.repository.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/clicknsweet/countries")
public class CountryController {

    @Autowired
    private CountryRepository countryRepository;

    // Obtener todos los países
    @GetMapping
    public List<Country> getAllCountries() {
        return countryRepository.findAll();
    }

    // Obtener país por id
    @GetMapping("/{id}")
    public ResponseEntity<Country> getCountryById(@PathVariable Integer id) {
        Optional<Country> country = countryRepository.findById(id);
        if (country.isPresent()) {
            return ResponseEntity.ok(country.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Crear nuevo país
    @PostMapping
    public Country createCountry(@RequestBody Country country) {
        return countryRepository.save(country);
    }

    // Actualizar país
    @PutMapping("/{id}")
    public ResponseEntity<Country> updateCountry(@PathVariable Integer id, @RequestBody Country countryDetails) {
        Optional<Country> countryOptional = countryRepository.findById(id);
        if (!countryOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        Country country = countryOptional.get();
        country.setName(countryDetails.getName());
        country.setLatitude(countryDetails.getLatitude());
        country.setLongitude(countryDetails.getLongitude());
        countryRepository.save(country);
        return ResponseEntity.ok(country);
    }

}