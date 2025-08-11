package com.clicknsweet.clicknsweet.model;

import jakarta.persistence.*;
import org.antlr.v4.runtime.misc.NotNull;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Table (name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    @Column(name = "product_name", nullable = false, length = 150)
    private String productName;

    @Column(name = "sku", length = 100, unique = true)
    private String sku;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "picture", length = 255)
    private String picture;

    @Column(name = "price", nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name = "sales_format_id")
    private Integer salesFormatId;

    @Column(name = "supplier_cost", precision = 10, scale = 2)
    private BigDecimal supplierCost;

    @Column(name = "quantity_stock", nullable = false)
    private Integer quantityStock = 0;

    @Column(name = "weight", nullable = false, precision = 10, scale = 2)
    private BigDecimal weight = BigDecimal.ZERO;

    @Column(name = "length", nullable = false, precision = 10, scale = 2)
    private BigDecimal length = BigDecimal.ZERO;

    @Column(name = "width", nullable = false, precision = 10, scale = 2)
    private BigDecimal width = BigDecimal.ZERO;

    @Column(name = "height", nullable = false, precision = 10, scale = 2)
    private BigDecimal height = BigDecimal.ZERO;

    @Column(name = "status", nullable = false)
    private Boolean status = true;

    @Column(name = "low_stock_threshold", nullable = false)
    private Integer lowStockThreshold = 10;

    @Column(name = "allow_backorders", nullable = false)
    private Boolean allowBackorders = false;

    @Column(name = "average_rating", precision = 3, scale = 2)
    private BigDecimal averageRating = BigDecimal.ZERO;

    @Column(name = "discount_type", length = 50)
    private String discountType;

    @Column(name = "discount_value", precision = 10, scale = 2)
    private BigDecimal discountValue;

    @Column(name = "category_id")
    private Integer categoryId;

    @Column(name = "origin_country_id")
    private Integer originCountryId;

    @Column(name = "origin_state_id")
    private Integer originStateId;

    @Column(name = "created_at", updatable = false, insertable = false)
    private Timestamp createdAt;

    @Column(name = "updated_at", insertable = false, updatable = false)
    private Timestamp updatedAt;

    public Product() {
    }

    public Product(Long id, String productName, String sku, String description, String picture, BigDecimal price, Integer salesFormatId, BigDecimal supplierCost, Integer quantityStock, BigDecimal weight, BigDecimal length, BigDecimal width, BigDecimal height, Boolean status, Integer lowStockThreshold, Boolean allowBackorders, BigDecimal averageRating, String discountType, BigDecimal discountValue, Integer categoryId, Integer originCountryId, Integer originStateId, Timestamp createdAt, Timestamp updatedAt) {
        this.id = id;
        this.productName = productName;
        this.sku = sku;
        this.description = description;
        this.picture = picture;
        this.price = price;
        this.salesFormatId = salesFormatId;
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
        this.categoryId = categoryId;
        this.originCountryId = originCountryId;
        this.originStateId = originStateId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getSalesFormatId() {
        return salesFormatId;
    }

    public void setSalesFormatId(Integer salesFormatId) {
        this.salesFormatId = salesFormatId;
    }

    public BigDecimal getSupplierCost() {
        return supplierCost;
    }

    public void setSupplierCost(BigDecimal supplierCost) {
        this.supplierCost = supplierCost;
    }

    public Integer getQuantityStock() {
        return quantityStock;
    }

    public void setQuantityStock(Integer quantityStock) {
        this.quantityStock = quantityStock;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public BigDecimal getLength() {
        return length;
    }

    public void setLength(BigDecimal length) {
        this.length = length;
    }

    public BigDecimal getWidth() {
        return width;
    }

    public void setWidth(BigDecimal width) {
        this.width = width;
    }

    public BigDecimal getHeight() {
        return height;
    }

    public void setHeight(BigDecimal height) {
        this.height = height;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Integer getLowStockThreshold() {
        return lowStockThreshold;
    }

    public void setLowStockThreshold(Integer lowStockThreshold) {
        this.lowStockThreshold = lowStockThreshold;
    }

    public Boolean getAllowBackorders() {
        return allowBackorders;
    }

    public void setAllowBackorders(Boolean allowBackorders) {
        this.allowBackorders = allowBackorders;
    }

    public BigDecimal getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(BigDecimal averageRating) {
        this.averageRating = averageRating;
    }

    public String getDiscountType() {
        return discountType;
    }

    public void setDiscountType(String discountType) {
        this.discountType = discountType;
    }

    public BigDecimal getDiscountValue() {
        return discountValue;
    }

    public void setDiscountValue(BigDecimal discountValue) {
        this.discountValue = discountValue;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getOriginCountryId() {
        return originCountryId;
    }

    public void setOriginCountryId(Integer originCountryId) {
        this.originCountryId = originCountryId;
    }

    public Integer getOriginStateId() {
        return originStateId;
    }

    public void setOriginStateId(Integer originStateId) {
        this.originStateId = originStateId;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
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
                ", salesFormatId=" + salesFormatId +
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
                ", categoryId=" + categoryId +
                ", originCountryId=" + originCountryId +
                ", originStateId=" + originStateId +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }

    // Relacion de product a category N:1
    @ManyToOne
    @JoinColumn(name = "product_category_id") // columna FK en la tabla products
    private Category category;

    public Category getCategory(){
        return category;
    }
}
