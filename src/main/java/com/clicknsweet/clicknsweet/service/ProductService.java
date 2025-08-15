package com.clicknsweet.clicknsweet.service;

import com.clicknsweet.clicknsweet.dto.ProductFilterRequest;
import com.clicknsweet.clicknsweet.exceptions.ProductAlreadyExistsException;
import com.clicknsweet.clicknsweet.exceptions.ProductNotFoundException;
import com.clicknsweet.clicknsweet.model.Product;
import com.clicknsweet.clicknsweet.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

import java.util.HashMap;
import java.util.Map;

@Service
public class ProductService {
    private  final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // Mapear producto por id
    public Product findById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
    }

    // Mapear producto por name
    public Product findByName(String name) {
        return productRepository.findByName(name);
    }

    // Crear nuevo producto
    public Product createProduct(Product newProduct) {
        return productRepository.save(newProduct);
    }

    // Mapear productos paginados y por filtros
    public Map<String, Object> getPaginatedProducts(ProductFilterRequest filter) {
        try {
            Pageable paging = PageRequest.of(filter.getPage(), filter.getSize(), Sort.by("id").ascending());

            Specification<Product> spec = Specification.unrestricted();

            // Filtrar por nombre
            if (filter.getName() != null && !filter.getName().isEmpty()) {
                String namePattern = "%" + filter.getName().toLowerCase() + "%";
                spec = spec.and((root, query, cb) -> cb.like(cb.lower(root.get("name")), namePattern));
            }

            // Filtrar por preico minimo
            if (filter.getMinPrice() != null) {
                spec = spec.and((root, query, cb) -> cb.greaterThanOrEqualTo(root.get("price"), filter.getMinPrice()));
            }

            // Filtrar por precio maximo
            if (filter.getMaxPrice() != null) {
                spec = spec.and((root, query, cb) -> cb.lessThanOrEqualTo(root.get("price"), filter.getMaxPrice()));
            }

            // Filtrar por status
            if (filter.getStatus() != null && !filter.getStatus().isEmpty()) {
                Boolean statusValue = null;
                if ("ACTIVE".equalsIgnoreCase(filter.getStatus())) {
                    statusValue = true;
                } else if ("INACTIVE".equalsIgnoreCase(filter.getStatus())) {
                    statusValue = false;
                }

                if (statusValue != null) {
                    Boolean finalStatusValue = statusValue;
                    spec = spec.and((root, query, cb) -> cb.equal(root.get("status"), finalStatusValue));
                }
            }

            if (filter.getAverageRating() != null) {
                spec = spec.and((root, query, cb) -> cb.greaterThanOrEqualTo(root.get("averageRating"), filter.getAverageRating()));
            }

            // Filtrar por categoría
            if (filter.getCategoryId() != null) {
                spec = spec.and((root, query, cb) -> {
                    return cb.equal(root.join("productCategoryId").get("id"), filter.getCategoryId());
                });
            }


            Page<Product> pagedResult = productRepository.findAll(spec, paging);

            Map<String, Object> response = new HashMap<>();
            response.put("items", pagedResult.getContent());
            response.put("currentPage", pagedResult.getNumber());
            response.put("pageSize", pagedResult.getSize());
            response.put("totalItems", pagedResult.getTotalElements());
            response.put("totalPages", pagedResult.getTotalPages());
            response.put("isFirst", pagedResult.isFirst());
            response.put("isLast", pagedResult.isLast());

            return response;
        } catch (Exception e) {
            // Imprimir en consola para debug
            e.printStackTrace();

            // Puedes lanzar una excepción personalizada o devolver un mapa con un error
            throw new RuntimeException("Error al obtener los productos paginados: " + e.getMessage());
        }
    }

    // Metodo para eliminar producto por id
    public void deleteById(Long id) {
        if(productRepository.existsById(id)){
            productRepository.deleteById(id);
        }else{
            throw new ProductNotFoundException(id);
        }
    }

    // Metodo para actualizar producto por id
    public Product updateProduct(Product product, Long id) {
        return productRepository.findById(id)
                .map(productItem -> {
                    productItem.setProductName(product.getProductName());
                    productItem.setSku(product.getSku());
                    productItem.setDescription(product.getDescription());
                    productItem.setPicture(product.getPicture());
                    productItem.setPrice(product.getPrice());
                    productItem.setProductSalesFormatId(product.getProductSalesFormatId());
                    productItem.setSupplierCost(product.getSupplierCost());
                    productItem.setQuantityStock(product.getQuantityStock());
                    productItem.setWeight(product.getWeight());
                    productItem.setLength(product.getLength());
                    productItem.setWidth(product.getWidth());
                    productItem.setHeight(product.getHeight());
                    productItem.setStatus(product.getStatus());
                    productItem.setLowStockThreshold(product.getLowStockThreshold());
                    productItem.setAllowBackorders(product.getAllowBackorders());
                    productItem.setAverageRating(product.getAverageRating());
                    productItem.setDiscountType(product.getDiscountType());
                    productItem.setDiscountValue(product.getDiscountValue());
                    productItem.setProductCategoryId(product.getProductCategoryId());
                    productItem.setProductCountryId(product.getProductCountryId());
                    productItem.setProductStateId(product.getProductStateId());
                    productItem.setImage(product.getImage());

                    return productRepository.save(productItem);
                })
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

}
