package com.clicknsweet.clicknsweet.controller;

import com.clicknsweet.clicknsweet.model.Cart;
import com.clicknsweet.clicknsweet.service.CartService;
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
@Tag(name = " Cart Controller", description = "Gestion de carritos: creacion, actualizacion, eliminacion y obtencion de carritos.")
public class CartController {

    private final CartService service;

    @Autowired
    public CartController(CartService service) {
        this.service = service;
    }

    @Operation(
            summary = "Obtener todos los carritos",
            description = "Recupera una lista de todos los carritos almacenados en el sistema."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de carritos recuperada exitosamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/carts")
    public ResponseEntity<List<Cart>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    // Mapear por id
    @Operation(
            summary = "Obtener carrito por ID",
            description = "Recupera un carrito específico utilizando su ID único."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Carrito recuperado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Carrito no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/carts/{id}")
    public ResponseEntity<Cart> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    // Mapear por userId
    @Operation(
            summary = "Obtener carrito por ID de usuario",
            description = "Recupera un carrito específico utilizando el ID del usuario asociado."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Carrito recuperado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Carrito no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/carts/user/{userId}")
    public ResponseEntity<Cart> getByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(service.getByUser(userId));
    }

    @Operation(
            summary = "Crear un nuevo carrito",
            description = "Crea un nuevo carrito con la información proporcionada."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Carrito creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping("/carts")
    public ResponseEntity<Cart> create(@RequestBody Cart cart) {
        Cart created = service.create(cart);
        return ResponseEntity.created(URI.create("/api/v1/clicknsweet/carts/" + created.getId()))
                .body(created);
    }

    @Operation(
            summary = "Actualizar un carrito existente",
            description = "Actualiza la información de un carrito existente utilizando su ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Carrito actualizado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida"),
            @ApiResponse(responseCode = "404", description = "Carrito no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PutMapping("/carts/{id}")
    public ResponseEntity<Cart> update(@PathVariable Long id, @RequestBody Cart cart) {
        return ResponseEntity.ok(service.update(id, cart));
    }

    @Operation(
            summary = "Eliminar un carrito",
            description = "Elimina un carrito existente utilizando su ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Carrito eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Carrito no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @DeleteMapping("/carts/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}

