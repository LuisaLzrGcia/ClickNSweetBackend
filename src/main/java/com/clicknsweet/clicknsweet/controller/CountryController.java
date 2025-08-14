package com.clicknsweet.clicknsweet.controller;

import com.clicknsweet.clicknsweet.model.Country;
import com.clicknsweet.clicknsweet.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/clicknsweet/countries")
public class CountryController {

    @Autowired
    private CountryService countryService;

    // Obtener todos los países con sus estados
    @GetMapping
    public List<Country> getAllCountries() {
        return countryService.getAllCountriesWithStates();
    }

    // Obtener país por id con estados
    @GetMapping("/{id}")
    public ResponseEntity<Country> getCountryById(@PathVariable Integer id) {
        Country country = countryService.getCountryByIdWithStates(id);
        if (country != null) {
            return ResponseEntity.ok(country);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Crear nuevo país
    @PostMapping
    public boolean createCountry(@RequestBody Country country) {
        // Se puede implementar en el servicio si quieres validaciones
        return countryService.getAllCountriesWithStates().add(country); // Opcional
    }

    // Actualizar país
    @PutMapping("/{id}")
    public ResponseEntity<Country> updateCountry(@PathVariable Integer id, @RequestBody Country countryDetails) {
        Country country = countryService.getCountryByIdWithStates(id);
        if (country == null) {
            return ResponseEntity.notFound().build();
        }
        country.setName(countryDetails.getName());
        country.setLatitude(countryDetails.getLatitude());
        country.setLongitude(countryDetails.getLongitude());
        return ResponseEntity.ok(country);
    }

}
