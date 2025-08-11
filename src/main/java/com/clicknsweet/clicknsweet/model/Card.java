package com.clicknsweet.clicknsweet.model;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "cards")
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "card_id")
    private Integer id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "Number_Card", nullable = false, length = 255)
    private String numberCard;

    @Column(name = "Expiration_date", nullable = false, length = 7)
    private String expirationDate; // MM/YY o MM/YYYY según tu front

    @Column(name = "CVV", nullable = false, length = 4)
    private String cvv;

    public Card() {}

    public Card(Integer id, User user, String numberCard, String expirationDate, String cvv) {
        this.id = id;
        this.user = user;
        this.numberCard = numberCard;
        this.expirationDate = expirationDate;
        this.cvv = cvv;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public String getNumberCard() { return numberCard; }
    public void setNumberCard(String numberCard) { this.numberCard = numberCard; }

    public String getExpirationDate() { return expirationDate; }
    public void setExpirationDate(String expirationDate) { this.expirationDate = expirationDate; }

    public String getCvv() { return cvv; }
    public void setCvv(String cvv) { this.cvv = cvv; }

    @Override public boolean equals(Object o){ if(!(o instanceof Card c)) return false; return Objects.equals(id,c.id);}
    @Override public int hashCode(){ return Objects.hash(id); }
}

