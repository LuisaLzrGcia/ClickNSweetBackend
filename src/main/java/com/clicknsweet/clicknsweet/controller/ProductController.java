package com.clicknsweet.clicknsweet.controller;

import com.clicknsweet.clicknsweet.dto.ProductFilterRequest;
import com.clicknsweet.clicknsweet.exceptions.ProductAlreadyExistsException;
import com.clicknsweet.clicknsweet.exceptions.ProductNotFoundException;
import com.clicknsweet.clicknsweet.model.Product;
import com.clicknsweet.clicknsweet.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/clicknsweet")
public class ProductController {
    private  final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // Mapear create Product
    @PostMapping("/product")
    public ResponseEntity<Product> addProduct(@RequestBody Product newProduct) {
        Product productByname = productService.findByName(newProduct.getProductName());
        if (productByname != null) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.createProduct(newProduct));
    }



    // Mapear get Products por paginacion
    @PostMapping("/products")
    public Map<String, Object> getFilteredProducts(@RequestBody ProductFilterRequest filterRequest) {
        return productService.getPaginatedProducts(filterRequest);
    }

    // Mapear product por id
    @GetMapping("/product/{id}")
    public ResponseEntity<Product> getById(@PathVariable Long id){
        try {
            return ResponseEntity.ok(productService.findById(id));
        } catch (ProductNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Mapear delete por id
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
    @PutMapping("/product/{id}")
    public ResponseEntity<Product> updateProduct(@RequestBody Product newProduct, @PathVariable Long id) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(productService.updateProduct(newProduct, id));
        } catch (ProductNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
