package com.clicknsweet.clicknsweet.controller;

import com.clicknsweet.clicknsweet.model.CartItem;
import com.clicknsweet.clicknsweet.service.CartItemService;
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
@Tag(name = "Cart Item Controller", description = "Gestion de items del carrito: creacion, actualizacion, eliminacion y obtencion de items.")
public class CartItemController {

    private final CartItemService service;

    @Autowired
    public CartItemController(CartItemService service) {
        this.service = service;
    }

    @Operation(
            summary = "Obtener todos los items del carrito",
            description = "Recupera una lista de todos los items del carrito almacenados en el sistema."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de items del carrito recuperada exitosamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/cart-items")
    public ResponseEntity<List<CartItem>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    // Mapear por id
    @Operation(
            summary = "Obtener item del carrito por ID",
            description = "Recupera un item del carrito específico utilizando su ID único."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Item del carrito recuperado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Item del carrito no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/cart-items/{id}")
    public ResponseEntity<CartItem> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getById(id));
    }

    // Mapear por cartId
    @Operation(
            summary = "Obtener items del carrito por ID de carrito",
            description = "Recupera una lista de items del carrito asociados a un ID de carrito específico."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de items del carrito recuperada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Carrito no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/carts/{cartId}/items")
    public ResponseEntity<List<CartItem>> getByCart(@PathVariable Integer cartId) {
        return ResponseEntity.ok(service.getByCart(cartId));
    }

    @Operation(
            summary = "Crear un nuevo item del carrito",
            description = "Agrega un nuevo item al carrito en el sistema."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Item del carrito creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping("/cart-items")
    public ResponseEntity<CartItem> create(@RequestBody CartItem item) {
        CartItem created = service.create(item);
        return ResponseEntity.created(URI.create("/api/v1/clicknsweet/cart-items/" + created.getId()))
                .body(created);
    }

    @Operation(
            summary = "Actualizar un item del carrito",
            description = "Actualiza los detalles de un item del carrito existente."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Item del carrito actualizado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida"),
            @ApiResponse(responseCode = "404", description = "Item del carrito no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PutMapping("/cart-items/{id}")
    public ResponseEntity<CartItem> update(@PathVariable Integer id, @RequestBody CartItem item) {
        return ResponseEntity.ok(service.update(id, item));
    }

    // Actualizar cantidad por (cartId, productId)
    @Operation(
            summary = "Actualizar o insertar un item del carrito por ID de carrito e ID de producto",
            description = "Actualiza la cantidad de un item del carrito existente o lo inserta si no existe, utilizando el ID del carrito y el ID del producto."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Item del carrito actualizado o insertado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida"),
            @ApiResponse(responseCode = "404", description = "Carrito o producto no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PutMapping("/carts/{cartId}/items/{productId}")
    public ResponseEntity<CartItem> upsertByCartAndProduct(@PathVariable Long cartId,
                                                           @PathVariable Long productId,
                                                           @RequestParam Integer quantity) {
        return ResponseEntity.ok(service.upsertByCartAndProduct(cartId, productId, quantity));
    }
    @Operation(
            summary = "Eliminar un item del carrito",
            description = "Elimina un item del carrito específico utilizando su ID único."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Item del carrito eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Item del carrito no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @DeleteMapping("/cart-items/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}

