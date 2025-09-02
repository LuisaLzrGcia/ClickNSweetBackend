package com.clicknsweet.clicknsweet.controller;

import com.clicknsweet.clicknsweet.model.Card;
import com.clicknsweet.clicknsweet.service.CardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/clicknsweet")
@Tag(name = "Card Controller", description = "Gestion de tarjetas: creacion, actualizacion, eliminacion y obtencion de tarjetas.")
public class CardController {

    private final CardService service;

    @Autowired
    public CardController(CardService service) {
        this.service = service;
    }

    @Operation(
            summary = "Obtener todas las tarjetas",
            description = "Recupera una lista de todas las tarjetas almacenadas en el sistema."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de tarjetas recuperada exitosamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/cards")
    public ResponseEntity<List<Card>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    // Mapear por id

    @Operation(
            summary = "Obtener tarjeta por ID",
            description = "Recupera una tarjeta específica utilizando su ID único."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tarjeta recuperada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Tarjeta no encontrada"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/cards/{id}")
    public ResponseEntity<Card> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getById(id));
    }

    // Mapear por userId
    @Operation(
            summary = "Obtener tarjetas por ID de usuario",
            description = "Recupera una lista de tarjetas asociadas a un ID de usuario específico."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de tarjetas recuperada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/users/{userId}/cards")
    public ResponseEntity<List<Card>> getByUser(@PathVariable Integer userId) {
        return ResponseEntity.ok(service.getByUser(userId));
    }

    // Mapear create
    @Operation(
            summary = "Crear una nueva tarjeta",
            description = "Agrega una nueva tarjeta al sistema."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Tarjeta creada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })

    @PostMapping("/cards")
    public ResponseEntity<Card> create(@RequestBody Card card) {
        Card created = service.create(card);
        return ResponseEntity.created(URI.create("/api/v1/clicknsweet/cards/" + created.getId()))
                .body(created);
    }

    // Mapear update
    @Operation(
            summary = "Actualizar una tarjeta existente",
            description = "Actualiza los detalles de una tarjeta existente utilizando su ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tarjeta actualizada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida"),
            @ApiResponse(responseCode = "404", description = "Tarjeta no encontrada"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PutMapping("/cards/{id}")
    public ResponseEntity<Card> update(@PathVariable Integer id, @RequestBody Card card) {
        return ResponseEntity.ok(service.update(id, card));
    }
    // Mapear delete
    @Operation(
            summary = "Eliminar una tarjeta",
            description = "Elimina una tarjeta del sistema utilizando su ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Tarjeta eliminada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Tarjeta no encontrada"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })

    @DeleteMapping("/cards/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}

