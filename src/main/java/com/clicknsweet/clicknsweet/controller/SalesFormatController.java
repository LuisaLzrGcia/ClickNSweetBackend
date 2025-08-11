package com.clicknsweet.clicknsweet.controller;

import com.clicknsweet.clicknsweet.model.SalesFormat;
import com.clicknsweet.clicknsweet.repository.SalesFormatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/clicknsweet/salesformats")
public class SalesFormatController {

    @Autowired
    private SalesFormatRepository salesFormatRepository;

    // Obtener todos los formatos de venta
    @GetMapping
    public List<SalesFormat> getAllSalesFormats() {
        return salesFormatRepository.findAll();
    }

    // Obtener formato por id
    @GetMapping("/{id}")
    public ResponseEntity<SalesFormat> getSalesFormatById(@PathVariable Integer id) {
        Optional<SalesFormat> salesFormat = salesFormatRepository.findById(id);
        if (salesFormat.isPresent()) {
            return ResponseEntity.ok(salesFormat.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Crear nuevo formato de venta
    @PostMapping
    public SalesFormat createSalesFormat(@RequestBody SalesFormat salesFormat) {
        return salesFormatRepository.save(salesFormat);
    }

    // Actualizar formato de venta
    @PutMapping("/{id}")
    public ResponseEntity<SalesFormat> updateSalesFormat(@PathVariable Integer id, @RequestBody SalesFormat salesFormatDetails) {
        Optional<SalesFormat> salesFormatOptional = salesFormatRepository.findById(id);
        if (!salesFormatOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        SalesFormat salesFormat = salesFormatOptional.get();
        salesFormat.setName(salesFormatDetails.getName());
        salesFormatRepository.save(salesFormat);
        return ResponseEntity.ok(salesFormat);
    }

}