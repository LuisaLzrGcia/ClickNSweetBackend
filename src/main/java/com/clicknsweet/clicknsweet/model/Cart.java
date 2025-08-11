package com.clicknsweet.clicknsweet.model;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "carts")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Cart_ID")
    private Long id;

    // En BD User_ID es UNIQUE: un carrito por usuario
    @OneToOne(optional = false)
    @JoinColumn(name = "User_ID", nullable = false, unique = true)
    private User user;

    public Cart() {}

    public Cart(Long id, User user) {
        this.id = id;
        this.user = user;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    @Override public boolean equals(Object o){ if(!(o instanceof Cart c)) return false; return Objects.equals(id,c.id);}
    @Override public int hashCode(){ return Objects.hash(id); }
}

