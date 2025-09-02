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

    // Mapear filtrar productos
    public Map<String, Object> getPaginatedProducts(ProductFilterRequest filter) {
        try {
            // ---------------- Determinar dirección de orden ----------------
            Sort.Direction direction;

            if (filter.getOrderBy() != null) {
                String orderBy = filter.getOrderBy().toLowerCase();

                // Si el cliente envía sort explícito, lo usamos
                if (filter.getSort() != null) {
                    direction = "asc".equalsIgnoreCase(filter.getSort()) ? Sort.Direction.ASC : Sort.Direction.DESC;
                } else {
                    // Si no envía sort, definimos el default según el orderBy
                    switch (orderBy) {
                        case "popular":
                        case "new":
                        case "rating":
                            direction = Sort.Direction.DESC;
                            break;
                        case "price":
                            direction = Sort.Direction.ASC;
                            break;
                        default:
                            direction = Sort.Direction.DESC;
                            break;
                    }
                }
            } else {
                // Si no envía orderBy → default popular DESC
                filter.setOrderBy("popular");
                direction = Sort.Direction.DESC;
            }

            // ---------------- Determinar campo de orden ----------------
            Sort sortOrder;
            switch (filter.getOrderBy().toLowerCase()) {
                case "popular":
                    sortOrder = Sort.by(direction, "totalSold");
                    break;
                case "new":
                    sortOrder = Sort.by(direction, "createdAt");
                    break;
                case "price":
                    sortOrder = Sort.by(direction, "price");
                    break;
                case "rating":
                    sortOrder = Sort.by(direction, "averageRating");
                    break;
                default:
                    sortOrder = Sort.by(direction, "id");
                    break;
            }

            Pageable paging = PageRequest.of(filter.getPage(), filter.getSize(), sortOrder);

            // ---------------- Construir especificaciones de filtro ----------------
            Specification<Product> spec = Specification.unrestricted();

            if (filter.getName() != null && !filter.getName().isEmpty()) {
                String namePattern = "%" + filter.getName().toLowerCase() + "%";
                spec = spec.and((root, query, cb) -> cb.like(cb.lower(root.get("productName")), namePattern));
            }

            if (filter.getMinPrice() != null) {
                spec = spec.and((root, query, cb) -> cb.greaterThanOrEqualTo(root.get("price"), filter.getMinPrice()));
            }

            if (filter.getMaxPrice() != null) {
                spec = spec.and((root, query, cb) -> cb.lessThanOrEqualTo(root.get("price"), filter.getMaxPrice()));
            }

            if (filter.getStatus() != null && !filter.getStatus().isEmpty()) {
                Boolean statusValue = null;
                if ("ACTIVE".equalsIgnoreCase(filter.getStatus())) statusValue = true;
                else if ("INACTIVE".equalsIgnoreCase(filter.getStatus())) statusValue = false;
                if (statusValue != null) {
                    Boolean finalStatusValue = statusValue;
                    spec = spec.and((root, query, cb) -> cb.equal(root.get("status"), finalStatusValue));
                }
            }

            if (filter.getAverageRating() != null) {
                spec = spec.and((root, query, cb) -> cb.greaterThanOrEqualTo(root.get("averageRating"), filter.getAverageRating()));
            }

            if (filter.getCategory() != null && !filter.getCategory().isEmpty()) {
                try {
                    Long categoryId = Long.parseLong(filter.getCategory());
                    spec = spec.and((root, query, cb) -> cb.equal(root.join("productCategoryId").get("id"), categoryId));
                } catch (NumberFormatException e) {
                    String categoryPattern = "%" + filter.getCategory().toLowerCase() + "%";
                    spec = spec.and((root, query, cb) -> cb.like(cb.lower(root.join("productCategoryId").get("name")), categoryPattern));
                }
            }

            if (filter.getCountry() != null && !filter.getCountry().isEmpty()) {
                try {
                    Long countryId = Long.parseLong(filter.getCountry());
                    spec = spec.and((root, query, cb) -> cb.equal(root.join("productCountryId").get("id"), countryId));
                } catch (NumberFormatException e) {
                    String countryPattern = "%" + filter.getCountry().toLowerCase() + "%";
                    spec = spec.and((root, query, cb) -> cb.like(cb.lower(root.join("productCountryId").get("name")), countryPattern));
                }
            }

            // ---------------- Ejecutar consulta ----------------
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
            e.printStackTrace();
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
