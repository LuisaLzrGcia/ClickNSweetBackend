package com.clicknsweet.clicknsweet.service;

import com.clicknsweet.clicknsweet.model.Country;
import com.clicknsweet.clicknsweet.repository.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CountryService {

    @Autowired
    private CountryRepository countryRepository;

    // Obtener todos los países con sus estados
    public List<Country> getAllCountriesWithStates() {
        return countryRepository.findAllWithStates();
    }

    // Obtener país por id con estados
    public Country getCountryByIdWithStates(Integer id) {
        return countryRepository.findByIdWithStates(id).orElse(null);
    }

}
