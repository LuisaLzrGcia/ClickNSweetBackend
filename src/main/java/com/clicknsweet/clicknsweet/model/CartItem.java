package com.clicknsweet.clicknsweet.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "cart_items",
        uniqueConstraints = @UniqueConstraint(columnNames = {"Cart_ID","Product_ID"}))
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Cart_Items_ID")
    private Integer id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "Cart_ID", nullable = false)
    private Cart cart;

    @ManyToOne(optional = false)
    @JoinColumn(name = "Product_ID", nullable = false)
    private Product product;

    @Column(name = "Quantity", nullable = false)
    private Integer quantity = 1;

    @Column(name = "Created_at", insertable = false, updatable = false)
    private LocalDateTime createdAt;

    public CartItem() {}

    public CartItem(Integer id, Cart cart, Product product, Integer quantity) {
        this.id = id;
        this.cart = cart;
        this.product = product;
        this.quantity = quantity;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Cart getCart() { return cart; }
    public void setCart(Cart cart) { this.cart = cart; }

    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }

    public LocalDateTime getCreatedAt() { return createdAt; }

    @Override public boolean equals(Object o){ if(!(o instanceof CartItem ci)) return false; return Objects.equals(id,ci.id);}
    @Override public int hashCode(){ return Objects.hash(id); }
}

