package com.clicknsweet.clicknsweet.controller;

import com.clicknsweet.clicknsweet.model.CommentReview;
import com.clicknsweet.clicknsweet.service.CommentReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/clicknsweet")
public class CommentReviewController {

    private final CommentReviewService service;

    @Autowired
    public CommentReviewController(CommentReviewService service) {
        this.service = service;
    }

    // --- CRUD básico ---

    @GetMapping("/comments")
    public ResponseEntity<List<CommentReview>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/comments/{id}")
    public ResponseEntity<CommentReview> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PostMapping("/comments")
    public ResponseEntity<CommentReview> create(@RequestBody CommentReview cr) {
        CommentReview created = service.create(cr);
        return ResponseEntity
                .created(URI.create("/api/v1/clicknsweet/comments/" + created.getId()))
                .body(created);
    }

    @PutMapping("/comments/{id}")
    public ResponseEntity<CommentReview> update(@PathVariable Integer id, @RequestBody CommentReview cr) {
        return ResponseEntity.ok(service.update(id, cr));
    }

    @DeleteMapping("/comments/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    // --- Filtros útiles ---

    @GetMapping("/comments/product/{productId}")
    public ResponseEntity<List<CommentReview>> getByProduct(@PathVariable Integer productId) {
        return ResponseEntity.ok(service.getByProduct(productId));
    }

    @GetMapping("/comments/user/{userId}")
    public ResponseEntity<List<CommentReview>> getByUser(@PathVariable Integer userId) {
        return ResponseEntity.ok(service.getByUser(userId));
    }
}

