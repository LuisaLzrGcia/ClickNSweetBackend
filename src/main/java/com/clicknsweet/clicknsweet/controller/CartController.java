package com.clicknsweet.clicknsweet.controller;

import com.clicknsweet.clicknsweet.model.Cart;
import com.clicknsweet.clicknsweet.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/clicknsweet")
public class CartController {

    private final CartService service;

    @Autowired
    public CartController(CartService service) {
        this.service = service;
    }

    @GetMapping("/carts")
    public ResponseEntity<List<Cart>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/carts/{id}")
    public ResponseEntity<Cart> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @GetMapping("/carts/user/{userId}")
    public ResponseEntity<Cart> getByUser(@PathVariable Integer userId) {
        return ResponseEntity.ok(service.getByUser(userId));
    }

    @PostMapping("/carts")
    public ResponseEntity<Cart> create(@RequestBody Cart cart) {
        Cart created = service.create(cart);
        return ResponseEntity.created(URI.create("/api/v1/clicknsweet/carts/" + created.getId()))
                .body(created);
    }

    @PutMapping("/carts/{id}")
    public ResponseEntity<Cart> update(@PathVariable Integer id, @RequestBody Cart cart) {
        return ResponseEntity.ok(service.update(id, cart));
    }

    @DeleteMapping("/carts/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}

