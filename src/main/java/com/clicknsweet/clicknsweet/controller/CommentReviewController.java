package com.clicknsweet.clicknsweet.controller;

import com.clicknsweet.clicknsweet.model.CommentReview;
import com.clicknsweet.clicknsweet.model.Product;
import com.clicknsweet.clicknsweet.model.User;
import com.clicknsweet.clicknsweet.repository.CommentReviewRepository;
import com.clicknsweet.clicknsweet.repository.ProductRepository;
import com.clicknsweet.clicknsweet.repository.UserRepository;
import com.clicknsweet.clicknsweet.service.CommentReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/clicknsweet")
@Tag(name = "Comment Review Controller", description = "Gestion de comentarios y reseñas: creacion, actualizacion, eliminacion y obtencion de comentarios y reseñas.")
public class CommentReviewController {

    private final CommentReviewService service;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final CommentReviewRepository commentRepository;



    @Autowired
    public CommentReviewController(CommentReviewService service, UserRepository userRepository, ProductRepository productRepository, CommentReviewRepository commentRepository) {
        this.service = service;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.commentRepository = commentRepository;
    }


    // --- CRUD básico ---

    @Operation(
            summary = "Obtener todos los comentarios y reseñas",
            description = "Recupera una lista de todos los comentarios y reseñas almacenados en el sistema."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de comentarios y reseñas recuperada exitosamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/comments")
    public ResponseEntity<List<CommentReview>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    // Mapear por id
    @Operation(
            summary = "Obtener comentario o reseña por ID",
            description = "Recupera un comentario o reseña específico utilizando su ID único."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comentario o reseña recuperada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Comentario o reseña no encontrada"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/comments/{id}")
    public ResponseEntity<CommentReview> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getById(id));
    }

    // Crear comentario o reseña
    @Operation(
            summary = "Crear un nuevo comentario o reseña",
            description = "Permite la creación de un nuevo comentario o reseña asociado a un producto y un usuario."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comentario o reseña creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping("/comments")
    public ResponseEntity<CommentReview> create(@RequestBody CommentReview commentReview) {
        Product product = productRepository.findById(commentReview.getProduct().getId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        User user = userRepository.findById(commentReview.getUser().getId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        commentReview.setProduct(product);
        commentReview.setUser(user);
        commentReview.setCommentDate(LocalDateTime.now());

        CommentReview saved = commentRepository.save(commentReview);
        return ResponseEntity.ok(saved);
    }


    // Actualizar comentario o reseña
    @Operation(
            summary = "Actualizar un comentario o reseña existente",
            description = "Permite la actualización de un comentario o reseña existente utilizando su ID único."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comentario o reseña actualizado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Comentario o reseña no encontrada"),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PutMapping("/comments/{id}")
    public ResponseEntity<CommentReview> update(@PathVariable Integer id, @RequestBody CommentReview cr) {
        return ResponseEntity.ok(service.update(id, cr));
    }

    // Eliminar comentario o reseña
    @Operation(
            summary = "Eliminar un comentario o reseña",
            description = "Permite la eliminación de un comentario o reseña utilizando su ID único."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Comentario o reseña eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Comentario o reseña no encontrada"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @DeleteMapping("/comments/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    // --- Filtros útiles ---
    @Operation(
            summary = "Obtener comentarios y reseñas por producto",
            description = "Recupera una lista de comentarios y reseñas asociados a un producto específico utilizando su ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de comentarios y reseñas recuperada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/comments/product/{productId}")
    public ResponseEntity<List<CommentReview>> getByProduct(@PathVariable Integer productId) {
        return ResponseEntity.ok(service.getByProduct(productId));
    }

    @Operation(
            summary = "Obtener comentarios y reseñas por usuario",
            description = "Recupera una lista de comentarios y reseñas realizados por un usuario específico utilizando su ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de comentarios y reseñas recuperada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/comments/user/{userId}")
    public ResponseEntity<List<CommentReview>> getByUser(@PathVariable Integer userId) {
        return ResponseEntity.ok(service.getByUser(userId));
    }
}

