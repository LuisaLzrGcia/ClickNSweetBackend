package com.clicknsweet.clicknsweet.controller;

import com.clicknsweet.clicknsweet.model.State;
import com.clicknsweet.clicknsweet.repository.StateRepository;
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
@RequestMapping("/api/v1/clicknsweet/states")
@Tag(name = "State Controller", description = "Gestion de estados: creacion, actualizacion, eliminacion y obtencion de estados.")
public class StateController {

    @Autowired
    private StateRepository stateRepository;

    // Obtener todos los estados

    @Operation(
            summary = "Obtener todos los estados",
            description = "Recupera una lista de todos los estados almacenados en el sistema."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de estados recuperada exitosamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping
    public List<State> getAllStates() {
        return stateRepository.findAll();
    }

    // Obtener estado por id
    @Operation(
            summary = "Obtener estado por ID",
            description = "Recupera un estado específico utilizando su ID único."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estado recuperado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Estado no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/{id}")
    public ResponseEntity<State> getStateById(@PathVariable Integer id) {
        Optional<State> state = stateRepository.findById(id);
        return state.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Obtener estados por país
    @Operation(
            summary = "Obtener estados por ID de país",
            description = "Recupera una lista de estados asociados a un país específico utilizando el ID del país."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de estados recuperada exitosamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/country/{countryId}")
    public List<State> getStatesByCountry(@PathVariable Integer countryId) {
        return stateRepository.findByCountryId(countryId);
    }

    // Crear nuevo estado
    @Operation(
            summary = "Crear un nuevo estado",
            description = "Crea un nuevo estado en el sistema."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Estado creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping
    public State createState(@RequestBody State state) {
        return stateRepository.save(state);
    }

    // Actualizar estado
    @Operation(
            summary = "Actualizar un estado existente",
            description = "Actualiza los detalles de un estado existente utilizando su ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estado actualizado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida"),
            @ApiResponse(responseCode = "404", description = "Estado no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PutMapping("/{id}")
    public ResponseEntity<State> updateState(@PathVariable Integer id, @RequestBody State stateDetails) {
        Optional<State> stateOptional = stateRepository.findById(id);
        if (!stateOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        State state = stateOptional.get();
        state.setName(stateDetails.getName());
        state.setLatitude(stateDetails.getLatitude());
        state.setLongitude(stateDetails.getLongitude());
        state.setCountry(stateDetails.getCountry());
        stateRepository.save(state);
        return ResponseEntity.ok(state);
    }

}
