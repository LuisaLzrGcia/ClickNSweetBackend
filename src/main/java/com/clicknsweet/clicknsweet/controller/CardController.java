package com.clicknsweet.clicknsweet.controller;

import com.clicknsweet.clicknsweet.model.Card;
import com.clicknsweet.clicknsweet.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/clicknsweet")
public class CardController {

    private final CardService service;

    @Autowired
    public CardController(CardService service) {
        this.service = service;
    }

    @GetMapping("/cards")
    public ResponseEntity<List<Card>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/cards/{id}")
    public ResponseEntity<Card> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @GetMapping("/users/{userId}/cards")
    public ResponseEntity<List<Card>> getByUser(@PathVariable Integer userId) {
        return ResponseEntity.ok(service.getByUser(userId));
    }

    @PostMapping("/cards")
    public ResponseEntity<Card> create(@RequestBody Card card) {
        Card created = service.create(card);
        return ResponseEntity.created(URI.create("/api/v1/clicknsweet/cards/" + created.getId()))
                .body(created);
    }

    @PutMapping("/cards/{id}")
    public ResponseEntity<Card> update(@PathVariable Integer id, @RequestBody Card card) {
        return ResponseEntity.ok(service.update(id, card));
    }

    @DeleteMapping("/cards/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}

