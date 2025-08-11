package com.clicknsweet.clicknsweet.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Table (name = "products")
public class Product {
    @Setter
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    @Getter
    @Setter
    @Column(name = "product_name", nullable = false, length = 150)
    private String productName;

    @Setter
    @Getter
    @Column(name = "sku", length = 100, unique = true)
    private String sku;

    @Setter
    @Getter
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Setter
    @Getter
    @Column(name = "picture", length = 255)
    private String picture;

    @Setter
    @Getter
    @Column(name = "price", nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Setter
    @Getter
    @ManyToOne
    @JoinColumn(name = "product_sales_format_id")
    private SalesFormat productSalesFormatId;

    @Setter
    @Getter
    @Column(name = "supplier_cost", precision = 10, scale = 2)
    private BigDecimal supplierCost;

    @Setter
    @Getter
    @Column(name = "quantity_stock", nullable = false)
    private Integer quantityStock = 0;

    @Setter
    @Getter
    @Column(name = "weight", nullable = false, precision = 10, scale = 2)
    private BigDecimal weight = BigDecimal.ZERO;

    @Setter
    @Getter
    @Column(name = "length", nullable = false, precision = 10, scale = 2)
    private BigDecimal length = BigDecimal.ZERO;

    @Setter
    @Getter
    @Column(name = "width", nullable = false, precision = 10, scale = 2)
    private BigDecimal width = BigDecimal.ZERO;

    @Setter
    @Getter
    @Column(name = "height", nullable = false, precision = 10, scale = 2)
    private BigDecimal height = BigDecimal.ZERO;

    @Setter
    @Getter
    @Column(name = "status", nullable = false)
    private Boolean status = true;

    @Setter
    @Getter
    @Column(name = "low_stock_threshold", nullable = false)
    private Integer lowStockThreshold = 10;

    @Setter
    @Getter
    @Column(name = "allow_backorders", nullable = false)
    private Boolean allowBackorders = false;

    @Setter
    @Getter
    @Column(name = "average_rating", precision = 3, scale = 2)
    private BigDecimal averageRating = BigDecimal.ZERO;

    @Setter
    @Getter
    @Column(name = "discount_type", length = 50)
    private String discountType;

    @Setter
    @Getter
    @Column(name = "discount_value", precision = 10, scale = 2)
    private BigDecimal discountValue;

    @Setter
    @Getter
    @ManyToOne
    @JoinColumn(name = "product_country_id")
    private Country productCountryId;

    @Setter
    @Getter
    @ManyToOne
    @JoinColumn(name = "product_state_id")
    private State productStateId;

    @Setter
    @Getter
    @Column(name = "created_at", updatable = false, insertable = false)
    private Timestamp createdAt;

    @Setter
    @Getter
    @Column(name = "updated_at", insertable = false, updatable = false)
    private Timestamp updatedAt;

    // Relacion de product a category N:1
    @Setter
    @Getter
    @ManyToOne
    @JoinColumn(name = "product_category_id")
    @JsonBackReference
    private Category productCategoryId;

    public Product() {
    }

    public Product(Long id, String productName, String sku, String description, String picture, BigDecimal price, SalesFormat productSalesFormatId, BigDecimal supplierCost, Integer quantityStock, BigDecimal weight, BigDecimal length, BigDecimal width, BigDecimal height, Boolean status, Integer lowStockThreshold, Boolean allowBackorders, BigDecimal averageRating, String discountType, BigDecimal discountValue, Country productCountryId, State productStateId, Category productCategoryId) {
        this.id = id;
        this.productName = productName;
        this.sku = sku;
        this.description = description;
        this.picture = picture;
        this.price = price;
        this.productSalesFormatId = productSalesFormatId;
        this.supplierCost = supplierCost;
        this.quantityStock = quantityStock;
        this.weight = weight;
        this.length = length;
        this.width = width;
        this.height = height;
        this.status = status;
        this.lowStockThreshold = lowStockThreshold;
        this.allowBackorders = allowBackorders;
        this.averageRating = averageRating;
        this.discountType = discountType;
        this.discountValue = discountValue;
        this.productCountryId = productCountryId;
        this.productStateId = productStateId;
        this.productCategoryId = productCategoryId;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", productName='" + productName + '\'' +
                ", sku='" + sku + '\'' +
                ", description='" + description + '\'' +
                ", picture='" + picture + '\'' +
                ", price=" + price +
                ", productSalesFormatId=" + productSalesFormatId +
                ", supplierCost=" + supplierCost +
                ", quantityStock=" + quantityStock +
                ", weight=" + weight +
                ", length=" + length +
                ", width=" + width +
                ", height=" + height +
                ", status=" + status +
                ", lowStockThreshold=" + lowStockThreshold +
                ", allowBackorders=" + allowBackorders +
                ", averageRating=" + averageRating +
                ", discountType='" + discountType + '\'' +
                ", discountValue=" + discountValue +
                ", productCountryId=" + productCountryId +
                ", productStateId=" + productStateId +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", productCategoryId=" + productCategoryId +
                '}';
    }

}
