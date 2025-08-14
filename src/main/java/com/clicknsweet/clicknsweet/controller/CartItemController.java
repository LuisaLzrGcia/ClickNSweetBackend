package com.clicknsweet.clicknsweet.controller;

import com.clicknsweet.clicknsweet.model.CartItem;
import com.clicknsweet.clicknsweet.service.CartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/clicknsweet")
public class CartItemController {

    private final CartItemService service;

    @Autowired
    public CartItemController(CartItemService service) {
        this.service = service;
    }

    @GetMapping("/cart-items")
    public ResponseEntity<List<CartItem>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/cart-items/{id}")
    public ResponseEntity<CartItem> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @GetMapping("/carts/{cartId}/items")
    public ResponseEntity<List<CartItem>> getByCart(@PathVariable Integer cartId) {
        return ResponseEntity.ok(service.getByCart(cartId));
    }

    @PostMapping("/cart-items")
    public ResponseEntity<CartItem> create(@RequestBody CartItem item) {
        CartItem created = service.create(item);
        return ResponseEntity.created(URI.create("/api/v1/clicknsweet/cart-items/" + created.getId()))
                .body(created);
    }

    @PutMapping("/cart-items/{id}")
    public ResponseEntity<CartItem> update(@PathVariable Integer id, @RequestBody CartItem item) {
        return ResponseEntity.ok(service.update(id, item));
    }

    // Actualizar cantidad por (cartId, productId)
    @PutMapping("/carts/{cartId}/items/{productId}")
    public ResponseEntity<CartItem> upsertByCartAndProduct(@PathVariable Long cartId,
                                                           @PathVariable Long productId,
                                                           @RequestParam Integer quantity) {
        return ResponseEntity.ok(service.upsertByCartAndProduct(cartId, productId, quantity));
    }

    @DeleteMapping("/cart-items/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}

