package com.clicknsweet.clicknsweet.controller;

import com.clicknsweet.clicknsweet.dto.ProductFilterRequest;
import com.clicknsweet.clicknsweet.exceptions.ProductAlreadyExistsException;
import com.clicknsweet.clicknsweet.exceptions.ProductNotFoundException;
import com.clicknsweet.clicknsweet.model.Product;
import com.clicknsweet.clicknsweet.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/clicknsweet")
@Tag(name = "Product Controller", description = "Gestion de productos: creacion, actualizacion, eliminacion y obtencion de productos.")
public class ProductController {
    private  final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // Mapear create Product
    @Operation(
            summary = "Crear un nuevo producto",
            description = "Permite la creacion de un nuevo producto en el sistema."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Producto creado exitosamente"),
            @ApiResponse(responseCode = "409", description = "Conflicto: El producto ya existe"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping("/product")
    public ResponseEntity<Product> addProduct(@RequestBody Product newProduct) {
        Product productByname = productService.findByName(newProduct.getProductName());
        if (productByname != null) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.createProduct(newProduct));
    }



    // Mapear get Products por paginacion
    @Operation(
            summary = "Obtener productos con filtros y paginacion",
            description = "Recupera una lista de productos aplicando filtros y paginacion."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de productos recuperada exitosamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping("/products")
    public Map<String, Object> getFilteredProducts(@RequestBody ProductFilterRequest filterRequest) {
        return productService.getPaginatedProducts(filterRequest);
    }

    // Mapear product por id
    @Operation(
            summary = "Obtener producto por ID",
            description = "Recupera un producto específico utilizando su ID único."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto recuperado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/product/{id}")
    public ResponseEntity<Product> getById(@PathVariable Long id){
        try {
            return ResponseEntity.ok(productService.findById(id));
        } catch (ProductNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Mapear delete por id
    @Operation(
            summary = "Eliminar producto por ID",
            description = "Elimina un producto específico utilizando su ID único."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @DeleteMapping("/product/{id}")
    public ResponseEntity<Product> deleteById(@PathVariable Long id) {
        try {
            productService.deleteById(id);
            return ResponseEntity.ok().build();
        } catch (ProductNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Mapear update por id
    @Operation(
            summary = "Actualizar producto por ID",
            description = "Actualiza los detalles de un producto existente utilizando su ID único."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Producto actualizado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PutMapping("/product/{id}")
    public ResponseEntity<Product> updateProduct(@RequestBody Product newProduct, @PathVariable Long id) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(productService.updateProduct(newProduct, id));
        } catch (ProductNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
