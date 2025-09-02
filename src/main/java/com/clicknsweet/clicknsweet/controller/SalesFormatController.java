package com.clicknsweet.clicknsweet.controller;

import com.clicknsweet.clicknsweet.model.SalesFormat;
import com.clicknsweet.clicknsweet.repository.SalesFormatRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/clicknsweet/salesformats")
@Tag(name = "Sales Format Controller", description = "Gestion de formatos de venta: creacion, actualizacion, eliminacion y obtencion de formatos de venta.")
public class SalesFormatController {

    @Autowired
    private SalesFormatRepository salesFormatRepository;

    // Obtener todos los formatos de venta
    @Operation(
            summary = "Obtener todos los formatos de venta",
            description = "Recupera una lista de todos los formatos de venta almacenados en el sistema."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de formatos de venta recuperada exitosamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping
    public List<SalesFormat> getAllSalesFormats() {
        return salesFormatRepository.findAll();
    }

    // Obtener formato por id
    @Operation(
            summary = "Obtener formato de venta por ID",
            description = "Recupera un formato de venta específico utilizando su ID único."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Formato de venta recuperado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Formato de venta no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
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
    @Operation(
            summary = "Crear un nuevo formato de venta",
            description = "Crea un nuevo formato de venta en el sistema."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Formato de venta creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping
    public SalesFormat createSalesFormat(@RequestBody SalesFormat salesFormat) {
        return salesFormatRepository.save(salesFormat);
    }

    // Actualizar formato de venta
    @Operation(
            summary = "Actualizar un formato de venta",
            description = "Actualiza los detalles de un formato de venta existente."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Formato de venta actualizado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Formato de venta no encontrado"),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
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