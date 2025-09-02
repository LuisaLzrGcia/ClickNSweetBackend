package com.clicknsweet.clicknsweet.controller;

import com.clicknsweet.clicknsweet.model.Country;
import com.clicknsweet.clicknsweet.service.CountryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/clicknsweet/countries")
@Tag(name = "Country Controller", description = "Gestion de paises: creacion, actualizacion, eliminacion y obtencion de paises.")
public class CountryController {

    @Autowired
    private CountryService countryService;

    // Obtener todos los países con sus estados
    @Operation(
            summary = "Obtener todos los paises con sus estados",
            description = "Recupera una lista de todos los paises almacenados en el sistema junto con sus estados."
    )
    @ApiResponses(value={
            @ApiResponse(responseCode = "200", description = "Lista de paises recuperada exitosamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping
    public List<Country> getAllCountries() {
        return countryService.getAllCountriesWithStates();
    }

    // Obtener país por id con estados
    @Operation(
            summary = "Obtener pais por ID con sus estados",
            description = "Recupera un pais específico utilizando su ID único junto con sus estados."
    )
    @ApiResponses(value={
            @ApiResponse(responseCode = "200", description = "Pais recuperado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Pais no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
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
    @Operation(
            summary = "Crear un nuevo pais",
            description = "Crea un nuevo pais en el sistema."
    )
    @ApiResponses(value={
            @ApiResponse(responseCode = "201", description = "Pais creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping
    public boolean createCountry(@RequestBody Country country) {
        // Se puede implementar en el servicio si quieres validaciones
        return countryService.getAllCountriesWithStates().add(country); // Opcional
    }

    // Actualizar país
    @Operation(
            summary = "Actualizar un pais existente",
            description = "Actualiza los detalles de un pais existente utilizando su ID."
    )
    @ApiResponses(value={
            @ApiResponse(responseCode = "200", description = "Pais actualizado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida"),
            @ApiResponse(responseCode = "404", description = "Pais no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
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
